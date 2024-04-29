package az.edu.ada.mstest.controller;

import az.edu.ada.mstest.model.dto.TestDTO;
import az.edu.ada.mstest.model.entities.Test;
import az.edu.ada.mstest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tests")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {
    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public ResponseEntity<List<TestDTO>> getAllTests() {
        List<TestDTO> tests = testService.findAll();
        return ResponseEntity.ok(tests);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TestDTO> getTestById(@PathVariable Long id) {
        TestDTO test = testService.findTestById(id);
        return ResponseEntity.ok(test);
    }

    @PostMapping
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        Test savedTest = testService.saveTest(test);
        return new ResponseEntity<>(savedTest, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.ok().build();
    }
}