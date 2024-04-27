package az.edu.ada.mstest.controller;

import az.edu.ada.mstest.model.dto.CorrectAnswerAssessDTO;
import az.edu.ada.mstest.model.entities.QuestionBooklet;
import az.edu.ada.mstest.service.QuestionBookletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/v1/tests/question-booklets")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuestionBookletController {

    private final QuestionBookletService service;
    private final static String DOCUMENTS_DIR = "/app/Tests";

    @Autowired
    public QuestionBookletController(QuestionBookletService service) {
        this.service = service;
    }

    @GetMapping("/getInfoForAssessment/{id}")
    public ResponseEntity<List<CorrectAnswerAssessDTO>> getCorrectAnswers(@PathVariable Long id) {
        List<CorrectAnswerAssessDTO> correctAnswers = service.findCorrectAnswersByBookletId(id);
        return ResponseEntity.ok(correctAnswers);
    }

    @PostMapping("/create-docs")
    public ResponseEntity<InputStreamResource> createQuestionBookletDocs(@RequestBody List<Long> bookletIds) throws IOException {
        service.generateBookletDocs(bookletIds);

        // Assume you have a method to get the file paths of generated documents
        List<Path> paths = getDocumentPaths();

        // Creating a zip file
        File zipFile = createZipFile(paths);

        // Set headers to inform the browser about a file download
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=question_booklets.zip");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(zipFile.length())
                .contentType(MediaType.parseMediaType("application/zip"))
                .body(resource);
    }

    private File createZipFile(List<Path> paths) throws IOException {
        File zipFile = File.createTempFile("question_booklets", ".zip");
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (Path path : paths) {
                zos.putNextEntry(new ZipEntry(path.getFileName().toString()));
                Files.copy(path, zos);
                zos.closeEntry();
            }
        }
        return zipFile;
    }

    private List<Path> getDocumentPaths() {
        // This will list all DOCX files in the specified directory
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(DOCUMENTS_DIR), "*.docx")) {
            List<Path> paths = new ArrayList<>();
            for (Path path : stream) {
                paths.add(path);
            }
            return paths;
        } catch (IOException e) {
            // Handle the situation when the directory cannot be read
            e.printStackTrace();
            return new ArrayList<>();
        }
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
