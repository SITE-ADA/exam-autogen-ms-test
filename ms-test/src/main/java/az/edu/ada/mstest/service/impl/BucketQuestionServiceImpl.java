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

    @Autowired
    public BucketQuestionServiceImpl(BucketQuestionRepository bucketQuestionRepository) {
        this.bucketQuestionRepository = bucketQuestionRepository;
    }

    @Override
    public List<BucketQuestion> findAll() {
        return bucketQuestionRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        bucketQuestionRepository.deleteById(id);
    }
}
