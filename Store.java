/* Name: Viv Banks
Course: CNT 4714 – Fall 2026
Assignment title: Project 1 – An Event-driven Enterprise Simulation
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


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

    private final JLabel Item1Label = new JLabel();
    private final JLabel Item2Label = new JLabel();
    private final JLabel Item3Label = new JLabel();
    private final JLabel Item4Label = new JLabel();
    private final JLabel Item5Label = new JLabel();
    private final LinkedList<String> cart = new LinkedList<>();
    private double cartTotal = 0.0;
    private double subtotal = 0.0;
    private int ItemCount = 0; // Track the number of items in the cart

    public Store() {
        window = new JFrame("Nile.com - FALL 2026");
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                window.getContentPane().setBackground(Color.DARK_GRAY); // prevents white gap between the window and the panels

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 8, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                formPanel.setBackground(Color.DARK_GRAY);
                formPanel.setOpaque(true);

        JLabel itemIdLabel = new JLabel("Enter ID for Item #:");
                itemIdLabel.setForeground(Color.YELLOW);
                itemIdLabel.setBackground(Color.DARK_GRAY);
        itemIdLabel.setOpaque(true);
        itemIdText = new JTextField(FIELD_WIDTH);


        JLabel quantityLabel = new JLabel("Enter quantity for Item #:");
                quantityLabel.setForeground(Color.YELLOW);
                quantityLabel.setBackground(Color.DARK_GRAY);
                quantityLabel.setOpaque(true);
        quantityText = new JTextField(FIELD_WIDTH);


                JLabel detailsLabel = new JLabel("Details for Item #:");
                detailsLabel.setForeground(Color.CYAN);
                detailsLabel.setBackground(Color.DARK_GRAY);
                detailsLabel.setOpaque(true);
                detailsText = new JTextField(FIELD_WIDTH);
                detailsText.setEditable(false);


        JLabel subtotalLabel = new JLabel("Current Subtotal for # item(s):");
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
        JButton searchButton = new JButton("Search for item #");
        deleteButton = new JButton("Delete Last Item From Cart");
        emptyButton = new JButton("Empty Cart - Start A New Order");
        addButton = new JButton("Add Item # To Cart");
        checkoutButton = new JButton("Check Out");
        JButton exitButton = new JButton("Exit (Close App)");

        userLabel.setForeground(Color.GREEN);
        userLabel.setBackground(Color.DARK_GRAY);
        userLabel.setOpaque(true);


        searchButton.addActionListener(this);
        deleteButton.addActionListener(this);
        emptyButton.addActionListener(this);
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

        //JPanel cartPanel = new JPanel(new BorderLayout(0, 5));
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

       
         if ("Search for item #".equals(command)) {
            searchItem();
        } else if ("Add Item # To Cart".equals(command)) {

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
        ItemCount++;
        String itemId = itemIdText.getText().trim();
        String qtyText = quantityText.getText().trim();
        int count = cart.size();
        if (itemId.isEmpty() || qtyText.isEmpty()) {
            JOptionPane.showMessageDialog(window, "Enter both an item ID and quantity.");
            return;
        }
            
        try {
            int quantity = Integer.parseInt(qtyText);
            if (quantity <= 0) {
                throw new NumberFormatException();
            }

            String item[] = findItem(itemId);
            if (item == null ) {
                JOptionPane.showMessageDialog(window, "Item ID not found.");
                return;
            }

           // Convert price to double
            double priceDouble = Double.parseDouble(item[4]);
            double lineTotal = priceDouble * quantity;
            subtotal += lineTotal;
            subtotalText.setText(String.format("$%.2f", subtotal));
            cart.addLast("ID: " + itemId + " | Qty: " + quantity + " | Total: $" + String.format("%.2f", lineTotal));
            System.out.println("Item added to cart: " + itemId + " | Qty: " + quantity + " | Total: $" + String.format("%.2f", lineTotal));
            
            switch (count) {
                case 0:
                    Item1Label.setText("Item " + (count + 1) + " - SKU:" + itemId + " Desc:" + detailsText.getText() + " Qty:" + quantity + " Total: " + subtotalText.getText());
                    break;
                case 1:
                    Item2Label.setText("Item " + (count + 1) + " - SKU:" + itemId + " Desc:" + detailsText.getText() + " Qty:" + quantity + " Total: " + subtotalText.getText());
                    break;
                case 2:
                    Item3Label.setText("Item " + (count + 1) + " - SKU:" + itemId + " Desc:" + detailsText.getText() + " Qty:" + quantity + " Total: " + subtotalText.getText());
                    break;
                case 3:
                    Item4Label.setText("Item " + (count + 1) + " - SKU:" + itemId + " Desc:" + detailsText.getText() + " Qty:" + quantity + " Total: " + subtotalText.getText());
                    break;
                case 4:
                    Item5Label.setText("Item " + (count + 1) + " - SKU:" + itemId + " Desc:" + detailsText.getText() + " Qty:" + quantity + " Total: " + subtotalText.getText());
                    break;
                default:
                    JOptionPane.showMessageDialog(window, "Cart is full. Cannot add more items.");
                    return;
            }
            
            System.out.println("Cart contents: " + cart);
            cartArea.setText(String.join(System.lineSeparator(), cart));
              deleteButton.setEnabled(true);
              emptyButton.setEnabled(true);
            checkoutButton.setEnabled(true);


                //print added item 

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(window, "Quantity must be a positive whole number.");
        }
    }

    private void searchItem() {
        System.out.println("The search button was clicked");
        String itemId = itemIdText.getText().trim();
         String item[] = findItem(itemId);
        boolean Available = Boolean.parseBoolean(item[2]);
        int qtyText = Integer.parseInt(quantityText.getText().trim());
        double subtotal = Double.parseDouble(item[4]);
        int inStockQty = Integer.parseInt(item[3]);

        
        if (itemId.isEmpty()) {
            JOptionPane.showMessageDialog(window, "Enter an item ID to search.");
            return;
        }

       
        //JOptionPane.showMessageDialog(window, "Item found. Price: $" + String.format("%.2f", price));


       //make if statement about quanity and price and subtotal and display in details text field


       if (Available) {
            
            addButton.setEnabled(true);
          
             if (qtyText > inStockQty) {
            JOptionPane.showMessageDialog(window, "Requested quantity exceeds available stock. Available stock: "+ inStockQty);
            return;
            }        
          subtotal = subtotal * qtyText; // Calculate subtotal based on quantity
            if (qtyText>=5 && qtyText<=9) {
                subtotal = subtotal * 0.9; // Apply 10% discount
                } else if  (qtyText>=10 && qtyText<=14) {
                subtotal = subtotal * 0.85; // Apply 15% discount
            } else if (qtyText>=15) {
                subtotal = subtotal * 0.8; // Apply 20% discount
            }
           
           //make array of indivual item prices and quantities and display in details text field
            cartTotal += subtotal; // Update cart total
        subtotalText.setText(String.format("$%.2f", cartTotal)); //subtotal for all in cart
        detailsText.setText(item[0]+" "+ item[1] +" "+ item[3] +  " $"+item[4] + " $" + String.format("%.2f", subtotal)); //details for item searched

        } else {
            JOptionPane.showMessageDialog(window, "Item is not available.");
        }
        
    }



    private void clearCart() {

       System.out.println("Empty Cart button clicked.");
        cart.clear();
        subtotal = 0.0;
        subtotalText.setText("$0.00");
        deleteButton.setEnabled(false);
        emptyButton.setEnabled(false);
        checkoutButton.setEnabled(false);
        cartArea.setText("");
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
            JOptionPane.showMessageDialog(window, "Your cart is empty.");
            return;
        }

        String lastItem = cart.removeLast();
        String[] parts = lastItem.split(" | ");
        if (parts.length >= 3) {
            try {
                double itemTotal = Double.parseDouble(parts[2].substring(1));
                subtotal -= itemTotal;
                subtotalText.setText(String.format("$%.2f", subtotal));
                cartArea.setText(String.join(System.lineSeparator(), cart));

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(window, "Error occurred while deleting the last item.");
            }
        }
    }

    
   


    private String[] findItem(String itemId) {
        String[] itemDetails = null;
        File inputFile = new File("inventory.csv");
        FileReader inputFileReader = null;
        BufferedReader inputBufferReader = null;        
        Scanner aScanner = null; // Scanner object
        String inventoryLine;
        String itemIDFromFile;
        //boolean found = false;
        //try different item numbers from the inventory tile - hard coded testing - no Gul input here ir edge case testing -
      
        try {
        inputFileReader = new FileReader (inputFile);
        inputBufferReader = new BufferedReader (inputFileReader);

        System.out.println("Search Item Is: " + itemId);
        inventoryLine = inputBufferReader.readLine();// read from file
        whileloop:while(inventoryLine!=null) {
        aScanner = new Scanner(inventoryLine).useDelimiter("\\s*,\\s*");
        itemIDFromFile = aScanner.next();


            if (itemIDFromFile.equals(itemId)) {
            System.out.println("FOUND IT!!");
            //found = true;
            itemDetails = new String[] {itemIDFromFile, aScanner.next(), aScanner.next(), aScanner.next(), aScanner.next()};
       
            break whileloop;

            }else {
                inventoryLine = inputBufferReader.readLine(); // read next line from file
            }
        //end while

            // end try
            }
        
        } catch(FileNotFoundException fileNotFoundException) {
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
