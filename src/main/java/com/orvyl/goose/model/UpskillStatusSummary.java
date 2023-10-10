package com.orvyl.goose.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UpskillStatusSummary {
    private String coachId;
    private String coachName;
    private LocalDate upskillSubscriptionStart;
    private LocalDate lastPaid;
    private boolean canJoinMasterclass;
}
