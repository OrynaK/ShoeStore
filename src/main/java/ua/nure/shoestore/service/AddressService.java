package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dao.EntityDAO.AddressDAO;
import ua.nure.shoestore.entity.Address;


@Service
public class AddressService {
    private AddressDAO addressDAO;
    public AddressService(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
    public long addAddress(Address address){
        return addressDAO.add(address);
    }
}
