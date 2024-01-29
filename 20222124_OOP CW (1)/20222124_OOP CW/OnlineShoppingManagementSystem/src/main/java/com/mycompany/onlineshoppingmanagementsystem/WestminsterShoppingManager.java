/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ranugi Thihansa
 */
public class WestminsterShoppingManager implements ShoppingManager {

    private List<Product> productList;
    private ArrayList<User> userList = new ArrayList<User>();
    Scanner sc = new Scanner(System.in);

    private static final int MAX_PRODUCT = 50;
    private static final String FILE_NAME = "products.txt";
    String username;
    boolean applicationRunStatus = true;

    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
        this.loadProducts();
        this.loadUsers();

        System.out.println("Enter the Username : ");
        String username = sc.nextLine();
        System.out.println("Password : ");
        String password = sc.nextLine();

        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    this.username=username;
                    while (applicationRunStatus) {

                        selection();
                    }
                } else {
                    System.out.println("Invalid Password!");
                }
            } else {
                System.out.println("Invalid Username! ");
            }
        }

    }

    private void selection() {

        displayMenu();

        String option = sc.nextLine();

        switch (option) {
            case "1":
            case "ADD":
                addProduct();
                break;

            case "2":
            case "DELETE":
                System.out.print("Enter Product ID: ");
                String productID = sc.nextLine();
                removeProduct(productID);
                break;

            case "3":
            case "PRINT":
                getAllProducts();
                break;

            case "4":
            case "SAVE":
                saveProduct();
                break;

            case "5":
            case "LOAD":
                loadProducts();
                break;

            case "6":
                addUser();
                break;

            case "7":
                saveUser();
                break;

            case "8":
                loadUsers();
                break;

            case "9":
            case "GUI":
                displayGUI();
                break;

            case "10":
            case "EXIT":
                applicationRunStatus = false;
                System.out.println("Exiting the Menu");
                break;

        }

    }

    //  public static void main(String[] args) {
    // }
    public static void displayMenu() {
        System.out.println("Westminster Shopping Manager Menu Options:");
        System.out.println("1 or ADD    : Add a New Product");
        System.out.println("2 or DELETE : Delete Product from the System");
        System.out.println("3 or PRINT  : Print the list of Products in the System");
        System.out.println("4 or SAVE   : Save Products to a File");
        System.out.println("5 or LOAD   :Load products from file");
        System.out.println("6 : Add a user");
        System.out.println("7 : Save Users to a file");
        System.out.println("8 : Load Users from File");
        System.out.println("9 or GUI    : Display GUI");
        System.out.println("10 or EXIT    : Exit Menu");
        System.out.print("Enter your option: ");
    }

    @Override
    public void addProduct() {
        if (productList.size() < MAX_PRODUCT) {
            System.out.println("Enter the product you need to select? (Select 'E' or 'C'):");
            String selection = sc.nextLine().toUpperCase();
            System.out.println("Enter product ID: ");
            String productID = sc.nextLine();
            // Check if the product ID already exists in the productList
            if (productList.stream().anyMatch(product -> product.getProductID().equals(productID))) {
                System.out.println("Product with the same ID already exists. Cannot add the product.");
                return;  // Exit the method if the product ID already exists
            }
            System.out.println("Enter product Name: ");
            String productName = sc.nextLine();
            System.out.println("Enter the available number of product items: ");
            int noOfItems = sc.nextInt();
            System.out.println("Enter product price: ");
            Double price = sc.nextDouble();

            try {
                if (selection.equals("E")) {
                    System.out.println("Enter electronic product brand: ");
                    String brand = sc.nextLine();

                    System.out.println("Enter electronic product warranty period: ");
                    int warrantyPeriod = sc.nextInt();

                    Electronics electronicProduct = new Electronics(brand, warrantyPeriod, productID, productName, noOfItems, price);
                    productList.add(electronicProduct);
                    System.out.println("Successfully added product");
                } else if (selection.equals("C")) {
                    System.out.println("Enter product size: ");
                    Double size = sc.nextDouble();
                    System.out.println("Enter the colour of clothing product: ");
                    String colour = sc.next();

                    Clothing clothingProduct = new Clothing(size, colour, productID, productName, noOfItems, price);
                    productList.add(clothingProduct);
                    System.out.println("Successfully added product");

                } else {
                    System.out.println("Invalid Product Type");
                }
            } catch (Exception e) {
                System.out.println("Error adding Product, Please add valid data");

            }

        }

    }

    @Override
    public void removeProduct(String productID) {
        int removeIndex = -1;
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            if (product.getProductID().equals(productID)) {
                removeIndex = i;

                //Display information about the deleted product
                System.out.println("Deleted Product Information");
                System.out.println("Product ID: " + product.getProductID());
                System.out.println("Product Name: " + product.getProductName());
                System.out.println("Number of Available Items: " + product.getNoOfAvailableItems());
                System.out.println("Price: " + product.getPrice());
                if (product instanceof Electronics) {
                    System.out.println("Brand: " + ((Electronics) product).getBrand());
                    System.out.println("Warranty Period: " + ((Electronics) product).getWarrantyPeriod());

                } else if (product instanceof Clothing) {
                    System.out.println("Size: " + ((Clothing) product).getSize());
                    System.out.println("Colour: " + ((Clothing) product).getColour());

                }
                break;
            }
        }
        if (removeIndex != -1) {
            productList.remove(removeIndex);
            System.out.println("Product removed successfully");
            System.out.println("Available products: " + productList.size());
        } else {
            System.out.println("Product " + productID + " doesn't exist. Please try again");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        Collections.sort(productList, Comparator.comparing(Product::getProductID));
        for (Product product : productList) {
            System.out.println("Product List");
            System.out.println("Product ID: " + product.getProductID());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Available number of products: " + product.getNoOfAvailableItems());
            System.out.println("Product Price: " + product.getPrice());

            if (product instanceof Electronics) {
                System.out.println("Type: Electronics");
                System.out.println("Brand: " + ((Electronics) product).getBrand());
                System.out.println("Warranty Period: " + ((Electronics) product).getWarrantyPeriod());

            } else if (product instanceof Clothing) {
                System.out.println("Type: Clothing");
                System.out.println("Size: " + ((Clothing) product).getSize());
                System.out.println("Colour: " + ((Clothing) product).getColour());

            }
            System.out.println("");

        }
        return productList;
    }

    public void addUser() {

        System.out.println("Enter Username: ");
        String userName = sc.nextLine();
        System.out.println("Enter the password: ");
        String password = sc.nextLine();

        User newUser = new User(userName, password);
        userList.add(newUser);

    }

    public void saveProduct() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("products.txt"))) {
            outputStream.writeObject(productList);
            System.out.println("Product data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving product data: " + e.getMessage());
        }
    }

    public void loadProducts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("products.txt"))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                this.productList = (List) obj;

                System.out.println("Products loaded from file.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading products from file: " + e.getMessage());
        }
    }

    public void saveUser() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("users.txt"))) {
            outputStream.writeObject(userList);
            System.out.println("User data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving users data: " + e.getMessage());
        }
    }

    public void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.txt"))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                this.userList = (ArrayList) obj;

                System.out.println("Users loaded from file.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
    }

    public void displayGUI() {
        new WestminsterShoppingCenterGUI(this,username).setVisible(true);
    }

    @Override
    public ArrayList<Electronics> getElectronicProducts() {
        Collections.sort(productList, Comparator.comparing(Product::getProductID));
        ArrayList<Electronics> electronicProducts = new ArrayList<Electronics>();
        System.out.println("Product List");
        for (Product product : productList) {
            if (product instanceof Electronics) {
                electronicProducts.add((Electronics) product);

            }

        }
        return electronicProducts;
    }

    @Override
    public ArrayList<Clothing> getClothingProducts() {
        Collections.sort(productList, Comparator.comparing(Product::getProductID));
        ArrayList<Clothing> clothingProducts = new ArrayList<Clothing>();
        System.out.println("Product List");
        for (Product product : productList) {
            if (product instanceof Clothing) {
                clothingProducts.add((Clothing) product);

            }

        }
        return clothingProducts;
    }

    @Override
    public Product findProductByID(String productID) {
        for (Product product : productList) {
            if (productID.equals(product.getProductID())) {
                return product;
            }
        }
        return null;
    }

}
