package az.edu.ada.mstest.repository;

import az.edu.ada.mstest.model.entities.GeneratedTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneratedTestRepository extends JpaRepository<GeneratedTest, Long> {
}