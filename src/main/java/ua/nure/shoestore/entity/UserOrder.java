package ua.nure.shoestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrder {
    private long userId;
    private String description;
    private LocalDateTime dateTime;

    public UserOrder(long userId) {
        this.userId = userId;
    }
}
