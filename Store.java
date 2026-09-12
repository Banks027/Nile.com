/* Name: Viv Banks
Course: CNT 4714 - Fall 2026
Assignment title: Project 1 - An Event-driven Enterprise Simulation
Date: Sunday September 13, 2026
*/



import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.*;
/*fix item details textbox formating and total*/ 
/*fix shoping cart duplicate  */

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
    private int displayNumber;

    private final JLabel subtotalLabel;
    private final JLabel Item1Label = new JLabel();
    private final JLabel Item2Label = new JLabel();
    private final JLabel Item3Label = new JLabel();
    private final JLabel Item4Label = new JLabel();
    private final JLabel Item5Label = new JLabel();

    private final JLabel cartLabel;
    private final JLabel itemIdLabel;
    private final JLabel quantityLabel;
    private final JLabel detailsLabel;

    private final LinkedList<String> cart = new LinkedList<>();
    private final LinkedList<CartItem> invoiceItems = new LinkedList<>();
    private double cartTotal = 0.0;
    private double subtotal = 0.0;

    private static final class CartItem {
        private final String itemId;
        private final String title;
        private final double unitPrice;
        private final int quantity;
        private final int discount;
        private final double lineTotal;

        private CartItem(String itemId, String title, double unitPrice, int quantity, int discount, double lineTotal) {
            this.itemId = itemId;
            this.title = title;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
            this.discount = discount;
            this.lineTotal = lineTotal;
        }
    }

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

        cartLabel = new JLabel();
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

    
            cartLabel.setText("Your Shopping Cart Is Currently Empty.");
            Item1Label.setText(" ");
            Item2Label.setText(" ");
            Item3Label.setText(" ");
            Item4Label.setText(" ");
            Item5Label.setText(" ");
            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
            checkoutButton.setEnabled(false);
       
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
            
            cart.clear();
            itemIdText.setText("");
            quantityText.setText("");
            subtotalText.setText("");
            System.exit(0);
        }
    }

    private void addItemToCart() {
        System.out.println("The add button was clicked");
        int count = cart.size()+1;
        String itemId = itemIdText.getText().trim();
        String qtyTextValue = quantityText.getText().trim();

        if (itemId.isEmpty() || qtyTextValue.isEmpty()) {
            JOptionPane.showMessageDialog(window, "Enter both an item ID and quantity.","Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(qtyTextValue);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(window, "Quantity must be a positive whole number.","Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (quantity <= 0) {
            JOptionPane.showMessageDialog(window, "Enter both an item ID and quantity.","Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String[] item = findItem(itemId);
            
            
            int inStockQty = Integer.parseInt(item[3]);

           
        

            /*if (quantity > inStockQty) {

            JOptionPane.showMessageDialog(window, "Insufficient stock. Only " + inStockQty+ "on hand. Please reduce the quantity.","Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
                addButton.setEnabled(false);
                searchButton.setEnabled(false);
                return;
            } */

            double priceDouble = Double.parseDouble(item[4]);
            double lineTotal = priceDouble * quantity;
            int discount = 0;
            if (quantity >= 5 && quantity <= 9) {
                discount=10;
                lineTotal *= 0.90;
            } else if (quantity >= 10 && quantity <= 14) {
                discount=15;
                lineTotal *= 0.85;
            } else if (quantity >= 15) {
                discount=20;
                lineTotal *= 0.80;
            }


            
            subtotal += lineTotal;
            cartTotal = subtotal; 

            subtotalText.setText(String.format("$%.2f", subtotal));

            detailsText.setText(item[0] + " " + item[1] + " " + item[3] + " $" + item[4] +" "+ quantity +" "+ discount+"% $" + String.format("%.2f", lineTotal));

            switch (cart.size()) {
                case 0:
                    Item1Label.setText("Item " + count + " - SKU: " + itemId + " Desc: " + item[1] + " Price Ea. $" + String.format("%.2f", priceDouble) + ", Qty: " + quantity + ", Total: " + String.format("$%.2f", lineTotal));
                    cart.addLast(itemId +" "+ item[1]+" " + String.format("%.2f", priceDouble)+" " +quantity +" " + discount+ "% $" + String.format("%.2f", lineTotal)); //ID, Name, indiv Price, Quantity, bulk discount, LineTotal
                    invoiceItems.addLast(new CartItem(itemId, item[1], priceDouble, quantity, discount, lineTotal));
                    break; //split itemID string to display only the name of the item in the cart display, do not include amt in stock
                case 1:
                    Item2Label.setText("Item " + count + " - SKU: " + itemId + " Desc: " +  item[1] + " Price Ea. $" + String.format("%.2f", priceDouble) + ", Qty: " + quantity + ", Total: " + String.format("$%.2f", lineTotal));
                    cart.addLast(itemId +" "+ item[1]+" " + String.format("%.2f", priceDouble)+" " +quantity +" " + discount+ "% $" + String.format("%.2f", lineTotal)); //ID, Name, indiv Price, Quantity, bulk discount, LineTotal
                    invoiceItems.addLast(new CartItem(itemId, item[1], priceDouble, quantity, discount, lineTotal));
                    break;
                case 2:
                    Item3Label.setText("Item " + count + " - SKU: " + itemId + " Desc: " +  item[1] + " Price Ea. $" + String.format("%.2f", priceDouble) + ", Qty: " + quantity + ", Total: " + String.format("$%.2f", lineTotal));
                    cart.addLast(itemId +" "+ item[1]+" " + String.format("%.2f", priceDouble)+" " +quantity +" " + discount+ "% $" + String.format("%.2f", lineTotal)); //ID, Name, indiv Price, Quantity, bulk discount, LineTotal
                    invoiceItems.addLast(new CartItem(itemId, item[1], priceDouble, quantity, discount, lineTotal));
                    break;
                case 3:
                    Item4Label.setText("Item " + count + " - SKU: " + itemId + " Desc: " +  item[1] + " Price Ea. $" + String.format("%.2f", priceDouble) + ", Qty: " + quantity + ", Total: " + String.format("$%.2f", lineTotal));
                    cart.addLast(itemId +" "+ item[1]+" " + String.format("%.2f", priceDouble)+" " +quantity +" " + discount+ "% $" + String.format("%.2f", lineTotal)); //ID, Name, indiv Price, Quantity, bulk discount, LineTotal
                    invoiceItems.addLast(new CartItem(itemId, item[1], priceDouble, quantity, discount, lineTotal));
                    break;
                case 4:
                    Item5Label.setText("Item " + count + " - SKU: " + itemId + " Desc: " +  item[1] + " Price Ea. $" + String.format("%.2f", priceDouble) + ", Qty: " + quantity + ", Total: " + String.format("$%.2f", lineTotal));
                    cart.addLast(itemId +" "+ item[1]+" " + String.format("%.2f", priceDouble)+" " +quantity +" " + discount+ "% $" + String.format("%.2f", lineTotal)); //ID, Name, indiv Price, Quantity, bulk discount, LineTotal
                    invoiceItems.addLast(new CartItem(itemId, item[1], priceDouble, quantity, discount, lineTotal));
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
            searchButton.setEnabled(true);
            addButton.setEnabled(false);

            if (cart.size() >= 5) { //when cart is full, hide the item ID and quantity text fields and update the cart label to indicate that the cart is full
               itemIdText.setVisible(false);
                quantityText.setVisible(false);
              

            
            } else if (cart.size() < 5 && !cart.isEmpty()) {
                itemIdText.setVisible(true);
                quantityText.setVisible(true);
               cartLabel.setText("Your Shopping Cart Currently Contains " + cart.size() + " Item(s):");
            }



        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(window, "Unable to read item price.");
        }
    }

    private void searchItem() {
        System.out.println("The search button was clicked");
        String itemId = itemIdText.getText().trim();
        String qtyTextValue = quantityText.getText().trim();
        int quantity = Integer.parseInt(qtyTextValue);
        String[] item = findItem(itemId);
        boolean available = Boolean.parseBoolean(item[2]);
        int inStockQty = Integer.parseInt(item[3]);
         double priceDouble = Double.parseDouble(item[4]);
        double lineTotal = priceDouble * quantity;
        int discount = 0;
        Integer requestedQty = null;

          addButton.setEnabled(false);
            searchButton.setEnabled(true);


        if (itemId.isEmpty()) {
            JOptionPane.showMessageDialog(window, "Enter an item ID to search.");
            return;
        }

     
        if (!qtyTextValue.isEmpty()) {
            try {
                requestedQty = Integer.parseInt(qtyTextValue);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(window, "Quantity must be a positive whole number.");
                return;
            }
        }

        if (available) {
            //searchButton.setEnabled(false);
            //addButton.setEnabled(true);
            //do calculations for subtotal and total based on quantity and price
            
            if (quantity >= 5 && quantity <= 9) {
                discount=10;
                lineTotal *= 0.90;
            } else if (quantity >= 10 && quantity <= 14) {
                discount=15;
                lineTotal *= 0.85;
            } else if (quantity >= 15) {
                discount=20;
                lineTotal *= 0.80;
            }

  
          //make program not do subtotal calculations until the add button is clicked, not when the search button is clicked, so that the user can change the quantity before adding to cart
           
         } 
            if (requestedQty != null && requestedQty > inStockQty) {
              
                 JOptionPane.showMessageDialog(window, "Insufficient stock. Only " + inStockQty+ " on hand. Please reduce the quantity.","Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
                    searchButton.setEnabled(true);
                return;
        }  if (!available || inStockQty <= 0) {
            addButton.setEnabled(false);
            JOptionPane.showMessageDialog(window, "Sorry ... that item is out of stock, please try another item","Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
                   itemIdText.setText("");
                    quantityText.setText("");
             searchButton.setEnabled(true);
            
        } else {
            addButton.setEnabled(true);
            searchButton.setEnabled(false);
            detailsText.setText(item[0] + " " + item[1] + " " + item[3] + " $" + item[4] +" "+ quantity +" "+ discount+"% $" + String.format("%.2f", lineTotal));
        }
    }

    private void clearCart() {
        System.out.println("Empty Cart button clicked.");
        searchButton.setEnabled(true);
        cart.clear();
        invoiceItems.clear();
        subtotal = 0.0;
        cartTotal = 0.0;
        nextItemNumber = 1;
        int displayNumber = nextItemNumber;
        subtotalText.setText("$0.00");
        detailsText.setText("");
        cartArea.setText("");
        detailsLabel.setText("Details for Item #1:");
        Item1Label.setText(" ");
        Item2Label.setText(" ");
        Item3Label.setText(" ");
        Item4Label.setText(" ");
        Item5Label.setText(" ");


       itemIdText.setVisible(true);
        quantityText.setVisible(true);
        searchButton.setEnabled(true);
        addButton.setEnabled(false);
    
        refreshCartControls();
    }

    private void checkout() {
        addButton.setEnabled(false);
        searchButton.setEnabled(false);
        deleteButton.setEnabled(false);
       checkoutButton.setEnabled(false);
     
        itemIdText.setVisible(false);
        quantityText.setVisible(false);
        System.out.println("The checkout button was clicked.");
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(window, "Your cart is empty.");
            return;
        }
        StringBuilder invoiceText = new StringBuilder();
        invoiceText.append("Date: ").append(new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a z").format(new Date())).append("\n");
        invoiceText.append("Number of line items: ").append(invoiceItems.size()).append("\n\n");
        invoiceText.append("Item# / ID / Title / Price / Qty / Disc % / Subtotal:\n");

        for (int index = 0; index < invoiceItems.size(); index++) {
            CartItem item = invoiceItems.get(index);
            invoiceText.append(String.format("%d. %s %s $%.2f %d %d%% $%.2f\n",
                    index + 1,
                    item.itemId,
                    item.title,
                    item.unitPrice,
                    item.quantity,
                    item.discount,
                    item.lineTotal));
        }

        double taxAmount = subtotal * 0.06;
        double orderTotal = subtotal + taxAmount;

        invoiceText.append("\nOrder subtotal: $").append(String.format("%.2f", subtotal))
                .append("\nTax rate: 6%")
                .append("\nTax amount: $").append(String.format("%.2f", taxAmount))
                .append("\n\nORDER TOTAL: $").append(String.format("%.2f", orderTotal))
                .append("\n\nThanks for shopping at Nile Dot Com!");

        JOptionPane.showMessageDialog(window, invoiceText.toString(), "Nile.com - Final Invoice", JOptionPane.INFORMATION_MESSAGE);
        TransactionLog(invoiceItems);
    }


  public static void TransactionLog(LinkedList<CartItem> invoiceItems) {
        File logFile = new File("transactions.csv");
        try (FileWriter fileWriter = new FileWriter(logFile, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (CartItem item : invoiceItems) {
                double discount=item.discount/100.0;


                String timestampId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
                String dateStamp = new SimpleDateFormat("MMMM dd yyyy hh:mm:ss a z").format(new Date());
                String formattedLine = String.format("%s, %s, %s, %.2f, %d, %.2f, $%.2f, %s",
                        timestampId,
                        item.itemId,
                        item.title,
                        item.unitPrice,
                        item.quantity,
                        discount,
                        item.lineTotal,
                        dateStamp);

                bufferedWriter.write(formattedLine);
                bufferedWriter.newLine();
            }
        bufferedWriter.newLine();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to transaction log: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error writing to transaction log: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }



    private void deleteLastItem() {
        System.out.println("The Delete Last item added to cart button was clicked.");
        detailsText.setText("");
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(window, "Cart is empty. Cannot delete items.");
            return;
        }

        int removedIndex = cart.size() - 1;
        nextItemNumber--;
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

        if (!invoiceItems.isEmpty()) {
            invoiceItems.removeLast();
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

          if (cart.size() <= 5) {
               itemIdText.setVisible(true);
                quantityText.setVisible(true);
            } 
        refreshCartControls();
         addButton.setEnabled(false);
    }

    private void refreshCartControls() {
        int displayNumber = nextItemNumber;
        boolean hasItems = !cart.isEmpty();

        itemIdLabel.setText("Enter ID for Item #" + displayNumber + ":");
        quantityLabel.setText("Enter quantity for Item #" + displayNumber + ":");
        detailsLabel.setText("Details for Item #" + cart.size() + ":");
        subtotalLabel.setText("Current Subtotal for " + cart.size() + " item(s):");

        if (cart.size() >= 5) {
            cartLabel.setText("Your Shopping Cart Currently Contains " + cart.size() + " Item(s):");
                searchButton.setActionCommand("Search for item #" + cart.size());
                searchButton.setText("Search for item #" + cart.size());
                addButton.setActionCommand("Add Item #" + cart.size() + " To Cart");
                addButton.setText("Add Item #" + cart.size() + " To Cart");
   
        } else {
        searchButton.setActionCommand("Search for item #" + displayNumber);
        searchButton.setText("Search for item #" + displayNumber);
        addButton.setActionCommand("Add Item #" + displayNumber + " To Cart");
        addButton.setText("Add Item #" + displayNumber + " To Cart");

        }
      if (cart.isEmpty()) {
            cartLabel.setText("Your Shopping Cart Is Currently Empty.");
            detailsLabel.setText("Details for Item #1:");
        } 
        deleteButton.setEnabled(hasItems);
        emptyButton.setEnabled(hasItems);
        checkoutButton.setEnabled(hasItems);
        addButton.setEnabled(hasItems && cart.size() < 5);
        searchButton.setEnabled(hasItems || cart.size() < 5);
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
                         detailsLabel.setText("Details for Item #" + (cart.size() + 1)+ ":");
                        itemDetails = new String[] {itemIDFromFile, aScanner.next(), aScanner.next(), aScanner.next(), aScanner.next()};
                        break;
                    }
                }
                inventoryLine = inputBufferReader.readLine();



            }

            if (itemDetails == null) {
                addButton.setEnabled(false);
            detailsText.setText("");
                JOptionPane.showMessageDialog(window, "Item ID " + itemId + " not in file","Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
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