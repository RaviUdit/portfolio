/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.controllers;

import com.raviudit.superherosightings.entities.Sighting;
import com.raviudit.superherosightings.service.ServiceLayer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author raviu
 */
@Controller
public class IndexController {
    
    @Autowired
    ServiceLayer service;
    
    @GetMapping("/")
    public String displayLastTen(Model model){
        
        List<Sighting> sightingList = service.getLastTenSightings();
        model.addAttribute("sightings", sightingList);
        
        return "index";
    }
    
    
    
}
