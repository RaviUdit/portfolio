/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.dao;

import com.raviudit.superherosightings.entities.Location;
import com.raviudit.superherosightings.entities.Superhero;
import com.raviudit.superherosightings.entities.Superpower;
import com.raviudit.superherosightings.entities.Team;
import java.util.List;

/**
 *
 * @author raviu
 */
public interface SuperheroDAO {
    
    Superhero getSuperheroByID(int id);
    List<Superhero> getAllSuperheros();
    Superhero addSuperhero(Superhero hero, Superpower power, Team team);
    void updateSuperhero(Superhero hero);
    void deleteSuperherobyID(int id);
    void addHeroToTeam(Superhero hero, Team team);
    void removeHeroFromTeam(Superhero hero, Team team);
    
    List<Superhero> getSuperherosByTeam(Team team);
    List<Superhero> getSuperherosByLocation(Location location);
}
