/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.view;

import com.raviudit.flooringmastery.model.Order;
import com.raviudit.flooringmastery.model.Product;
import com.raviudit.flooringmastery.model.Taxes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author raviu
 */
public class FlooringMasteryView {
    
    private UserIO io; 
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    public FlooringMasteryView(UserIO io){
        this.io = io; 
    }
    
    //MENUS
    /*
    ** Function Name: flooringMasteryMenu
    ** Return Type: int
    ** Purpose: Displays the main menu and takes the user's input. 
    */  
    
    public int flooringMasteryMenu(){
        
        io.print("*****************************************************");
        io.print("************* << Flooring Program >> ****************");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export all Orders");
        io.print("6. Exit");
        io.print(" ");
        io.print("*****************************************************");
        
        return io.readInt("Please select from the Listed Options", 1, 6);
    }
    
     /*
    ** Function Name: displayOrdersOnDate
    ** Return Type: void
    ** Purpose: Takes in a List of Orders for a predetermined date and displays 
                those orders on the console.
    */  
    
    public void displayOrdersOnDate(List<Order> orderList){
        
        for (Order currentOrder: orderList){
            
            String orderInfo = String.format("%s : %s, %s, %s, %s, $%s", 
                    String.valueOf(currentOrder.getOrderNumber()), 
                    currentOrder.getCustomerName(),
                    currentOrder.getState(),
                    currentOrder.getProductType(),
                    currentOrder.getArea().toString(),
                    currentOrder.getTotal().toString());
            
            io.print(orderInfo);
        }
        
        io.readString("Hit Enter To Continue");
    }
    
     /*
    ** Function Name: displayOrder
    ** Return Type: void
    ** Purpose: Takes in an Order and displays the order on the console.
    */ 
    public void displayOrder(Order order){
        
        io.print("Customer Name, State, Product, Total Area, Material Cost, Labor Cost, Taxes, Total");
        
        String orderInfo = String.format("%s %s %s %s $%s $%s $%s $%s", 
           // String.valueOf(order.getOrderNumber()), 
            order.getCustomerName(),
            order.getState(),
            order.getProductType(),
            order.getArea().toString(),
            order.getMaterialCost(),
            order.getLaborCost(),
            order.getTax(),
            order.getTotal().toString());
            
        io.print(orderInfo);
        
        io.readString("Hit Enter To Continue");
    }   
    
     /*
    ** Function Name: displayProducts
    ** Return Type: void
    ** Purpose: Takes in a List of Products and displays that list on the
                console.
    */ 
    public void displayProducts(List<Product> productList){
        
        io.print("Product Type, Cost Per Square Foot, Labor Cost Per Square Foot");
        
        for (Product currentProduct: productList){
            
            String productInfo = String.format("%s, $%s, $%s", 
                    
                    currentProduct.getProductType(),
                    currentProduct.getCostPerSquareFoot().toString(),
                    currentProduct.getLaborCostPerSquareFoot().toString());
            
            io.print(productInfo);
        }
        
        
    }
    
     /*
    ** Function Name: displayTaxes
    ** Return Type: void
    ** Purpose: Takes in a List of Taxes and displays that list on the
                console.
    */ 
    public void displayTaxes(List<Taxes> taxesList){
        
        io.print("State Code, State, Tax Rate");
        
        for (Taxes currentTax: taxesList){
            
            String taxInfo = String.format("%s, %s, $%s", 
                    
                    currentTax.getStateAbbr(),
                    currentTax.getStateName(),
                    currentTax.getTaxRate().toString());
            
            io.print(taxInfo);
        }
    }
    
    
    //Retrieval fuctions. 
    public String getDay(){
        return io.readString("Please enter the Day of the requested order [DD].");
    }
    
    public String getMonth(){
        return io.readString("Please enter the Month of the requested order [MM].");
    }
    
    public String getYear(){
        
        return io.readString("Please enter the Year of the requested order [YYYY].");
    }
    
    public LocalDate getDate(){
        
       // LocalDate ld = null; 
       // ld = LocalDate.parse(io.readLocalDate("Enter the date of the order [MM/DD/YYYY]").toString(), formatter);
       //  return ld;
       
       return io.readLocalDate("Enter the date of the order [MM/DD/YYYY]");
    }
    
    public String getCustomerName(){
        return io.readString("Please enter the name of the customer.");
    }
    
    public String getState(){
        return io.readString("Please enter the State the order will be fufilled in.");
    }
    
    public String getProductType(){
        return io.readString("Please enter the product you would like to purchase.");
    }
    
    public String getArea(){
        return io.readString("Please enter the area(feet squared) that you what to cover. Minimum order size is 100sq. feet.");
    }
    
    public String getNewCustomerName(String oldCustomerName){
        return io.readString("Please enter the name of the customer. (" + oldCustomerName + ")");
    }
    
    public String getNewState(String oldState){
        return io.readString("Please enter the State Code the order will be fufilled in. (" + oldState + ")");
    }
    
    public String getNewProductType(String oldProductType){
        return io.readString("Please enter the product you would like to purchase. (" + oldProductType +")" );
    }
    
    public String getNewArea(String oldArea){
        return io.readString("Please enter the area(feet squared) that you what to cover. Minimum order size is 100sq. feet. (" + oldArea + ")" );
    }
    
    public String getOrderNumber(){
        return io.readString("Please enter the Order Number of the order you wish to alter.");
    }
    
    public String confirmationMessage(String message){
        return io.readString(message);
    }
    
    // BANNERS
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    public void displayUnknownCommandBanner(){
        io.print("Unknown Command.");
    }
    
    public void displayExitBanner(){
        io.print("Program Complete.");
    }
}
