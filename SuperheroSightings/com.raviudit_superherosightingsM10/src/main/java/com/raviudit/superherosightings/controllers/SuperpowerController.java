/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.controllers;

import com.raviudit.superherosightings.entities.Sighting;
import com.raviudit.superherosightings.entities.Superpower;
import com.raviudit.superherosightings.entities.Team;
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
public class SuperpowerController {
    
    @Autowired 
    ServiceLayer service;
    
    Set<ConstraintViolation<Superpower>> violations = new HashSet<>();
    
    @GetMapping("powers")
    public String displaySuperpowers(Model model){
        
        List<Superpower> powers = service.getAllPowers();
        model.addAttribute("powers", powers);
        
        model.addAttribute("errors", violations);
        
        return "powers";
    }
    
    @PostMapping("addPower")
    public String addPower(HttpServletRequest request){
        
        String powerName = request.getParameter("powerName");
        String powerDesc = request.getParameter("powerDesc");
        
        Superpower power = new Superpower();
        power.setPowerName(powerName);
        power.setPowerDesc(powerDesc);
        
        //service.addPower(power);
        
        //Validating Superpower to be added.
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(power);

        if(violations.isEmpty()) {
            service.addPower(power);
        }
        
        return "redirect:/powers";
    }
    
    
    //////////////////////////////////////
    //           
    //          Power Details
    //
    //
    @GetMapping("powerDetails")
    public String displayPowerDetails(Integer id, Model model){
        
        Superpower power = service.getPowerByID(id);
        model.addAttribute("power", power);
        return "powerDetails";
    }
    
    //////////////////////////////////////
    //           
    //          Delete Power
    //
    //
    @GetMapping("deletePower")
    public String deletePower(Integer powerId, Model model) {
        
        Superpower power = service.getPowerByID(powerId);
        model.addAttribute("power", power);
        
        
        return "deletePower";
    }
    
    @PostMapping("deletePower")
    public String confirmDeletePower(HttpServletRequest request, Model model){
        
        int id = Integer.parseInt(request.getParameter("powerId"));
        service.deletePowerByID(id);
        
        return "redirect:/powers";
    }
    
    //////////////////////////////////////
    //           
    //          Edit Power
    //
    //
    @GetMapping("editPower")
    public String editPower(HttpServletRequest request, Model model) {
       
        int id = Integer.parseInt(request.getParameter("powerId"));        
        Superpower power = service.getPowerByID(id);
        
        model.addAttribute("power", power);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(power);
        model.addAttribute("errors", violations);
        
        return "editPower";
    }
    
    @PostMapping("editPower")
    public String executeEditPower(HttpServletRequest request, Model model) {
        
        int id = Integer.parseInt(request.getParameter("powerId"));        
        Superpower power = service.getPowerByID(id);    
        
        power.setPowerName(request.getParameter("powerName"));
        power.setPowerDesc(request.getParameter("powerDesc"));
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(power);
        
        if(!violations.isEmpty()) {
            
        Superpower newPower = service.getPowerByID(id);       
        model.addAttribute("power", newPower);
        
        model.addAttribute("errors", violations);
            
            return "editPower";
        }
        
        service.updatePower(power);
        
        model.addAttribute("power", power);
        return displayPowerDetails(id, model);
        
        //return "redirect:/powers";
    }
    
}
