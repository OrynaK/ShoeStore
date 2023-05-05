package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import ua.nure.shoestore.cards.ShoeCard;
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

    public List<ShoeCard> getShoes(){
       List<ShoeCard> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getAllShoes()) {
            ShoeCard shoeCard = new ShoeCard(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
       return shoeCards;
    }
    public List<ShoeCard> getShoesByColor(String color){
        List<ShoeCard> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesByColor(color)) {
            ShoeCard shoeCard = new ShoeCard(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }
    public List<ShoeCard> getShoesBySize(BigDecimal size){
        List<ShoeCard> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesBySize(size)) {
            ShoeCard shoeCard = new ShoeCard(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }
    public List<ShoeCard> getShoesBySex(Sex sex){
        List<ShoeCard> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesBySex(sex)) {
            ShoeCard shoeCard = new ShoeCard(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }
    public List<ShoeCard> getShoesAscendingPrice(){
        List<ShoeCard> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesAscendingPrice()) {
            ShoeCard shoeCard = new ShoeCard(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }
    public List<ShoeCard> getShoesDescendingPrice(){
        List<ShoeCard> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesDescendingPrice()) {
            ShoeCard shoeCard = new ShoeCard(s.getPrice(), s.getName(), s.getImageId());
            if(!shoeCards.contains(shoeCard)){
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }
    public List<ShoeCard> searchShoes(String name){
        List<ShoeCard> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.searchShoes(name)) {
            ShoeCard shoeCard = new ShoeCard(s.getPrice(), s.getName(), s.getImageId());
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
