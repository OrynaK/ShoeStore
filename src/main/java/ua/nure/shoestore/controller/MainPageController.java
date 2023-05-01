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
    public List<Shoe> getShoesBySize(@RequestParam("size") BigDecimal size){
        System.out.println(shoeService.getShoesBySize(size));
        return shoeService.getShoesBySize(size);
    }

    @GetMapping(value = "/getShoesBySex")
    public List<Shoe> getShoesBySex(@RequestParam("sex") String sex){
        System.out.println(shoeService.getShoesBySex(Sex.valueOf(sex.toUpperCase())));
        return shoeService.getShoesBySex(Sex.valueOf(sex.toUpperCase()));
    }
    @GetMapping(value = "/getShoesAscendingPrice")
    public List<Shoe> getAllAscendingPrice(){
        System.out.println(shoeService.getShoesAscendingPrice());
        return shoeService.getShoesAscendingPrice();
    }
    @GetMapping(value = "/getShoesDescendingPrice")
    public List<Shoe> getAllDescendingPrice(){
        System.out.println(shoeService.getShoesDescendingPrice());
        return shoeService.getShoesDescendingPrice();
    }
}
