package az.edu.ada.mstest.service.impl;

import az.edu.ada.mstest.model.entities.BucketQuestion;
import az.edu.ada.mstest.repository.BucketQuestionRepository;
import az.edu.ada.mstest.repository.QuestionBucketRepository;
import az.edu.ada.mstest.service.BucketQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BucketQuestionServiceImpl implements BucketQuestionService {

    private final BucketQuestionRepository bucketQuestionRepository;
    private final QuestionBucketRepository questionBucketRepository;

    @Autowired
    public BucketQuestionServiceImpl(BucketQuestionRepository bucketQuestionRepository,
                                     QuestionBucketRepository questionBucketRepository) {
        this.bucketQuestionRepository = bucketQuestionRepository;
        this.questionBucketRepository = questionBucketRepository;
    }

    @Override
    public List<BucketQuestion> findAll() {
        return bucketQuestionRepository.findAll();
    }

    @Override
    public BucketQuestion save(BucketQuestionRequest bucketQuestionRequest) {
        var bucketQuestion = BucketQuestion.builder()
                .questionId(bucketQuestionRequest.getQuestionId())
                .questionBucket(questionBucketRepository.findById(bucketQuestionRequest.getQuestionBucketId()).get())
                .build();

        return bucketQuestionRepository.save(bucketQuestion);
    }

    @Override
    public void delete(Long id) {
        bucketQuestionRepository.deleteById(id);
    }
}
