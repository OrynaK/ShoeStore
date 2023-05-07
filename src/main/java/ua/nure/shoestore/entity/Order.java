package ua.nure.shoestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.shoestore.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private long orderId;
    private long clientId;
    private long adminId;
    private long packerId;
    private long warehouseId;
    private long courierId;
    private LocalDate date;
    private LocalTime time;
    private BigDecimal cost;
    private Map<Long, Integer> shoesAmountInOrder;
    private OrderStatus status;
    private Address address;
}
