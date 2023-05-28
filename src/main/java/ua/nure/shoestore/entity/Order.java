package ua.nure.shoestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.nure.shoestore.entity.enums.OrderStatus;
import ua.nure.shoestore.entity.enums.Role;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Order {
    private long id;
    private Map<Role, UserOrder> usersInOrder;
    private LocalDate date;
    private LocalTime time;
    private OrderStatus status;
    private Address address;
    private List<ShoeOrder> shoesInOrder;
    private BigDecimal totalCost;

    public Order() {
        shoesInOrder = new ArrayList<>();
        usersInOrder = new EnumMap<>(Role.class);
    }

    public Order(long clientId, Address address, List<ShoeOrder> shoesInOrder) { // for creating order
        this.address = address;
        this.shoesInOrder = shoesInOrder;
        this.usersInOrder = new EnumMap<>(Role.class);
        usersInOrder.put(Role.CLIENT, new UserOrder(clientId));
    }

    public void addShoe(ShoeOrder shoeOrder) {
        shoesInOrder.add(shoeOrder);
    }

    public void putUser(Role role, UserOrder userOrder) {
        usersInOrder.put(role, userOrder);
    }
}
