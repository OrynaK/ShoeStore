package ua.nure.shoestore.entity.enums;

public enum OrderStatus {
    PROCESSING("Processing"),
    ACCEPTED("Accepted"),
    COMPILED("Compiled"),
    READY_FOR_SENDING("ReadyForSending"),
    DELIVERED("Delivered"),
    BASKET("Basket");
    private String orderStatus;

    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
