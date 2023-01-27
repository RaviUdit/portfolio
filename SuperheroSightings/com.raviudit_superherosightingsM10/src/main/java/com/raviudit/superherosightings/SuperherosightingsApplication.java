package com.raviudit.superherosightings;

import com.raviudit.superherosightings.dao.SightingDAO;
import com.raviudit.superherosightings.entities.Sighting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SuperherosightingsApplication {

//    @Autowired 
//    SightingDAO sightingDao;
    
	public static void main(String[] args) {
		SpringApplication.run(SuperherosightingsApplication.class, args);
	}
        
        
        
        //List<Sighting> sightings = sightingDao.getAllSightings();
        //C:\Repos\M10\superherosightingsM10\src\main\java\com\raviudit\superherosightings\SuperherosightingsApplication.java

}
