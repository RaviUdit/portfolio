/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.service;

import com.raviudit.superherosightings.entities.Location;
import com.raviudit.superherosightings.entities.Sighting;
import com.raviudit.superherosightings.entities.Superhero;
import com.raviudit.superherosightings.entities.Superpower;
import com.raviudit.superherosightings.entities.Team;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author raviu
 */
public interface ServiceLayer {
    
    //Methods for Superpowers
    
    Superpower getPowerByID(int id);
    Superpower getPowerByName(String powerName);
    List<Superpower> getAllPowers();
    Superpower addPower(Superpower power);
    void updatePower(Superpower power);
    void deletePowerByID(int id);
    
    //Methods for Location
    Location getLocationByID(int id);
    Location getLocationByName(String name);
    List<Location> getAllLocations();
    Location addLocation(Location location);
    void updateLocation(Location location);
    void deleteLocationbyID(int id);
    
    List<Location> getLocationsBySuperHero(Superhero hero);
    
    //Methods for Team
    Team getTeamByID(int id);
    Team getTeamByName(String name);
    List<Team> getAllTeams();
    Team addTeam(Team team);
    void updateTeam(Team team);
    void deleteTeamByID(int id);
    
    List<Team> getTeamsBySuperhero(Superhero hero);
    
    //Methods for Superhero
    Superhero getSuperheroByID(int id);
    List<Superhero> getAllSuperheros();
    Superhero addSuperhero(Superhero hero, Superpower power, Team team);
    void updateSuperhero(Superhero hero);
    void deleteSuperherobyID(int id);
    void addHeroToTeam(Superhero hero, Team team);
    void removeHeroFromTeam(Superhero hero, Team team);
    
    List<Superhero> getSuperherosByTeam(Team team);
    List<Superhero> getSuperherosByLocation(Location location);
    
    //Methods for Sighting
    Sighting getSightingByID(int id);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
    
    List<Sighting> getSightingsByDate(LocalDateTime date);
    List<Sighting> getSightingsByLocation(Location location);
    List<Sighting> getSightingByHero(Superhero hero);
    List<Sighting> getLastTenSightings();
    
}
