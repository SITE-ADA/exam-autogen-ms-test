package az.edu.ada.mstest.client;

import az.edu.ada.mstest.model.dto.QuestionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "questions-service", url = "http://localhost:9897/api/v1/questions/")
public interface QuestionClient {

    @GetMapping("{id}/getQuestionByIdForTest")
    QuestionDTO getQuestionById(@PathVariable("id") Long id);
}
