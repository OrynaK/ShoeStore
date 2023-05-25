package ua.nure.shoestore.dao.EntityDAOImpl;

import ua.nure.shoestore.dao.EntityDAO.CartDAO;
import ua.nure.shoestore.entity.Cart;

import java.util.List;

public class CartDAOImpl implements CartDAO {
    @Override
    public long insert(Cart entity) {
        return 0;
    }

    @Override
    public void update(Cart entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Cart> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cart findById(long id) {
        throw new UnsupportedOperationException();
    }
}
