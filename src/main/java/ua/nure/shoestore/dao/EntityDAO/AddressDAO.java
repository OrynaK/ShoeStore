package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.dao.CRUDRepository;
import ua.nure.shoestore.entity.Address;

import java.util.List;

public interface AddressDAO extends CRUDRepository<Address> {
    long insert(Address address);

    void update(Address address);

    List<Address> findAll();

    Address findById(long id);
}
