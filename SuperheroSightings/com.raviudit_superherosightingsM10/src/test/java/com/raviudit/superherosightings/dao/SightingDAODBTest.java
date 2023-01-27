/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.dao;

import com.raviudit.superherosightings.entities.Location;
import com.raviudit.superherosightings.entities.Sighting;
import com.raviudit.superherosightings.entities.Superhero;
import com.raviudit.superherosightings.entities.Superpower;
import com.raviudit.superherosightings.entities.Team;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

/**
 *
 * @author raviu
 */
@SpringBootTest
public class SightingDAODBTest {
    
    @Autowired
    SuperpowerDAO superpowerDao;
    
    @Autowired
    SuperheroDAO superheroDao;
    
    @Autowired
    TeamDAO teamDao;
    
    @Autowired
    LocationDAO locationDao;
    
    @Autowired 
    SightingDAO sightingDao;
    
    public SightingDAODBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        List<Team> teams = teamDao.getAllTeams();
        for(Team team : teams){
            teamDao.deleteTeamByID(team.getTeamId());
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings){
            sightingDao.deleteSightingById(sighting.getSightingID());
        }
        
        List<Superhero> heroes = superheroDao.getAllSuperheros();
        for(Superhero hero : heroes){
            superheroDao.deleteSuperherobyID(hero.getId());
        }
        
        List<Superpower> powers = superpowerDao.getAllPowers();
        for(Superpower power : powers){
            superpowerDao.deletePowerByID(power.getPowerId());
        }
        
        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations){
            locationDao.deleteLocationbyID(location.getLocationID());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddGetSightingByID() {
        Superpower power = new Superpower();
        power.setPowerName("Super Strength");
        power.setPowerDesc("Can lift 100 tonnes.");
        power = superpowerDao.addPower(power);
        
        Team team = new Team();
        team.setTeamName("team1");
        team.setTeamDesc("Test Team1");
        team.setTeamAddress("test address");
        team.setTeamContactInfo("test contact info");
        team = teamDao.addTeam(team);
        
        Superhero hero = new Superhero();
        hero.setName("testHero");
        hero.setIsHero(true);
        superheroDao.addSuperhero(hero, power, team);
        
        Location location = new Location();
        location.setLocationName("test location");
        location.setLocationAdd("1234 test st.");
        location.setLocationDesc("test location");
        location.setLocationLat(Double.toString(12.3456));
        location.setLocationLat(Double.toString(98.7654));
        location = locationDao.addLocation(location);
        
        String ld = "2018-01-03 10:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(ld, formatter);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(dateTime);
        sighting.setLocationID(location.getLocationID());
        sighting.setSuperID(hero.getId());
        sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingByID(sighting.getSightingID());
        
        assertEquals(sighting.getSightingID(), fromDao.getSightingID());
    }

    @Test
    public void testGetAllSightingsDeleteSightingByID() {
        
        Superpower power = new Superpower();
        power.setPowerName("Super Strength");
        power.setPowerDesc("Can lift 100 tonnes.");
        power = superpowerDao.addPower(power);
        
        Team team = new Team();
        team.setTeamName("team1");
        team.setTeamDesc("Test Team1");
        team.setTeamAddress("test address");
        team.setTeamContactInfo("test contact info");
        team = teamDao.addTeam(team);
        
        Superhero hero = new Superhero();
        hero.setName("testHero");
        hero.setIsHero(true);
        superheroDao.addSuperhero(hero, power, team);
        
        Location location = new Location();
        location.setLocationName("test location");
        location.setLocationAdd("1234 test st.");
        location.setLocationDesc("test location");
        location.setLocationLat(Double.toString(12.3456));
        location.setLocationLat(Double.toString(98.7654));
        location = locationDao.addLocation(location);
        
        String ld = "2018-01-03 10:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(ld, formatter);
        
        String ld2 = "2018-01-04 10:00:00";
        LocalDateTime dateTime2 = LocalDateTime.parse(ld2, formatter);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(dateTime);
        sighting.setLocationID(location.getLocationID());
        sighting.setSuperID(hero.getId());
        sighting = sightingDao.addSighting(sighting);
        sighting = sightingDao.getSightingByID(sighting.getSightingID());
        
        Sighting sighting2 = new Sighting();
        sighting2.setSightingDate(dateTime2);
        sighting2.setLocationID(location.getLocationID());
        sighting2.setSuperID(hero.getId());
        sighting2 = sightingDao.addSighting(sighting2);
        sighting2 = sightingDao.getSightingByID(sighting2.getSightingID());
        
        List<Sighting> testList = sightingDao.getAllSightings();
        
        assertEquals(2, testList.size());
        assertTrue(testList.contains(sighting));
        assertTrue(testList.contains(sighting2));
        
        sightingDao.deleteSightingById(sighting.getSightingID());
        
        List<Sighting> testList2 = sightingDao.getAllSightings();
        
        assertEquals(1, testList2.size());
        assertTrue(testList.contains(sighting2));
    }

    @Test
    public void testUpdateSighting() {
        Superpower power = new Superpower();
        power.setPowerName("Super Strength");
        power.setPowerDesc("Can lift 100 tonnes.");
        power = superpowerDao.addPower(power);
        
        Team team = new Team();
        team.setTeamName("team1");
        team.setTeamDesc("Test Team1");
        team.setTeamAddress("test address");
        team.setTeamContactInfo("test contact info");
        team = teamDao.addTeam(team);
        
        Superhero hero = new Superhero();
        hero.setName("testHero");
        hero.setIsHero(true);
        superheroDao.addSuperhero(hero, power, team);
        
        Location location = new Location();
        location.setLocationName("test location");
        location.setLocationAdd("1234 test st.");
        location.setLocationDesc("test location");
        location.setLocationLat(Double.toString(12.3456));
        location.setLocationLat(Double.toString(98.7654));
        location = locationDao.addLocation(location);
        
        String ld = "2018-01-03 10:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(ld, formatter);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(dateTime);
        sighting.setLocationID(location.getLocationID());
        sighting.setSuperID(hero.getId());
        sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingByID(sighting.getSightingID());
        assertEquals(sighting.getSightingDate(), fromDao.getSightingDate());
        
        String ld2 = "2018-01-04 10:00:00";
        LocalDateTime dateTime2 = LocalDateTime.parse(ld2, formatter);
        sighting.setSightingDate(dateTime2);
        sightingDao.updateSighting(sighting);
        
        
        assertNotEquals(fromDao.getSightingDate(), sighting.getSightingDate());
        
        fromDao = sightingDao.getSightingByID(sighting.getSightingID());
        assertEquals(sighting.getSightingDate(), fromDao.getSightingDate());
        
    }
    @Test
    public void testGetMethods() {
        Superpower power = new Superpower();
        power.setPowerName("Super Strength");
        power.setPowerDesc("Can lift 100 tonnes.");
        power = superpowerDao.addPower(power);
        
        Team team = new Team();
        team.setTeamName("team1");
        team.setTeamDesc("Test Team1");
        team.setTeamAddress("test address");
        team.setTeamContactInfo("test contact info");
        team = teamDao.addTeam(team);
        
        Superhero hero = new Superhero();
        hero.setName("testHero");
        hero.setIsHero(true);
        hero = superheroDao.addSuperhero(hero, power, team);
        
        Superhero hero2 = new Superhero();
        hero2.setName("testHero2");
        hero2.setIsHero(true);
        hero2 = superheroDao.addSuperhero(hero2, power, team);
        
        Location location = new Location();
        location.setLocationName("test location");
        location.setLocationAdd("1234 test st.");
        location.setLocationDesc("test location");
        location.setLocationLat(Double.toString(12.3456));
        location.setLocationLat(Double.toString(98.7654));
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setLocationName("test location 2");
        location2.setLocationAdd("1234 test blvd.");
        location2.setLocationDesc("test location 2");
        location2.setLocationLat(Double.toString(98.2345));
        location2.setLocationLat(Double.toString(12.3456));
        location2 = locationDao.addLocation(location2);
        
        String ld = "2018-01-03 10:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(ld, formatter);
        
        String ld2 = "2018-01-04 10:00:00";
        LocalDateTime dateTime2 = LocalDateTime.parse(ld2, formatter);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(dateTime);
        sighting.setLocationID(location.getLocationID());
        sighting.setSuperID(hero2.getId());
        sightingDao.addSighting(sighting);
        sighting = sightingDao.getSightingByID(sighting.getSightingID());
        
        Sighting sighting2 = new Sighting();
        sighting2.setSightingDate(dateTime);
        sighting2.setLocationID(location2.getLocationID());
        sighting2.setSuperID(hero.getId());
        sightingDao.addSighting(sighting2);
        sighting2 = sightingDao.getSightingByID(sighting2.getSightingID());
        
        Sighting sighting3 = new Sighting();
        sighting3.setSightingDate(dateTime2);
        sighting3.setLocationID(location.getLocationID());
        sighting3.setSuperID(hero.getId());
        sightingDao.addSighting(sighting3);
        sighting3 = sightingDao.getSightingByID(sighting3.getSightingID());
        
        List<Sighting> testListDate = sightingDao.getSightingsByDate(dateTime);
        
        assertEquals(2, testListDate.size());
        assertTrue(testListDate.contains(sighting));
        assertTrue(testListDate.contains(sighting2));
        assertFalse(testListDate.contains(sighting3));
        
        List<Sighting> testListLocation = sightingDao.getSightingsByLocation(location);
        assertEquals(2, testListLocation.size());
        assertTrue(testListLocation.contains(sighting));
        assertTrue(testListLocation.contains(sighting3));
        assertFalse(testListLocation.contains(sighting2));
        
        List<Sighting> testListHero = sightingDao.getSightingByHero(hero);
        assertEquals(2, testListHero.size());
        assertTrue(testListHero.contains(sighting2));
        assertTrue(testListHero.contains(sighting3));
        assertFalse(testListHero.contains(sighting));
    }

    
}
