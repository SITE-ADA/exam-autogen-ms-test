package az.edu.ada.mstest.service.impl;

import az.edu.ada.mstest.model.entities.Test;
import az.edu.ada.mstest.repository.TestRepository;
import az.edu.ada.mstest.service.TestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    @Autowired
    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public List<Test> findAllTests() {
        return testRepository.findAll();
    }

    @Override
    public Test findTestById(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Test not found"));
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
