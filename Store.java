/* Name: Viv Banks
Course: CNT 4714 – Fall 2026
Assignment title: Project 1 – An Event-driven Enterprise Simulation
Date: Sunday September 13, 2026
*/

/*
STEPS:

TOP SECTION
1. user enters ID of item and quantity, user presses enter
2. Inventory.csv is scanned every time. item by ID is searched
	a. moreover, Account for ID not found
	b. after found quanity requested is larger then req = error
	c. otherwise do calc for (quantity req * price from CSV) Details section 
		d. prompt for add item is in USER CONTROLS done.
		
	3. use linked list to store items
	4. items can be deleted by last one added (use java linked list pop)
		

*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;


public class Store implements ActionListener{
   //class constants
   private static final int WINDOW_WIDTH = 450; //pixels
   private static final int WINDOW_HEIGHT = 235; //pixels
   private static final int FIELD_WIDTH = 20;   //characters
   private static final int AREA_WIDTH = 40;   //characters

   private static final FlowLayout LAYOUT_STYLE = new FlowLayout();

    private static final String LEGEND = "Welcome to Nile.com - FALL 2026\n" +
            "Enter item ID and quantity, then press Run to calculate subtotal.\n" +
            "Use the buttons below to manage your shopping cart."; 
   //instance variables
   //window for GUI
   private JFrame window = new JFrame("Nile.com - FALL 2026");

   //shopping cart
   private LinkedList<String> Cart = new LinkedList<>();

   //legend
   private JTextArea legendArea = new JTextArea(LEGEND, 2, AREA_WIDTH);

   //user entry area for ID
   private JLabel ItemIDTag = new JLabel("Enter item ID for Item :"); //list item number in Cart
   private JTextField  ItemIDText = new JTextField(FIELD_WIDTH);

   //user entry area for Quanitity
   private JLabel QuanitityTag = new JLabel("       Enter item quantity for Item:"); //list item number in Cart
   private JTextField  QuanitityText = new JTextField(FIELD_WIDTH);

   //entry area for windchill result
   private JLabel SubtotalTag = new JLabel(" Current Subtotal for # items(s)");
   private JTextField  SubtotalText = new JTextField(FIELD_WIDTH);



   //run button
   private JButton RunButton = new JButton("Run");

//search 
   
   private JButton SearchButton = new JButton("Search for item #");

//empty 
   
   private JButton EmptyButton = new JButton("Empty Cart - Start A New Order");



//add item to linked list
   
   private JButton AddButton = new JButton(" Add Item # To Cart");


//c
   
   private JButton CheckOutButton = new JButton(" Check Out");

//exit
   
   private JButton ExitButton = new JButton(" Exit (Close App)");



   //Store():  constructor
   public Store() {
       //configure GUI
       window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      legendArea.setEditable(false);
      legendArea.setLineWrap(true);
      legendArea.setWrapStyleWord(true);
      legendArea.setBackground(window.getBackground());

      SubtotalText.setEditable(false);
      //SubtotalText.setBackground(Color.WHITE);

      //register event listener
      RunButton.setBackground(Color.RED);
      RunButton.setOpaque(true);
      RunButton.addActionListener(this);



	SearchButton.setBackground(Color.BLUE);
	SearchButton.setOpaque(true);
	SearchButton.addActionListener(this);



      //add components to the container
      Container c = window.getContentPane();
      c.setLayout(LAYOUT_STYLE);

      c.add(legendArea);

//text entry boxes
      c.add(ItemIDTag);
      c.add(ItemIDText);
      c.add(QuanitityTag);
      c.add(QuanitityText);


//non-text entry tabs
      c.add(SubtotalTag); //out put label
      c.add(SubtotalText);

//buttons
      c.add(RunButton);
 	c.add(SearchButton);
   c.add(EmptyButton);
   c.add(AddButton);
   c.add(CheckOutButton);
   c.add(ExitButton);




      // c.add(DetailsForItem);

         //display GUI
         window.setVisible(true);
   }
 //actionPerformed(): run button action event handler
   public void actionPerformed(ActionEvent e) {
      //get user's responses
      String response1 = ItemIDText.getText(); //get item ID from user
      double t = Double.parseDouble(response1);
      String response2 = QuanitityText.getText(); //get item quantity from user
      double v = Double.parseDouble(response2);
      //compute item cost
      
      double ItemQuanityTotal = 0.081 * (t - 91.4) * (3.71 * Math.sqrt(v) + 5.81 - 0.25*v) + 91.4;
        int perceivedItemQuanityTotal = (int)Math.round(ItemQuanityTotal);




      if (!Cart.isEmpty()) {
         Cart.removeLast();
      }

   ClearCart(Cart);


      //display item name
      //String OutputDetailsForItem = String.valueOf(DetailsForItem); // this is the name from the file
      //SubtotalText.setText(OutputDetailsForItem);
      
      String OutputTotalForItem = String.valueOf(perceivedItemQuanityTotal); // this the math equation
      SubtotalText.setText(OutputTotalForItem);
   }

//main():  application entry point
   public static void main(String[] args) {

      new Store();
   }

   //ClearCart(): empties the cart
   public void ClearCart(LinkedList<String> list){
      list.clear();
   }
}

