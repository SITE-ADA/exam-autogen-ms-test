package az.edu.ada.mstest.repository;

import az.edu.ada.mstest.model.entities.BucketQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BucketQuestionRepository extends JpaRepository<BucketQuestion, Long> {
    List<BucketQuestion> findAllByQuestionBucketId(Long questionBucketId);
}