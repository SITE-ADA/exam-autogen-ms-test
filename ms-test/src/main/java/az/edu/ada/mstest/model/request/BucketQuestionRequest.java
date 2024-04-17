package az.edu.ada.mstest.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class BucketQuestionRequest {
    @NotNull
    private Long questionId;
    @NotNull
    private Long questionBucketId;
}
