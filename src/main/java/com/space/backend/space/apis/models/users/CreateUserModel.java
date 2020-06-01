package com.space.backend.space.apis.models.users;

public class CreateUserModel extends User {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CreateUserModel{" +
                "password='" + password + '\'' +
                '}' + super.toString();
    }
}
