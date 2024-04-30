package az.edu.ada.mstest.service.impl;

import az.edu.ada.mstest.client.MsQuestionClient;
import az.edu.ada.mstest.model.dto.SubjectDTO;
import az.edu.ada.mstest.model.dto.TestDTO;
import az.edu.ada.mstest.model.entities.Test;
import az.edu.ada.mstest.repository.TestRepository;
import az.edu.ada.mstest.service.TestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final MsQuestionClient subjectClient;

    @Autowired
    public TestServiceImpl(TestRepository testRepository,
                           MsQuestionClient subjectClient) {
        this.testRepository = testRepository;
        this.subjectClient = subjectClient;
    }

    @Override
    public List<Test> findAllTests() {
        return testRepository.findAll();
    }

    @Override
    public List<TestDTO> findAll(){
        List<Test> tests = testRepository.findAll();
        List<TestDTO> testDTOS = new ArrayList<>();
        for(Test test: tests){
            SubjectDTO subjectDTO = subjectClient.getSubjectById(test.getSubjectId());
            var testDTO = TestDTO.builder()
                    .id(test.getId())
                    .name(test.getName())
                    .notes(test.getNotes())
                    .instructions(test.getInstructions())
                    .subject(subjectDTO)
                    .maximumPoints(test.getMaximumPoints())
                    .build();

            testDTOS.add(testDTO);
        }

        return testDTOS;
    }

    @Override
    public TestDTO findTestById(Long id) {
        Test test = testRepository.findById(id).get();
        SubjectDTO subjectDTO = subjectClient.getSubjectById(test.getSubjectId());

        return TestDTO.builder()
                .name(test.getName())
                .notes(test.getNotes())
                .instructions(test.getInstructions())
                .subject(subjectDTO)
                .maximumPoints(test.getMaximumPoints())
                .build();
    }

    @Override
    public Test saveTest(Test test) {
        return testRepository.save(test);
    }

    @Override
    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }
}
