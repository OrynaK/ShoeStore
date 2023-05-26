package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dto.ShoeDTO;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.service.OrderService;

import java.util.List;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping(value = "/getOrdersByRole")
    public List<Order> showShoePage(@RequestParam("role") Role role) {
        return service.getOrdersByRole(role);
    }
}
