package az.edu.ada.mstest.repository;

import az.edu.ada.mstest.model.entities.QuestionBucket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionBucketRepository extends JpaRepository<QuestionBucket, Long> {
    List<QuestionBucket> findByTestId(Long testId);
}
