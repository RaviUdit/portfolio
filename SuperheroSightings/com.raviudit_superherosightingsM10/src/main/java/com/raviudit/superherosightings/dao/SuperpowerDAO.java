/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.dao;

import com.raviudit.superherosightings.entities.Superpower;
import java.util.List;

/**
 *
 * @author raviu
 */
public interface SuperpowerDAO {
    
    Superpower getPowerByID(int id);
    Superpower getPowerByName(String powerName);
    List<Superpower> getAllPowers();
    Superpower addPower(Superpower power);
    void updatePower(Superpower power);
    void deletePowerByID(int id);
    
}
