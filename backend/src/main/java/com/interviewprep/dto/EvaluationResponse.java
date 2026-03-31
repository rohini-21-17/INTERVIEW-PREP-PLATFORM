package com.interviewprep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationResponse {
    private Long answerId;
    private int score;
    private String feedback;
    private String questionText;
    private String topicName;
    private LocalDateTime submittedAt;
}