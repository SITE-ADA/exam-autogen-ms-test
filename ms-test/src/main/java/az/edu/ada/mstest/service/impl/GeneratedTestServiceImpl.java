package az.edu.ada.mstest.service.impl;

import az.edu.ada.mstest.client.QuestionClient;
import az.edu.ada.mstest.model.dto.QuestionDTO;
import az.edu.ada.mstest.model.entities.*;
import az.edu.ada.mstest.model.request.GeneratedTestRequest;
import az.edu.ada.mstest.repository.*;
import az.edu.ada.mstest.service.GeneratedTestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GeneratedTestServiceImpl implements GeneratedTestService {
    private final GeneratedTestRepository generatedTestRepository;
    private final QuestionBookletRepository questionBookletRepository;
    private final QuestionBucketRepository questionBucketRepository;
    private final BucketQuestionRepository bucketQuestionRepository;
    private final QuestionClient questionClient;
    private final TestRepository testRepository;

    @Autowired
    public GeneratedTestServiceImpl(GeneratedTestRepository generatedTestRepository,
                                    QuestionBookletRepository questionBookletRepository,
                                    QuestionBucketRepository questionBucketRepository,
                                    BucketQuestionRepository bucketQuestionRepository,
                                    QuestionClient questionClient, TestRepository testRepository) {
        this.generatedTestRepository = generatedTestRepository;
        this.questionBookletRepository = questionBookletRepository;
        this.questionBucketRepository = questionBucketRepository;
        this.bucketQuestionRepository = bucketQuestionRepository;
        this.questionClient = questionClient;
        this.testRepository = testRepository;
    }

    @Override
    public List<GeneratedTest> findAllGeneratedTests() {
        return generatedTestRepository.findAll();
    }

    @Override
    public GeneratedTest findGeneratedTestById(Long id) {
        return generatedTestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GeneratedTest not found"));
    }

    @Override
    public GeneratedTest saveGeneratedTest(GeneratedTestRequest generatedTestRequest) {

        var generatedTest = GeneratedTest.builder()
                .test(testRepository.findById(generatedTestRequest.getTestId()).get())
                .nbVariants(generatedTestRequest.getNbVariants())
                .nbExaminees(generatedTestRequest.getNbExaminees())
                .name(generatedTestRequest.getName())
                .build();

        generatedTest = generatedTestRepository.save(generatedTest);

        List<QuestionBooklet> questionBooklets = new ArrayList<>();
        GeneratedTest finalGeneratedTest = generatedTest;
        IntStream.rangeClosed(1, Math.toIntExact(generatedTestRequest.getNbVariants())).forEach(variantNumber -> {
            QuestionBooklet booklet = QuestionBooklet.builder()
                    .generatedTest(finalGeneratedTest)
                    .variantName(finalGeneratedTest.getName() + " " + variantNumber)
                    .questionsJson("[]")
                    .date(generatedTestRequest.getDate())
                    .build();
            questionBooklets.add(questionBookletRepository.save(booklet));
        });

        List<QuestionBucket> questionBuckets = questionBucketRepository.findByTestId(generatedTest.getTest().getId());
        List<BucketQuestion> bucketQuestions = new ArrayList<>();
        for(QuestionBucket questionBucket : questionBuckets) {
            bucketQuestions = bucketQuestionRepository.findAllByQuestionBucketId(questionBucket.getId());
        }

        List<QuestionDTO> questions = new ArrayList<>();
        for(BucketQuestion bucketQuestion : bucketQuestions) {
            questions.add(questionClient.getQuestionById(bucketQuestion.getQuestionId()));
        }

        for(QuestionBooklet questionBooklet : questionBooklets) {
            List<QuestionDTO> bookletQuestions = new ArrayList<>();
            for(QuestionBucket questionBucket : questionBuckets) {
                Integer selectQuestionsNum = questionBucket.getNbSelectedQuestions();

                Collections.shuffle(questions);

                List<QuestionDTO> selectedQuestions = questions.stream().limit(selectQuestionsNum).toList();
                bookletQuestions.addAll(selectedQuestions);
            }

            Gson gson = new Gson();
            String jsonString = gson.toJson(bookletQuestions);
            questionBooklet.setQuestionsJson(jsonString);
            questionBookletRepository.save(questionBooklet);
        }

        return generatedTestRepository.save(generatedTest);
    }

    @Override
    public void deleteGeneratedTest(Long id) {
        generatedTestRepository.deleteById(id);
    }

}
