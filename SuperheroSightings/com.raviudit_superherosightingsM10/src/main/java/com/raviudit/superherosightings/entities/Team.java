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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author raviu
 */
public class Team {
    
    Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

    
    private int teamId;
    
    @NotBlank(message = "All Teams need a name.")
    @Size(max = 15, message = "Team name must be less than 15 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9\\-]*$", message = "Names cannot have special characters.")
    private String teamName;
    
    @NotBlank(message = "All Teams need a Description.")
    @Size(max = 50, message = "Team Description must be less than 50 characters.")
    private String teamDesc;
    
    @NotBlank(message = "All Teams need contact information.")
    @Size(max = 50, message = "Contact Information must be less than 50 characters.")
    private String teamContactInfo;
    
    @NotBlank(message = "All Teams need an Address.")
    @Size(max = 150, message = "Team Address must be less than 150 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-\\.\\'\\,]*$", message = "Addresses cannot have special characters.")
    private String teamAddress;
    
    private List<Superhero> teamMembers;

    @Override
    public String toString() {
        return "Team{" + "teamId=" + teamId + ", teamName=" + teamName + ", teamDesc=" + teamDesc + ", teamContactInfo=" + teamContactInfo + ", teamAddress=" + teamAddress + ", teamMembers=" + teamMembers + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.teamId;
        hash = 79 * hash + Objects.hashCode(this.teamName);
        hash = 79 * hash + Objects.hashCode(this.teamDesc);
        hash = 79 * hash + Objects.hashCode(this.teamContactInfo);
        hash = 79 * hash + Objects.hashCode(this.teamAddress);
        hash = 79 * hash + Objects.hashCode(this.teamMembers);
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
        final Team other = (Team) obj;
        if (this.teamId != other.teamId) {
            return false;
        }
        if (!Objects.equals(this.teamName, other.teamName)) {
            return false;
        }
        if (!Objects.equals(this.teamDesc, other.teamDesc)) {
            return false;
        }
        if (!Objects.equals(this.teamContactInfo, other.teamContactInfo)) {
            return false;
        }
        if (!Objects.equals(this.teamAddress, other.teamAddress)) {
            return false;
        }
        if (!Objects.equals(this.teamMembers, other.teamMembers)) {
            return false;
        }
        return true;
    }
    
    
    
    public String getTeamAddress() {
        return teamAddress;
    }

    public void setTeamAddress(String teamAddress) {
        this.teamAddress = teamAddress;
    }
    
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamDesc() {
        return teamDesc;
    }

    public void setTeamDesc(String teamDesc) {
        this.teamDesc = teamDesc;
    }

    public String getTeamContactInfo() {
        return teamContactInfo;
    }

    public void setTeamContactInfo(String teamContactInfo) {
        this.teamContactInfo = teamContactInfo;
    }

    public List<Superhero> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<Superhero> teamMembers) {
        this.teamMembers = teamMembers;
    }
    
    
}
