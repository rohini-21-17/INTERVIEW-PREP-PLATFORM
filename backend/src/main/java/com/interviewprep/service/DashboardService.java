package com.interviewprep.service;

import com.interviewprep.dto.DashboardStats;
import com.interviewprep.model.User;
import com.interviewprep.repository.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserAnswerRepository userAnswerRepository;

    public DashboardStats getStatsForUser(User user) {
        Long userId = user.getId();

        long totalAttempts = userAnswerRepository.countByUserId(userId);
        Double avgScore = userAnswerRepository.findAverageScoreByUserId(userId);
        Integer highestScore = userAnswerRepository.findHighestScoreByUserId(userId);

        List<Object[]> rawStats = userAnswerRepository.findTopicStatsForUser(userId);
        List<DashboardStats.TopicStat> topicStats = new ArrayList<>();

        for (Object[] row : rawStats) {
            String topicName = (String) row[0];
            int attempts = ((Long) row[1]).intValue();
            double avg = row[2] != null ? (Double) row[2] : 0.0;
            topicStats.add(new DashboardStats.TopicStat(topicName, attempts, Math.round(avg * 10.0) / 10.0));
        }

        String strongestTopic = topicStats.stream()
                .max(Comparator.comparingDouble(DashboardStats.TopicStat::getAvgScore))
                .map(DashboardStats.TopicStat::getTopicName)
                .orElse("N/A");

        String weakestTopic = topicStats.stream()
                .min(Comparator.comparingDouble(DashboardStats.TopicStat::getAvgScore))
                .map(DashboardStats.TopicStat::getTopicName)
                .orElse("N/A");

        return DashboardStats.builder()
                .totalAttempts((int) totalAttempts)
                .averageScore(avgScore != null ? Math.round(avgScore * 10.0) / 10.0 : 0.0)
                .highestScore(highestScore != null ? highestScore : 0)
                .strongestTopic(strongestTopic)
                .weakestTopic(weakestTopic)
                .topicBreakdown(topicStats)
                .build();
    }
}