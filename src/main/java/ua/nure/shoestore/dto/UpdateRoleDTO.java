package ua.nure.shoestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ua.nure.shoestore.entity.enums.Role;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDTO {
    private long user_id;
    private Role role;

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
