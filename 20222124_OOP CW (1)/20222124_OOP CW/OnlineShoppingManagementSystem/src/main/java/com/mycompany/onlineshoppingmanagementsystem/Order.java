/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Ranugi Thihansa
 */
public class Order implements Serializable{

    private ArrayList<Product> products;
   
    private String username;

    public Order() {
    }

    public Order(ArrayList<Product> products, String username) {
        this.products = products;
        this.username = username;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    

  }
