/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.dao;

import com.raviudit.superherosightings.dao.SuperheroDAODB.SuperheroMapper;
import com.raviudit.superherosightings.entities.Superhero;
import com.raviudit.superherosightings.entities.Superpower;
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
public class SuperpowerDAODB implements SuperpowerDAO{

    @Autowired
    JdbcTemplate jdbc;
    
    final String GET_POWER_BY_ID = "SELECT * FROM SuperPower WHERE SuperPowerID = ?";
    final String GET_POWER_BY_NAME = "SELECT * FROM SuperPower WHERE SuperPowerName = ?";
    final String GET_ALL_POWERS = "SELECT * FROM SuperPower";
    final String INSERT_POWER = "INSERT INTO SuperPower(SuperPowerName, SuperPowerDesc) VALUES(?,?)";
    final String UPDATE_POWER = "UPDATE SuperPower SET SuperPowerName = ?, SuperPowerDesc = ? WHERE SuperPowerID =?";
    final String DELETE_POWER = "DELETE FROM SuperPower WHERE SuperPowerID = ?";
    final String DELETE_HERO = "DELETE FROM Superhero WHERE SuperPowerID = ?";
    final String GET_HEROES = "SELECT * FROM Superhero WHERE SuperPowerID = ?";
    final String DELETE_HERO_FROM_TEAM = "DELETE FROM TeamMembers WHERE SuperID = ?";
    
    @Override
    public Superpower getPowerByID(int id) {
        try{
            return jdbc.queryForObject(GET_POWER_BY_ID, new SuperpowerMapper(), id);
        } catch(DataAccessException ex){
            return null;
        }
    }
    
    @Override
    public Superpower getPowerByName(String powerName){
        try{
            return jdbc.queryForObject(GET_POWER_BY_NAME, new SuperpowerMapper(), powerName);
        } catch(DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Superpower> getAllPowers() {
        return jdbc.query(GET_ALL_POWERS, new SuperpowerMapper());
    }

    @Override
    @Transactional
    public Superpower addPower(Superpower power) {
        jdbc.update(INSERT_POWER, 
                    power.getPowerName(),
                    power.getPowerDesc());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setPowerId(newId);
        return power;
    }

    @Override
    public void updatePower(Superpower power) {
        jdbc.update(UPDATE_POWER, 
                    power.getPowerName(),
                    power.getPowerDesc(),
                    power.getPowerId());
    }

    @Override
    @Transactional
    public void deletePowerByID(int id) {
    
        List<Superhero> heroes = jdbc.query(GET_HEROES, new SuperheroMapper(), id);
        for(Superhero hero : heroes){
            jdbc.update(DELETE_HERO_FROM_TEAM, hero.getId());
        }
    
        jdbc.update(DELETE_HERO, id);
        jdbc.update(DELETE_POWER, id);
    }
    
    public static final class SuperpowerMapper implements RowMapper<Superpower> {
        
        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException{
            Superpower power = new Superpower();
            power.setPowerId(rs.getInt("SuperPowerID"));
            power.setPowerName(rs.getString("SuperPowerName"));
            power.setPowerDesc(rs.getString("SuperPowerDesc"));
            
            return power;
        }
    }
    
}
