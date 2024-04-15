package az.edu.ada.mstest.repository;

import az.edu.ada.mstest.model.entities.QuestionBooklet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionBookletRepository extends JpaRepository<QuestionBooklet, Long> {
}
