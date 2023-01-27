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
public class SuperheroDAODBTest {
    
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
    
    public SuperheroDAODBTest() {
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
    public void testAddGetSuperheroByID() {
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
        
        Superhero fromDao = superheroDao.getSuperheroByID(hero.getId());
        
        assertEquals(hero.getId(), fromDao.getId());
    }

    @Test
    public void testUpdateGetAllSuperherosDeleteByID() {
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
        hero = superheroDao.getSuperheroByID(hero.getId());
        
        Superhero hero2 = new Superhero();
        hero2.setName("testHero2");
        hero2.setIsHero(true);
        hero2 = superheroDao.addSuperhero(hero2, power, team);
        hero2 = superheroDao.getSuperheroByID(hero2.getId());
        
        List<Superhero> testList = superheroDao.getAllSuperheros();     
        assertEquals(2, testList.size());
        assertTrue(testList.contains(hero));
        assertTrue(testList.contains(hero2));
        
        superheroDao.deleteSuperherobyID(hero.getId());
        
        List<Superhero> testList2 = superheroDao.getAllSuperheros();
        assertEquals(1, testList2.size());
        assertFalse(testList2.contains(hero));
        assertTrue(testList2.contains(hero2));
        
        Superhero fromDao = superheroDao.getSuperheroByID(hero2.getId());
        
        hero2.setName("HeroNamev.2");
        superheroDao.updateSuperhero(hero2);
        hero2 = superheroDao.getSuperheroByID(hero2.getId());
        
        //assertNotEquals(fromDao.getName(), hero2.getName());
        assertNotEquals(fromDao, hero2);
        
    }


    @Test
    public void testGetSuperherosByTeamByLocation() {
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
        
        Team team2 = new Team();
        team2.setTeamName("team2");
        team2.setTeamDesc("Test Team2");
        team2.setTeamAddress("test address2");
        team2.setTeamContactInfo("test contact info");
        team2 = teamDao.addTeam(team2);
        
        Superhero hero = new Superhero();
        hero.setName("testHero");
        hero.setIsHero(true);
        hero = superheroDao.addSuperhero(hero, power, team);
        hero = superheroDao.getSuperheroByID(hero.getId());
        
        Superhero hero2 = new Superhero();
        hero2.setName("testHero2");
        hero2.setIsHero(true);
        hero2 = superheroDao.addSuperhero(hero2, power, team);
        hero2 = superheroDao.getSuperheroByID(hero2.getId());
        
        Superhero hero3 = new Superhero();
        hero3.setName("testHero3");
        hero3.setIsHero(true);
        hero3 = superheroDao.addSuperhero(hero3, power, team2);
        hero3 = superheroDao.getSuperheroByID(hero3.getId());
        
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
        sighting3.setSuperID(hero3.getId());
        sightingDao.addSighting(sighting3);
        sighting3 = sightingDao.getSightingByID(sighting3.getSightingID());
        
        List<Superhero> testListTeam = superheroDao.getSuperherosByTeam(team);
        assertEquals(2, testListTeam.size());
        assertTrue(testListTeam.contains(hero));
        assertTrue(testListTeam.contains(hero2));
        assertFalse(testListTeam.contains(hero3));
        
        List<Superhero> testListLocation = superheroDao.getSuperherosByLocation(location);
        assertEquals(2, testListLocation.size());
        assertTrue(testListLocation.contains(hero2));
        assertFalse(testListLocation.contains(hero));
        assertTrue(testListLocation.contains(hero3));
    }

    @Test
    public void testAddHeroToRemoveHeroFromTeam() {
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
        
        Team team2 = new Team();
        team2.setTeamName("team2");
        team2.setTeamDesc("Test Team2");
        team2.setTeamAddress("test address2");
        team2.setTeamContactInfo("test contact info");
        team2 = teamDao.addTeam(team2);
        
        Superhero hero = new Superhero();
        hero.setName("testHero");
        hero.setIsHero(true);
        hero = superheroDao.addSuperhero(hero, power, team);
        hero = superheroDao.getSuperheroByID(hero.getId());
        
        List<Team> testList= teamDao.getTeamsBySuperhero(hero);
        assertEquals(1, testList.size());
        assertTrue(testList.contains(team));
        
        superheroDao.addHeroToTeam(hero, team2);
        
        List<Team> testList2= teamDao.getTeamsBySuperhero(hero);
        assertEquals(2, testList2.size());
        assertTrue(testList2.contains(team));
        assertTrue(testList2.contains(team2));
        
        superheroDao.removeHeroFromTeam(hero, team);
        
        List<Team> testList3= teamDao.getTeamsBySuperhero(hero);
        assertEquals(1, testList3.size());
        assertTrue(testList3.contains(team2));
        
    }
    
}
