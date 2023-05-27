package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.entity.enums.OrderStatus;

import java.util.List;

public interface WorkerDAO{

    void setWorker(Long order_id, Long user_id);
    void setDescription(Long order_id, Long user_id, String description);
    List<Long> getIdFromUserOrder(Long order_id);
}
