package ua.nure.shoestore.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.shoestore.dao.EntityDAO.ShoeDAO;
import ua.nure.shoestore.dto.ShoeCardDTO;
import ua.nure.shoestore.dto.ShoeDTO;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.enums.Sex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoeService {
    private final ShoeDAO shoeDAO;

    public ShoeService(ShoeDAO shoeDAO) {
        this.shoeDAO = shoeDAO;
    }

    public List<ShoeCardDTO> getShoes() {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.findAll()) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
            if (!shoeCards.contains(shoeCard)) {
                shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }

    public List<ShoeCardDTO> getShoesByColor(String color) {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesByColor(color)) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
            if (!shoeCards.contains(shoeCard)) {
                shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }

    public List<ShoeCardDTO> getShoesBySize(BigDecimal size) {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesBySize(size)) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
            if (!shoeCards.contains(shoeCard)) {
                shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }

    public List<ShoeCardDTO> getShoesBySex(Sex sex) {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesBySex(sex)) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
            if (!shoeCards.contains(shoeCard)) {
                shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }

    public List<ShoeCardDTO> getShoesAscendingPrice() {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesAscendingPrice()) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
            if (!shoeCards.contains(shoeCard)) {
                shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }

    public List<ShoeCardDTO> getShoesDescendingPrice() {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesDescendingPrice()) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
            if (!shoeCards.contains(shoeCard)) {
                shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }

    public List<ShoeCardDTO> searchShoes(String name) {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.searchShoes(name)) {
            ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
            if (!shoeCards.contains(shoeCard)) {
                shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                shoeCards.add(shoeCard);
            }
        }
        return shoeCards;
    }

    public Long addShoe(Shoe shoe) {
       return shoeDAO.insert(shoe);
    }

    public Long addShoeWithImage(ShoeDTO shoeDTO, String path, String name){
        shoeDTO.setImagePath(path);
        return shoeDAO.addShoeWithImage(shoeDTO, name);
    }

    public List<ShoeDTO> showShoePage(String name) {
        List<ShoeDTO> shoePages = new ArrayList<>();
        for (Shoe s : shoeDAO.showShoePage(name)) {
            ShoeDTO shoePage = new ShoeDTO(s.getName(), s.getSize(), s.getColor(), s.getSeason(), s.getSex(), s.getPrice(), s.getAmount());
            if (!shoePages.contains(shoePage)) {
                shoePages.add(shoePage);
            }
        }
        return shoePages;

    }
}
