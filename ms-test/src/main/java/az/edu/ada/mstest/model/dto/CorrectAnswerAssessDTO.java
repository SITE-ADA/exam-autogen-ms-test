package az.edu.ada.mstest.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CorrectAnswerAssessDTO {
    @NotNull
    private AnswerDTO correctAnswer; // A, B, C, D
    @NotNull
    private Integer maxPoints;
}
