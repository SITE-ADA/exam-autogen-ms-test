package az.edu.ada.mstest.service;

import az.edu.ada.mstest.model.entities.BucketQuestion;

import java.util.List;

public interface BucketQuestionService {
    List<BucketQuestion> findAll();
    void delete(Long id);
}