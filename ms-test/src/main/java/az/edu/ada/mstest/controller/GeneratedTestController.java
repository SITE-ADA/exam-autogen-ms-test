package az.edu.ada.mstest.controller;

import az.edu.ada.mstest.model.entities.GeneratedTest;
import az.edu.ada.mstest.service.GeneratedTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tests/generated-tests")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GeneratedTestController {
    private final GeneratedTestService generatedTestService;

    @Autowired
    public GeneratedTestController(GeneratedTestService generatedTestService) {
        this.generatedTestService = generatedTestService;
    }

    @GetMapping
    public ResponseEntity<List<GeneratedTest>> getAllGeneratedTests() {
        List<GeneratedTest> generatedTests = generatedTestService.findAllGeneratedTests();
        return ResponseEntity.ok(generatedTests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneratedTest> getGeneratedTestById(@PathVariable Long id) {
        GeneratedTest generatedTest = generatedTestService.findGeneratedTestById(id);
        return ResponseEntity.ok(generatedTest);
    }

    @PostMapping
    public ResponseEntity<GeneratedTest> createGeneratedTest(@RequestBody GeneratedTest generatedTest) {
        GeneratedTest savedGeneratedTest = generatedTestService.saveGeneratedTest(generatedTest);
        return new ResponseEntity<>(savedGeneratedTest, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGeneratedTest(@PathVariable Long id) {
        generatedTestService.deleteGeneratedTest(id);
        return ResponseEntity.ok().build();
    }
}