/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.entities;

import java.util.Objects;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author raviu
 */
public class Location {
    
    Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

    private int locationID;
    
    @NotBlank(message = "All Locations need a name.")
    @Size(max = 20, message = "Team name must be less than 15 characters.")
    private String locationName;
    
    @NotBlank(message = "All Locations need a Description.")
    @Size(max = 50, message = "Location description must be less than 50 characters.")
    private String locationDesc;
    
    @NotBlank(message = "All Locations  need an Address.")
    @Size(max = 150, message = "Location Address must be less than 150 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-\\.\\'\\,]*$", message = "Addresses cannot have special characters.")
    private String locationAdd;
    
//    private double locationLat;
//    private double locationLon;
    
    @NotBlank(message = "All locations require valid Longitude and Latitude coordinates.")
    @Digits(integer=2, fraction=4, message = "Latitude must be represented as [xx.xxxx].")
    @Min(value = -90, message = "A Latitude must be above -90 degrees.")
    @Max(value = 90, message = "A Latitude must be below 90 degrees.")
    @Pattern(regexp = "^-?[0-9][0-9]\\.[0-9][0-9][0-9][0-9]", message = "Latitude must only be represented in numbers in a [xx.xxxx] format.")
    private String locationLat;
    
    @NotBlank(message = "All locations require valid Longitude and Latitude coordinates.")
    @Digits(integer=3, fraction=4, message = "Longitude must be represented as [xxx.xxxx].")
    @Min(value = -180, message = "A Longitude must be above -180 degrees.")
    @Max(value = 180, message = "A Longitude must be below 180 degrees.")
//    @Pattern(regexp = "^-?[0-9][0-9][0-9]\\.[0-9][0-9][0-9][0-9]", message = "Longitude must only be represented in numbers in a [xxx.xxxx] format.")
    @Pattern(regexp = "^-?[0-9\\.]*$", message = "Longitude must only be represented in numbers in a [xxx.xxxx] format.")
    private String locationLon;

//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 19 * hash + this.locationID;
//        hash = 19 * hash + Objects.hashCode(this.locationName);
//        hash = 19 * hash + Objects.hashCode(this.locationDesc);
//        hash = 19 * hash + Objects.hashCode(this.locationAdd);
//        hash = 19 * hash + (int) (Double.doubleToLongBits(this.locationLat) ^ (Double.doubleToLongBits(this.locationLat) >>> 32));
//        hash = 19 * hash + (int) (Double.doubleToLongBits(this.locationLon) ^ (Double.doubleToLongBits(this.locationLon) >>> 32));
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Location other = (Location) obj;
//        if (this.locationID != other.locationID) {
//            return false;
//        }
//        if (Double.doubleToLongBits(this.locationLat) != Double.doubleToLongBits(other.locationLat)) {
//            return false;
//        }
//        if (Double.doubleToLongBits(this.locationLon) != Double.doubleToLongBits(other.locationLon)) {
//            return false;
//        }
//        if (!Objects.equals(this.locationName, other.locationName)) {
//            return false;
//        }
//        if (!Objects.equals(this.locationDesc, other.locationDesc)) {
//            return false;
//        }
//        if (!Objects.equals(this.locationAdd, other.locationAdd)) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.locationID;
        hash = 41 * hash + Objects.hashCode(this.locationName);
        hash = 41 * hash + Objects.hashCode(this.locationDesc);
        hash = 41 * hash + Objects.hashCode(this.locationAdd);
        hash = 41 * hash + Objects.hashCode(this.locationLat);
        hash = 41 * hash + Objects.hashCode(this.locationLon);
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
        final Location other = (Location) obj;
        if (this.locationID != other.locationID) {
            return false;
        }
        if (!Objects.equals(this.locationName, other.locationName)) {
            return false;
        }
        if (!Objects.equals(this.locationDesc, other.locationDesc)) {
            return false;
        }
        if (!Objects.equals(this.locationAdd, other.locationAdd)) {
            return false;
        }
        if (!Objects.equals(this.locationLat, other.locationLat)) {
            return false;
        }
        if (!Objects.equals(this.locationLon, other.locationLon)) {
            return false;
        }
        return true;
    }
    
    

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public String getLocationAdd() {
        return locationAdd;
    }

    public void setLocationAdd(String locationAdd) {
        this.locationAdd = locationAdd;
    }

//    public double getLocationLat() {
//        return locationLat;
//    }
//
//    public void setLocationLat(double locationLat) {
//        this.locationLat = locationLat;
//    }
//
//    public double getLocationLon() {
//        return locationLon;
//    }
//
//    public void setLocationLon(double locationLon) {
//        this.locationLon = locationLon;
//    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(String locationLon) {
        this.locationLon = locationLon;
    }
    
    
}
