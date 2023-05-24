package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.shoestore.cards.ShoeCard;
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
    public List<ShoeCard> getAll(){
        return shoeService.getShoes();
    }
    @GetMapping(value = "/getShoesByColor")
    public List<ShoeCard> getShoesByColor(@RequestParam("color") String color){
        return shoeService.getShoesByColor(color);
    }
    @GetMapping(value = "/getShoesBySize")
    public List<ShoeCard> getShoesBySize(@RequestParam("size") BigDecimal size){
        return shoeService.getShoesBySize(size);
    }
    @GetMapping(value = "/getShoesBySex")
    public List<ShoeCard> getShoesBySex(@RequestParam("sex") String sex){
        return shoeService.getShoesBySex(Sex.valueOf(sex.toUpperCase()));
    }
    @GetMapping(value = "/getShoesAscendingPrice")
    public List<ShoeCard> getAllAscendingPrice(){
        return shoeService.getShoesAscendingPrice();
    }
    @GetMapping(value = "/getShoesDescendingPrice")
    public List<ShoeCard> getAllDescendingPrice(){
        return shoeService.getShoesDescendingPrice();
    }
    @GetMapping(value = "/searchShoes")
    public List<ShoeCard> searchShoes(@RequestParam("name") String name){
        return shoeService.searchShoes(name);
    }
}
