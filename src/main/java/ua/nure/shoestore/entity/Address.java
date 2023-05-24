package ua.nure.shoestore.entity;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Address {
    private @NonNull long id;
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private int entrance;
    private int apartmentNumber;
}
