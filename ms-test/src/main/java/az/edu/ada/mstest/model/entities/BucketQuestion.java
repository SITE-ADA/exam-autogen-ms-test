package az.edu.ada.mstest.model.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
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
@Table(name = "bucket-question")
public class BucketQuestion {

    @EmbeddedId
    private BucketQuestionId id;

    @MapsId("questionBucketId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_bucket_id")
    private QuestionBucket questionBucket;

    private Long questionId;
}

