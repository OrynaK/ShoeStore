package ua.nure.shoestore.entity;

import ua.nure.shoestore.entity.enums.Season;
import ua.nure.shoestore.entity.enums.Sex;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Objects;

public class Shoe {
    private long shoeId;
    private BigDecimal size;
    private String color;
    private Season season;
    private Sex sex;
    private BigDecimal price;
    private String name;
    private int amount;
    private int imageId;

    public long getShoeId() {
        return shoeId;
    }

    public void setShoeId(long shoeId) {
        this.shoeId = shoeId;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shoe shoe = (Shoe) o;
        return shoeId == shoe.shoeId && amount == shoe.amount && size.equals(shoe.size) && color.equals(shoe.color) && season == shoe.season && sex == shoe.sex && price.equals(shoe.price) && name.equals(shoe.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shoeId, size, color, season, sex, price, name, amount);
    }

    @Override
    public String toString() {
        return "Shoe{" +
                "shoeId=" + shoeId +
                ", size=" + size +
                ", color=" + color +
                ", season=" + season +
                ", sex=" + sex +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
