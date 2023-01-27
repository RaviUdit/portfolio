/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.dao;

import com.raviudit.superherosightings.entities.Superhero;
import com.raviudit.superherosightings.entities.Team;
import java.util.List;

/**
 *
 * @author raviu
 */
public interface TeamDAO {
    
    Team getTeamByID(int id);
    Team getTeamByName(String name);
    List<Team> getAllTeams();
    Team addTeam(Team team);
    void updateTeam(Team team);
    void deleteTeamByID(int id);
    
    List<Team> getTeamsBySuperhero(Superhero hero);
    
}
