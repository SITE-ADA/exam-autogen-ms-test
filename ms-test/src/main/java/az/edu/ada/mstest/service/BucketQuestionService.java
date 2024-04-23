package az.edu.ada.mstest.service;

import az.edu.ada.mstest.model.entities.BucketQuestion;

import java.util.List;

public interface BucketQuestionService {
    List<BucketQuestion> findAll();
    BucketQuestion save(BucketQuestionRequest bucketQuestionRequest);
    void delete(Long id);
}