package az.edu.ada.mstest.service;

import az.edu.ada.mstest.model.dto.QuestionDTO;
import az.edu.ada.mstest.model.entities.BucketQuestion;
import az.edu.ada.mstest.model.entities.BucketQuestionId;

import java.util.List;
import java.util.Optional;

public interface BucketQuestionService {
    List<BucketQuestion> findAllByQuestionBucketId(Long questionBucketId);
    BucketQuestion save(BucketQuestion bucketQuestion);
    void delete(BucketQuestionId id);
}