package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.dao.CRUDRepository;
import ua.nure.shoestore.entity.Order;
import ua.nure.shoestore.entity.User;
import ua.nure.shoestore.entity.enums.OrderStatus;
import ua.nure.shoestore.entity.enums.Role;

import java.util.List;

public interface WorkerDAO{

    void setWorker(Long order_id, Long user_id);
    void changeStatus(Long order_id, Long user_id, OrderStatus status, String description);
}
