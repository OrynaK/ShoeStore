package ua.nure.shoestore.entity.enums;

public enum Season {
    SPRING("Spring"),
    WINTER("Winter"),
    SUMMER("Summer"),
    FALL("Fall"),
    DEMI("Demi");

    private String season;

    Season(String season) {
        this.season = season;
    }

    public String getSeason() {
        return season;
    }
}
