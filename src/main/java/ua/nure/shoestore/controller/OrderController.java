package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dto.MakeOrderDTO;

import ua.nure.shoestore.dto.ShoeDTO;
import ua.nure.shoestore.entity.Address;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.ShoeOrder;
import ua.nure.shoestore.entity.UserOrder;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.service.OrderService;

import java.util.ArrayList;
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
    @GetMapping(value = "/myOrders")
    public List <Order> showOrdersByUserId(@RequestParam("userId") Long userId) {
        return service.getOrderByUserId(userId);
    }
    @PostMapping(value = "setWorker")
    public void setWorker(@RequestParam Long orderId, @RequestParam Long userId){
        service.setWorker(orderId, userId);
    }
    @PostMapping(value = "/makeorder")
    public void makeOrder(@RequestBody MakeOrderDTO makeOrderDTO) {
        Address address = new Address(makeOrderDTO.getCountry(), makeOrderDTO.getCity(), makeOrderDTO.getStreet(), makeOrderDTO.getHouseNumber(), makeOrderDTO.getEntrance(), makeOrderDTO.getApartmentNumber());
        List<ShoeOrder> shoesInOrder = new ArrayList<>();
        for (ShoeDTO shoeDTO : makeOrderDTO.getShoeOrder()) {
            ShoeOrder shoeOrder = new ShoeOrder(shoeDTO.getId(), shoeDTO.getPrice(), shoeDTO.getAmount());
            shoesInOrder.add(shoeOrder);
        }
        service.makeOrder(new Order(makeOrderDTO.getUserId(), address, shoesInOrder));
    }

}
