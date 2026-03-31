package com.interviewprep.controller;

import com.interviewprep.dto.GenerateQuestionRequest;
import com.interviewprep.dto.QuestionResponse;
import com.interviewprep.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Tag(name = "Questions", description = "AI-powered question generation and retrieval")
@SecurityRequirement(name = "bearerAuth")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/generate")
    @Operation(summary = "Generate AI interview questions for a topic")
    public ResponseEntity<List<QuestionResponse>> generateQuestions(
            @Valid @RequestBody GenerateQuestionRequest request) {
        List<QuestionResponse> questions = questionService.generateAndSaveQuestions(request);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/topic/{topicName}")
    @Operation(summary = "Get saved questions for a topic")
    public ResponseEntity<List<QuestionResponse>> getByTopic(@PathVariable String topicName) {
        List<QuestionResponse> questions = questionService.getQuestionsByTopic(topicName);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/topic/{topicName}/difficulty/{difficulty}")
    @Operation(summary = "Get questions filtered by topic and difficulty")
    public ResponseEntity<List<QuestionResponse>> getByTopicAndDifficulty(
            @PathVariable String topicName,
            @PathVariable String difficulty) {
        List<QuestionResponse> questions = questionService.getQuestionsByTopicAndDifficulty(topicName, difficulty);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/topics")
    @Operation(summary = "Get all available topics")
    public ResponseEntity<List<String>> getAllTopics() {
        return ResponseEntity.ok(questionService.getAllTopicNames());
    }
}