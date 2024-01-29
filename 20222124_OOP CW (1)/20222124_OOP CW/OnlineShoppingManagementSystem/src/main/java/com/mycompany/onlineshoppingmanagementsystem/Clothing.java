/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem;

/**
 *
 * @author Ranugi Thihansa
 */
public class Clothing extends Product {

    private double size;
    private String colour;

    public Clothing() {
    }

    public Clothing(String productID, double size, String colour) {
        super.setProductID(productID);
        this.size = size;
        this.colour = colour;
    }

//    public Clothing(String productID, String productName, int noOfAvailableItems, double price) {
//        super(productID, productName, noOfAvailableItems, price);
//    }

    public Clothing(double size, String colour, String productID, String productName, int noOfAvailableItems, double price) {
        super(productID, productName, noOfAvailableItems, price);
        this.size = size;
        this.colour = colour;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

}
