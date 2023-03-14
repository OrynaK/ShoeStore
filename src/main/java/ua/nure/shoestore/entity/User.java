package ua.nure.shoestore.entity;

import java.util.Objects;

public class User {
    private String name;
    private String surname;
    private String email;
    private String password;
    private Roles role;
    private boolean blocked;

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

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
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
        return blocked == user.blocked
                && name.equals(user.name)
                && surname.equals(user.surname)
                && email.equals(user.email)
                && password.equals(user.password)
                && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email, password, role, blocked);
    }
}
