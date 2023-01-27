/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raviudit.superherosightings.entities;

import java.util.List;
import java.util.Objects;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author raviu
 */
public class Superhero {
    
    Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
    
    private int id;
    
    @NotBlank(message = "All Entities need a name.")
    @Size(max = 12, message = "All names must be 12 characters or less.")
    @Pattern(regexp = "^[a-zA-Z0-9\\-]*$", message = "Names cannot have special characters.")
    private String name;
//    @Pattern(regexp = "/^(\\w\\s\\.\\'\\,\\-.)*$/", message = "Names cannot have special characters.")
    private boolean isHero;
    private int powerId;
    
    private Superpower superpower;
    
    //@NotEmpty(message = "A Superhero or Supervillian must be on atleast one team.")
    private List<Team> teams;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + (this.isHero ? 1 : 0);
        hash = 71 * hash + this.powerId;
        hash = 71 * hash + Objects.hashCode(this.superpower);
        hash = 71 * hash + Objects.hashCode(this.teams);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Superhero other = (Superhero) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.isHero != other.isHero) {
            return false;
        }
        if (this.powerId != other.powerId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.superpower, other.superpower)) {
            return false;
        }
        if (!Objects.equals(this.teams, other.teams)) {
            return false;
        }
        return true;
    }
    
    

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsHero() {
        return isHero;
    }

    public void setIsHero(boolean isHero) {
        this.isHero = isHero;
    }

    public int getPowerId() {
        return powerId;
    }

    public void setPowerId(int powerId) {
        this.powerId = powerId;
    }
    

    public Superpower getSuperpower() {
        return superpower;
    }

    public void setSuperpower(Superpower superpower) {
        this.superpower = superpower;
    }

}
