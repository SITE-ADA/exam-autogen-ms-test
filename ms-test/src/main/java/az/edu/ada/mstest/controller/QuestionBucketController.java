package az.edu.ada.mstest.controller;

import az.edu.ada.mstest.model.entities.QuestionBucket;
import az.edu.ada.mstest.model.request.QuestionBucketRequest;
import az.edu.ada.mstest.service.QuestionBucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tests/question-buckets")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuestionBucketController {
    private final QuestionBucketService questionBucketService;

    @Autowired
    public QuestionBucketController(QuestionBucketService questionBucketService) {
        this.questionBucketService = questionBucketService;
    }

    @GetMapping("")
    public ResponseEntity<List<QuestionBucket>> getAllQuestionBuckets(){
        List<QuestionBucket> questionBuckets = questionBucketService.findAllQuestionBuckets();
        return ResponseEntity.ok(questionBuckets);
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<List<QuestionBucket>> getQuestionBucketsByTestId(@PathVariable Long testId) {
        List<QuestionBucket> questionBuckets = questionBucketService.findByQuestionBucketId(testId);
        return ResponseEntity.ok(questionBuckets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionBucket> getQuestionBucketById(@PathVariable Long id) {
        QuestionBucket questionBucket = questionBucketService.findQuestionBucketById(id);
        return ResponseEntity.ok(questionBucket);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<QuestionBucket> patchQuestionBucket(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        QuestionBucket patchedQuestionBucket = questionBucketService.patchQuestionBucket(id, updates);
        if (patchedQuestionBucket != null) {
            return ResponseEntity.ok(patchedQuestionBucket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<QuestionBucket> createQuestionBucket(@RequestBody QuestionBucketRequest questionBucketRequest) {
        QuestionBucket savedQuestionBucket = questionBucketService.saveQuestionBucket(questionBucketRequest);
        return new ResponseEntity<>(savedQuestionBucket, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestionBucket(@PathVariable Long id) {
        questionBucketService.deleteQuestionBucket(id);
        return ResponseEntity.ok().build();
    }

}