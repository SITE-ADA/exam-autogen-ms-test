package az.edu.ada.mstest.service.impl;

import az.edu.ada.mstest.model.entities.GeneratedTest;
import az.edu.ada.mstest.repository.GeneratedTestRepository;
import az.edu.ada.mstest.service.GeneratedTestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratedTestServiceImpl implements GeneratedTestService {
    private final GeneratedTestRepository generatedTestRepository;

    @Autowired
    public GeneratedTestServiceImpl(GeneratedTestRepository generatedTestRepository) {
        this.generatedTestRepository = generatedTestRepository;
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
    public GeneratedTest saveGeneratedTest(GeneratedTest generatedTest) {
        return generatedTestRepository.save(generatedTest);
    }

    @Override
    public void deleteGeneratedTest(Long id) {
        generatedTestRepository.deleteById(id);
    }

}
