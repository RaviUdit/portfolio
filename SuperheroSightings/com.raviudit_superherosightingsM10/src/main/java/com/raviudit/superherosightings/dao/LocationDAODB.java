/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.dao;

import com.raviudit.superherosightings.entities.Location;
import com.raviudit.superherosightings.entities.Superhero;
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
public class LocationDAODB implements LocationDAO{
    
    @Autowired
    JdbcTemplate jdbc;
    
    final String GET_LOCATION_BY_ID = "SELECT * FROM Location WHERE LocationID = ?";
    final String GET_LOCATION_BY_NAME = "SELECT * FROM Location WHERE LocationName = ?";
    final String GET_LOCATIONS_BY_HERO = "SELECT * FROM Location l INNER JOIN Sighting st ON st.LocationID = l.LocationID WHERE st.SuperID = ?";
    final String GET_ALL_LOCATIONS = "SELECT * FROM Location";
    final String INSERT_LOCATION = "INSERT INTO Location(LocationName, LocationDesc, LocationAddress, LocationLat, LocationLong) VALUES(?,?,?,?,?)";
    final String UPDATE_LOCATION = "UPDATE Location SET LocationName = ?, LocationDesc = ?, LocationAddress = ?, LocationLat = ?, LocationLong = ? WHERE LocationID =?";
    final String DELETE_LOCATION = "DELETE FROM Location WHERE LocationID = ?";
    final String DELETE_LOCATION_FROM_SIGHTING = "DELETE FROM Sighting WHERE LocationID = ?";

    @Override
    public Location getLocationByID(int id) {
       try{
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), id);
        } catch(DataAccessException ex){
            return null;
        }
    }
    
    @Override
    public Location getLocationByName(String name) {
        try{
            return jdbc.queryForObject(GET_LOCATION_BY_NAME, new LocationMapper(), name);
        } catch(DataAccessException ex){
            return null;
        }
    }
    
    @Override
    public List<Location> getAllLocations() {
        return jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        jdbc.update(INSERT_LOCATION,
                    location.getLocationName(),
                    location.getLocationDesc(),
                    location.getLocationAdd(),
                    location.getLocationLat(),
                    location.getLocationLon());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationID(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        jdbc.update(UPDATE_LOCATION,
                    location.getLocationName(),
                    location.getLocationDesc(),
                    location.getLocationAdd(),
                    location.getLocationLat(),
                    location.getLocationLon(),
                    location.getLocationID());
    }

    @Override
    @Transactional
    public void deleteLocationbyID(int id) {
        jdbc.update(DELETE_LOCATION_FROM_SIGHTING, id);
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public List<Location> getLocationsBySuperHero(Superhero hero) {
        
        List<Location> locationList = jdbc.query(GET_LOCATIONS_BY_HERO, new LocationMapper(), hero.getId());
        return locationList;
    }


    public static final class LocationMapper implements RowMapper<Location> {
        
        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException{
            Location location = new Location();
            location.setLocationID(rs.getInt("LocationID"));
            location.setLocationName(rs.getString("LocationName"));
            location.setLocationDesc(rs.getString("LocationDesc"));
            location.setLocationAdd(rs.getString("LocationAddress"));
            location.setLocationLat(Double.toString(rs.getDouble("LocationLat")));
            location.setLocationLon(Double.toString(rs.getDouble("LocationLong")));
            
            return location;
        }
    }
    
}
