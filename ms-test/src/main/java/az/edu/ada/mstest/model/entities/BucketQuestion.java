package az.edu.ada.mstest.model.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Valid
@Builder
@Table(name = "bucket_question")
public class BucketQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long questionId;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "question_bucket_id")
    private QuestionBucket questionBucket;
}

