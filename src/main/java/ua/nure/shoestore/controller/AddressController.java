package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.entity.Address;
import ua.nure.shoestore.service.AddressService;

@RestController
@CrossOrigin
public class AddressController {
    @Autowired
    private AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping("/address/{id}")
    public Address getAddressById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

}
