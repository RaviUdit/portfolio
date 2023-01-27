/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.model;

import java.math.BigDecimal;

/**
 *
 * @author raviu
 */

/*****
 * Type: Object Definition: Taxes
 * 
 * 
 */
public class Product {
    
    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;

    //CONSTRUCTOR
    public Product(String productType){
        this.productType = productType;
    }
    
    
    // GETTERS AND SETTERS
    public String getProductType() {
        return productType;
    }

    // public void setProductType(String productType) {
    //     this.productType = productType;
    // }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }
    
}
