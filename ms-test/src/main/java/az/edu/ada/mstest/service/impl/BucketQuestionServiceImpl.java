package az.edu.ada.mstest.service.impl;

import az.edu.ada.mstest.client.QuestionClient;
import az.edu.ada.mstest.model.dto.QuestionDTO;
import az.edu.ada.mstest.model.entities.BucketQuestion;
import az.edu.ada.mstest.model.entities.BucketQuestionId;
import az.edu.ada.mstest.repository.BucketQuestionRepository;
import az.edu.ada.mstest.service.BucketQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BucketQuestionServiceImpl implements BucketQuestionService {

    private final BucketQuestionRepository bucketQuestionRepository;
    private final QuestionClient questionClient;

    @Autowired
    public BucketQuestionServiceImpl(BucketQuestionRepository bucketQuestionRepository,
                                     QuestionClient questionClient) {
        this.bucketQuestionRepository = bucketQuestionRepository;
        this.questionClient = questionClient;
    }

    @Override
    public List<BucketQuestion> findAllByQuestionBucketId(Long questionBucketId) {
        return bucketQuestionRepository.findByIdQuestionBucketId(questionBucketId);
    }

    @Override
    public BucketQuestion save(BucketQuestion bucketQuestion) {
        Long questionId = bucketQuestion.getId().getQuestionId();
        try {
            // Attempt to fetch the question from ms-questions to verify its existence
            QuestionDTO question = questionClient.getQuestionById(questionId);
            if (question == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question with ID " + questionId + " not found in ms-questions");
            }
        } catch (Exception ex) {
            // Handling Feign client exceptions or any network related exceptions
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Failed to connect to ms-questions service", ex);
        }

        // If the question exists, save the BucketQuestion
        return bucketQuestionRepository.save(bucketQuestion);
    }

    @Override
    public void delete(BucketQuestionId id) {
        bucketQuestionRepository.deleteById(id);
    }
}
