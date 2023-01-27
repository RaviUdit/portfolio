/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.dao;

import com.raviudit.flooringmastery.model.Order;
import com.raviudit.flooringmastery.model.Product;
import com.raviudit.flooringmastery.model.Taxes;
import java.util.List;

/**
 *
 * @author raviu
 */

/*****
 * Type: Interface
 * 
 * 
 */
public interface FlooringMasteryDAO {
    
    
    List<Taxes> getTaxes() throws FlooringMasteryFilePersistanceException;
    
    List<Product> getProducts() throws FlooringMasteryFilePersistanceException;
    
    Taxes getTaxesByState(String stateAbbr) throws FlooringMasteryFilePersistanceException;
    
    Product getProductByName(String productName) throws FlooringMasteryFilePersistanceException;
    
    void addOrder(String orderDate, Order order)  throws FlooringMasteryFilePersistanceException;
    
    void editOrder(String orderDate, Order order)  throws FlooringMasteryFilePersistanceException;
    
    Order getOrder(String orderDate, int orderNumber)throws FlooringMasteryFilePersistanceException;
    
    List<Order> getAllOrdersOnDate(String orderDate) throws FlooringMasteryFilePersistanceException;
    
    Order removeOrder(String orderDate, int orderNumber) throws FlooringMasteryFilePersistanceException;
    
    public void exportOrderData() throws FlooringMasteryFilePersistanceException;
}
