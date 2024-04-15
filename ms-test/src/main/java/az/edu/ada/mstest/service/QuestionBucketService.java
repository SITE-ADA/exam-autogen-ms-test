package az.edu.ada.mstest.service;

import az.edu.ada.mstest.model.entities.QuestionBucket;

import java.util.List;

public interface QuestionBucketService {
    List<QuestionBucket> findByQuestionBucketId(Long testId);
    QuestionBucket findQuestionBucketById(Long id);
    QuestionBucket saveQuestionBucket(QuestionBucket questionBucket);
    void deleteQuestionBucket(Long id);
}

