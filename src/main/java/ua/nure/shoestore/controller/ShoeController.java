package ua.nure.shoestore.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.shoestore.dto.ShoeDTO;
import ua.nure.shoestore.dto.UpdateAmountDTO;
import ua.nure.shoestore.dto.UpdateRoleDTO;
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
    public ResponseEntity<String> addShoe(@RequestParam("shoeDTO") String shoeDTOJson, @RequestParam("imageData") MultipartFile imageData) throws IOException {
        ShoeDTO shoeDTO = new ObjectMapper().readValue(shoeDTOJson, ShoeDTO.class);
        if( shoeService.addShoeWithImage(shoeDTO, imageData)) return ResponseEntity.ok("Shoe added successfully");
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding shoe");
    }

    @GetMapping(value = "/showShoePage")
    public List<ShoeDTO> showShoePage(@RequestParam("name") String name) {
        return shoeService.showShoePage(name);
    }

    @PostMapping(value = "/updateShoesAmount")
    public String updateRole(@RequestBody UpdateAmountDTO updateAmountDTO) {
        if (updateAmountDTO.getId() > 0) {
            shoeService.updateAmount(updateAmountDTO.getId(), updateAmountDTO.getSelectedAmount());
            return "Shoe amount updated";
        }
        return null;
    }
}
