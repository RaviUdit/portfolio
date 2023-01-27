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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author raviu
 */
@Controller
public class SuperheroController {
    
    @Autowired
    ServiceLayer service;
    
    Set<ConstraintViolation<Superhero>> violations = new HashSet<>();
    
    
    
    @GetMapping("supers")
    public String displaySupers(Model model){
        
       //violations.clear();
        
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

        return "supers";
    }
    
    @PostMapping("addHero")
//    public String addHero(@Valid Superhero newHero, BindingResult result, HttpServletRequest request){
    public String addHero(HttpServletRequest request){        
        
        String heroName = request.getParameter("name");
        String isHero = request.getParameter("isHero");
        String powerId = request.getParameter("powerId");
        String teamId = request.getParameter("teamId");
        
//        Boolean isThisAHero = false;
//        
//        if(isHero.contains("hero")){
//            isThisAHero = true;
//        } 
        
        Team team = service.getTeamByID(Integer.parseInt(teamId));
        Superpower power = service.getPowerByID(Integer.parseInt(powerId));
        
        Superhero newHero = new Superhero();
        newHero.setName(heroName);
        newHero.setIsHero(Boolean.parseBoolean(isHero));
        newHero.setPowerId(Integer.parseInt(powerId));
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(newHero);
        
        
        if(violations.isEmpty()) {
            service.addSuperhero(newHero, power, team);
        }

//        if (result.hasErrors()){
//            return "redirect:/supers";
//        }
        
        // service.addSuperhero(newHero, power, team);
        
        return "redirect:/supers";
    }
    
    @GetMapping("heroDetails")
    public String displayHeroDetails(Integer id, Model model){
        
        Superhero hero = service.getSuperheroByID(id);
        model.addAttribute("hero", hero);
        return "heroDetails";
    }
    
    //////////////////////////////////////
    //           
    //          Delete Hero
    //
    //
    @GetMapping("deleteHero")
    public String deleteHero(Integer id, Model model) {
        
        Superhero hero = service.getSuperheroByID(id);
        model.addAttribute("hero", hero);
        
        return "deleteHero";
    }
    
    @PostMapping("deleteHero")
    public String confirmDeleteHero(HttpServletRequest request, Model model){
        
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteSuperherobyID(id);
        
        return "redirect:/supers";
    }
    
    @GetMapping("editHero")
    public String editHero(Integer id, Model model){
        
        Superhero superhero = service.getSuperheroByID(id);
        model.addAttribute("superhero", superhero);
        
        List<Superpower> powerList = service.getAllPowers();
        model.addAttribute("powers", powerList);
        
        List<Team> teamList = service.getAllTeams();
        model.addAttribute("teams", teamList);
        
        List<Location> locationList = service.getAllLocations();
        model.addAttribute("locations", locationList);
        
        List<Sighting> sightingList = service.getAllSightings();
        model.addAttribute("sightings", sightingList);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superhero);
        model.addAttribute("errors", violations);
        
        return "editHero";
    }
    
    @PostMapping("editHero")
    public String executeEditHero(@Valid Superhero newHero, BindingResult result, 
                                   HttpServletRequest request, Model model){
        
        //newHero = service.getSuperheroByID(newHero.getId());
        String heroName = request.getParameter("name");
        String isHero = request.getParameter("isHero");
        String powerId = request.getParameter("powerId");
        String[] teamIds = request.getParameterValues("teamId");

        List<Team> teams = new ArrayList<>();
        
//        Adding and removing teams
        if(teamIds != null){
            List<Team> teamList = service.getAllTeams();
            for(Team team : teamList){
                service.removeHeroFromTeam(newHero, team);
            }
        
            for(String teamIdArray : teamIds){
                Team team = service.getTeamByID(Integer.parseInt(teamIdArray));
                teams.add(team);
                service.addHeroToTeam(newHero, team);
            }
        } else {
            FieldError error = new FieldError("superhero", "teams", "Must be on atleast one team");
            result.addError(error);
            
        }
        
        newHero.setName(heroName);
        newHero.setIsHero(Boolean.parseBoolean(isHero));
        newHero.setPowerId(Integer.parseInt(powerId));   
        newHero.setSuperpower(service.getPowerByID(Integer.parseInt(powerId)));
        newHero.setTeams(teams);
        
        if(result.hasErrors()) {
            
            //System.out.println(result.getAllErrors().toString());
            //Superhero hero = service.getSuperheroByID(newHero.getId());
            model.addAttribute("superhero", newHero);

            //List<Superpower> powerList = service.getAllPowers();
            model.addAttribute("powers", service.getAllPowers());

            //List<Team> newTeamList = service.getAllTeams();
            model.addAttribute("teams", service.getAllTeams());

           //List<Location> locationList = service.getAllLocations();
            model.addAttribute("locations", service.getAllLocations());

            //List<Sighting> sightingList = service.getAllSightings();
            model.addAttribute("sightings", service.getAllSightings());
            
//            Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
//            violations = validate.validate(newHero);
//            model.addAttribute("errors", violations);
            
            return "editHero";
        }
        
        service.updateSuperhero(newHero);
        
        model.addAttribute("hero", newHero);
        return displayHeroDetails(newHero.getId(), model);
        //return "redirect:/supers";
        
        
    }
//      public String executeEditHero(@Valid Superhero newHero, String[] teamId, 
//                                   HttpServletRequest request, Model model){
//    
//        newHero = service.getSuperheroByID(newHero.getId());
//        String heroName = request.getParameter("name");
//        String isHero = request.getParameter("isHero");
//        String powerId = request.getParameter("powerId");
//        String[] teamIds = request.getParameterValues("teamId");
//        
//        if(teamIds == null) {
//            
//            //System.out.println(result.getAllErrors().toString());
//            Superhero hero = service.getSuperheroByID(newHero.getId());
//            model.addAttribute("hero", hero);
//
//            List<Superpower> powerList = service.getAllPowers();
//            model.addAttribute("powers", powerList);
//
//            List<Team> newTeamList = service.getAllTeams();
//            model.addAttribute("teams", newTeamList);
//
//            List<Location> locationList = service.getAllLocations();
//            model.addAttribute("locations", locationList);
//
//            List<Sighting> sightingList = service.getAllSightings();
//            model.addAttribute("sightings", sightingList);
//            
//            Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
//            violations = validate.validate(newHero);
//            model.addAttribute("errors", violations);
//            
//            return "editHero";
//        }
//        
//        //Adding and removing teams
//        if(teamIds != null){
//            List<Team> teamList = service.getAllTeams();
//            for(Team team : teamList){
//                service.removeHeroFromTeam(newHero, team);
//            }
//        
//            for(String teamIdArray : teamIds){
//                Team team = service.getTeamByID(Integer.parseInt(teamIdArray));
//                service.addHeroToTeam(newHero, team);
//            }
//        } else {
////            FieldError error = new FieldError("newHero", "teams", "Must be on atleast one team");
////            result.addError(error);
//            
//        }
//        
//        newHero.setName(heroName);
//        newHero.setIsHero(Boolean.parseBoolean(isHero));
//        newHero.setPowerId(Integer.parseInt(powerId));
//        
//        
//        
//        
//        service.updateSuperhero(newHero);
//        
//         return "redirect:/supers";
//    }
    
}
