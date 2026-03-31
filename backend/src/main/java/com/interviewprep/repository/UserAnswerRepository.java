package com.interviewprep.repository;

import com.interviewprep.model.User;
import com.interviewprep.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    List<UserAnswer> findByUserOrderBySubmittedAtDesc(User user);

    @Query("SELECT ua FROM UserAnswer ua WHERE ua.user.id = :userId ORDER BY ua.submittedAt DESC")
    List<UserAnswer> findByUserId(@Param("userId") Long userId);

    @Query("SELECT AVG(ua.score) FROM UserAnswer ua WHERE ua.user.id = :userId")
    Double findAverageScoreByUserId(@Param("userId") Long userId);

    @Query("SELECT MAX(ua.score) FROM UserAnswer ua WHERE ua.user.id = :userId")
    Integer findHighestScoreByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(ua) FROM UserAnswer ua WHERE ua.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);

    @Query("SELECT ua.question.topic.name, COUNT(ua), AVG(ua.score) " +
            "FROM UserAnswer ua WHERE ua.user.id = :userId " +
            "GROUP BY ua.question.topic.name")
    List<Object[]> findTopicStatsForUser(@Param("userId") Long userId);
}