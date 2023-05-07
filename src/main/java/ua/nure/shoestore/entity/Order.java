package ua.nure.shoestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.nure.shoestore.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    private long orderId;
    private long clientId;
    private long adminId;
    private long packerId;
    private long warehouseId;
    private long courierId;
    private LocalDate date;
    private LocalTime time;
    private OrderStatus status;
    private Address address;
    private List<OrderShoe> shoesInOrder;
    private BigDecimal cost;

    public Order(){
        shoesInOrder = new ArrayList<>();
    }
}
