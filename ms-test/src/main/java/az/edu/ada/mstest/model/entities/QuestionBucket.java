package az.edu.ada.mstest.model.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Valid
@Builder
@Table(name = "question_bucket")
public class QuestionBucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
    @Column(name = "qbucket_order")
    private Integer order;
    private Integer noTotalQuestions;
    private Integer nbSelectedQuestions;
    private Double maximumPoints;
}