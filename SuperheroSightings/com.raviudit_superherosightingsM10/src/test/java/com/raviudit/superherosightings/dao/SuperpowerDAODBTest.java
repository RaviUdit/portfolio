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
import java.util.ArrayList;
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
public class SuperpowerDAODBTest {
    
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
    
    
    public SuperpowerDAODBTest() {
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
        
//        Sighting sighting1 = new Sighting();
//        sighting1.setSightingID(1);
//        Sighting sighting2 = new Sighting();
//        sighting2.setSightingID(2);
//        Sighting sighting3 = sightingDao.getSightingByID(3);
//        
//        List<Sighting> testSightings = new ArrayList<Sighting>(); 
//        testSightings.add(sighting1);
//        testSightings.add(sighting2);
//        
//        sightingDao.addSighting(sighting1);
//        sightingDao.addSighting(sighting2);
        
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

    @Test
    public void testGetPowerByID() {
        Superpower power = new Superpower();
        power.setPowerName("Super Strength");
        power.setPowerDesc("Can lift 100 tonnes.");
        power = superpowerDao.addPower(power);
        
        Superpower fromDao = superpowerDao.getPowerByID(power.getPowerId());
        
        assertEquals(power, fromDao);
        
    }
    
    @Test
    public void testAddPowerGetPowerByName() {
        Superpower power = new Superpower();
        power.setPowerName("Super Speed");
        power.setPowerDesc("Can move very fast");
        power = superpowerDao.addPower(power);
        
        Superpower fromDao = superpowerDao.getPowerByName(power.getPowerName());
        
        assertEquals(power, fromDao);
        
    }
    
    @Test
    public void testGetAllPowers(){
        
        Superpower power = new Superpower();
        power.setPowerName("Super Strength");
        power.setPowerDesc("Can lift 100 tonnes.");
        power = superpowerDao.addPower(power);
        
        Superpower power2 = new Superpower();
        power2.setPowerName("Super Speed");
        power2.setPowerDesc("Can move very fast");
        power2 = superpowerDao.addPower(power2);
        
        List<Superpower> powers = superpowerDao.getAllPowers();
        
        assertEquals(2, powers.size());
        assertTrue(powers.contains(power));
        assertTrue(powers.contains(power2));
    }
    
    @Test
    public void testUpdatePower(){
        
        Superpower power = new Superpower();
        power.setPowerName("Super Strength");
        power.setPowerDesc("Can lift 100 tonnes.");
        power = superpowerDao.addPower(power);
        
        Superpower fromDao = superpowerDao.getPowerByID(power.getPowerId());
        assertEquals(power, fromDao);
        
        power.setPowerDesc("Can lift 10 tonnes.");
        superpowerDao.updatePower(power);
        assertNotEquals(fromDao, power);
        
        fromDao = superpowerDao.getPowerByID(power.getPowerId());
        assertEquals(power, fromDao);
        
    }

    @Test
    public void testDeletePowerById(){
        
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
        
        Superpower fromDao = superpowerDao.getPowerByID(power.getPowerId());
        assertEquals(power, fromDao);
        
        superpowerDao.deletePowerByID(power.getPowerId());
        
        fromDao = superpowerDao.getPowerByID(power.getPowerId());
        
        assertNull(fromDao);
    }
    
}
