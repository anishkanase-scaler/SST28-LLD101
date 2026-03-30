package models;

import enums.UserRole;

public class User {
    private final String userId;
    private String name;
    private final String email; // unique — enforced by UserRepository
    private String phone;
    private final UserRole role;

    public User(String userId, String name, String email, String phone, UserRole role) {
        this.userId = userId;
        this.name   = name;
        this.email  = email;
        this.phone  = phone;
        this.role   = role;
    }

    public String getUserId() { return userId; }
    public String getName()   { return name; }
    public String getEmail()  { return email; }
    public String getPhone()  { return phone; }
    public UserRole getRole() { return role; }

    public void setName(String name)   { this.name  = name; }
    public void setPhone(String phone) { this.phone = phone; }
}
