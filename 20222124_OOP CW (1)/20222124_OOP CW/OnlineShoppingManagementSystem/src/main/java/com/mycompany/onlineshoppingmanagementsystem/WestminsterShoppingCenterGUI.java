/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

/**
 *
 * @author Ranugi Thihansa
 */
public class WestminsterShoppingCenterGUI extends JFrame implements ActionListener {

    private JComboBox<String> categoryComboBox;
    private JTable productTable;
    private JTable cartTable;
    private JTextArea productDetails;
    private DefaultTableModel tableModel;
    private JButton addToCartButton;
    private List<Product> productList;
    private List<Product> shoppingCart;
    private List<Order> orderList;
    private User currentUser;
    String username;
    WestminsterShoppingManager shoppingManager;
    int buyingQty;
    double firstOrderDiscount = 0;
    double sameCategoryDiscount = 0;
    double finalTotal = 0;
    double total = 0;
    int electronicsCount = 0;
    int clothingCount = 0;
    private boolean firstOrder = true;

    WestminsterShoppingCenterGUI(WestminsterShoppingManager shoppingManager, String username) {
        this.username = username;
        setTitle("WestminsterShoppingCenter");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.shoppingManager = shoppingManager;

        productList = new ArrayList<>();
        shoppingCart = new ArrayList<>();
        orderList = new ArrayList<>();
        currentUser = new User();

        loadOrders();
        if (orderList.isEmpty()) {
            firstOrder = true;
        } else {
            for (Order order : orderList) {
                if (order.getUsername().equals(username)) {
                    firstOrder = false;
                    break;
                }
            }
        }

        categoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothes"});
        JButton shoppingCartButton = new JButton("Shopping Cart");

        productDetails = new JTextArea("Product Details");
        addToCartButton = new JButton("Add to Shopping Cart");

        // Set up layout
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Product Category:"));
        topPanel.add(categoryComboBox);
        topPanel.add(shoppingCartButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setSize(800, 100);
        centerPanel.setBackground(Color.black);

        centerPanel.add(productDetails, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addToCartButton);
        productDetails.setSize(800, 20);
        String[] columns = {"Product ID", "Name", "Category", "Price ($)", "Info"};
        tableModel = new DefaultTableModel(columns, 0);
        productTable = new JTable(tableModel);
        productTable.setModel(tableModel);
        productTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = productTable.getSelectedRow();
                String selectedProductID = (String) productTable.getValueAt(selectedRow, 0);
                Product selectedProduct = shoppingManager.findProductByID(selectedProductID);
                if (productTable.getValueAt(selectedRow, 2).equals("Electronics")) {
                    productDetails.setText("Product ID: " + selectedProduct.getProductID() + "\nCategory: Electronics" + "\nName: "
                            + selectedProduct.getProductName() + "\nBrand: " + ((Electronics) selectedProduct).getBrand() + "\nWarranty Period: " + ((Electronics) selectedProduct).getWarrantyPeriod() + "\nAvailable Quantity: " + selectedProduct.getNoOfAvailableItems());
                } else {
                    productDetails.setText("Product ID: " + selectedProduct.getProductID() + "\nCategory: Clothing" + "\nName: " + selectedProduct.getProductName() + "\nSize: " + ((Clothing) selectedProduct).getSize() + "\nColour: " + ((Clothing) selectedProduct).getColour() + "\nAvailable Quantity: " + selectedProduct.getNoOfAvailableItems());
                }
            }
        });

        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                tableModel.setRowCount(0);
                int selectedIndex = categoryComboBox.getSelectedIndex();
                if (selectedIndex == 1) {
                    loadElectronicsTable();
                } else if (selectedIndex == 2) {
                    loadClothingTable();
                } else if (selectedIndex == 0) {
                    loadAlllProducts();
                }
            }
        });

        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayShoppingCart();
            }

        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                int quantity = 0;
                if (selectedRow != -1) {

                    JDialog quantityDialog = new JDialog();
                    quantityDialog.setSize(200, 200);
                    quantityDialog.setLocationRelativeTo(null);
                    JPanel newPanel = new JPanel();
                    JLabel buyingQuantityLbl = new JLabel("Enter the buying quantity : ");
                    newPanel.add(buyingQuantityLbl);
                    JTextField productQuantity = new JTextField(10);
                    JButton btn = new JButton("OK");
                    quantityDialog.add(newPanel);
                    newPanel.add(productQuantity);
                    newPanel.setSize(200, 200);
                    newPanel.add(btn);
                    quantityDialog.setVisible(true);

                    btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            buyingQty = Integer.parseInt(productQuantity.getText());
                            Product selectedProduct = null;
                            if (productTable.getValueAt(selectedRow, 2).equals("Electronics")) {
                                selectedProduct = new Electronics("", 0, (String) productTable.getValueAt(selectedRow, 0), productTable.getValueAt(selectedRow, 1) + " " + productTable.getValueAt(selectedRow, 4), buyingQty, buyingQty * Double.parseDouble("" + productTable.getValueAt(selectedRow, 3)));
                                electronicsCount += buyingQty;

                            } else {
                                selectedProduct = new Clothing(0, "", (String) productTable.getValueAt(selectedRow, 0), productTable.getValueAt(selectedRow, 1) + " " + productTable.getValueAt(selectedRow, 4), buyingQty, buyingQty * Double.parseDouble("" + productTable.getValueAt(selectedRow, 3)));
                                clothingCount += buyingQty;

                            }

                            shoppingCart.add(selectedProduct);
                            total += selectedProduct.getPrice();
                            firstOrderDiscount = 0.1 * total;

                            Product findProductByID = shoppingManager.findProductByID(selectedProduct.getProductID());

                            findProductByID.setNoOfAvailableItems(findProductByID.getNoOfAvailableItems() - buyingQty);

                            if (findProductByID.getNoOfAvailableItems() < 3) {
                                productTable.setSelectionBackground(Color.red);

                            }
                            JOptionPane.showMessageDialog(null, "Added to Shopping Cart!");
                        }

                    }
                    );

                }

            }
        }
        );

        JScrollPane scrollPane = new JScrollPane(productTable);

        productTable.setGridColor(Color.BLACK);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        add(centerPanel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadAlllProducts() {
        List<Product> allProducts = shoppingManager.getAllProducts();
        //  Object[] row;
        for (Product product : allProducts) {
            if (product instanceof Electronics) {
                Object[] row = {product.getProductID(), product.getProductName(),
                    "Electronics", product.getPrice(), ((Electronics) product).getBrand() + " Warranty: " + ((Electronics) product).getWarrantyPeriod()};
                tableModel.addRow(row);
            } else if (product instanceof Clothing) {
                Object[] row = {product.getProductID(), product.getProductName(),
                    "Clothing", product.getPrice(), ((Clothing) product).getSize() + " Colour: " + ((Clothing) product).getColour()};
                tableModel.addRow(row);
            }

        }

    }

    private void loadElectronicsTable() {

        ArrayList<Electronics> allProducts = shoppingManager.getElectronicProducts();
        for (Electronics product : allProducts) {
            Object[] row = {product.getProductID(), product.getProductName(), "Electronics", product.getPrice(), product.getBrand() + " Warranty " + product.getWarrantyPeriod()};
            tableModel.addRow(row);
        }

    }

    private void loadClothingTable() {

        ArrayList<Clothing> allProducts = shoppingManager.getClothingProducts();
        for (Clothing product : allProducts) {
            Object[] row = {product.getProductID(), product.getProductName(), "Clothing", product.getPrice(), product.getSize() + " Color " + product.getColour()};
            tableModel.addRow(row);
        }

    }

    private void displayShoppingCart() {
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setSize(420, 420);
        shoppingCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shoppingCartFrame.setLocationRelativeTo(null);
        shoppingCartFrame.setVisible(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        String[] cartColumns = {"Product", "Quantity", "Price ($)"};
        DefaultTableModel cartTableModel = new DefaultTableModel(cartColumns, 0);
        JTable cartTable = new JTable(cartTableModel);
        cartTable.setModel(cartTableModel);
        JScrollPane tableScrollPane = new JScrollPane(cartTable);
        mainPanel.add(tableScrollPane);
        shoppingCartFrame.add(mainPanel);
        loadCartTable(cartTableModel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        JLabel totalLabel = new JLabel("Total : ");
        bottomPanel.add(totalLabel);
        totalLabel.setText("Total : " + total);
        

        JLabel firstDisLabel = new JLabel("First Purchase Discount (10%): ");
        bottomPanel.add(firstDisLabel);
        firstDisLabel.setText("First Purchase Discount (10%): " + firstOrderDiscount);

        JLabel sameCategoryDisLbl = new JLabel("Three items in same category discount (20%) : ");
        bottomPanel.add(sameCategoryDisLbl);
        sameCategoryDisLbl.setText("Three items in same category discount (20%) : " + sameCategoryDiscount);

        JLabel finalTotLbl = new JLabel("Final Total : ");
        bottomPanel.add(finalTotLbl);
        finalTotLbl.setText("Final Total : " + finalTotal);

        mainPanel.add(bottomPanel);

    }

    void loadCartTable(DefaultTableModel cartTableModel) {
        sameCategoryDiscount = 0;

        for (Product allProduct : shoppingCart) {
            Object[] row = {(allProduct.getProductID() + " " + allProduct.getProductName()), allProduct.getNoOfAvailableItems(), allProduct.getPrice()};
            cartTableModel.addRow(row);
            if (electronicsCount >= 3 && allProduct instanceof Electronics) {
                sameCategoryDiscount += allProduct.getPrice() * 0.2;
            } else if (clothingCount >= 3 && allProduct instanceof Clothing) {
                sameCategoryDiscount += allProduct.getPrice() * 0.2;
            }
        }
        finalTotal = total - firstOrderDiscount - sameCategoryDiscount;

    }

    public void saveOrders() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("orders.txt"))) {
            outputStream.writeObject(orderList);
            System.out.println("Order data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving order data: " + e.getMessage());
        }
    }

    public void loadOrders() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("orders.txt"))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                this.orderList = (ArrayList) obj;

                System.out.println("Orders loaded from file.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading orders from file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new WestminsterShoppingCenterGUI(new WestminsterShoppingManager(), "user1").setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
