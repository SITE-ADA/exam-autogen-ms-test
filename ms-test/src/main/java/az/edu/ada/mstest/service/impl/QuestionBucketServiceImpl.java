package az.edu.ada.mstest.service.impl;

import az.edu.ada.mstest.client.QuestionClient;
import az.edu.ada.mstest.model.entities.BucketQuestion;
import az.edu.ada.mstest.model.entities.QuestionBucket;
import az.edu.ada.mstest.model.request.QuestionBucketRequest;
import az.edu.ada.mstest.repository.BucketQuestionRepository;
import az.edu.ada.mstest.repository.QuestionBucketRepository;
import az.edu.ada.mstest.repository.TestRepository;
import az.edu.ada.mstest.service.QuestionBucketService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class QuestionBucketServiceImpl implements QuestionBucketService {
    private final QuestionBucketRepository questionBucketRepository;
    private final BucketQuestionRepository bucketQuestionRepository;
    private final QuestionClient questionClient;
    private final TestRepository testRepository;

    @Autowired
    public QuestionBucketServiceImpl(QuestionBucketRepository questionBucketRepository, BucketQuestionRepository bucketQuestionRepository,
                                     TestRepository testRepository, QuestionClient questionClient) {
        this.questionBucketRepository = questionBucketRepository;
        this.bucketQuestionRepository = bucketQuestionRepository;
        this.testRepository = testRepository;
        this.questionClient = questionClient;
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
                .noTotalQuestions(questionBucketRequest.getQuestionIds().size())
                .nbSelectedQuestions(questionBucketRequest.getNbSelectedQuestions())
                .build();

        questionBucket = questionBucketRepository.save(questionBucket);

        QuestionBucket finalQuestionBucket = questionBucket;
        AtomicReference<Double> maxPoints = new AtomicReference<>(0.0);
        questionBucketRequest.getQuestionIds().forEach(questionId -> {
            BucketQuestion bucketQuestion = BucketQuestion.builder()
                    .questionId(questionId)
                    .questionBucket(finalQuestionBucket)
                    .build();
            bucketQuestionRepository.save(bucketQuestion);
            maxPoints.updateAndGet(v -> v + questionClient.getQuestionById(questionId).getDefaultScore());
        });

        questionBucket.setMaximumPoints(maxPoints.get());
        questionBucketRepository.save(questionBucket);

        return questionBucket;
    }

    @Override
    public QuestionBucket patchQuestionBucket(Long id, Map<String, Object> updates) {
        Optional<QuestionBucket> optionalQuestionBucket = questionBucketRepository.findById(id);
        if (!optionalQuestionBucket.isPresent()) {
            return null;
        }

        QuestionBucket questionBucket = optionalQuestionBucket.get();
        applyPatchToQuestionBucket(questionBucket, updates);
        questionBucketRepository.save(questionBucket);
        return questionBucket;
    }

    private void applyPatchToQuestionBucket(QuestionBucket questionBucket, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            try {
                Field field = questionBucket.getClass().getDeclaredField(key);
                field.setAccessible(true);
                if (key.equals("questionIds")) {
                    List<Long> existingQuestionIds = (List<Long>) field.get(questionBucket);
                    existingQuestionIds.addAll((List<Long>) value);
                    addBucketQuestions(questionBucket, (List<Long>) value);
                    updateMaximumPoints(questionBucket, existingQuestionIds);
                } else {
                    field.set(questionBucket, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                handleException(e);
            }
        });
    }

    private void addBucketQuestions(QuestionBucket questionBucket, List<Long> newQuestionIds) {
        newQuestionIds.forEach(questionId -> {
            BucketQuestion bucketQuestion = BucketQuestion.builder()
                    .questionId(questionId)
                    .questionBucket(questionBucket)
                    .build();
            bucketQuestionRepository.save(bucketQuestion);
        });
    }

    private void updateMaximumPoints(QuestionBucket questionBucket, List<Long> questionIds) {
        System.out.println(questionIds);
        AtomicReference<Double> maxPoints = new AtomicReference<>(0.0);
        questionIds.forEach(questionId -> {
            maxPoints.updateAndGet(v -> v + questionClient.getQuestionById(questionId).getDefaultScore());
        });
        questionBucket.setMaximumPoints(maxPoints.get());
    }

    private void handleException(Exception e) {
        // Handle the exception, possibly logging a warning or throwing a custom exception
    }

    @Override
    public void deleteQuestionBucket(Long id) {
        questionBucketRepository.deleteById(id);
    }

}
