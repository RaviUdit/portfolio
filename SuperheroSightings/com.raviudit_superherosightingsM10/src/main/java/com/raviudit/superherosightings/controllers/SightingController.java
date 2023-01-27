/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.controllers;

import com.raviudit.superherosightings.entities.Location;
import com.raviudit.superherosightings.entities.Sighting;
import com.raviudit.superherosightings.entities.Superhero;
import com.raviudit.superherosightings.entities.Superpower;
import com.raviudit.superherosightings.entities.Team;
import com.raviudit.superherosightings.service.ServiceLayer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author raviu
 */
@Controller
public class SightingController {
    
    @Autowired
    ServiceLayer service;
    
    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

    @GetMapping("sightings")
    public String displaySightings(Model model){
        
        List<Superhero> heroList = service.getAllSuperheros();
        model.addAttribute("heroes", heroList);
        
        List<Superpower> powerList = service.getAllPowers();
        model.addAttribute("powers", powerList);
        
        List<Team> teamList = service.getAllTeams();
        model.addAttribute("teams", teamList);
        
        List<Location> locationList = service.getAllLocations();
        model.addAttribute("locations", locationList);
        
        List<Sighting> sightingList = service.getAllSightings();
        model.addAttribute("sightings", sightingList);
        
        model.addAttribute("errors", violations);
        
        return "sightings";
    }
    
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request){
        
        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        
        String ld = request.getParameter("sightingDate");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(ld, formatter);
        
        Sighting newSighting = new Sighting();
        newSighting.setSuperID(Integer.parseInt(heroId));
        newSighting.setLocationID(Integer.parseInt(locationId));
        newSighting.setSightingDate(dateTime);
        
        
        //Validating Sighting to be added.
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(newSighting);

        if(violations.isEmpty()) {
            service.addSighting(newSighting);
        }
        
        return "redirect:/sightings";
    }
    
    @GetMapping("sightingDetails")
    public String displaySightingDetails(Integer id, Model model) {
        
        Sighting sighting = service.getSightingByID(id);
        model.addAttribute("sighting", sighting);
        return "sightingDetails";
    }
    
    //////////////////////////////////////
    //           
    //          Delete Sighting
    //
    //
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id, Model model) {
        
        Sighting sighting = service.getSightingByID(id);
        model.addAttribute("sighting", sighting);
        
        return "deleteSighting";
    }
    
    @PostMapping("deleteSighting")
    public String confirmDeleteSighting(HttpServletRequest request, Model model){
        
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteSightingById(id);
        
        return "redirect:/sightings";
    }
    
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model){
        
        List<Superhero> heroList = service.getAllSuperheros();
        model.addAttribute("heroes", heroList);
        
        List<Superpower> powerList = service.getAllPowers();
        model.addAttribute("powers", powerList);
        
        List<Team> teamList = service.getAllTeams();
        model.addAttribute("teams", teamList);
        
        List<Location> locationList = service.getAllLocations();
        model.addAttribute("locations", locationList);
        
        //List<Sighting> sightingList = service.getAllSightings();
        Sighting sighting = service.getSightingByID(id);
        model.addAttribute("sighting", sighting);
        //model.addAttribute("sightings", sightingList);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);
        model.addAttribute("errors", violations);
        
        return "editSighting";
    }
    
//    @PostMapping("editSighting")
//    public String executeEditSighting(Sighting sighting, HttpServletRequest request){
//        
//        String heroId = request.getParameter("heroId");
//        String locationId = request.getParameter("locationId");
//        
//        String ld = request.getParameter("sightingDate");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//        LocalDateTime dateTime = LocalDateTime.parse(ld, formatter);
//        
//        sighting.setSuperID(Integer.parseInt(heroId));
//        sighting.setLocationID(Integer.parseInt(locationId));
//        sighting.setSightingDate(dateTime);
//        
//        service.updateSighting(sighting);
//        
//        return "redirect:/sightings";
//    }
    
    @PostMapping("editSighting")
    public String executeEditSighting(HttpServletRequest request, Model model){
        
        int id = Integer.parseInt(request.getParameter("sightingID"));  
        Sighting newSighting = service.getSightingByID(id);
        
        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        
        String ld = request.getParameter("sightingDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(ld, formatter);
        
        newSighting.setSuperID(Integer.parseInt(heroId));
        newSighting.setLocationID(Integer.parseInt(locationId));
        newSighting.setSightingDate(dateTime);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(newSighting);
        
        if(!violations.isEmpty()) {
            
            List<Superhero> heroList = service.getAllSuperheros();
            model.addAttribute("heroes", heroList);
            
            List<Location> locationList = service.getAllLocations();
            model.addAttribute("locations", locationList);
            
            Sighting sighting = service.getSightingByID(id);
            model.addAttribute("sighting", sighting);
            
            //FieldError error = new FieldError("sighting", "sightingDate", "Date must not be in the future.");
            
            
            model.addAttribute("errors", violations);
            return "editSighting";
        }
        
        service.updateSighting(newSighting);
        
        
        model.addAttribute("sighting", newSighting);
        return displaySightingDetails(newSighting.getSightingID(), model);
        
        //return "redirect:/sightings";
    }
    
}

