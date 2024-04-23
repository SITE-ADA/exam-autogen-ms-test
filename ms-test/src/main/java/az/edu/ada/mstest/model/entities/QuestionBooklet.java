package az.edu.ada.mstest.model.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Valid
@Builder
@Table(name = "question_booklet")
public class QuestionBooklet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gentest_id")
    private GeneratedTest generatedTest;
    private String variantName;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String questionsJson;
    private String date;
}