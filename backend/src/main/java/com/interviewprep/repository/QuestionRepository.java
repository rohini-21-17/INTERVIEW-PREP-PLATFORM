package com.interviewprep.repository;

import com.interviewprep.model.Question;
import com.interviewprep.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByTopic(Topic topic);

    List<Question> findByTopicAndDifficulty(Topic topic, Question.Difficulty difficulty);

    @Query("SELECT q FROM Question q WHERE q.topic.name = :topicName")
    List<Question> findByTopicName(@Param("topicName") String topicName);

    @Query("SELECT q FROM Question q WHERE q.topic.name = :topicName AND q.difficulty = :difficulty")
    List<Question> findByTopicNameAndDifficulty(
            @Param("topicName") String topicName,
            @Param("difficulty") Question.Difficulty difficulty);
}