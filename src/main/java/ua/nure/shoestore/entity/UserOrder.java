package ua.nure.shoestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrder {
    private long userId;
    private String description;
    private LocalDate date;
    private LocalTime time;

    public UserOrder(long userId) {
        this.userId = userId;
    }
}
