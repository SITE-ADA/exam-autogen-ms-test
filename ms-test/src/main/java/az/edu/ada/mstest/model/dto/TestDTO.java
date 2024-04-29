package az.edu.ada.mstest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestDTO {
    private String name;
    private String notes;
    private String instructions;
    private SubjectDTO subject;
    private Integer maximumPoints;
}
