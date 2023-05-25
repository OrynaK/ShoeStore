package ua.nure.shoestore.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private long id;
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private int entrance;
    private int apartmentNumber;

    public Address(String country, String city, String street, String houseNumber, int entrance, int apartmentNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.entrance = entrance;
        this.apartmentNumber = apartmentNumber;
    }

    public Address(String country, String city, String street, String houseNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }
}
