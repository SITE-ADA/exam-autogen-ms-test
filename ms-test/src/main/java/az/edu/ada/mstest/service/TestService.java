package az.edu.ada.mstest.service;

import az.edu.ada.mstest.model.entities.Test;

import java.util.List;

public interface TestService {
    List<Test> findAllTests();
    Test findTestById(Long id);
    Test saveTest(Test test);
    void deleteTest(Long id);
}
