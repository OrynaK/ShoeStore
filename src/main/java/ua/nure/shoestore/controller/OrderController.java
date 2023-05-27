package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dto.ChangeStatusDTO;
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
    public List<Order> showOrdersByRole(@RequestParam("role") Role role) {
        return service.getOrdersByRole(role);
    }

    @GetMapping(value = "/getOrderByUserId")
    public List<Order> showOrdersByUserId(@RequestParam("userId") Long userId) {
        return service.getOrderByUserId(userId);
    }

    @PostMapping(value = "/setWorker")
    public ResponseEntity<String> setWorker(@RequestParam Long orderId, @RequestParam Long userId) {
        if (!service.setWorker(orderId, userId)) {
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to set worker");
    }

    @PostMapping(value = "/changeStatus")
    public void changeStatus(@RequestBody ChangeStatusDTO dto) {
        service.changeStatus(dto.getOrderId(), dto.getUserId(), dto.getStatus(), dto.getDescription());
    }
}
