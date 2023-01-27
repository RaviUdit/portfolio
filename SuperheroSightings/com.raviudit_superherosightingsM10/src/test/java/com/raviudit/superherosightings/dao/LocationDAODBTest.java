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

/**
 *
 * @author raviu
 */

@SpringBootTest
public class LocationDAODBTest {
    
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
    
    public LocationDAODBTest() {
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
        
//        List<Team> teams = teamDao.getAllTeams();
//        for(Team team : teams){
//            teamDao.deleteTeamByID(team.getTeamId());
//        }
//        
//        List<Sighting> sightings = sightingDao.getAllSightings();
//        for(Sighting sighting : sightings){
//            sightingDao.deleteSightingById(sighting.getSightingID());
//        }
//        
//        List<Superhero> heroes = superheroDao.getAllSuperheros();
//        for(Superhero hero : heroes){
//            superheroDao.deleteSuperherobyID(hero.getId());
//        }
//        
//        List<Superpower> powers = superpowerDao.getAllPowers();
//        for(Superpower power : powers){
//            superpowerDao.deletePowerByID(power.getPowerId());
//        }
//        
//        List<Location> locations = locationDao.getAllLocations();
//        for(Location location : locations){
//            locationDao.deleteLocationbyID(location.getLocationID());
//        }
    }

//    @Test
    public void testAddLocationGetLocationByID() {
        Location location = new Location();
        location.setLocationName("test location");
        location.setLocationAdd("1234 test st.");
        location.setLocationDesc("test location");
        location.setLocationLat(Double.toString(12.3456));
        location.setLocationLat(Double.toString(98.7654));
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationByID(location.getLocationID());
        assertEquals(location, fromDao);
    }

//    @Test
    public void testGetLocationByName() {
        Location location = new Location();
        location.setLocationName("test location");
        location.setLocationAdd("1234 test st.");
        location.setLocationDesc("test location");
//        location.setLocationLat(12.3456);
//        location.setLocationLat(98.7654);
        location.setLocationLat(Double.toString(12.3456));
        location.setLocationLat(Double.toString(98.7654));
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationByName(location.getLocationName());
        assertEquals(location, fromDao);
    }

//    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setLocationName("test location");
        location.setLocationAdd("1234 test st.");
        location.setLocationDesc("test location");
//        location.setLocationLat(12.3456);
//        location.setLocationLat(98.7654);
        location.setLocationLat(Double.toString(12.3456));
        location.setLocationLat(Double.toString(98.7654));
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setLocationName("test location");
        location2.setLocationAdd("1234 test st.");
        location2.setLocationDesc("test location");
//        location.setLocationLat(12.3456);
//        location.setLocationLat(98.7654);
        location.setLocationLat(Double.toString(12.3456));
        location.setLocationLat(Double.toString(98.7654));
        location2 = locationDao.addLocation(location2);
        
        List<Location> locations = locationDao.getAllLocations();
        
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

//    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setLocationName("test location");
        location.setLocationAdd("1234 test st.");
        location.setLocationDesc("test location");
//        location.setLocationLat(12.3456);
//        location.setLocationLat(98.7654);
        location.setLocationLat(Double.toString(12.3456));
        location.setLocationLat(Double.toString(98.7654));
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationByName(location.getLocationName());
        assertEquals(location, fromDao);
        
        location.setLocationAdd("2345 location blvd.");
        locationDao.updateLocation(location);
        assertNotEquals(fromDao, location);
        
        fromDao = locationDao.getLocationByName(location.getLocationName());
        assertEquals(location, fromDao);
    }

//    @Test
    public void testDeleteLocationbyID() {
        Location location = new Location();
        location.setLocationName("test location");
        location.setLocationAdd("1234 test st.");
        location.setLocationDesc("test location");
//        location.setLocationLat(12.3456);
//        location.setLocationLat(98.7654);
        location.setLocationLat(Double.toString(12.3456));
        location.setLocationLat(Double.toString(98.7654));
        location = locationDao.addLocation(location);
        
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
        
        Location fromDao = locationDao.getLocationByName(location.getLocationName());
        assertEquals(location, fromDao);
        
        locationDao.deleteLocationbyID(location.getLocationID());
        fromDao = locationDao.getLocationByName(location.getLocationName());
        
        assertNull(fromDao);
    }

//    @Test
    public void testGetLocationsBySuperHero() {
        Location location = new Location();
        location.setLocationName("test location");
        location.setLocationAdd("1234 test st.");
        location.setLocationDesc("test location");
//        location.setLocationLat(12.3456);
//        location.setLocationLat(98.7654);
        location.setLocationLat(Double.toString(12.3456));
        location.setLocationLat(Double.toString(98.7654));
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setLocationName("test location");
        location2.setLocationAdd("5672 test lane");
        location2.setLocationDesc("test2 location");
//        location.setLocationLat(12.3456);
//        location.setLocationLat(98.7654);
        location.setLocationLat(Double.toString(12.3456));
        location.setLocationLat(Double.toString(98.7654));
        location2 = locationDao.addLocation(location2);
        
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
        
        String ld = "2018-01-03 10:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(ld, formatter);
        
        String ld2 = "2018-01-04 10:00:00";
        LocalDateTime dateTime2 = LocalDateTime.parse(ld2, formatter);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(dateTime);
        sighting.setLocationID(location.getLocationID());
        sighting.setSuperID(hero.getId());
        sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setSightingDate(dateTime2);
        sighting2.setLocationID(location2.getLocationID());
        sighting2.setSuperID(hero.getId());
        sightingDao.addSighting(sighting2);
        
        List<Location> locations = locationDao.getLocationsBySuperHero(hero);
        
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }    
}
