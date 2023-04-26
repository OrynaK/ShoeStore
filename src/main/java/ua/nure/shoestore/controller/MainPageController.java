package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.service.ShoeService;

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
}
