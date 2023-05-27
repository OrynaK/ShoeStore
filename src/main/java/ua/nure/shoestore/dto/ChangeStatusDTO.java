package ua.nure.shoestore.dto;

import lombok.Getter;
import lombok.Setter;
import ua.nure.shoestore.entity.enums.OrderStatus;

@Getter
@Setter
public class ChangeStatusDTO {
    private Long orderId;
    private Long userId;
    private String description;
    private OrderStatus status;
}
