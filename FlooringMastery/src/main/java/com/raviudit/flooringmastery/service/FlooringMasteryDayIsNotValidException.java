/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.flooringmastery.service;

/**
 *
 * @author raviu
 */
public class FlooringMasteryDayIsNotValidException extends Exception{
    
    public FlooringMasteryDayIsNotValidException(String message){
        super(message);
    }
    
    public FlooringMasteryDayIsNotValidException(String message, Throwable cause){
        super(message, cause);
    }
}
