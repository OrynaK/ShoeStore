package ua.nure.shoestore.entity;

import ua.nure.shoestore.entity.statuses.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Order {
    private long order_id;

    private long client_id;
    private ClientStatus clientStatus;

    private long admin_id;
    private AdminStatus adminStatus;

    private long packer_id;
    private PackerStatus packerStatus;

    private long warehouse_id;
    private WarehouseStatus warehouseStatus;

    private long courier_id;
    private CourierStatus courierStatus;

    private LocalDate date;
    private LocalTime time;
    private BigDecimal cost;
    private List<Long> shoes_id;

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public long getClient_id() {
        return client_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }

    public ClientStatus getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(ClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }

    public long getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(long admin_id) {
        this.admin_id = admin_id;
    }

    public AdminStatus getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(AdminStatus adminStatus) {
        this.adminStatus = adminStatus;
    }

    public long getPacker_id() {
        return packer_id;
    }

    public void setPacker_id(long packer_id) {
        this.packer_id = packer_id;
    }

    public PackerStatus getPackerStatus() {
        return packerStatus;
    }

    public void setPackerStatus(PackerStatus packerStatus) {
        this.packerStatus = packerStatus;
    }

    public long getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(long warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public WarehouseStatus getWarehouseStatus() {
        return warehouseStatus;
    }

    public void setWarehouseStatus(WarehouseStatus warehouseStatus) {
        this.warehouseStatus = warehouseStatus;
    }

    public long getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(long courier_id) {
        this.courier_id = courier_id;
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

    public List<Long> getShoes_id() {
        return shoes_id;
    }

    public void setShoes_id(List<Long> shoes_id) {
        this.shoes_id = shoes_id;
    }
}
