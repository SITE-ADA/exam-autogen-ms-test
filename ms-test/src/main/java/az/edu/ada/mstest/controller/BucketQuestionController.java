package az.edu.ada.mstest.controller;

import az.edu.ada.mstest.model.entities.BucketQuestion;
import az.edu.ada.mstest.model.entities.BucketQuestionId;
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

    @GetMapping("/{bucketId}")
    public ResponseEntity<List<BucketQuestion>> getQuestionsForBucket(@PathVariable Long bucketId) {
        List<BucketQuestion> bucketQuestions = bucketQuestionService.findAllByQuestionBucketId(bucketId);
        return ResponseEntity.ok(bucketQuestions);
    }

    @PostMapping
    public ResponseEntity<BucketQuestion> addQuestionToBucket(@RequestBody BucketQuestion bucketQuestion) {
        BucketQuestion savedBucketQuestion = bucketQuestionService.save(bucketQuestion);
        return ResponseEntity.ok(savedBucketQuestion);
    }

    @DeleteMapping("/{bucketId}/{questionId}")
    public ResponseEntity<Void> removeQuestionFromBucket(@PathVariable Long bucketId, @PathVariable Long questionId) {
        BucketQuestionId id = new BucketQuestionId(bucketId, questionId);
        bucketQuestionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
