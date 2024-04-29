package az.edu.ada.mstest.model.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // Add cascade type here
    @JoinColumn(name = "gentest_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // Hibernate-specific, ensures cascade delete in DDL
    private GeneratedTest generatedTest;

    private String variantName;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String questionsJson;
    private String date;
}
