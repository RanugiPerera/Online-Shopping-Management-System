/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem;

import java.util.List;

/**
 *
 * @author Ranugi Thihansa
 */
public interface ShoppingManager {

    void addProduct();

    void removeProduct(String productID);

    List<Product> getAllProducts();

    List<Electronics> getElectronicProducts();

    List<Clothing> getClothingProducts();

    void saveProduct();

    void loadProducts();

    void displayGUI();

    Product findProductByID(String productID);
}
