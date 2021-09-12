package com.example.foodwastepreventionapplication;

import junit.framework.TestCase;

public class CustomerTest extends TestCase {

    public void testCreateUser(){
        Customer c = new Customer("abc@gmail.com", "abc123", "sonali",
                "selangor");

        assertEquals(c.email, "abc@gmail.com");
    }

}