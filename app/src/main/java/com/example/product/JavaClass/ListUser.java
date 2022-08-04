package com.example.product.JavaClass;

public class ListUser {
    private int id;
    private String user;
    private String password;
    private String email;

    public ListUser(int id, String user, String password, String email) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.email = email;
    }

    public ListUser() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
