package com.orvyl.goose.service;

import com.orvyl.goose.db.Coach;
import com.orvyl.goose.db.CoachRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CoachService {
    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    public Coach registerCoach(String firstName, String lastName, String batchName, LocalDate upskillSubscriptionStart) {
        var newCoach = Coach.builder()
                .firstName(firstName)
                .lastName(lastName)
                .batchName(batchName)
                .upskillSubscriptionStart(upskillSubscriptionStart)
                .lastUpskillSubscriptionPayment(upskillSubscriptionStart)
            .build();

        return coachRepository.save(newCoach);
    }

    public Coach getCoach(String id) throws CoachNotFoundException {
        return coachRepository
                .findById(id)
                .orElseThrow(CoachNotFoundException::new);
    }

    public void updateLastUpskillSubscriptionPayment(String id, LocalDate paidUpskillSubscriptionDate) throws CoachNotFoundException {
        Coach coach = getCoach(id);
        coach.setLastUpskillSubscriptionPayment(paidUpskillSubscriptionDate);

        coachRepository.save(coach);
    }

    public Iterable<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }
}
