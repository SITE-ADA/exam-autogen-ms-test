package az.edu.ada.mstest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private String text;
    private String notes;
    private Double defaultScore;
    private String questionType;
    private Long questionPoolId;
    private Long correctAnswerId;
    private Set<String> tags;
}
