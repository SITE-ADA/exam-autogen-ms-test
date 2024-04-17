package az.edu.ada.mstest.service;

import az.edu.ada.mstest.model.entities.QuestionBucket;
import az.edu.ada.mstest.model.request.QuestionBucketRequest;

import java.util.List;

public interface QuestionBucketService {
    List<QuestionBucket> findByQuestionBucketId(Long testId);
    QuestionBucket findQuestionBucketById(Long id);
    QuestionBucket saveQuestionBucket(QuestionBucketRequest questionBucketRequest);
    void deleteQuestionBucket(Long id);
}

