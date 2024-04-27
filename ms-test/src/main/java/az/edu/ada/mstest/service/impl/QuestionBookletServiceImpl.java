package az.edu.ada.mstest.service.impl;
import az.edu.ada.mstest.model.dto.AnswerDTO;
import az.edu.ada.mstest.model.dto.CorrectAnswerAssessDTO;
import az.edu.ada.mstest.model.dto.CorrectAnswerDTO;
import az.edu.ada.mstest.model.dto.QuestionDTO;
import az.edu.ada.mstest.model.entities.QuestionBooklet;
import az.edu.ada.mstest.model.entities.QuestionBucket;
import az.edu.ada.mstest.model.entities.Test;
import az.edu.ada.mstest.repository.*;
import az.edu.ada.mstest.service.QuestionBookletService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionBookletServiceImpl implements QuestionBookletService {

    private final QuestionBookletRepository repository;

    @Autowired
    public QuestionBookletServiceImpl(QuestionBookletRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CorrectAnswerAssessDTO> findCorrectAnswersByBookletId(Long id) {

        QuestionBooklet questionBooklet = repository.findById(id).get();

        Gson gson = new Gson();
        Type questionListType = new TypeToken<List<QuestionDTO>>(){}.getType();
        List<QuestionDTO> questions = gson.fromJson(questionBooklet.getQuestionsJson(), questionListType);
        System.out.println(questions);

        List<CorrectAnswerAssessDTO> correctAnswerOptions = new ArrayList<>();
        for (QuestionDTO question : questions) {
            List<AnswerDTO> correctAnswer = question.getAnswers().stream()
                    .filter(a -> a.getId().equals(question.getCorrectAnswer().getAnswer().getId()))
                    .toList();

            CorrectAnswerAssessDTO correctAnswerAssessDTO = CorrectAnswerAssessDTO.builder()
                    .correctAnswer(correctAnswer.get(0))
                    .maxPoints(questionBooklet.getGeneratedTest().getTest().getMaximumPoints())
                    .build();

            correctAnswerOptions.add(correctAnswerAssessDTO);
        }

        return correctAnswerOptions;
    }

    @Override
    public void generateBookletDocs(List<Long> bookletIds) throws IOException {
        List<QuestionBooklet> questionBooklets = repository.findAllById(bookletIds);

        for (QuestionBooklet booklet : questionBooklets) {
            XWPFDocument document = new XWPFDocument();

            // Title and Date
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText(booklet.getGeneratedTest().getName() + ": " + booklet.getVariantName());
            titleRun.setBold(true);
            titleRun.setFontSize(20);
            titleRun.addBreak();

            // Adding date below title
            XWPFRun dateRun = titleParagraph.createRun();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(booklet.getDate(), inputFormatter);
            String formattedDate = date.format(outputFormatter);
            dateRun.setText("Date of Exam: " + formattedDate);
            dateRun.setBold(false);
            dateRun.setFontSize(18);
            dateRun.addBreak();
            dateRun.addBreak();

            // Parse the JSON to extract questions
            List<QuestionDTO> questions = parseQuestionsJson(booklet.getQuestionsJson());

            int questionInc = 1;
            // Adding questions and their answers to the document
            for (QuestionDTO question : questions) {
                // Question paragraph
                XWPFParagraph questionParagraph = document.createParagraph();
                XWPFRun questionRun = questionParagraph.createRun();
                questionRun.setText(questionInc + ". " + question.getText() + " (" + question.getDefaultScore() + "p.)");
                questionRun.setFontSize(14);

                // List answers
                char option = 'A';
                for (AnswerDTO answer : question.getAnswers()) {
                    XWPFParagraph answerParagraph = document.createParagraph();
                    XWPFRun answerRun = answerParagraph.createRun();
                    answerRun.setText(option + ". " + answer.getText());
                    answerRun.setFontSize(12);
                    option++;
                }
                questionRun.addBreak(); // Adds space between questions
                questionInc++;
            }

            // Write to a byte array output stream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            document.close();

            // Save the file
            Path path = Paths.get("/app/Tests", "TestBooklet_" + booklet.getId() + ".docx");
            Files.write(path, outputStream.toByteArray());
        }
    }

    private List<QuestionDTO> parseQuestionsJson(String json) {
        Gson gson = new Gson();
        Type questionListType = new TypeToken<List<QuestionDTO>>() {}.getType();
        return gson.fromJson(json, questionListType);
    }

    @Override
    public QuestionBooklet findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("QuestionBooklet not found"));
    }

    @Override
    public List<QuestionBooklet> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
