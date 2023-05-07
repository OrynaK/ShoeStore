package ua.nure.shoestore.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String fileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());

        File newFile = new File("src/main/java/ua/nure/shoestore/photos/" + fileName);
        FileUtils.writeByteArrayToFile(newFile, file.getBytes());

        String fileUrl = "src/main/java/ua/nure/shoestore/photos/" + fileName;
        return new ResponseEntity<>(fileUrl, HttpStatus.OK);
    }
}
