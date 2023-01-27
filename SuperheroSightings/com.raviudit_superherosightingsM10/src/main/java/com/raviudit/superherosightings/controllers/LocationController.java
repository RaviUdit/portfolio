/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.controllers;

import com.raviudit.superherosightings.entities.Location;
import com.raviudit.superherosightings.entities.Sighting;
import com.raviudit.superherosightings.entities.Superpower;
import com.raviudit.superherosightings.service.ServiceLayer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author raviu
 */
@Controller
public class LocationController {
    
    @Autowired
    ServiceLayer service;
    
    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @GetMapping("locations")
    public String displayLocations(Model model){
        
        List<Location> locations = service.getAllLocations();
        model.addAttribute("locations", locations);
        
        model.addAttribute("errors", violations);
        return "locations";
    }
    
    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request){
        
        String locationName = request.getParameter("locationName");
        String locationDesc = request.getParameter("locationDesc");
        String locationAdd = request.getParameter("locationAdd");
        String locationLat = request.getParameter("locationLat");
        String locationLon = request.getParameter("locationLon");
        
        //Double newLat = Double.parseDouble(locationLat);
        //Double newLon = Double.parseDouble(locationLon);
        
        Location location = new Location();
        location.setLocationName(locationName);
        location.setLocationDesc(locationDesc);
        location.setLocationAdd(locationAdd);
//        location.setLocationLat(newLat);
//        location.setLocationLon(newLon);
        location.setLocationLat(locationLat);
        location.setLocationLon(locationLon);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        if(violations.isEmpty()) {
           service.addLocation(location);
        }
        
        //service.addLocation(location);
                
        return "redirect:/locations";
    }
    
    //////////////////////////////////////
    //           
    //          Delete Location
    //
    //
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer locationID, Model model) {
        
        Location location = service.getLocationByID(locationID);
        model.addAttribute("location", location);
        
        return "deleteLocation";
    }
    
    @PostMapping("deleteLocation")
    public String confirmDeleteLocation(HttpServletRequest request, Model model){
        
        int id = Integer.parseInt(request.getParameter("locationID"));
        service.deleteLocationbyID(id);
        
        return "redirect:/locations";
    }
    
    @GetMapping("locationDetails")
    public String displayLocationDetails(Integer id, Model model) {
        
        Location location = service.getLocationByID(id);
        model.addAttribute("location", location);
        return "locationDetails";
    }
    
    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
       
        int id = Integer.parseInt(request.getParameter("locationID"));        
        Location location = service.getLocationByID(id);
        
        model.addAttribute("location", location);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        model.addAttribute("errors", violations);
        
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String executeEditLocation(HttpServletRequest request, Model model) {
        
        int id = Integer.parseInt(request.getParameter("locationID"));    
        
        Location location = service.getLocationByID(id);
        String locationName = request.getParameter("locationName");
        String locationDesc = request.getParameter("locationDesc");
        String locationAdd = request.getParameter("locationAdd");
        String locationLat = request.getParameter("locationLat");
        String locationLon = request.getParameter("locationLon");
        
        //Double newLat = Double.parseDouble(locationLat);
        //Double newLon = Double.parseDouble(locationLon);
        
        location.setLocationName(locationName);
        location.setLocationDesc(locationDesc);
        location.setLocationAdd(locationAdd);
//        location.setLocationLat(newLat);
//        location.setLocationLon(newLon);
        location.setLocationLat(locationLat);
        location.setLocationLon(locationLon);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        
        if(!violations.isEmpty()) {
            
            Location newlocation = service.getLocationByID(id);
            model.addAttribute("location", location);
            
            model.addAttribute("errors", violations);
            
            return "editLocation";
        }
        
        
        service.updateLocation(location);
        
        model.addAttribute("location", location);
        return displayLocationDetails(location.getLocationID(), model);
        
        //return "redirect:/locations";
    }
    
    
}
