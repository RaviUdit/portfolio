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
public class FlooringMasteryOrderNumberIsNotValidException extends Exception{
    
    public FlooringMasteryOrderNumberIsNotValidException(String message){
        super(message);
    }
    
    public FlooringMasteryOrderNumberIsNotValidException(String message, Throwable cause){
        super(message, cause);
    }
}
