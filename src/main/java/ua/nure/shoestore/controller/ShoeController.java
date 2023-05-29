package ua.nure.shoestore.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.shoestore.dto.ShoeCardDTO;
import ua.nure.shoestore.dto.ShoeDTO;
import ua.nure.shoestore.dto.UpdateAmountDTO;
import ua.nure.shoestore.entity.enums.Sex;
import ua.nure.shoestore.service.ShoeService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin
public class ShoeController {
    @Autowired
    private ShoeService shoeService;

    @PostMapping(value = "/addShoe")
    public ResponseEntity<String> addShoe(@RequestParam("shoeDTO") String shoeDTOJson, @RequestParam("imageData") MultipartFile imageData) throws IOException {
        ShoeDTO shoeDTO = new ObjectMapper().readValue(shoeDTOJson, ShoeDTO.class);
        if (shoeService.addShoeWithImage(shoeDTO, imageData)) return ResponseEntity.ok("Shoe added successfully");
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

    @GetMapping(value = "/getShoes")
    public List<ShoeCardDTO> getAll() {
        return shoeService.getShoes();
    }

    @GetMapping(value = "/getShoesAmount")
    public List<ShoeDTO> getAllShoesAmount() {
        return shoeService.showShoeAmount();
    }

    @GetMapping(value = "/getShoesByColor")
    public List<ShoeCardDTO> getShoesByColor(@RequestParam("color") String color) {
        return shoeService.getShoesByColor(color);
    }

    @GetMapping(value = "/getShoesBySize")
    public List<ShoeCardDTO> getShoesBySize(@RequestParam("size") BigDecimal size) {
        return shoeService.getShoesBySize(size);
    }

    @GetMapping(value = "/getShoesBySex")
    public List<ShoeCardDTO> getShoesBySex(@RequestParam("sex") String sex) {
        return shoeService.getShoesBySex(Sex.valueOf(sex.toUpperCase()));
    }

    @GetMapping(value = "/getShoesAscendingPrice")
    public List<ShoeCardDTO> getAllAscendingPrice() {
        return shoeService.getShoesAscendingPrice();
    }

    @GetMapping(value = "/getShoesDescendingPrice")
    public List<ShoeCardDTO> getAllDescendingPrice() {
        return shoeService.getShoesDescendingPrice();
    }

    @GetMapping(value = "/searchShoes")
    public List<ShoeCardDTO> searchShoes(@RequestParam("name") String name) {
        return shoeService.searchShoes(name);
    }
}
