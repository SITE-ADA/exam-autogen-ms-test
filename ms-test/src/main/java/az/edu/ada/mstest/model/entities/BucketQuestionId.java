package az.edu.ada.mstest.model.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BucketQuestionId implements Serializable {
    private Long questionBucketId;
    private Long questionId;
}
