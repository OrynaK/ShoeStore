package ua.nure.shoestore.entity.enums;

public enum OrderStatus {
    PROCESSING("processing"),
    ACCEPTED("accepted"),
    COMPILED("compiled"),
    READY_FOR_SENDING("ready_for_sending"),
    DELIVERED("delivered"),
    CANCELLED("cancelled");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
