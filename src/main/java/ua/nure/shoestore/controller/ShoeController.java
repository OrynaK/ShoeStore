package ua.nure.shoestore.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public void addShoe(@RequestParam("shoeDTO") String shoeDTOJson, @RequestParam("imageData") MultipartFile imageData) throws IOException {
        ShoeDTO shoeDTO = new ObjectMapper().readValue(shoeDTOJson, ShoeDTO.class);

        String fileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(imageData.getOriginalFilename());

        File newFile = new File("src/main/webapp/my-shoestore/images/" + fileName);
        FileUtils.writeByteArrayToFile(newFile, imageData.getBytes());

        String fileUrl = "src/main/webapp/my-shoestore/images/photos/" + fileName;

        shoeService.addShorWithImage(shoeDTO, fileUrl, fileName);
    }

    @GetMapping(value = "/showShoePage")
    public List<ShoeDTO> showShoePage(@RequestParam("name") String name) {
        return shoeService.showShoePage(name);
    }
}
