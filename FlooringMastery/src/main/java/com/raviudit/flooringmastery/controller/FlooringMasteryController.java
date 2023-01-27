/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.controller;

import com.raviudit.flooringmastery.dao.FlooringMasteryFilePersistanceException;
import com.raviudit.flooringmastery.model.Order;
import com.raviudit.flooringmastery.model.Product;
import com.raviudit.flooringmastery.model.Taxes;
import com.raviudit.flooringmastery.service.FlooringMasteryAreaIsNotValidException;
import com.raviudit.flooringmastery.service.FlooringMasteryDateIsNotInTheFutureException;
import com.raviudit.flooringmastery.service.FlooringMasteryDayIsNotValidException;
import com.raviudit.flooringmastery.service.FlooringMasteryFieldIsBlankException;
import com.raviudit.flooringmastery.service.FlooringMasteryMonthIsNotValidException;
import com.raviudit.flooringmastery.service.FlooringMasteryNameIsNotValidException;
import com.raviudit.flooringmastery.service.FlooringMasteryOrderNumberIsNotValidException;
import com.raviudit.flooringmastery.service.FlooringMasteryProductDoesNotExistException;
import com.raviudit.flooringmastery.service.FlooringMasteryServiceLayer;
import com.raviudit.flooringmastery.service.FlooringMasteryStateCodeDoesNotExistException;
import com.raviudit.flooringmastery.service.FlooringMasteryYearIsNotValidException;
import com.raviudit.flooringmastery.view.FlooringMasteryView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author raviu
 */
public class FlooringMasteryController {
    
    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;
    
    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceLayer service){
        this.view = view;
        this.service = service;
    }
    
    /*
    ** Function Name: run
    ** Return Type: Void
    ** Purpose: Captures the users's input from the displayed options generated 
        by getMenuSelection(), then funnels the user into the appropriate 
        function.
    */
    
    public void run(){
        
        boolean menuOpen = true;
        int menuSelection = 0;
        
        do{
            try{
                while(menuOpen){

                    menuSelection = getMenuSelection();

                    switch(menuSelection){
                        case 1:

                            displayOrdersOnDate();
                            break;
                        case 2:

                            addOrder();
                            break;
                        case 3:

                            editOrder();
                            break;
                        case 4:

                            removeOrder();
                            break;
                        case 5:

                            exportAll();
                            break;
                        case 6:

                            view.displayExitBanner();

                            menuOpen = false; 
                            break;
                        default:
                            unknownCommand();
                    }
                }

            } catch (FlooringMasteryFilePersistanceException | FlooringMasteryFieldIsBlankException | 
                    FlooringMasteryYearIsNotValidException |  FlooringMasteryMonthIsNotValidException |
                    FlooringMasteryDayIsNotValidException | FlooringMasteryOrderNumberIsNotValidException e){

                view.displayErrorMessage(e.getMessage());
            }
        }while(menuOpen);
        
    }
    
        /*
    ** Function Name: getMenuSelection
    ** Return Type: int
    ** Purpose: Displayes a menu defined by flooringMasteryMenu in 
        view.FlooringMasteryView and asks for the user's input. Return the input
        to be used in run().
    */
    
    private int getMenuSelection(){
        
        return view.flooringMasteryMenu();
    }
    
         /*
    ** Function Name: displayOrdersOnDate
    ** Return Type: void
    ** Purpose: Creates a list of Orders and displays them through the console.
    */   
    
    private void displayOrdersOnDate() throws FlooringMasteryFilePersistanceException,
                                              FlooringMasteryFieldIsBlankException,
                                              FlooringMasteryYearIsNotValidException,
                                              FlooringMasteryMonthIsNotValidException,
                                              FlooringMasteryDayIsNotValidException{
        
        LocalDate ld = view.getDate(); //Get date the orders are on. 
        
        List<Order> orderList = service.getAllOrdersOnDate(ld); //Create list and populate it with Order Objects.
        view.displayOrdersOnDate(orderList); //Display Orders
        
    }
    
             /*
    ** Function Name: addOrder
    ** Return Type: void
    ** Purpose: Gathers the information needed to create an order object. 
        Afterwards, the function compiles the object through the compileOrder 
        fuction in service.FlooringMasteryService.
    
        After the function is compiled the programs asks the user if the order 
        should be added or discarded. Added orders are written to file. 
    */ 
    
    private void addOrder() throws FlooringMasteryFilePersistanceException,
                                   FlooringMasteryYearIsNotValidException,
                                   FlooringMasteryMonthIsNotValidException,
                                   FlooringMasteryDayIsNotValidException{
        
        boolean functionError = false; 
        
        
        //GET DATE
        LocalDate orderDate = null; 
        //String[] date = new String[3];
        
        do{
            try{
                orderDate = view.getDate();
                
                //date = getDate();
                service.isAppointmentInTheFuture(orderDate);
                
                functionError = false;
            } catch ( FlooringMasteryDateIsNotInTheFutureException e){
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
        }while (functionError);

        String[] orderInfo = new String[4];
        
        //GET CUSTOMER NAME
        do{
            try{
                orderInfo[0] = view.getCustomerName();
                service.isFieldBlank(orderInfo[0]);
                service.isNameValid(orderInfo[0]);  

                functionError = false;
            } catch (FlooringMasteryFieldIsBlankException | FlooringMasteryNameIsNotValidException e){
              
                functionError = true; 
                view.displayErrorMessage(e.getMessage());  
            }
        } while (functionError);
        
        //GET STATE OF PURCHASE
        List<Taxes> stateList = service.getTaxes();
        view.displayTaxes(stateList);
        
        do{
            try{
                orderInfo[1] = view.getState();
                service.isFieldBlank(orderInfo[1]);
                service.areServicesAvailableThere(orderInfo[1]);
                
                functionError = false; 
            } catch( FlooringMasteryFieldIsBlankException | FlooringMasteryStateCodeDoesNotExistException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());  
            }
        } while (functionError);
        
        //GET PRODUCT
        List<Product> productList = service.getProducts();
        view.displayProducts(productList);
        
        do{
            try{
                orderInfo[2] = view.getProductType();
                service.isFieldBlank(orderInfo[2]);
                service.isProductAvailable(orderInfo[2]);
                
                functionError = false;
            } catch( FlooringMasteryFieldIsBlankException | FlooringMasteryProductDoesNotExistException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());  
            }
        } while (functionError);
        
        // GET AREA
        do{
            try{
                orderInfo[3] = view.getArea();
                service.isFieldBlank(orderInfo[3]);
                service.isAreaValid(orderInfo[3]);
                
                functionError = false;
            } catch( FlooringMasteryFieldIsBlankException | FlooringMasteryAreaIsNotValidException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());  
            }
        } while (functionError);
        
        
        Order newOrder = new Order(1); //creates a blank Order object to be filled. 
        
        newOrder = service.compileOrder(newOrder, orderInfo[0], orderInfo[1], orderInfo[2], orderInfo[3]); //Compiles the order
        
        view.displayOrder(newOrder); //Displays order that was compiled. 
        
        boolean closeFunction = false; 
        
        String confirmOrder = "n";
        
        
        
        //Confirmation loop to determine if order should be added or discarded. 
        while (closeFunction == false){
            confirmOrder = view.confirmationMessage("Would you like to submit this order? (Y/N)");
            if (confirmOrder.equalsIgnoreCase("y")){
                
                service.addOrder(orderDate, newOrder);
                view.confirmationMessage("Order Submitted");
                closeFunction = true;
            } else if (confirmOrder.equalsIgnoreCase("n")){
                
                view.confirmationMessage("Order Not Submitted");
                closeFunction = true;
            } else {

                view.confirmationMessage("Unspecified option.");
                closeFunction = false;
            }
        }
        
    }

                 /*
    ** Function Name: editOrder
    ** Return Type: void
    ** Purpose: Gathers the information needed to edit an order. Afterwards, the
        information is sent to the compileOrder function in 
        service.FlooringMasteryServiceLayer. 
    
        The order returned from compileOrder is displayed to the user, at which 
        point the user can decide to submit the submitted order or discard the 
        changes, leaving the order unfilled. 
    */ 
    
    private void editOrder() throws FlooringMasteryFilePersistanceException, FlooringMasteryYearIsNotValidException,
                                    FlooringMasteryMonthIsNotValidException, FlooringMasteryDayIsNotValidException,
                                    FlooringMasteryOrderNumberIsNotValidException{
        
        boolean functionError = false;
        
        //GET DATE
        //String[] date = getDate(); //used to pull the file the order is on. 
        LocalDate orderDate = view.getDate();
        
        //GET ORDERNUMBER
        String ordernum = "0";
        
        do{
            try{
                
                ordernum = view.getOrderNumber();
                service.isFieldBlank(ordernum);
                service.isOrderNumberValid(orderDate, ordernum);
                
                functionError = false;
            } catch(FlooringMasteryFieldIsBlankException | FlooringMasteryOrderNumberIsNotValidException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
        }while(functionError);
        
        int orderNumber = Integer.parseInt(ordernum);
        
        Order workingOrder = service.getOrder(orderDate, orderNumber);
        
        view.displayOrder(workingOrder);
        
        //GET NAME
        String newName = "0";
        do{
            try{
                newName = view.getNewCustomerName(workingOrder.getCustomerName());
                service.isNameValid(newName);
                
                functionError = false;
            }catch(FlooringMasteryNameIsNotValidException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
        }while(functionError);
        
        
        //GET TAXES
        List<Taxes> stateList = service.getTaxes();
        view.displayTaxes(stateList);
        String newState = "0";
        
        do{
            try{
                newState = view.getNewState(workingOrder.getState());
                
                if(newState.isBlank()){
                    newState = workingOrder.getState();
                }
                service.areServicesAvailableThere(newState);
                
                functionError = false;
            }catch(FlooringMasteryStateCodeDoesNotExistException | FlooringMasteryFilePersistanceException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
            
        }while(functionError);
        
        //GET PRODUCT
        List<Product> productList = service.getProducts();
        view.displayProducts(productList);
        String newProduct = "0";
        
        do {
            try{
                newProduct = view.getNewProductType(workingOrder.getProductType());
                
                if(newProduct.isBlank()){
                    newProduct = workingOrder.getProductType();
                }
                service.isProductAvailable(newProduct);
                
                functionError = false;
            }catch(FlooringMasteryProductDoesNotExistException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
        }while(functionError);
        
        //GET AREA
        String newArea = "0";
        
        do{
            try{
                newArea = view.getNewArea(workingOrder.getArea().toString());
                
                if (newArea.isBlank()){
                    newArea = workingOrder.getArea().toString();
                }
                service.isAreaValid(newArea);
                
                functionError = false;
            } catch (FlooringMasteryAreaIsNotValidException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
        }while(functionError);
        
        workingOrder = service.compileOrder(workingOrder, newName, newState, newProduct, newArea);
        
        view.displayOrder(workingOrder);
        
        boolean closeFunction = false;
        
        String confirmEdit = "n";
       
        
        while (closeFunction == false){
            confirmEdit = view.confirmationMessage("Would you like to submit this edited order? (Y/N)");
             
            if (confirmEdit.equalsIgnoreCase("y")){
                
                service.editOrder(orderDate, workingOrder);
                view.confirmationMessage("Order Edited");
                closeFunction = true;
            } else if (confirmEdit.equalsIgnoreCase("n")){
                
                view.confirmationMessage("Order Not Edited");
                closeFunction = true;
            } else {

                view.confirmationMessage("Unspecified option.");
                closeFunction = false;
            }
        }
    }
    
                     /*
    ** Function Name: removeOrder
    ** Return Type: void
    ** Purpose: Gathers the information needed to remove an order. The order is 
        then displayed to the user, at which point the user can decide to 
        delete the order or keep it. 
    */ 
    
    private void removeOrder() throws FlooringMasteryFilePersistanceException{
        
        //String[] date = getDate();
        LocalDate orderDate = view.getDate();
        boolean functionError = false;
        

        //GET ORDERNUMBER
        String ordernum = "0";
        
        do{
            try{
                
                ordernum = view.getOrderNumber();
                service.isFieldBlank(ordernum);
                service.isOrderNumberValid(orderDate, ordernum);
                
                functionError = false;
            } catch(FlooringMasteryFieldIsBlankException | FlooringMasteryOrderNumberIsNotValidException e){
                
                functionError = true; 
                view.displayErrorMessage(e.getMessage());
            }
        }while(functionError);
        
        int orderNumber = Integer.parseInt(ordernum);
        
        view.displayOrder(service.getOrder(orderDate, orderNumber));
        
        String confirmDeletion = "n";
        
        boolean closeFunction = false;
        
        
        
        while (closeFunction == false){
            confirmDeletion = view.confirmationMessage("Would you like to delete this order? (Y/N)");
            
            if(confirmDeletion.equalsIgnoreCase("y")){

                service.removeOrder(orderDate, orderNumber);
                view.confirmationMessage("Order deleted.");
                closeFunction = true;

            } else if (confirmDeletion.equalsIgnoreCase("n")){

                view.confirmationMessage("Order not deleted.");
                closeFunction = true;
            } else {

                view.confirmationMessage("Unspecified option.");
                closeFunction = false;
            }
        }
        
    }
    
                         /*
    ** Function Name: exportAll
    ** Return Type: void
    ** Purpose: Exports all order data from the order files found in the Orders
        folder to a single file. 
    */ 
    
    private void exportAll() throws FlooringMasteryFilePersistanceException{
        
        service.exportOrderData(); 
        view.confirmationMessage("Order Exported.");
    }
 
                         /*
    ** Function Name: unknownCommand
    ** Return Type: void
    ** Purpose: Used in case the switch function breaks. Displays a message
        saying the command is unknown and allows the loop to continue. 
    */ 
    
    private void unknownCommand(){
        view.displayUnknownCommandBanner();
    }
    
    
}
