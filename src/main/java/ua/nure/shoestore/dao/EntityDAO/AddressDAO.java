package ua.nure.shoestore.dao.EntityDAO;

import ua.nure.shoestore.entity.Address;

public interface AddressDAO{
    long add(Address address);
    Address getById(long id);
}
