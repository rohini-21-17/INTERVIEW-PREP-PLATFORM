package com.interviewprep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStats {
    private int totalAttempts;
    private double averageScore;
    private int highestScore;
    private String strongestTopic;
    private String weakestTopic;
    private List<TopicStat> topicBreakdown;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopicStat {
        private String topicName;
        private int attempts;
        private double avgScore;
    }
}