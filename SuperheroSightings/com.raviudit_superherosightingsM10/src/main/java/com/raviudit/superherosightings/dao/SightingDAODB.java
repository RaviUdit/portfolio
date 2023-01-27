/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.dao;

import com.raviudit.superherosightings.dao.LocationDAODB.LocationMapper;
import com.raviudit.superherosightings.dao.SuperheroDAODB.SuperheroMapper;
import com.raviudit.superherosightings.entities.Location;
import com.raviudit.superherosightings.entities.Sighting;
import com.raviudit.superherosightings.entities.Superhero;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author raviu
 */

@Repository
public class SightingDAODB implements SightingDAO{
    
    @Autowired
    JdbcTemplate jdbc;
    
    final String GET_SIGHTING_BY_ID = "SELECT * FROM Sighting WHERE SightingID = ?";
    final String GET_SIGHTING_BY_DATE = "SELECT * FROM Sighting WHERE SightingDate = ?";
    final String GET_SIGHTING_BY_HERO = "SELECT * FROM Sighting WHERE SuperID = ?";
    final String GET_SIGHTING_BY_LOCATION = "SELECT * FROM Sighting WHERE LocationID = ?";
    final String GET_ALL_SIGHTINGS = "SELECT * FROM Sighting";
    //final String GET_HERO_FOR_SIGHTING = "SELECT * FROM Superhero sh INNER JOIN Sighting st ON st.SuperID = sh.SuperID WHERE sh.SuperID = ?";
    //final String GET_LOCATION_FOR_SIGHTING = "SELECT * FROM Location l INNER JOIN Sighting st ON st.LocationID = l.LocationID WHERE locationID = ?";
    final String GET_HERO_FOR_SIGHTING = "SELECT * FROM Superhero WHERE SuperID = ?";
    final String GET_LOCATION_FOR_SIGHTING = "SELECT * FROM Location WHERE locationID = ?";
    final String INSERT_SIGHTING = "INSERT INTO Sighting (SightingDate, SuperID, LocationID) VALUES (?, ?, ?)";
    final String UPDATE_SIGHTING = "UPDATE Sighting SET SightingDate = ?, SuperID = ?, LocationID = ? WHERE SightingID = ?";
    final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE SightingID = ?";
    final String GET_LAST_TEN_SIGHTINGS = "SELECT * FROM sighting ORDER BY SightingDate DESC LIMIT 10";
    
    @Override
    public Sighting getSightingByID(int id) {
        try{
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setSuperhero(getHeroForSighting(sighting.getSuperID()));
            sighting.setLocation(getLocationForSighting(sighting.getLocationID()));
            
            return sighting;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    private Superhero getHeroForSighting(int superheroID){
       
        Superhero hero = new Superhero();
        hero = jdbc.queryForObject(GET_HERO_FOR_SIGHTING, new SuperheroMapper(), superheroID);
        return hero;
    }
    
    private Location getLocationForSighting(int locationID){
        
        Location location = new Location();
        location = jdbc.queryForObject(GET_LOCATION_FOR_SIGHTING, new LocationMapper(), locationID);
        return location;
    }

    @Override
    public List<Sighting> getAllSightings() {
        
//        Sighting sighting1 = new Sighting();
//        sighting1.setSightingID(1);
//        Sighting sighting2 = new Sighting();
//        sighting2.setSightingID(2);
//        
//        List<Sighting> testSightings = new ArrayList<Sighting>(); 
//        testSightings.add(sighting1);
//        testSightings.add(sighting2);
        
        List<Sighting> sightings = jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
        getSuperheroAndLocationForSighting(sightings);
        
        //return testSightings;
        
        return sightings;
    }
    
    private void getSuperheroAndLocationForSighting(List<Sighting> sightings){
    
        for(Sighting sighting : sightings){
            sighting.setSuperhero(getHeroForSighting(sighting.getSuperID()));
            sighting.setLocation(getLocationForSighting(sighting.getLocationID()));
        }
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        
        jdbc.update(INSERT_SIGHTING, 
                    Timestamp.valueOf(sighting.getSightingDate()),
                    sighting.getSuperID(),
                    sighting.getLocationID());
        
        int newid = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingID(newid);
       
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        
        jdbc.update(UPDATE_SIGHTING, 
                    Timestamp.valueOf(sighting.getSightingDate()),
                    sighting.getSuperID(),
                    sighting.getLocationID(),
                    sighting.getSightingID());
    }

    @Override
    public void deleteSightingById(int id) {

        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDateTime date) {
        
        try{
            List<Sighting> sightings = jdbc.query(GET_SIGHTING_BY_DATE, new SightingMapper(), Timestamp.valueOf(date));
            getSuperheroAndLocationForSighting(sightings);
            
            return sightings;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location location) {
        try{
            List<Sighting> sightings = jdbc.query(GET_SIGHTING_BY_LOCATION, new SightingMapper(), location.getLocationID());
            getSuperheroAndLocationForSighting(sightings);
            
            return sightings;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getSightingByHero(Superhero hero) {
       try{
            List<Sighting> sightings = jdbc.query(GET_SIGHTING_BY_HERO, new SightingMapper(), hero.getId());
            getSuperheroAndLocationForSighting(sightings);
            
            return sightings;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public List<Sighting> getLastTenSightings(){
        
        List<Sighting> sightings = jdbc.query(GET_LAST_TEN_SIGHTINGS, new SightingMapper());
        getSuperheroAndLocationForSighting(sightings);

        return sightings;
    }
    
    public static final class SightingMapper implements RowMapper<Sighting> {
        
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException{
            
            Sighting sighting = new Sighting();
            sighting.setSightingID(rs.getInt("SightingID"));
            sighting.setSightingDate(rs.getTimestamp("SightingDate").toLocalDateTime());
            sighting.setSuperID(rs.getInt("SuperID"));
            sighting.setLocationID(rs.getInt("LocationID"));
            
            return sighting;
        }
    }
    
}
