package ua.nure.shoestore.entity;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UserOrder {
    @NonNull private long userId;
    private String description;
    private LocalDate date;
    private LocalTime time;
}
