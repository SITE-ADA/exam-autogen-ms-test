package az.edu.ada.mstest.repository;

import az.edu.ada.mstest.model.entities.BucketQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketQuestionRepository extends JpaRepository<BucketQuestion, Long> {
}