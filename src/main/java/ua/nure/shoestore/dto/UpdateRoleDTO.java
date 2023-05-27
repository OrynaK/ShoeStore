package ua.nure.shoestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.shoestore.entity.enums.Role;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDTO {
    private Long id;
    private Role role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
