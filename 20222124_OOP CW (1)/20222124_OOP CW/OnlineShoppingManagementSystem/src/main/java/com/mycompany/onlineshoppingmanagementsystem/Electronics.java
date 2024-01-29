/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem;

/**
 *
 * @author Ranugi Thihansa
 */
public class Electronics extends Product {

    private String brand;
    private int warrantyPeriod;

    public Electronics() {
    }

    public Electronics(String productID, String brand, int warrantyPeriod) {
        super.setProductID(productID);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

//    public Electronics(String productID, String productName, int noOfAvailableItems, double price) {
//        super(productID, productName, noOfAvailableItems, price);
//    }

    public Electronics(String brand, int warrantyPeriod, String productID, String productName, int noOfAvailableItems, double price) {
        super(productID, productName, noOfAvailableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

}
