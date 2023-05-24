package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.dto.ShoeCardDTO;
import ua.nure.shoestore.dao.EntityDAO.ShoeDAO;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.enums.Sex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoeService {
   private ShoeDAO shoeDAO;

   public ShoeService(ShoeDAO shoeDAO){this.shoeDAO = shoeDAO;}

    public List<ShoeCardDTO> getShoes(){
       List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getAllShoes()) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
       return shoeCards;
    }
    public List<ShoeCardDTO> getShoesByColor(String color){
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesByColor(color)) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }
    public List<ShoeCardDTO> getShoesBySize(BigDecimal size){
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesBySize(size)) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }
    public List<ShoeCardDTO> getShoesBySex(Sex sex){
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesBySex(sex)) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }
    public List<ShoeCardDTO> getShoesAscendingPrice(){
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesAscendingPrice()) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }
    public List<ShoeCardDTO> getShoesDescendingPrice(){
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesDescendingPrice()) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }
    public List<ShoeCardDTO> searchShoes(String name){
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.searchShoes(name)) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }
    public void addShoe(Shoe shoe) {
        shoeDAO.add(shoe);
    }

}
