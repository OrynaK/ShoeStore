package ua.nure.shoestore.entity;

import ua.nure.shoestore.entity.enums.statuses.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Order {
    private long orderId;

    private long clientId;
    private ClientStatus clientStatus;

    private long adminId;
    private AdminStatus adminStatus;

    private long packerId;
    private PackerStatus packerStatus;

    private long warehouseId;
    private WarehouseStatus warehouseStatus;

    private long courierId;
    private CourierStatus courierStatus;

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

    public ClientStatus getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(ClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public AdminStatus getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(AdminStatus adminStatus) {
        this.adminStatus = adminStatus;
    }

    public long getPackerId() {
        return packerId;
    }

    public void setPackerId(long packerId) {
        this.packerId = packerId;
    }

    public PackerStatus getPackerStatus() {
        return packerStatus;
    }

    public void setPackerStatus(PackerStatus packerStatus) {
        this.packerStatus = packerStatus;
    }

    public long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public WarehouseStatus getWarehouseStatus() {
        return warehouseStatus;
    }

    public void setWarehouseStatus(WarehouseStatus warehouseStatus) {
        this.warehouseStatus = warehouseStatus;
    }

    public long getCourierId() {
        return courierId;
    }

    public void setCourierId(long courierId) {
        this.courierId = courierId;
    }

    public CourierStatus getCourierStatus() {
        return courierStatus;
    }

    public void setCourierStatus(CourierStatus courierStatus) {
        this.courierStatus = courierStatus;
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
        return orderId == order.orderId && clientId == order.clientId && adminId == order.adminId && packerId == order.packerId && warehouseId == order.warehouseId && courierId == order.courierId && clientStatus == order.clientStatus && adminStatus == order.adminStatus && Objects.equals(packerStatus, order.packerStatus) && warehouseStatus == order.warehouseStatus && courierStatus == order.courierStatus && Objects.equals(date, order.date) && Objects.equals(time, order.time) && Objects.equals(cost, order.cost) && Objects.equals(shoesId, order.shoesId) && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, clientId, clientStatus, adminId, adminStatus, packerId, packerStatus, warehouseId, warehouseStatus, courierId, courierStatus, date, time, cost, shoesId, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", clientId=" + clientId +
                ", clientStatus=" + clientStatus +
                ", adminId=" + adminId +
                ", adminStatus=" + adminStatus +
                ", packerId=" + packerId +
                ", packerStatus=" + packerStatus +
                ", warehouseId=" + warehouseId +
                ", warehouseStatus=" + warehouseStatus +
                ", courierId=" + courierId +
                ", courierStatus=" + courierStatus +
                ", date=" + date +
                ", time=" + time +
                ", cost=" + cost +
                ", shoesId=" + shoesId +
                ", status=" + status +
                '}';
    }
}
