/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.entities;

import java.util.Objects;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author raviu
 */
public class Superpower {
    
    Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

    
    private int powerID;
    
    @NotBlank(message = "All Powers need a name.")
    @Size(max = 15, message = "Power name must be less than 15 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9\\-]*$", message = "Names cannot have special characters.")
    private String powerName;
    
    @NotBlank(message = "All Powers need a description.")
    @Size(max = 50, message = "Power Description must be less than 50 characters.")
    private String powerDesc;
    
    public int getPowerId() {
        return powerID;
    }

    public void setPowerId(int id) {
        this.powerID = id;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getPowerDesc() {
        return powerDesc;
    }

    public void setPowerDesc(String powerDesc) {
        this.powerDesc = powerDesc;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.powerID;
        hash = 37 * hash + Objects.hashCode(this.powerName);
        hash = 37 * hash + Objects.hashCode(this.powerDesc);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Superpower other = (Superpower) obj;
        if (this.powerID != other.powerID) {
            return false;
        }
        if (!Objects.equals(this.powerName, other.powerName)) {
            return false;
        }
        if (!Objects.equals(this.powerDesc, other.powerDesc)) {
            return false;
        }
        return true;
    }
    
    
    
}
