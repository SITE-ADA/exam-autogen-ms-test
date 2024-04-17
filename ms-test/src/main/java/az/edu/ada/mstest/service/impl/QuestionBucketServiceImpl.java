package az.edu.ada.mstest.service.impl;

import az.edu.ada.mstest.model.entities.QuestionBucket;
import az.edu.ada.mstest.model.request.QuestionBucketRequest;
import az.edu.ada.mstest.repository.QuestionBucketRepository;
import az.edu.ada.mstest.repository.TestRepository;
import az.edu.ada.mstest.service.QuestionBucketService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionBucketServiceImpl implements QuestionBucketService {
    private final QuestionBucketRepository questionBucketRepository;
    private final TestRepository testRepository;

    @Autowired
    public QuestionBucketServiceImpl(QuestionBucketRepository questionBucketRepository, TestRepository testRepository) {
        this.questionBucketRepository = questionBucketRepository;
        this.testRepository = testRepository;
    }

    @Override
    public List<QuestionBucket> findByQuestionBucketId(Long testId) {
        return questionBucketRepository.findByTestId(testId);
    }

    @Override
    public QuestionBucket findQuestionBucketById(Long id) {
        return questionBucketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("QuestionBucket not found"));
    }

    @Override
    public QuestionBucket saveQuestionBucket(QuestionBucketRequest questionBucketRequest) {
        var questionBucket = QuestionBucket.builder()
                .test(testRepository.findById(questionBucketRequest.getTestId()).get())
                .order(questionBucketRequest.getOrder())
                .noTotalQuestions(questionBucketRequest.getNoTotalQuestions())
                .nbSelectedQuestions(questionBucketRequest.getNbSelectedQuestions())
                .maximumPoints(questionBucketRequest.getMaximumPoints())
                .build();

        return questionBucketRepository.save(questionBucket);
    }

    @Override
    public void deleteQuestionBucket(Long id) {
        questionBucketRepository.deleteById(id);
    }

}
