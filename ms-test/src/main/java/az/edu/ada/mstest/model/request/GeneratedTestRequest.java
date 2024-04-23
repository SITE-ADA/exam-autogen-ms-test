package az.edu.ada.mstest.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class GeneratedTestRequest {
    @NotNull
    private Long testId;
    @NotNull
    private Long nbVariants;
    @NotNull
    private Long nbExaminees;
    @NotNull
    private String name;
    @NotNull
    private String date;
}
