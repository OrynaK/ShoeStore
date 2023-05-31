package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.shoestore.dao.DBException;
import ua.nure.shoestore.dao.EntityDAO.OrderDAO;
import ua.nure.shoestore.dao.EntityDAO.UserDAO;
import ua.nure.shoestore.dao.EntityDAO.WorkerDAO;
import ua.nure.shoestore.entity.enums.OrderStatus;
import ua.nure.shoestore.entity.enums.Role;

import java.sql.SQLException;
import java.util.List;
@Service
@Transactional(rollbackFor = SQLException.class)
public class WorkerService {
    private final WorkerDAO workerDAO;
    private final UserDAO userDAO;
    private final OrderDAO orderDAO;

    public WorkerService(WorkerDAO workerDAO, UserDAO userDAO, OrderDAO orderDAO) {
        this.workerDAO = workerDAO;
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
    }

    public boolean setWorker(Long orderId, Long userId) {
        List<Long> ids = workerDAO.getIdFromUserOrder(orderId);
        Role role = userDAO.findById(userId).getRole();
        boolean isChosen = false;
        for (Long id : ids) {
            if (role == userDAO.findById(id).getRole()) {
                isChosen = true;
            }
        }
        if (!isChosen) {
            workerDAO.setWorker(orderId, userId);
        }
        return isChosen;
    }



    @Transactional(rollbackFor = DBException.class)
    public void changeStatus(Long orderId, Long userId, OrderStatus status, String description) throws DBException {
        try {
            orderDAO.changeStatus(orderId, status);
            workerDAO.setDescription(orderId, userId, description);
        } catch (DBException e) {
            throw new DBException("Unable to commit changes in the DB", e);
        }
    }
}
