/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.dao;

import com.raviudit.superherosightings.entities.Location;
import com.raviudit.superherosightings.entities.Superhero;
import java.util.List;

/**
 *
 * @author raviu
 */
public interface LocationDAO {
    
    Location getLocationByID(int id);
    Location getLocationByName(String name);
    List<Location> getAllLocations();
    Location addLocation(Location location);
    void updateLocation(Location location);
    void deleteLocationbyID(int id);
    
    List<Location> getLocationsBySuperHero(Superhero hero);
    
}
