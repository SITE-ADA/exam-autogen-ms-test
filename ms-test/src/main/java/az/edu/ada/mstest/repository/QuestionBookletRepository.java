package az.edu.ada.mstest.repository;

import az.edu.ada.mstest.model.entities.QuestionBooklet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionBookletRepository extends JpaRepository<QuestionBooklet, Long> {
    List<QuestionBooklet> findAllByIdIn(List<Long> bookletIds);
    List<QuestionBooklet> getQuestionBookletsByGeneratedTestId(Long testId);
}
