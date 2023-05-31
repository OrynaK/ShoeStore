package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dao.DBException;
import ua.nure.shoestore.dto.ChangeStatusDTO;
import ua.nure.shoestore.dto.MakeOrderDTO;
import ua.nure.shoestore.dto.ShoeDTO;
import ua.nure.shoestore.entity.Address;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.ShoeOrder;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.service.CartService;
import ua.nure.shoestore.service.OrderService;
import ua.nure.shoestore.utils.ErrorUtil;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @GetMapping("/getAllOrders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(value = "/getOrdersByRole")
    public List<Order> showOrdersByRole(@RequestParam("role") Role role) {
        return orderService.getOrdersByRole(role);
    }

    @GetMapping(value = "/myOrders")
    public List<Order> showOrdersByUserId(@RequestParam("userId") Long userId) {
        return orderService.getOrderByUserId(userId);
    }

    @PostMapping(value = "/makeOrder")
    public ResponseEntity<Object> makeOrder
            (@RequestBody @Validated MakeOrderDTO makeOrderDTO, BindingResult bindingResult) throws DBException {
        ResponseEntity<Object> errors = ErrorUtil.getErrorsResponseEntity(bindingResult);
        if (errors != null) return errors;

        Address address = new Address(makeOrderDTO.getCountry(), makeOrderDTO.getCity(), makeOrderDTO.getStreet(), makeOrderDTO.getHouseNumber());
        if (makeOrderDTO.getEntrance() != null) address.setEntrance(makeOrderDTO.getEntrance());
        if (makeOrderDTO.getApartmentNumber() != null) address.setApartmentNumber(makeOrderDTO.getApartmentNumber());

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
