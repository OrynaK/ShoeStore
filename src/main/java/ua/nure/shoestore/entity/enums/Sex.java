package ua.nure.shoestore.entity.enums;

public enum Sex {
    MALE("male"),
    FEMALE("female"),
    UNISEX("unisex");
    private String sex;

    Sex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }
}
