package az.edu.ada.mstest.repository;

import az.edu.ada.mstest.model.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
