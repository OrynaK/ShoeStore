package ua.nure.shoestore.entity;

import ua.nure.shoestore.entity.enums.Role;

import java.util.List;
import java.util.Objects;

public class User {
    private long userId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;
    private boolean blocked;
    private List<Long> addressesId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Long> getAddressesId() {
        return addressesId;
    }

    public void setAddressesId(List<Long> addressesId) {
        this.addressesId = addressesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId
                && blocked == user.blocked
                && name.equals(user.name)
                && surname.equals(user.surname)
                && email.equals(user.email)
                && password.equals(user.password)
                && role == user.role
                && addressesId.equals(user.addressesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, surname, email, password, role, blocked, addressesId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", blocked=" + blocked +
                ", addressesId=" + addressesId +
                '}';
    }
}
