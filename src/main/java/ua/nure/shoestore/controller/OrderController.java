package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dto.SetWorkerDTO;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.UserOrder;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.service.OrderService;

import java.util.List;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping(value = "/getOrdersByRole")
    public List<Order> showOrdersByRole(@RequestParam("role") Role role) {
        return service.getOrdersByRole(role);
    }
    @GetMapping(value = "/getOrderByUserId")
    public List <Order> showOrdersByUserId(@RequestParam("userId") Long userId) {
        return service.getOrderByUserId(userId);
    }
    @PostMapping(value = "setWorker")
    public void setWorker(@RequestParam SetWorkerDTO setWorkerDTO){
        service.setWorker(setWorkerDTO.getOrderId(), setWorkerDTO.getUserId());
    }
}
