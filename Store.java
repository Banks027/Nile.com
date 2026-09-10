/* Name: Viv Banks
Course: CNT 4714 - Fall 2026
Assignment title: Project 1 - An Event-driven Enterprise Simulation
Date: Sunday September 13, 2026
*/
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.*;

public class Store implements ActionListener {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 260;
    private static final int FIELD_WIDTH = 20;

    private final JFrame window;
    private final JTextField itemIdText;
    private final JTextField quantityText;
    private final JTextField subtotalText;
    private final JTextField detailsText;
    private final JTextArea cartArea;
    private final JTextField Item1Text;

    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton emptyButton;
    private final JButton checkoutButton;
    private JButton searchButton;

    private int nextItemNumber = 1;

    private final JLabel subtotalLabel;
    private final JLabel Item1Label = new JLabel();
    private final JLabel Item2Label = new JLabel();
    private final JLabel Item3Label = new JLabel();
    private final JLabel Item4Label = new JLabel();
    private final JLabel Item5Label = new JLabel();

    private final JLabel itemIdLabel;
    private final JLabel quantityLabel;
    private final JLabel detailsLabel;

    private final LinkedList<String> cart = new LinkedList<>();
    private double cartTotal = 0.0;
    private double subtotal = 0.0;

    public Store() {
        window = new JFrame("Nile.com - FALL 2026");
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.getContentPane().setBackground(Color.DARK_GRAY);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 8, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(Color.DARK_GRAY);
        formPanel.setOpaque(true);

        itemIdLabel = new JLabel("Enter ID for Item #" + (cart.size() + 1) + ":", SwingConstants.RIGHT);
        itemIdLabel.setForeground(Color.YELLOW);
        itemIdLabel.setBackground(Color.DARK_GRAY);
        itemIdLabel.setOpaque(true);
        itemIdText = new JTextField(FIELD_WIDTH);

        quantityLabel = new JLabel("Enter quantity for Item #" + (cart.size() + 1) + ":", SwingConstants.RIGHT);
        quantityLabel.setForeground(Color.YELLOW);
        quantityLabel.setBackground(Color.DARK_GRAY);
        quantityLabel.setOpaque(true);
        quantityText = new JTextField(FIELD_WIDTH);

        detailsLabel = new JLabel("Details for Item #" + (cart.size() + 1) + ":", SwingConstants.RIGHT);
        detailsLabel.setForeground(Color.CYAN);
        detailsLabel.setBackground(Color.DARK_GRAY);
        detailsLabel.setOpaque(true);
        detailsText = new JTextField(FIELD_WIDTH);
        detailsText.setEditable(false);

        subtotalLabel = new JLabel("Current Subtotal for " + cart.size() + " item(s):", SwingConstants.RIGHT);
        subtotalLabel.setForeground(Color.CYAN);
        subtotalLabel.setBackground(Color.DARK_GRAY);
        subtotalLabel.setOpaque(true);
        subtotalText = new JTextField(FIELD_WIDTH);
        subtotalText.setEditable(false);

        formPanel.add(itemIdLabel);
        formPanel.add(itemIdText);
        formPanel.add(quantityLabel);
        formPanel.add(quantityText);
        formPanel.add(detailsLabel);
        formPanel.add(detailsText);
        formPanel.add(subtotalLabel);
        formPanel.add(subtotalText);

        JLabel userLabel = new JLabel("USER CONTROLS");
        searchButton = new JButton("Search for item #" + (cart.size() + 1));
        deleteButton = new JButton("Delete Last Item From Cart");
        emptyButton = new JButton("Empty Cart - Start A New Order");
        addButton = new JButton("Add Item #" + (cart.size() + 1) + " To Cart");
        checkoutButton = new JButton("Check Out");
        JButton exitButton = new JButton("Exit (Close App)");

        userLabel.setForeground(Color.GREEN);
        userLabel.setBackground(Color.DARK_GRAY);
        userLabel.setOpaque(true);

        searchButton.setActionCommand("Search for item #" + (cart.size() + 1));
        searchButton.addActionListener(this);
        deleteButton.addActionListener(this);
        emptyButton.addActionListener(this);
        addButton.setActionCommand("Add Item #" + (cart.size() + 1) + " To Cart");
        addButton.addActionListener(this);
        checkoutButton.addActionListener(this);
        exitButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 8, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setOpaque(true);

        JPanel labelRow = new JPanel(new BorderLayout());
        labelRow.setBackground(Color.DARK_GRAY);
        labelRow.add(userLabel, BorderLayout.CENTER);

        buttonPanel.add(labelRow);
        buttonPanel.add(new JLabel(""));
        buttonPanel.add(searchButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(emptyButton);
        buttonPanel.add(exitButton);

        cartArea = new JTextArea();
        cartArea.setEditable(false);
        cartArea.setRows(8);
        cartArea.setOpaque(true);
        cartArea.setBackground(Color.BLACK);

        Item1Text = new JTextField(FIELD_WIDTH);
        Item1Text.setEditable(false);

        JLabel cartLabel = new JLabel();
        cartLabel.setForeground(Color.RED);
        cartLabel.setBackground(Color.BLACK);
        cartLabel.setOpaque(true);

        Item1Label.setForeground(Color.BLACK);
        Item1Label.setBackground(Color.WHITE);
        Item1Label.setOpaque(true);

        Item2Label.setForeground(Color.BLACK);
        Item2Label.setBackground(Color.WHITE);
        Item2Label.setOpaque(true);

        Item3Label.setForeground(Color.BLACK);
        Item3Label.setBackground(Color.WHITE);
        Item3Label.setOpaque(true);

        Item4Label.setForeground(Color.BLACK);
        Item4Label.setBackground(Color.WHITE);
        Item4Label.setOpaque(true);

        Item5Label.setForeground(Color.BLACK);
        Item5Label.setBackground(Color.WHITE);
        Item5Label.setOpaque(true);

        if (cart.isEmpty()) {
            cartLabel.setText("Your shopping cart is currently empty.");
            Item1Label.setText(" ");
            Item2Label.setText(" ");
            Item3Label.setText(" ");
            Item4Label.setText(" ");
            Item5Label.setText(" ");
            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
            checkoutButton.setEnabled(false);
        } else {
            cartLabel.setText("Your Shopping Cart currently contains " + cart.size() + " item(s):");
        }

        JPanel itemPanel = new JPanel(new GridLayout(5, 1, 0, 4));
        itemPanel.setBackground(Color.BLACK);
        itemPanel.add(Item1Label);
        itemPanel.add(Item2Label);
        itemPanel.add(Item3Label);
        itemPanel.add(Item4Label);
        itemPanel.add(Item5Label);

        JPanel cartPanel = new JPanel(new BorderLayout(0, 5));
        cartPanel.setBackground(Color.BLACK);
        cartPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        cartPanel.add(cartLabel, BorderLayout.NORTH);
        cartPanel.add(itemPanel, BorderLayout.CENTER);

        window.add(formPanel, BorderLayout.NORTH);
        window.add(cartPanel, BorderLayout.CENTER);
        window.add(buttonPanel, BorderLayout.SOUTH);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (("Search for item #" + (cart.size() + 1)).equals(command)) {
            searchItem();
        } else if (("Add Item #" + (cart.size() + 1) + " To Cart").equals(command)) {
            addItemToCart();
        } else if ("Delete Last Item From Cart".equals(command)) {
            deleteLastItem();
        } else if ("Empty Cart - Start A New Order".equals(command)) {
            clearCart();
        } else if ("Check Out".equals(command)) {
            checkout();
        } else if ("Exit (Close App)".equals(command)) {
            System.exit(0);
        }
    }

    private void addItemToCart() {
        System.out.println("The add button was clicked");
        int count = cart.size();
        String itemId = itemIdText.getText().trim();
        String qtyTextValue = quantityText.getText().trim();

        if (itemId.isEmpty() || qtyTextValue.isEmpty()) {
            JOptionPane.showMessageDialog(window, "Enter both an item ID and quantity.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(qtyTextValue);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(window, "Quantity must be a positive whole number.");
            return;
        }

        if (quantity <= 0) {
            JOptionPane.showMessageDialog(window, "Enter both an item ID and quantity.");
            return;
        }

        try {
            String[] item = findItem(itemId);
            if (item == null) {
                JOptionPane.showMessageDialog(window, "Item ID " + itemId + " not found.");
                return;
            }

            boolean available = Boolean.parseBoolean(item[2]);
            int inStockQty = Integer.parseInt(item[3]);

            if (!available) {
                JOptionPane.showMessageDialog(window, "Item is not available.");
                return;
            }

            if (quantity > inStockQty) {
                JOptionPane.showMessageDialog(window, "Requested quantity exceeds available stock. Available stock: " + inStockQty);
                return;
            }

            double priceDouble = Double.parseDouble(item[4]);
            double lineTotal = priceDouble * quantity;

            if (quantity >= 5 && quantity <= 9) {
                lineTotal *= 0.90;
            } else if (quantity >= 10 && quantity <= 14) {
                lineTotal *= 0.85;
            } else if (quantity >= 15) {
                lineTotal *= 0.80;
            }

            subtotal += lineTotal;
            cartTotal = subtotal;
            subtotalText.setText(String.format("$%.2f", subtotal));
            cart.addLast("ID: " + itemId + " | Qty: " + quantity + " | Total: $" + String.format("%.2f", lineTotal));
            System.out.println("Item added to cart: " + itemId + " | Qty: " + quantity + " | Total: $" + String.format("%.2f", lineTotal));
            detailsText.setText(item[0] + " " + item[1] + " " + item[3] + " $" + item[4] + " $" + String.format("%.2f", lineTotal));

            switch (count) {
                case 0:
                    Item1Label.setText("Item " + cart.size() + " - SKU:" + itemId + " Desc:" + detailsText.getText() + " Price Ea. " + String.format("%.2f", priceDouble) + ", Qty:" + quantity + ", Total: " + String.format("$%.2f", lineTotal));
                    break;
                case 1:
                    Item2Label.setText("Item " + cart.size() + " - SKU:" + itemId + " Desc:" + detailsText.getText() + " Price Ea. " + String.format("%.2f", priceDouble) + ", Qty:" + quantity + ", Total: " + String.format("$%.2f", lineTotal));
                    break;
                case 2:
                    Item3Label.setText("Item " + cart.size() + " - SKU:" + itemId + " Desc:" + detailsText.getText() + " Price Ea." + String.format("%.2f", priceDouble) + ", Qty:" + quantity + ", Total: " + String.format("$%.2f", lineTotal));
                    break;
                case 3:
                    Item4Label.setText("Item " + cart.size() + " - SKU:" + itemId + " Desc:" + detailsText.getText() + " Price Ea. " + String.format("%.2f", priceDouble) + ", Qty:" + quantity + ", Total: " + String.format("$%.2f", lineTotal));
                    break;
                case 4:
                    Item5Label.setText("Item " + cart.size() + " - SKU:" + itemId + " Desc:" + detailsText.getText() + " Price Ea. " + String.format("%.2f", priceDouble) + ", Qty:" + quantity + ", Total: " + String.format("$%.2f", lineTotal));
                    break;
                default:
                    JOptionPane.showMessageDialog(window, "Cart is full. Cannot add more items.");
                    return;
            }

            nextItemNumber++;
            refreshCartControls();

            itemIdText.setText(" ");
            quantityText.setText(" ");
            System.out.println("Cart contents: " + cart);
            cartArea.setText(String.join(System.lineSeparator(), cart));
            deleteButton.setEnabled(true);
            emptyButton.setEnabled(true);
            checkoutButton.setEnabled(true);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(window, "Unable to read item price.");
        }
    }

    private void searchItem() {
        System.out.println("The search button was clicked");
        String itemId = itemIdText.getText().trim();

        if (itemId.isEmpty()) {
            JOptionPane.showMessageDialog(window, "Enter an item ID to search.");
            return;
        }

        String[] item = findItem(itemId);
        if (item == null) {
            JOptionPane.showMessageDialog(window, "Item ID " + itemId + " not found.");
            addButton.setEnabled(false);
            detailsText.setText("");
            return;
        }

        boolean available = Boolean.parseBoolean(item[2]);
        int inStockQty = Integer.parseInt(item[3]);
        String qtyTextValue = quantityText.getText().trim();
        Integer requestedQty = null;
        if (!qtyTextValue.isEmpty()) {
            try {
                requestedQty = Integer.parseInt(qtyTextValue);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(window, "Quantity must be a positive whole number.");
                return;
            }
        }

        if (available) {
            detailsText.setText(item[0] + " " + item[1] + " " + item[3] + " $" + item[4] + " $" + String.format("%.2f", subtotal));
            addButton.setEnabled(true);

            if (requestedQty != null && requestedQty > inStockQty) {
                JOptionPane.showMessageDialog(window, "Requested quantity exceeds available stock. Available stock: " + inStockQty);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(window, "Item is not available.");
            addButton.setEnabled(false);
        }
    }

    private void clearCart() {
        System.out.println("Empty Cart button clicked.");
        cart.clear();
        subtotal = 0.0;
        cartTotal = 0.0;
        nextItemNumber = 1;

        subtotalText.setText("$0.00");
        detailsText.setText("");
        cartArea.setText("");
        Item1Label.setText(" ");
        Item2Label.setText(" ");
        Item3Label.setText(" ");
        Item4Label.setText(" ");
        Item5Label.setText(" ");

        refreshCartControls();
    }

    private void checkout() {
        System.out.println("The checkout button was clicked.");
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(window, "Your cart is empty.");
            return;
        }

        JOptionPane.showMessageDialog(window, "Checkout complete. Total due: $" + String.format("%.2f", subtotal));
        clearCart();
    }

    private void deleteLastItem() {
        System.out.println("The Delete Last item added to cart button was clicked.");
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(window, "Cart is empty. Cannot delete items.");
            return;
        }

        int removedIndex = cart.size() - 1;
        String lastItem = cart.removeLast();
        int totalMarker = lastItem.lastIndexOf('$');
        if (totalMarker >= 0) {
            try {
                double itemTotal = Double.parseDouble(lastItem.substring(totalMarker + 1).trim());
                subtotal -= itemTotal;
                if (subtotal < 0.0) {
                    subtotal = 0.0;
                }
                cartTotal = subtotal;
            } catch (NumberFormatException e) {
                subtotal = Math.max(0.0, subtotal);
                cartTotal = subtotal;
            }
        }

        switch (removedIndex) {
            case 0:
                Item1Label.setText(" ");
                break;
            case 1:
                Item2Label.setText(" ");
                break;
            case 2:
                Item3Label.setText(" ");
                break;
            case 3:
                Item4Label.setText(" ");
                break;
            case 4:
                Item5Label.setText(" ");
                break;
            default:
                break;
        }

        cartArea.setText(String.join(System.lineSeparator(), cart));
        subtotalText.setText(String.format("$%.2f", subtotal));
        refreshCartControls();
    }

    private void refreshCartControls() {
        int displayNumber = nextItemNumber;
        itemIdLabel.setText("Enter ID for Item #" + displayNumber + ":");
        quantityLabel.setText("Enter quantity for Item #" + displayNumber + ":");
        detailsLabel.setText("Details for Item #" + displayNumber + ":");
        subtotalLabel.setText("Current Subtotal for " + cart.size() + " item(s):");

        searchButton.setActionCommand("Search for item #" + displayNumber);
        searchButton.setText("Search for item #" + displayNumber);
        addButton.setActionCommand("Add Item #" + displayNumber + " To Cart");
        addButton.setText("Add Item #" + displayNumber + " To Cart");

        boolean hasItems = !cart.isEmpty();
        deleteButton.setEnabled(hasItems);
        emptyButton.setEnabled(hasItems);
        checkoutButton.setEnabled(hasItems);
        addButton.setEnabled(hasItems && cart.size() < 5);
    }

    private String[] findItem(String itemId) {
        String[] itemDetails = null;
        File inputFile = new File("inventory.csv");

        if (!inputFile.exists()) {
            inputFile = new File("src/inventory.csv");
        }

        System.out.println("Search Item Is: " + itemId);

        try (FileReader inputFileReader = new FileReader(inputFile);
             BufferedReader inputBufferReader = new BufferedReader(inputFileReader)) {
            String inventoryLine = inputBufferReader.readLine();
            while (inventoryLine != null) {
                try (Scanner aScanner = new Scanner(inventoryLine).useDelimiter("\\s*,\\s*")) {
                    String itemIDFromFile = aScanner.next();

                    if (itemIDFromFile.equals(itemId)) {
                        itemDetails = new String[] {itemIDFromFile, aScanner.next(), aScanner.next(), aScanner.next(), aScanner.next()};
                        break;
                    }
                }
                inventoryLine = inputBufferReader.readLine();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            JOptionPane.showMessageDialog(null, "Error: File not found", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(window, "Unable to read inventory file.");
        }

        return itemDetails;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Store::new);
    }
}