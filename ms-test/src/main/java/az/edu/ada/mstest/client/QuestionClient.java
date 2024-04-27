package az.edu.ada.mstest.client;

import az.edu.ada.mstest.model.dto.QuestionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-gateway")
public interface QuestionClient {

    @GetMapping("/api/v1/questions/{id}/getQuestionByIdForTest")
    QuestionDTO getQuestionById(@PathVariable("id") Long id);
}
