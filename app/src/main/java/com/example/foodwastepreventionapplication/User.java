package com.example.foodwastepreventionapplication;

import java.nio.file.attribute.UserPrincipalLookupService;

public abstract class User {
    public String email;
    public String password;
    public String name;
    public String location;

    User(String _email, String _password, String _name, String _location) {

        email = _email;
        password = _password;
        name = _name;
        location = _location;
    }

    public abstract void create();
    public abstract void update();
    public abstract void delete();
}


