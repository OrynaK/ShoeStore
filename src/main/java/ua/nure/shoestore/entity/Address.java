package ua.nure.shoestore.entity;

import ua.nure.shoestore.entity.enums.Country;

import java.util.Objects;

public class Address {
    private long addressId;
    private Country country;
    private String city;
    private String street;
    private String houseNumber;
    private int entrance;
    private int apartmentNumber;

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getEntrance() {
        return entrance;
    }

    public void setEntrance(int entrance) {
        this.entrance = entrance;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return addressId == address.addressId && entrance == address.entrance && apartmentNumber == address.apartmentNumber && country == address.country && Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(houseNumber, address.houseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, country, city, street, houseNumber, entrance, apartmentNumber);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", country=" + country +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", entrance=" + entrance +
                ", apartmentNumber=" + apartmentNumber +
                '}';
    }
}
