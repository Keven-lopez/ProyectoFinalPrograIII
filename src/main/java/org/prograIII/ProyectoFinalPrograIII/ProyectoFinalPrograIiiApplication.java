package org.prograIII.ProyectoFinalPrograIII;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.prograIII.ProyectoFinalPrograIII.API.Services.TurnoService;
import org.prograIII.ProyectoFinalPrograIII.API.Services.HistorialService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
		"org.prograIII.ProyectoFinalPrograIII",  // your main project
		"umg.principal.Database.Repositories"     // your dependency repositories
})
@EntityScan(basePackages = {
		"org.prograIII.ProyectoFinalPrograIII",  // your main project entities
		"umg.principal.Database.Entities"        // your dependency entities
})
@ComponentScan(basePackages = {
		"org.prograIII.ProyectoFinalPrograIII",  // your main project
		"umg.principal"                          // your dependency root package
})
public class ProyectoFinalPrograIiiApplication implements CommandLineRunner {

	@Autowired
	TurnoService turnoService;

	@Autowired
	HistorialService historialService;

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFinalPrograIiiApplication.class, args);
	}

	@Override
	public void run(String... args) {
		turnoService.loadFromDB();
		historialService.loadFromDB();
	}

}
