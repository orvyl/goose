package com.orvyl.goose.service;

import com.orvyl.goose.db.UpskillSubscription;
import com.orvyl.goose.db.UpskillSubscriptionRepository;
import com.orvyl.goose.model.UpskillStatusSummary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UpskillSubscriptionService {

    private final CoachService coachService;
    private final UpskillSubscriptionRepository upskillSubscriptionRepository;

    public UpskillSubscriptionService(CoachService coachService, UpskillSubscriptionRepository upskillSubscriptionRepository) {
        this.coachService = coachService;
        this.upskillSubscriptionRepository = upskillSubscriptionRepository;
    }

    public void pay(String coachId, LocalDate paidAt, LocalDate subscriptionDatePaymentTarget, String note) throws CoachNotFoundException {
        var upskillSubscription = UpskillSubscription.builder()
                .coach(coachService.getCoach(coachId))
                .paidAt(paidAt)
                .subscriptionDatePaymentTarget(subscriptionDatePaymentTarget)
                .note(note)
            .build();

        upskillSubscriptionRepository.save(upskillSubscription);
        coachService.updateLastUpskillSubscriptionPayment(coachId, subscriptionDatePaymentTarget);
    }

    public List<UpskillStatusSummary> getAllSummary(LocalDate dateToCheck) {
        return StreamSupport.stream(coachService.getAllCoaches().spliterator(), false)
                .map(coach -> {
                    LocalDate lastUpskillSubscriptionPayment = coach.getLastUpskillSubscriptionPayment();
                    boolean canJoinMasterclass = Period.between(lastUpskillSubscriptionPayment, dateToCheck).getMonths() == 0;

                    return UpskillStatusSummary.builder()
                            .coachName(coach.getFirstName() + " " + coach.getLastName())
                            .upskillSubscriptionStart(coach.getUpskillSubscriptionStart())
                            .lastPaid(lastUpskillSubscriptionPayment)
                            .canJoinMasterclass(canJoinMasterclass)
                            .build();
                })
                .sorted(Comparator.comparing(UpskillStatusSummary::getLastPaid))
                .toList();
    }
}
