package az.edu.ada.mstest.service;

import az.edu.ada.mstest.model.entities.GeneratedTest;
import az.edu.ada.mstest.model.entities.Test;
import az.edu.ada.mstest.model.request.GeneratedTestRequest;

import java.util.List;

public interface GeneratedTestService {
    List<GeneratedTest> findAllGeneratedTests();
    GeneratedTest findGeneratedTestById(Long id);
    GeneratedTest saveGeneratedTest(GeneratedTestRequest generatedTestRequest);
    void deleteGeneratedTest(Long id);
}
