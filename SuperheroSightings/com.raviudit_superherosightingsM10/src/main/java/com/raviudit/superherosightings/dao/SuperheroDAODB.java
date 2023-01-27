/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.dao;

import com.raviudit.superherosightings.dao.SuperpowerDAODB.SuperpowerMapper;
import com.raviudit.superherosightings.dao.TeamDAODB.TeamMapper;
import com.raviudit.superherosightings.entities.Location;
import com.raviudit.superherosightings.entities.Superhero;
import com.raviudit.superherosightings.entities.Superpower;
import com.raviudit.superherosightings.entities.Team;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author raviu
 */
@Repository
public class SuperheroDAODB implements SuperheroDAO {
    
    @Autowired
    JdbcTemplate jdbc;
    
    final String GET_HERO_BY_ID = "SELECT * FROM Superhero WHERE SuperID = ?";
    final String GET_ALL_HEROES = "SELECT * FROM Superhero";
    final String GET_POWER_FOR_HERO = "SELECT sp.* FROM Superpower sp WHERE sp.SuperpowerID = ?";    
    final String GET_TEAMS_FOR_HERO = "SELECT t.* FROM Team t INNER JOIN TeamMembers tm ON t.TeamID = tm.TeamID WHERE tm.SuperID = ?";
    final String INSERT_HERO = "INSERT INTO Superhero( SuperName, IsHero, SuperPowerID) VALUES( ?, ?, ?)";
    final String INSERT_HERO_INTO_TEAM = "INSERT INTO TeamMembers(SuperID, TeamID) VALUES(?, ?)";
    final String UPDATE_HERO = "UPDATE Superhero SET SuperName = ?, IsHero = ?, SuperPowerID = ? WHERE SuperID = ?";
    final String REMOVE_HERO_FROM_TEAM = "DELETE FROM TeamMembers WHERE SuperID = ?";
    final String REMOVE_HERO_FROM_SPECIFIC_TEAM = "DELETE FROM TeamMembers WHERE SuperID = ? AND TeamID = ?";
    final String DELETE_HERO = "DELETE FROM Superhero WHERE SuperID = ?";
    final String GET_HEROES_BY_TEAM = "SELECT sh.* FROM Superhero sh INNER JOIN TeamMembers tm ON sh.SuperID = tm.SuperID WHERE tm.TeamID = ?";
    final String GET_HEROES_BY_LOCATION = "SELECT sh.* FROM Superhero sh INNER JOIN Sighting st ON sh.SuperID = st.SuperID WHERE st.LocationID = ?";
    
    @Override
    public Superhero getSuperheroByID(int id) {
        try{
            Superhero hero = jdbc.queryForObject(GET_HERO_BY_ID, new SuperheroMapper(), id);
            hero.setSuperpower(getPowerForSuperhero(hero.getPowerId()));
            hero.setTeams(getTeamsForSuperhero(hero.getId()));
            
            return hero;
        } catch(DataAccessException ex){
            return null;
        }
    }
    
    private Superpower getPowerForSuperhero(int powerId){
        Superpower power = new Superpower();
        power = jdbc.queryForObject(GET_POWER_FOR_HERO, new SuperpowerMapper(), powerId);
        return power;
    }
    
    private List<Team> getTeamsForSuperhero(int heroId){
        return jdbc.query(GET_TEAMS_FOR_HERO, new TeamMapper(), heroId);
    }

    @Override
    public List<Superhero> getAllSuperheros() {

        List<Superhero> heroList = jdbc.query(GET_ALL_HEROES, new SuperheroMapper());
        getPowerAndTeamForSuperhero(heroList);
        return heroList;
    }
    
    private void getPowerAndTeamForSuperhero(List<Superhero> heroes){
        
        for(Superhero hero : heroes){
            hero.setSuperpower(getPowerForSuperhero(hero.getPowerId()));
            hero.setTeams(getTeamsForSuperhero(hero.getId()));
        }
    }

    @Override
    @Transactional
    public Superhero addSuperhero(Superhero hero, Superpower power, Team team) {
        jdbc.update(INSERT_HERO, 
                    hero.getName(),
                    hero.isIsHero(),
                    power.getPowerId());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);
        
        jdbc.update(INSERT_HERO_INTO_TEAM,
                    hero.getId(),
                    team.getTeamId());
        
        hero.setSuperpower(getPowerForSuperhero(power.getPowerId()));
        hero.setTeams(getTeamsForSuperhero(hero.getId()));
        
        return hero;
    }

    @Override
    @Transactional
    public void updateSuperhero(Superhero hero) {
        jdbc.update(UPDATE_HERO, 
                    hero.getName(),
                    hero.isIsHero(),
                    hero.getPowerId(),
                    hero.getId());
    }

    @Override
    @Transactional
    public void deleteSuperherobyID(int id) {
        
        jdbc.update(REMOVE_HERO_FROM_TEAM, id);
        jdbc.update(DELETE_HERO, id);
    }

    @Override
    public List<Superhero> getSuperherosByTeam(Team team) {
        List<Superhero> heroList = jdbc.query(GET_HEROES_BY_TEAM, new SuperheroMapper(), team.getTeamId());
        getPowerAndTeamForSuperhero(heroList);
        return heroList;
    }

    @Override
    public List<Superhero> getSuperherosByLocation(Location location) {
        List<Superhero> heroList = jdbc.query(GET_HEROES_BY_LOCATION, new SuperheroMapper(), location.getLocationID());
        getPowerAndTeamForSuperhero(heroList);
        return heroList;
    }

    @Override
    public void addHeroToTeam(Superhero hero, Team team) {
        jdbc.update(INSERT_HERO_INTO_TEAM,
                    hero.getId(),
                    team.getTeamId());
    }

    @Override
    public void removeHeroFromTeam(Superhero hero, Team team) {
        jdbc.update(REMOVE_HERO_FROM_SPECIFIC_TEAM,
                    hero.getId(),
                    team.getTeamId());
    }
    
    public static final class SuperheroMapper implements RowMapper<Superhero> {
        
        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException{
            Superhero hero = new Superhero();
            hero.setId(rs.getInt("SuperID"));
            hero.setName(rs.getString("SuperName"));
            hero.setIsHero(rs.getBoolean("IsHero"));
            hero.setPowerId(rs.getInt("SuperPowerID"));
            
            
            return hero;
        }
    }
}
