package az.edu.ada.mstest.service;

import az.edu.ada.mstest.model.entities.GeneratedTest;
import az.edu.ada.mstest.model.entities.Test;

import java.util.List;

public interface GeneratedTestService {
    List<GeneratedTest> findAllGeneratedTests();
    GeneratedTest findGeneratedTestById(Long id);
    GeneratedTest saveGeneratedTest(GeneratedTest generatedTest);
    void deleteGeneratedTest(Long id);
}
