/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Ranugi Thihansa
 */
public class ShoppingCart {
    private List<Product> productList;

    public ShoppingCart() {
        this.productList = new ArrayList<Product>();
    }
    
    // Method to add a product to the cart
    public void addProduct(Product product) {
        productList.add(product);
    }

    // Method to remove a product from the cart
    public void removeProduct(Product product) {
        productList.remove(product);
    }

    // Method to calculate the total cost of items in the cart
    public double calculateTotalCost() {
        double totalCost = 0;
        for (Product productList : productList) {
            totalCost += productList.getPrice();
        }
        return totalCost;
    }

}
