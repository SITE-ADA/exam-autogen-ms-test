package az.edu.ada.mstest.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class QuestionBucketRequest {
    @NotNull
    private Long testId;
    @NotNull
    private Integer order;
    @NotNull
    private Integer nbSelectedQuestions;
    @NotNull
    private List<Long> questionIds;
}
