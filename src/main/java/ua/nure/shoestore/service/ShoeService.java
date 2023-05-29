package ua.nure.shoestore.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.shoestore.dao.EntityDAO.ShoeDAO;
import ua.nure.shoestore.dto.ShoeCardDTO;
import ua.nure.shoestore.dto.ShoeDTO;
import ua.nure.shoestore.entity.Shoe;
import ua.nure.shoestore.entity.enums.Role;
import ua.nure.shoestore.entity.enums.Sex;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShoeService {
    private final ShoeDAO shoeDAO;

    public ShoeService(ShoeDAO shoeDAO) {
        this.shoeDAO = shoeDAO;
    }

    public void updateAmount(long shoeId, int amount) {
        shoeDAO.updateShoeAmount(shoeId, amount);
    }

    public List<ShoeCardDTO> getShoes() {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.findAll()) {
            if(s.getAmount() > 0){
                ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
                if (!shoeCards.contains(shoeCard)) {
                    shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                    shoeCards.add(shoeCard);
                }
            }
        }
        return shoeCards;
    }

    public List<ShoeCardDTO> getShoesByColor(String color) {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesByColor(color)) {
            if(s.getAmount() > 0) {
                ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
                if (!shoeCards.contains(shoeCard)) {
                    shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                    shoeCards.add(shoeCard);
                }
            }
        }
        return shoeCards;
    }

    public List<ShoeCardDTO> getShoesBySize(BigDecimal size) {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesBySize(size)) {
            if(s.getAmount() > 0) {
                ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
                if (!shoeCards.contains(shoeCard)) {
                    shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                    shoeCards.add(shoeCard);
                }
            }
        }
        return shoeCards;
    }

    public List<ShoeCardDTO> getShoesBySex(Sex sex) {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesBySex(sex)) {
            if(s.getAmount() > 0) {
                ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
                if (!shoeCards.contains(shoeCard)) {
                    shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                    shoeCards.add(shoeCard);
                }
            }
        }
        return shoeCards;
    }

    public List<ShoeCardDTO> getShoesAscendingPrice() {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesAscendingPrice()) {
            if(s.getAmount() > 0) {
                ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
                if (!shoeCards.contains(shoeCard)) {
                    shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                    shoeCards.add(shoeCard);
                }
            }
        }
        return shoeCards;
    }

    public List<ShoeCardDTO> getShoesDescendingPrice() {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.getShoesDescendingPrice()) {
            if(s.getAmount() > 0) {
                ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
                if (!shoeCards.contains(shoeCard)) {
                    shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                    shoeCards.add(shoeCard);
                }
            }
        }
        return shoeCards;
    }

    public List<ShoeCardDTO> searchShoes(String name) {
        List<ShoeCardDTO> shoeCards = new ArrayList<>();
        for (Shoe s : shoeDAO.searchShoes(name)) {
            if(s.getAmount() > 0) {
                ShoeCardDTO shoeCard = new ShoeCardDTO(s.getPrice(), s.getName());
                if (!shoeCards.contains(shoeCard)) {
                    shoeCard.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
                    shoeCards.add(shoeCard);
                }
            }
        }
        return shoeCards;
    }

    public Long addShoe(Shoe shoe) {
       return shoeDAO.insert(shoe);
    }

    public boolean addShoeWithImage(ShoeDTO shoeDTO, MultipartFile imageData) throws IOException {
        String fileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(imageData.getOriginalFilename());
        String fileUrl = "src/main/webapp/my-shoestore/public/images/" + fileName;
        File newFile = new File(fileUrl);
        FileUtils.writeByteArrayToFile(newFile, imageData.getBytes());
        shoeDTO.setImagePath(fileUrl);
        return shoeDAO.addShoeWithImage(shoeDTO, fileName) != null;
    }

    public List<ShoeDTO> showShoeAmount() {
        List<ShoeDTO> shoePages = new ArrayList<>();
        for (Shoe s : shoeDAO.findAll()) {
            ShoeDTO shoePage = new ShoeDTO(s.getId(), s.getName(), s.getSize(), s.getColor(), s.getSeason(), s.getSex(), s.getPrice(), s.getAmount());
            shoePage.setImageName(shoeDAO.imageNameByImageId(s.getImageId()));
            shoePages.add(shoePage);
        }
        return shoePages;
    }

    public List<ShoeDTO> showShoePage(String name) {
        List<ShoeDTO> shoePages = new ArrayList<>();
        for (Shoe s : shoeDAO.showShoePage(name)) {
            ShoeDTO shoePage = new ShoeDTO(s.getId(), s.getName(), s.getSize(), s.getColor(), s.getSeason(), s.getSex(), s.getPrice(), s.getAmount());
            if (!shoePages.contains(shoePage)) {
                shoePages.add(shoePage);
            }
        }
        return shoePages;

    }
}
