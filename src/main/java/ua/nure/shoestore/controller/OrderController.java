package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dto.MakeOrderDTO;

import ua.nure.shoestore.dto.ShoeDTO;
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
    @GetMapping(value = "/getOrderByUserId")
    public List <Order> showOrdersByUserId(@RequestParam("userId") Long userId) {
        return service.getOrderByUserId(userId);
    }
    @PostMapping(value = "setWorker")
    public void setWorker(@RequestParam Long orderId, @RequestParam Long userId){
        service.setWorker(orderId, userId);
    }
    @PostMapping(value = "/makeOrder")
    public void makeOrder(@RequestBody MakeOrderDTO makeOrderDTO) {
        Order order = new Order();
        order.getAddress().setCountry(makeOrderDTO.getCountry());
        order.getAddress().setCity(makeOrderDTO.getCity());
        order.getAddress().setStreet(makeOrderDTO.getStreet());
        order.getAddress().setHouseNumber(makeOrderDTO.getHouseNumber());
        order.getAddress().setEntrance(makeOrderDTO.getEntrance());
        order.getAddress().setApartmentNumber(makeOrderDTO.getApartmentNumber());

        List<ShoeOrder> shoeOrders = new ArrayList<>();
        for (ShoeDTO shoeDTO : makeOrderDTO.getShoeOrder()) {
            ShoeOrder shoeOrder = new ShoeOrder();
            shoeOrder.setShoeId(shoeDTO.getId());
            shoeOrder.setPrice(shoeDTO.getPrice());
            shoeOrder.setAmount(shoeDTO.getAmount());

            shoeOrders.add(shoeOrder);
        }

        for (ShoeOrder shoeOrder : shoeOrders) {
            order.addShoe(shoeOrder);
        }

        service.makeOrder(order);
    }


}
