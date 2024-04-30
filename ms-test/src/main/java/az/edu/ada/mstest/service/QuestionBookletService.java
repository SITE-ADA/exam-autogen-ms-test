package az.edu.ada.mstest.service;

import az.edu.ada.mstest.model.dto.CorrectAnswerAssessDTO;
import az.edu.ada.mstest.model.entities.QuestionBooklet;

import java.io.IOException;
import java.util.List;

public interface QuestionBookletService {
    void generateBookletDocs(List<Long> bookletIds) throws IOException;
    QuestionBooklet findById(Long id);
    List<QuestionBooklet> findAll();
    void deleteById(Long id);

    List<QuestionBooklet> getQuestionBookletsByGeneratedTestId(Long testId);

    List<CorrectAnswerAssessDTO> findCorrectAnswersByBookletId(Long id);
}
