package ua.nure.shoestore.entity.enums;

public enum Season {
    WINTER("Winter"),
    SUMMER("Summer"),
    DEMI("Demi");

    private String season;

    Season(String season) {
        this.season = season;
    }

    public String getSeason() {
        return season;
    }
}
