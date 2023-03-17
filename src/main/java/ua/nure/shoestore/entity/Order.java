package ua.nure.shoestore.entity;

import ua.nure.shoestore.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Order {
    private long orderId;
    private long clientId;
    private long adminId;
    private long packerId;
    private long warehouseId;
    private long courierId;
    private LocalDate date;
    private LocalTime time;
    private BigDecimal cost;
    private List<Long> shoesId;
    private OrderStatus status;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public long getPackerId() {
        return packerId;
    }

    public void setPackerId(long packerId) {
        this.packerId = packerId;
    }

    public long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public long getCourierId() {
        return courierId;
    }

    public void setCourierId(long courierId) {
        this.courierId = courierId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public List<Long> getShoesId() {
        return shoesId;
    }

    public void setShoesId(List<Long> shoesId) {
        this.shoesId = shoesId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && clientId == order.clientId && adminId == order.adminId && packerId == order.packerId && warehouseId == order.warehouseId && courierId == order.courierId && Objects.equals(date, order.date) && Objects.equals(time, order.time) && Objects.equals(cost, order.cost) && Objects.equals(shoesId, order.shoesId) && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, clientId, adminId, packerId, warehouseId, courierId, date, time, cost, shoesId, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", clientId=" + clientId +
                ", adminId=" + adminId +
                ", packerId=" + packerId +
                ", warehouseId=" + warehouseId +
                ", courierId=" + courierId +
                ", date=" + date +
                ", time=" + time +
                ", cost=" + cost +
                ", shoesId=" + shoesId +
                ", status=" + status +
                '}';
    }
}
