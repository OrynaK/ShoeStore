package ua.nure.shoestore.entity.enums;

import javax.swing.*;

public enum Role {
    CLIENT("Client"),
    ADMIN("Admin"),
    PACKER("Packer"),
    WAREHOUSE("Warehouse"),
    COURIER("Courier");
    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
