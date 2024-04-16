package az.edu.ada.mstest.controller;

import az.edu.ada.mstest.model.entities.QuestionBooklet;
import az.edu.ada.mstest.service.QuestionBookletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tests/question-booklets")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuestionBookletController {

    private final QuestionBookletService service;

    @Autowired
    public QuestionBookletController(QuestionBookletService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<QuestionBooklet> createQuestionBooklet(@RequestBody QuestionBooklet questionBooklet) {
        QuestionBooklet savedBooklet = service.saveQuestionBooklet(questionBooklet);
        return ResponseEntity.ok(savedBooklet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionBooklet> getQuestionBookletById(@PathVariable Long id) {
        QuestionBooklet booklet = service.findById(id);
        return ResponseEntity.ok(booklet);
    }

    @GetMapping
    public ResponseEntity<List<QuestionBooklet>> getAllQuestionBooklets() {
        List<QuestionBooklet> booklets = service.findAll();
        return ResponseEntity.ok(booklets);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestionBooklet(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
