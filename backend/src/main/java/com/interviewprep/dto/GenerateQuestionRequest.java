package com.interviewprep.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenerateQuestionRequest {
    @NotBlank(message = "Topic is required")
    private String topic;

    private String difficulty = "MEDIUM";

    @Min(1)
    @Max(10)
    private int count = 5;
}