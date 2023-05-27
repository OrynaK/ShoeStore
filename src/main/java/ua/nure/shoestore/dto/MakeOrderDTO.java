package ua.nure.shoestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MakeOrderDTO {

    private Long userId;
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private int entrance;
    private int apartmentNumber;

    private List<ShoeDTO> shoeOrder;


}
