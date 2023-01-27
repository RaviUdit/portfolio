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
public class TeamDAODBTest {
    
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
    
    public TeamDAODBTest() {
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
    public void testAddGetTeamByID() {
        
//        Superpower power = new Superpower();
//        power.setPowerName("Super Strength");
//        power.setPowerDesc("Can lift 100 tonnes.");
//        power = superpowerDao.addPower(power);
        
        Team team = new Team();
        team.setTeamName("team1");
        team.setTeamDesc("Test Team1");
        team.setTeamAddress("test address");
        team.setTeamContactInfo("test contact info");
        team = teamDao.addTeam(team);
        
//        Superhero hero = new Superhero();
//        hero.setName("testHero");
//        hero.setIsHero(true);
//        superheroDao.addSuperhero(hero, power, team);
        
        Team fromDao = teamDao.getTeamByID(team.getTeamId());
        fromDao.setTeamMembers(null);
        
        assertEquals(team.toString(), fromDao.toString());
    }

    @Test
    public void testAddGetTeamByName() {
        Team team = new Team();
        team.setTeamName("team1");
        team.setTeamDesc("Test Team1");
        team.setTeamAddress("test address");
        team.setTeamContactInfo("test contact info");
        team = teamDao.addTeam(team);
        
        Team fromDao = teamDao.getTeamByName(team.getTeamName());
        
        assertEquals(team.getTeamId(), fromDao.getTeamId());
    }

    @Test
    public void testGetAllTeams() {
        Team team = new Team();
        team.setTeamName("team1");
        team.setTeamDesc("Test Team1");
        team.setTeamAddress("test address");
        team.setTeamContactInfo("test contact info");
        team = teamDao.addTeam(team);
        
        Team team2 = new Team();
        team2.setTeamName("team 1");
        team2.setTeamDesc("Test Team 2");
        team2.setTeamAddress("test address 2");
        team2.setTeamContactInfo("test contact info 2");
        team2 = teamDao.addTeam(team2);
        
        List<Team> testTeams = teamDao.getAllTeams();
        
        assertEquals(2, testTeams.size());
        assertTrue(testTeams.contains(team));
        assertTrue(testTeams.contains(team2));
    }

    @Test
    public void testUpdateTeam() {
        Team team = new Team();
        team.setTeamName("team1");
        team.setTeamDesc("Test Team1");
        team.setTeamAddress("test address");
        team.setTeamContactInfo("test contact info");
        team = teamDao.addTeam(team);
        
        Team fromDao = teamDao.getTeamByID(team.getTeamId());
        
        assertEquals(team.getTeamId(), fromDao.getTeamId());
        
        team.setTeamDesc("changed team description");
        teamDao.updateTeam(team);
        
        assertNotEquals(fromDao.getTeamDesc(), team.getTeamDesc());
        
        fromDao = teamDao.getTeamByID(team.getTeamId());
        assertEquals(team.getTeamDesc(), fromDao.getTeamDesc());
        
    }

    @Test
    public void testDeleteTeamByID() {
        Team team = new Team();
        team.setTeamName("team1");
        team.setTeamDesc("Test Team1");
        team.setTeamAddress("test address");
        team.setTeamContactInfo("test contact info");
        team = teamDao.addTeam(team);
        
        Team team2 = new Team();
        team2.setTeamName("team 2");
        team2.setTeamDesc("Test Team 2");
        team2.setTeamAddress("test address 2");
        team2.setTeamContactInfo("test contact info 2");
        team = teamDao.addTeam(team2);
        
        List<Team> testList = teamDao.getAllTeams();
        assertEquals(2, testList.size());
        assertTrue(testList.contains(team));
        assertTrue(testList.contains(team2));
        
        teamDao.deleteTeamByID(team2.getTeamId());
        
        List<Team> testList2 = teamDao.getAllTeams();
        assertEquals(1, testList2.size());
        assertFalse(testList2.contains(team2));
    }

    @Test
    public void testGetTeamsBySuperhero() {
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
        team2.setTeamName("team 2");
        team2.setTeamDesc("Test Team 2");
        team2.setTeamAddress("test address 2");
        team2.setTeamContactInfo("test contact info 2");
        team2 = teamDao.addTeam(team2);
        
        Team team3 = new Team();
        team3.setTeamName("team 3");
        team3.setTeamDesc("Test Team 3");
        team3.setTeamAddress("test address 3");
        team3.setTeamContactInfo("test contact info 3");
        team3 = teamDao.addTeam(team3);
        
        Superhero hero = new Superhero();
        hero.setName("testHero");
        hero.setIsHero(true);
        superheroDao.addSuperhero(hero, power, team);
        superheroDao.addHeroToTeam(hero, team2);
        
        List<Team> testList = teamDao.getTeamsBySuperhero(hero);
        assertEquals(2, testList.size());
        assertFalse(testList.contains(team3));
    }
    
}
