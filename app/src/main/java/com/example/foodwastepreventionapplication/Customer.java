package com.example.foodwastepreventionapplication;

import java.util.List;

public class Customer extends User {

    public List <String> favouriteRestaurant;

    Customer(String email, String password, String name, String location) {
        super(email, password, name, location);

    }

    @Override
    public void create() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }


}
