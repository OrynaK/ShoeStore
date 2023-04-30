package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.enums.Sex;
import ua.nure.shoestore.service.ShoeService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin
public class MainPageController {
    @Autowired
    private ShoeService shoeService;

    @GetMapping(value = "/getShoes")
    public List<Shoe> getAll(){
        System.out.println(shoeService.getShoes());
        return shoeService.getShoes();
    }
    @GetMapping(value = "/getShoesByColor")
    public List<Shoe> getShoesByColor(@RequestParam("color") String color){
        System.out.println(shoeService.getShoesByColor(color));
        return shoeService.getShoesByColor(color);
    }

    @GetMapping(value = "/getShoesBySize")
    public List<Shoe> getShoesByColor(@RequestParam("size") BigDecimal size){
        System.out.println(shoeService.getShoesBySize(size));
        return shoeService.getShoesBySize(size);
    }

    @GetMapping(value = "/getShoesBySex")
    public List<Shoe> getShoesByColor(@RequestParam("sex") Sex sex){
        System.out.println(shoeService.getShoesBySex(sex));
        return shoeService.getShoesBySex(sex);
    }
}
