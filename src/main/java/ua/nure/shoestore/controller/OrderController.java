package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.shoestore.service.OrderService;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService service;


}
