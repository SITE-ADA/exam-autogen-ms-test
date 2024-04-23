package az.edu.ada.mstest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {
    private Long id;
    private String text;
    private String notes;
    private Double defaultScore;
    private Long questionTypeId;
    private Long questionPoolId;
    private Set<Long> tagsIds;
    private List<AnswerDTO> answers;
    private CorrectAnswerDTO correctAnswer;
}