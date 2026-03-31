package com.interviewprep.service;

import com.interviewprep.dto.AnswerSubmitRequest;
import com.interviewprep.dto.EvaluationResponse;
import com.interviewprep.model.Question;
import com.interviewprep.model.User;
import com.interviewprep.model.UserAnswer;
import com.interviewprep.repository.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationService {

        private final UserAnswerRepository userAnswerRepository;
        private final QuestionService questionService;
        private final QuestionBankService questionBankService; // replaces OpenAIService

        // ──────────────────────────────────────────────────────────────────────────
        // Submit answer → keyword-based evaluation → save to DB
        // ──────────────────────────────────────────────────────────────────────────
        @Transactional
        public EvaluationResponse submitAndEvaluate(AnswerSubmitRequest request, User user) {
                Question question = questionService.getQuestionById(request.getQuestionId());

                Map<String, Object> evaluation = questionBankService.evaluateAnswer(
                                question.getQuestionText(),
                                request.getAnswerText());

                int score = (int) evaluation.get("score");
                String feedback = (String) evaluation.get("feedback");

                UserAnswer userAnswer = UserAnswer.builder()
                                .user(user)
                                .question(question)
                                .answerText(request.getAnswerText())
                                .score(score)
                                .feedback(feedback)
                                .build();

                UserAnswer saved = userAnswerRepository.save(userAnswer);

                return EvaluationResponse.builder()
                                .answerId(saved.getId())
                                .score(score)
                                .feedback(feedback)
                                .questionText(question.getQuestionText())
                                .topicName(question.getTopic().getName())
                                .submittedAt(saved.getSubmittedAt())
                                .build();
        }

        // ──────────────────────────────────────────────────────────────────────────
        // Fetch all answers for a user
        // ──────────────────────────────────────────────────────────────────────────
        public List<EvaluationResponse> getUserAnswerHistory(User user) {
                return userAnswerRepository.findByUserOrderBySubmittedAtDesc(user)
                                .stream()
                                .map(ua -> EvaluationResponse.builder()
                                                .answerId(ua.getId())
                                                .score(ua.getScore())
                                                .feedback(ua.getFeedback())
                                                .questionText(ua.getQuestion().getQuestionText())
                                                .topicName(ua.getQuestion().getTopic().getName())
                                                .submittedAt(ua.getSubmittedAt())
                                                .build())
                                .collect(Collectors.toList());
        }
}