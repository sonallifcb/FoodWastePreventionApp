package com.example.foodwastepreventionapplication;

import java.util.List;
import java.util.Optional;

public class Seller extends User {

    public List<Product> products;

    Seller(String email, String password, String name, String location) {
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

    public void addProduct(Product p) {
        products.add(p);

    }

    public Product getProductByName(String productName) {
        for (Product p: products) {
            if (p.name.contentEquals(productName))
                return p;
        }
        return new Product();
    }

    public void updateProduct() {

    }

    public void deleteProduct() {

    }

    public void decreaseProductQuantity() {

    }
}
