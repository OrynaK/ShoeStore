package ua.nure.shoestore.cards;


import java.math.BigDecimal;
import java.util.Objects;

public class ShoeCard {
    private BigDecimal price;
    private String name;
    private int imageId;

    public ShoeCard(BigDecimal price, String name, int imageId) {
        this.price = price;
        this.name = name;
        this.imageId = imageId;
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
        ShoeCard shoeCard = (ShoeCard) o;
        return imageId == shoeCard.imageId && price.equals(shoeCard.price) && name.equals(shoeCard.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, name, imageId);
    }
}
