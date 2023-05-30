package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dto.ChangeStatusDTO;
import ua.nure.shoestore.dto.MakeOrderDTO;
import ua.nure.shoestore.dto.ShoeDTO;
import ua.nure.shoestore.entity.Address;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.ShoeOrder;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.service.CartService;
import ua.nure.shoestore.service.OrderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @GetMapping(value = "/getOrdersByRole")
    public List<Order> showOrdersByRole(@RequestParam("role") Role role) {
        return orderService.getOrdersByRole(role);
    }

    @GetMapping(value = "/myOrders")
    public List<Order> showOrdersByUserId(@RequestParam("userId") Long userId) {
        return orderService.getOrderByUserId(userId);
    }

    @PostMapping(value = "setWorker")
    public void setWorker(@RequestParam Long orderId, @RequestParam Long userId) {
        orderService.setWorker(orderId, userId);
    }

    @PostMapping(value = "changeStatus")
    public void changeStatus(@RequestBody ChangeStatusDTO changeStatusDTO) {
        orderService.changeStatus(changeStatusDTO.getOrderId(), changeStatusDTO.getUserId(), changeStatusDTO.getStatus(), changeStatusDTO.getDescription());
    }

    @PostMapping(value = "/makeOrder")
    public ResponseEntity<Object> makeOrder(@RequestBody @Validated MakeOrderDTO makeOrderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>(); // If there are validation errors, return a response with the field errors
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        Address address = new Address(makeOrderDTO.getCountry(), makeOrderDTO.getCity(), makeOrderDTO.getStreet(), makeOrderDTO.getHouseNumber(), makeOrderDTO.getEntrance(), makeOrderDTO.getApartmentNumber());
        List<ShoeOrder> shoesInOrder = new ArrayList<>();
        for (ShoeDTO shoeDTO : makeOrderDTO.getShoeOrder()) {
            ShoeOrder shoeOrder = new ShoeOrder(shoeDTO.getId(), shoeDTO.getPrice(), shoeDTO.getAmount());
            shoesInOrder.add(shoeOrder);
        }
        orderService.makeOrder(new Order(makeOrderDTO.getUserId(), address, shoesInOrder));
        cartService.clearCart(makeOrderDTO.getUserId());
        return ResponseEntity.ok().build();
    }

}
