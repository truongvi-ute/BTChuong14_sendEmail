package model;

import java.io.Serializable;

public class User implements Serializable {

    private String firstName;
    private String lastName;
    private String email;

    public User() {
        // Thuộc tính cũ
        firstName = "";
        lastName = "";
        email = "";
    }

    // Constructor có tham số (chỉ cho các trường ban đầu, bạn có thể thêm các trường mới nếu muốn)
    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // --- Getters and Setters cho thuộc tính cũ ---

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}