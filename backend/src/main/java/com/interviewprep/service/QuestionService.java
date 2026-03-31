package com.interviewprep.service;

import com.interviewprep.dto.GenerateQuestionRequest;
import com.interviewprep.dto.QuestionResponse;
import com.interviewprep.model.Question;
import com.interviewprep.model.Topic;
import com.interviewprep.repository.QuestionRepository;
import com.interviewprep.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;
    private final QuestionBankService questionBankService; // replaces OpenAIService

    // ──────────────────────────────────────────────────────────────────────────
    // "Generate" questions — pulls from the built-in question bank and saves
    // any that aren't already stored for this topic+difficulty combination.
    // Returns a random shuffled subset of exactly `count` questions.
    // ──────────────────────────────────────────────────────────────────────────
    @Transactional
    public List<QuestionResponse> generateAndSaveQuestions(GenerateQuestionRequest request) {
        Topic topic = getOrCreateTopic(request.getTopic());

        Question.Difficulty difficulty;
        try {
            difficulty = Question.Difficulty.valueOf(request.getDifficulty().toUpperCase());
        } catch (IllegalArgumentException e) {
            difficulty = Question.Difficulty.MEDIUM;
        }
        final Question.Difficulty finalDifficulty = difficulty;

        // Pull questions from the bank
        List<String> bankQuestions = questionBankService.getQuestions(
                request.getTopic(), request.getDifficulty(), 50); // fetch up to 50, we'll filter

        // Save any that don't already exist for this topic+difficulty
        List<Question> existing = questionRepository.findByTopicAndDifficulty(topic, finalDifficulty);
        List<String> existingTexts = existing.stream()
                .map(Question::getQuestionText)
                .collect(Collectors.toList());

        for (String qText : bankQuestions) {
            if (!existingTexts.contains(qText)) {
                questionRepository.save(Question.builder()
                        .topic(topic)
                        .questionText(qText)
                        .difficulty(finalDifficulty)
                        .build());
            }
        }

        // Reload and return a random subset of requested count
        List<Question> all = questionRepository.findByTopicAndDifficulty(topic, finalDifficulty);
        Collections.shuffle(all);
        int count = Math.min(request.getCount(), all.size());
        return all.subList(0, count).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Fetch saved questions for a topic
    // ──────────────────────────────────────────────────────────────────────────
    public List<QuestionResponse> getQuestionsByTopic(String topicName) {
        return questionRepository.findByTopicName(topicName)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<QuestionResponse> getQuestionsByTopicAndDifficulty(String topicName, String difficulty) {
        Question.Difficulty diff = Question.Difficulty.valueOf(difficulty.toUpperCase());
        return questionRepository.findByTopicNameAndDifficulty(topicName, diff)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<String> getAllTopicNames() {
        return topicRepository.findAll()
                .stream().map(Topic::getName).collect(Collectors.toList());
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Helpers
    // ──────────────────────────────────────────────────────────────────────────
    private Topic getOrCreateTopic(String topicName) {
        return topicRepository.findByNameIgnoreCase(topicName)
                .orElseGet(() -> topicRepository.save(
                        Topic.builder()
                                .name(topicName)
                                .description("Questions about " + topicName)
                                .build()));
    }

    private QuestionResponse toResponse(Question q) {
        return QuestionResponse.builder()
                .id(q.getId())
                .questionText(q.getQuestionText())
                .topicName(q.getTopic().getName())
                .difficulty(q.getDifficulty().name())
                .build();
    }
}