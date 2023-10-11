package com.orvyl.goose;

import com.orvyl.goose.service.CoachService;
import com.slack.api.bolt.App;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Month;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Bean
    public CommandLineRunner runSlackApp(App app, @Value("${slack.app-token}") String appToken) {
        return args -> {
            SocketModeApp socketModeApp = new SocketModeApp(appToken, app);
            socketModeApp.run(true);
        };
    }

	@Bean
	public CommandLineRunner initializeData(CoachService coachService) {
		return args -> {
			log.info("Loading initial data...");
            coachService.registerCoach("Andrea Joyce", "Pamintuan", "Class 16", LocalDate.of(2023, Month.OCTOBER, 4));
            coachService.registerCoach("Nancy", "Berame", "Class 16", LocalDate.of(2023, Month.OCTOBER, 3));
            coachService.registerCoach("Catherine", "Sosa", "Class 16", LocalDate.of(2023, Month.OCTOBER, 3));
            coachService.registerCoach("Al John", "Catacutan", "Class 16", LocalDate.of(2023, Month.OCTOBER, 3));
            coachService.registerCoach("Candee", "Teng", "Dcup1", LocalDate.of(2023, Month.SEPTEMBER, 27));
            coachService.registerCoach("Colleen", "Bercacio", "Dcup1", LocalDate.of(2023, Month.SEPTEMBER, 26));
            coachService.registerCoach("Ianne", "Pabalan", "Dcup1", LocalDate.of(2023, Month.SEPTEMBER, 9));
            coachService.registerCoach("Lyn", "Garcia", "Dcup1", LocalDate.of(2023, Month.SEPTEMBER, 27));
            coachService.registerCoach("Marife", "Garfin", "Dcup1", LocalDate.of(2023, Month.OCTOBER, 3));
            coachService.registerCoach("Maureen", "Mestica", "Dcup1", LocalDate.of(2023, Month.SEPTEMBER, 27));
            coachService.registerCoach("Nordelyn", "Cales", "Dcup2", LocalDate.of(2023, Month.SEPTEMBER, 29));
            coachService.registerCoach("Shaun", "De Joya", "Dcup1", LocalDate.of(2023, Month.SEPTEMBER, 25));
            coachService.registerCoach("Berna", "Ong", "Dcup2", LocalDate.of(2023, Month.SEPTEMBER, 27));
            coachService.registerCoach("Dorothy", "Antoni", "TAC", LocalDate.of(2023, Month.SEPTEMBER, 25));
            coachService.registerCoach("Jobeleih", "Calleja", "TAC", LocalDate.of(2023, Month.SEPTEMBER, 25));
            coachService.registerCoach("Rhey", "Gaspay", "TAC", LocalDate.of(2023, Month.SEPTEMBER, 25));
            coachService.registerCoach("Febie", "Singzon", "TAC", LocalDate.of(2023, Month.SEPTEMBER, 25));
            coachService.registerCoach("Herman", "Nicolas", "TAC", LocalDate.of(2023, Month.SEPTEMBER, 25));
            coachService.registerCoach("Lyra", "Macatimpag", "TAC", LocalDate.of(2023, Month.SEPTEMBER, 25));
            coachService.registerCoach("Marvin", "Joco", "TAC", LocalDate.of(2023, Month.SEPTEMBER, 25));
            coachService.registerCoach("Beejay", "Amancio", "TAC", LocalDate.of(2023, Month.SEPTEMBER, 27));
            coachService.registerCoach("Tin", "De Joya", "DCup1", LocalDate.of(2023, Month.JANUARY, 1));
			log.info("Initial data loaded");
        };
    }
}
