package az.edu.ada.mstest.controller;

import az.edu.ada.mstest.model.entities.BucketQuestion;
import az.edu.ada.mstest.model.request.BucketQuestionRequest;
import az.edu.ada.mstest.service.BucketQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tests/bucket-questions")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BucketQuestionController {
    private final BucketQuestionService bucketQuestionService;

    @Autowired
    public BucketQuestionController(BucketQuestionService bucketQuestionService) {
        this.bucketQuestionService = bucketQuestionService;
    }

    @GetMapping
    public ResponseEntity<List<BucketQuestion>> getAll() {
        List<BucketQuestion> bucketQuestions = bucketQuestionService.findAll();
        return ResponseEntity.ok(bucketQuestions);
    }

    @PostMapping
    public ResponseEntity<BucketQuestion> save(@RequestBody BucketQuestionRequest bucketQuestionRequest) {
        BucketQuestion savedBucketQuestion = bucketQuestionService.save(bucketQuestionRequest);
        return ResponseEntity.ok(savedBucketQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBucket(@PathVariable Long id) {
        bucketQuestionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
