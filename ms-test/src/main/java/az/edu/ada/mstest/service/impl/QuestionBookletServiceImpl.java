package az.edu.ada.mstest.service.impl;
import az.edu.ada.mstest.model.entities.QuestionBooklet;
import az.edu.ada.mstest.repository.QuestionBookletRepository;
import az.edu.ada.mstest.service.QuestionBookletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionBookletServiceImpl implements QuestionBookletService {

    private final QuestionBookletRepository repository;

    @Autowired
    public QuestionBookletServiceImpl(QuestionBookletRepository repository) {
        this.repository = repository;
    }

    @Override
    public QuestionBooklet saveQuestionBooklet(QuestionBooklet questionBooklet) {
        return repository.save(questionBooklet);
    }

    @Override
    public QuestionBooklet findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("QuestionBooklet not found"));
    }

    @Override
    public List<QuestionBooklet> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
