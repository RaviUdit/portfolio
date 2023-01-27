/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.service;

import com.raviudit.superherosightings.dao.LocationDAO;
import com.raviudit.superherosightings.dao.SightingDAO;
import com.raviudit.superherosightings.dao.SuperheroDAO;
import com.raviudit.superherosightings.dao.SuperpowerDAO;
import com.raviudit.superherosightings.dao.TeamDAO;
import com.raviudit.superherosightings.entities.Location;
import com.raviudit.superherosightings.entities.Sighting;
import com.raviudit.superherosightings.entities.Superhero;
import com.raviudit.superherosightings.entities.Superpower;
import com.raviudit.superherosightings.entities.Team;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author raviu
 */

@Repository
public class ServiceLayerWebImpl implements ServiceLayer{
    
    SuperpowerDAO spDao;
    LocationDAO locDao;
    TeamDAO teamDao;
    SuperheroDAO shDao;
    SightingDAO stDao;
    
    public ServiceLayerWebImpl(SuperpowerDAO spDao, 
                               LocationDAO locDao, 
                               TeamDAO teamDao, 
                               SuperheroDAO shDao, 
                               SightingDAO stDao){
        
        this.spDao = spDao;
        this.locDao = locDao;
        this.teamDao = teamDao;
        this.shDao = shDao;
        this.stDao = stDao;
    }
    
    //Superpower Methods
    @Override
    public Superpower getPowerByID(int id) {
        Superpower power = spDao.getPowerByID(id);
        return power;
    }

    @Override
    public Superpower getPowerByName(String powerName) {
        Superpower power = spDao.getPowerByName(powerName);
        return power;
    }

    @Override
    public List<Superpower> getAllPowers() {
        List<Superpower> powers = spDao.getAllPowers();
        return powers;
    }

    @Override
    public Superpower addPower(Superpower power) {
        Superpower newPower = spDao.addPower(power);
        return newPower;
    }

    @Override
    public void updatePower(Superpower power) {
        spDao.updatePower(power);
    }

    @Override
    public void deletePowerByID(int id) {
        spDao.deletePowerByID(id);
    }

    //Location Methods
    @Override
    public Location getLocationByID(int id) {
        return locDao.getLocationByID(id);
    }

    @Override
    public Location getLocationByName(String name) {
        return locDao.getLocationByName(name);
    }

    @Override
    public List<Location> getAllLocations() {
        List<Location> locationList = locDao.getAllLocations();
        return locationList;
    }

    @Override
    public Location addLocation(Location location) {
        Location newLocation = locDao.addLocation(location);
        return newLocation;
    }

    @Override
    public void updateLocation(Location location) {
        locDao.updateLocation(location);
    }

    @Override
    public void deleteLocationbyID(int id) {
        locDao.deleteLocationbyID(id);
    }

    @Override
    public List<Location> getLocationsBySuperHero(Superhero hero) {
        List<Location> locationList = locDao.getLocationsBySuperHero(hero);
        return locationList;
    }

    //Team Methods
    @Override
    public Team getTeamByID(int id) {
        return teamDao.getTeamByID(id);
    }

    @Override
    public Team getTeamByName(String name) {
        return teamDao.getTeamByName(name);
    }

    @Override
    public List<Team> getAllTeams() {
        List<Team> teamList = teamDao.getAllTeams();
        return teamList;
    }

    @Override
    public Team addTeam(Team team) {
        Team newTeam = teamDao.addTeam(team);
        return newTeam;
    }

    @Override
    public void updateTeam(Team team) {
        teamDao.updateTeam(team);
    }

    @Override
    public void deleteTeamByID(int id) {
        teamDao.deleteTeamByID(id);
    }

    @Override
    public List<Team> getTeamsBySuperhero(Superhero hero) {
        List<Team> teamList = teamDao.getTeamsBySuperhero(hero);
        return teamList;
    }

    //Superhero Methods
    @Override
    public Superhero getSuperheroByID(int id) {
        return shDao.getSuperheroByID(id);
    }

    @Override
    public List<Superhero> getAllSuperheros() {
        return shDao.getAllSuperheros();
    }

    @Override
    public Superhero addSuperhero(Superhero hero, Superpower power, Team team) {
        Superhero newSuper = shDao.addSuperhero(hero, power, team);
        return newSuper;
    }

    @Override
    public void updateSuperhero(Superhero hero) {
        shDao.updateSuperhero(hero);
    }

    @Override
    public void deleteSuperherobyID(int id) {
        shDao.deleteSuperherobyID(id);
    }

    @Override
    public void addHeroToTeam(Superhero hero, Team team) {
        shDao.addHeroToTeam(hero, team);
    }

    @Override
    public void removeHeroFromTeam(Superhero hero, Team team) {
        shDao.removeHeroFromTeam(hero, team);
    }

    @Override
    public List<Superhero> getSuperherosByTeam(Team team) {
        return shDao.getSuperherosByTeam(team);
    }

    @Override
    public List<Superhero> getSuperherosByLocation(Location location) {
        return shDao.getSuperherosByLocation(location);
    }

    //Sighting Methods
    @Override
    public Sighting getSightingByID(int id) {
        return stDao.getSightingByID(id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        return stDao.getAllSightings();
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        Sighting newSighting = stDao.addSighting(sighting);
        return newSighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        stDao.updateSighting(sighting);
    }

    @Override
    public void deleteSightingById(int id) {
        stDao.deleteSightingById(id);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDateTime date) {
        return stDao.getSightingsByDate(date);
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location location) {
        return stDao.getSightingsByLocation(location);
    }

    @Override
    public List<Sighting> getSightingByHero(Superhero hero) {
        return stDao.getSightingByHero(hero);
    }

    @Override
    public List<Sighting> getLastTenSightings() {
        return stDao.getLastTenSightings();
    }
    
}
