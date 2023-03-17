package ua.nure.shoestore.entity.enums;

public enum Sex {
    MALE("Male"),
    FEMALE("Female"),
    UNISEX("Unisex");
    private String sex;

    Sex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }
}
