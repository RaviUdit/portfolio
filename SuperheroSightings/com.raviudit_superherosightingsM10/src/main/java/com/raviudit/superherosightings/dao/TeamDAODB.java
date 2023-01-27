/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.dao;

import com.raviudit.superherosightings.entities.Superhero;
import com.raviudit.superherosightings.entities.Team;
import com.raviudit.superherosightings.dao.SuperheroDAODB.SuperheroMapper;
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
public class TeamDAODB implements TeamDAO{
    
    @Autowired
    JdbcTemplate jdbc;
    
    final String GET_TEAM_BY_ID = "SELECT * FROM Team WHERE TeamID = ?";
    final String GET_TEAM_BY_NAME = "SELECT * FROM Team WHERE TeamName = ?";
    final String GET_ALL_TEAM = "SELECT * FROM Team";
    final String INSERT_TEAM = "INSERT INTO Team(TeamName, TeamDesc, TeamAddress, TeamContactInfo) VALUES(?,?,?,?)";
    final String UPDATE_TEAM = "UPDATE Team SET TeamName = ?, TeamDesc = ?, TeamAddress = ?, TeamContactInfo = ? WHERE TeamID =?";
    final String DELETE_TEAM = "DELETE FROM Team WHERE TeamID = ?";
    final String DELETE_TEAMMEMBERS = "DELETE FROM TeamMembers WHERE TeamID = ?";
    final String GET_HEROES_FOR_TEAM = "SELECT DISTINCT sh.* FROM Superhero sh INNER JOIN TeamMembers tm ON sh.SuperID = tm.SuperID WHERE tm.TeamID = ?";
    final String GET_TEAMS_BY_SUPERHERO = "SELECT t.* FROM Team t INNER JOIN TeamMembers tm ON t.teamID = tm.teamID WHERE tm.SuperID = ?";


    @Override
    public Team getTeamByID(int id) {
        try{
            Team team = jdbc.queryForObject(GET_TEAM_BY_ID, new TeamMapper(), id);
            team.setTeamMembers(getHeroesForTeam(id));
            return team;
        } catch(DataAccessException ex){
            return null;
        }
    }
    
    private List<Superhero> getHeroesForTeam(int teamId){
        
//        Superhero hero = new Superhero();
//        hero.setTeams(getTeamsBySuperhero(hero));
    
        List<Superhero> heroes = jdbc.query(GET_HEROES_FOR_TEAM, new SuperheroMapper(), teamId);
        for(Superhero hero : heroes){
            hero.setTeams(getTeamsBySuperhero(hero));
        }

        return heroes;
        //return jdbc.query(GET_HEROES_FOR_TEAM, new SuperheroMapper(), teamId);
    }   

    @Override
    public Team getTeamByName(String name) {
        try{
            return jdbc.queryForObject(GET_TEAM_BY_NAME, new TeamMapper(), name);
        } catch(DataAccessException ex){
            return null;
        }
    }
    
    @Override
    public List<Team> getAllTeams(){
        return jdbc.query(GET_ALL_TEAM, new TeamMapper());
    }

    @Override
    @Transactional
    public Team addTeam(Team team) {
        jdbc.update(INSERT_TEAM,
                    team.getTeamName(),
                    team.getTeamDesc(),
                    team.getTeamAddress(),
                    team.getTeamContactInfo());
       
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        team.setTeamId(newId);
        return team;
    }

    @Override
    public void updateTeam(Team team) {
        jdbc.update(UPDATE_TEAM,
                    team.getTeamName(),
                    team.getTeamDesc(),
                    team.getTeamAddress(),
                    team.getTeamContactInfo(),
                    team.getTeamId());
    }

    @Override
    @Transactional
    public void deleteTeamByID(int id) {
        jdbc.update(DELETE_TEAMMEMBERS, id);
        jdbc.update(DELETE_TEAM, id);
    }

    @Override
    public List<Team> getTeamsBySuperhero(Superhero hero) {
        return jdbc.query(GET_TEAMS_BY_SUPERHERO, new TeamMapper(), hero.getId());
    }
    
    public static final class TeamMapper implements RowMapper<Team> {
        
        @Override
        public Team mapRow(ResultSet rs, int index) throws SQLException{
            Team team = new Team();
            team.setTeamId(rs.getInt("TeamID"));
            team.setTeamName(rs.getString("TeamName"));
            team.setTeamDesc(rs.getString("TeamDesc"));
            team.setTeamContactInfo(rs.getString("TeamContactInfo"));
            team.setTeamAddress(rs.getString("TeamAddress"));
            
            return team;
        }
    }
    
}
