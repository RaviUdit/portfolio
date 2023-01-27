/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;



/**
 *
 * @author raviu
 */
public class Sighting {
    
    Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

    private int sightingID;
    
 //   @NotBlank(message = "All Sightings need a date.")
    @Past(message = "Sighting Date cannot be in the Future.")
    private LocalDateTime sightingDate;
    
    
    private int superID;
    private int locationID;
    
    private Superhero superhero;
    private Location location;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.sightingID;
        hash = 37 * hash + Objects.hashCode(this.sightingDate);
        hash = 37 * hash + this.superID;
        hash = 37 * hash + this.locationID;
        hash = 37 * hash + Objects.hashCode(this.superhero);
        hash = 37 * hash + Objects.hashCode(this.location);
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
        final Sighting other = (Sighting) obj;
        if (this.sightingID != other.sightingID) {
            return false;
        }
        if (this.superID != other.superID) {
            return false;
        }
        if (this.locationID != other.locationID) {
            return false;
        }
        if (!Objects.equals(this.sightingDate, other.sightingDate)) {
            return false;
        }
        if (!Objects.equals(this.superhero, other.superhero)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        return true;
    }
    
    

    public int getSightingID() {
        return sightingID;
    }

    public void setSightingID(int sightingID) {
        this.sightingID = sightingID;
    }

    public LocalDateTime getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(LocalDateTime sightingDate) {
        this.sightingDate = sightingDate;
    }

    public int getSuperID() {
        return superID;
    }

    public void setSuperID(int superID) {
        this.superID = superID;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public Superhero getSuperhero() {
        return superhero;
    }

    public void setSuperhero(Superhero superhero) {
        this.superhero = superhero;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    
    
}
