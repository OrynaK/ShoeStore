package ua.nure.shoestore.dto;

import ua.nure.shoestore.entity.enums.Role;

public class UpdateRoleDTO {
    private long user_id;
    private Role role;

    public UpdateRoleDTO() {
    }

    public UpdateRoleDTO(long user_id, Role role) {
        this.user_id = user_id;
        this.role = role;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
