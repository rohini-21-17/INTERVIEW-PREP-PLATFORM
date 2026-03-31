package com.interviewprep.controller;

import com.interviewprep.dto.AnswerSubmitRequest;
import com.interviewprep.dto.EvaluationResponse;
import com.interviewprep.model.User;
import com.interviewprep.service.EvaluationService;
import com.interviewprep.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
@Tag(name = "Answers", description = "Submit answers and get AI evaluation")
@SecurityRequirement(name = "bearerAuth")
public class AnswerController {

    private final EvaluationService evaluationService;
    private final UserService userService;

    @PostMapping("/submit")
    @Operation(summary = "Submit an answer and receive AI evaluation with score + feedback")
    public ResponseEntity<EvaluationResponse> submitAnswer(
            @Valid @RequestBody AnswerSubmitRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        EvaluationResponse response = evaluationService.submitAndEvaluate(request, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    @Operation(summary = "Get all answers submitted by the logged-in user")
    public ResponseEntity<List<EvaluationResponse>> getHistory(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        List<EvaluationResponse> history = evaluationService.getUserAnswerHistory(user);
        return ResponseEntity.ok(history);
    }
}