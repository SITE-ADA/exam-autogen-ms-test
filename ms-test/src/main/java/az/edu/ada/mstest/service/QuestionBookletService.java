package az.edu.ada.mstest.service;

import az.edu.ada.mstest.model.entities.QuestionBooklet;

import java.util.List;

public interface QuestionBookletService {
    QuestionBooklet saveQuestionBooklet(QuestionBooklet questionBooklet);
    QuestionBooklet findById(Long id);
    List<QuestionBooklet> findAll();
    void deleteById(Long id);
}
