package ua.nure.shoestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.shoestore.dto.ShoeCardDTO;
import ua.nure.shoestore.dto.ShoeDTO;
import ua.nure.shoestore.service.ShoeService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class ShoeController {
    @Autowired
    private ShoeService shoeService;
    @PostMapping(value = "/addShoe")
    public void addShoe(@RequestParam("shoe") String shoeDTOJson, @RequestParam("image") MultipartFile file) throws IOException {
        System.out.println(file);
        if (file.isEmpty()) {
        }

        String fileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());

        File newFile = new File("src/main/java/ua/nure/shoestore/photos/" + fileName);
        FileUtils.writeByteArrayToFile(newFile, file.getBytes());

        String fileUrl = "src/main/java/ua/nure/shoestore/photos/" + fileName;
//        return new ResponseEntity<>(fileUrl, HttpStatus.OK);
    }

    @GetMapping(value = "/showShoePage")
    public List<ShoeDTO> showShoePage(@RequestParam("name") String name){
        return shoeService.showShoePage(name);
    }
}
