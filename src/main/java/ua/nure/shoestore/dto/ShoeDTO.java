package ua.nure.shoestore.dto;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.shoestore.entity.enums.Season;
import ua.nure.shoestore.entity.enums.Sex;

import java.math.BigDecimal;


public class ShoeDTO {
    private String name;
    private BigDecimal size;
    private String color;
    private Season season;
    private Sex sex;
    private BigDecimal price;
    private int amount;

    public ShoeDTO(){

    }

    public ShoeDTO(String name, BigDecimal size, String color, Season season, Sex sex, BigDecimal price, int amount) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.season = season;
        this.sex = sex;
        this.price = price;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ShoeDTO{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", color='" + color + '\'' +
                ", season=" + season +
                ", sex=" + sex +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
