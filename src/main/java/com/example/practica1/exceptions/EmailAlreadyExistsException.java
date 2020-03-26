package com.example.practica1.exceptions;

public class EmailAlreadyExistsException extends Exception {

    private String email;

    public EmailAlreadyExistsException(String email){
        super("Email: " + email + " already exists");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
