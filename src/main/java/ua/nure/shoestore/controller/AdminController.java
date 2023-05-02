package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.service.ShoeService;

@RestController
@CrossOrigin
public class AdminController {
    @Autowired
    private ShoeService service;

    public AdminController(ShoeService service) {
        this.service = service;
    }

    @PostMapping(value={"/addNewShoe"})
    public String registration(@RequestBody Shoe shoe){
        if(shoe != null){
            service.addShoe(shoe);
            return "New shoe added";
        } else return "";
    }


}
