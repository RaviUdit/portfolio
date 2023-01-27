/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.controllers;

import com.raviudit.superherosightings.entities.Superhero;
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
public class TeamController {
    
    @Autowired
    ServiceLayer service;
    
    Set<ConstraintViolation<Team>> violations = new HashSet<>();
    
    @GetMapping("teams")
    public String displayTeams(Model model){
        
        List<Team> teams = service.getAllTeams();
        model.addAttribute("teams", teams);
        
        model.addAttribute("errors", violations);
        return "teams";
    }
    
    @PostMapping("addTeam")
    public String addTeam(HttpServletRequest request){
        
        String teamName = request.getParameter("teamName");
        String teamDesc = request.getParameter("teamDesc");
        String teamAddress = request.getParameter("teamAddress");
        String teamContactInfo = request.getParameter("teamContactInfo");
        
        Team team = new Team();
        team.setTeamName(teamName);
        team.setTeamDesc(teamDesc);
        team.setTeamAddress(teamAddress);
        team.setTeamContactInfo(teamContactInfo);
        
        //service.addTeam(team);
        
        //Validating Superpower to be added.
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(team);

        if(violations.isEmpty()) {
            service.addTeam(team);
        }
                
        return "redirect:/teams";
    }
    
    @GetMapping("teamDetails")
    public String displayTeamDetails(Integer id, Model model){
        
        Team team = service.getTeamByID(id);
        model.addAttribute("team", team);
        return "teamDetails";
    }
    
    //////////////////////////////////////
    //           
    //          Delete Team
    //
    //
    @GetMapping("deleteTeam")
    public String deleteTeam(Integer teamId, Model model) {
        
        Team team = service.getTeamByID(teamId);
        model.addAttribute("team", team);
        
        
        return "deleteTeam";
    }
    
    @PostMapping("deleteTeam")
    public String confirmDeleteTeam(HttpServletRequest request, Model model){
        
        int id = Integer.parseInt(request.getParameter("teamId"));
        service.deleteTeamByID(id);
        
        return "redirect:/teams";
    }
    
    //////////////////////////////////////
    //           
    //          Edit Team
    //
    //
    @GetMapping("editTeam")
    public String editTeam(HttpServletRequest request, Model model) {
       
        int id = Integer.parseInt(request.getParameter("teamId"));        
        Team team = service.getTeamByID(id);
        
        model.addAttribute("team", team);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(team);
        model.addAttribute("errors", violations);
        
        return "editTeam";
    }
    
    @PostMapping("editTeam")
    public String executeEditTeam(HttpServletRequest request, Model model) {
        
        int id = Integer.parseInt(request.getParameter("teamId"));
        Team team = service.getTeamByID(id);
        
        String teamName = request.getParameter("teamName");
        String teamDesc = request.getParameter("teamDesc");
        String teamAddress = request.getParameter("teamAddress");
        String teamContactInfo = request.getParameter("teamContactInfo");
        
        
        team.setTeamName(teamName);
        team.setTeamDesc(teamDesc);
        team.setTeamAddress(teamAddress);
        team.setTeamContactInfo(teamContactInfo);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(team);
        
        if(!violations.isEmpty()) {
            
            Team newTeam = service.getTeamByID(id);
            model.addAttribute("team", newTeam);
            
            model.addAttribute("errors", violations);
            
            return "editTeam";
        }
        
        service.updateTeam(team);
        
        model.addAttribute("team", team);
        return displayTeamDetails(team.getTeamId(), model);
        //return "redirect:/teams";
    }
    
    @GetMapping("teamMembers")
    public String displayTeamMembers(Integer id, Model model){
        
        Team team = service.getTeamByID(id);
        
        List<Superhero> heroesOnTeam = service.getSuperherosByTeam(team);
        List<Superhero> allHeroes = service.getAllSuperheros();
        
        allHeroes.removeAll(heroesOnTeam);
        
        model.addAttribute("team", team);
        model.addAttribute("hot", heroesOnTeam);
        model.addAttribute("hnot", allHeroes);
        
        return "teamMembers";
                
    }
    
    @PostMapping("addHeroToTeam")
    public String addHeroToTeam(HttpServletRequest request, Model model){
        
        String id = request.getParameter("id");
        Superhero hero = service.getSuperheroByID(Integer.parseInt(id));
        String teamId = request.getParameter("teamId");
        Team team = service.getTeamByID(Integer.parseInt(teamId));
        
        service.addHeroToTeam(hero, team);
        
        List<Superhero> heroesOnTeam = service.getSuperherosByTeam(team);
        List<Superhero> allHeroes = service.getAllSuperheros();
        
        allHeroes.removeAll(heroesOnTeam);
        
        model.addAttribute("team", team);
        model.addAttribute("hot", heroesOnTeam);
        model.addAttribute("hnot", allHeroes);
        
       
        return displayTeamMembers(team.getTeamId(), model);
    }
    
    @PostMapping("removeHeroFromTeam")
    public String removeHeroFromTeam(HttpServletRequest request, Model model){
        
        String id = request.getParameter("id");
        Superhero hero = service.getSuperheroByID(Integer.parseInt(id));
        String teamId = request.getParameter("teamId");
        Team team = service.getTeamByID(Integer.parseInt(teamId));
        
        service.removeHeroFromTeam(hero, team);
        
        List<Superhero> heroesOnTeam = service.getSuperherosByTeam(team);
        List<Superhero> allHeroes = service.getAllSuperheros();
        
        allHeroes.removeAll(heroesOnTeam);
        
        model.addAttribute("team", team);
        model.addAttribute("hot", heroesOnTeam);
        model.addAttribute("hnot", allHeroes);
        
       
        return displayTeamMembers(team.getTeamId(), model);
    }
    
//    public String addHeroToTeam(HttpServletRequest request)
//    {
//    
//    get all heroes()
//            get heroes by team()
//    
//    remove getallheros(heroesbyteam)
//    
//    heros model1
//    getallheroes model2
//        int teamId = Integer.parseInt(request.getParameter("teamId"));
//        Team team = service.getTeamByID(teamId);
//        
//        int heroId = Integer.parseInt(request.getParameter("heroId"));
//        Superhero hero = service.getSuperheroByID(heroId);
//        
//        service.addHeroToTeam(hero, team);
//        
//        return "teamMembers";
//    }
    
}


