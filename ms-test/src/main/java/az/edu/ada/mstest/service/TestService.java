package az.edu.ada.mstest.service;

import az.edu.ada.mstest.model.dto.TestDTO;
import az.edu.ada.mstest.model.entities.Test;

import java.util.List;

public interface TestService {
    List<Test> findAllTests();
    TestDTO findTestById(Long id);
    List<TestDTO> findAll();
    Test saveTest(Test test);
    void deleteTest(Long id);
}
