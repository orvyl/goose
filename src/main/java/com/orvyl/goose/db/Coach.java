package com.orvyl.goose.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String lastName;

    private String batchName;

    private LocalDate upskillSubscriptionStart;
    private LocalDate lastUpskillSubscriptionPayment;

    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    @OneToMany
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    private List<UpskillSubscription> upskillSubscriptions;

    public Coach() {}

    @Builder
    public Coach(String firstName, String lastName, String batchName, LocalDate upskillSubscriptionStart, LocalDate lastUpskillSubscriptionPayment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.batchName = batchName;
        this.upskillSubscriptionStart = upskillSubscriptionStart;
        this.lastUpskillSubscriptionPayment = lastUpskillSubscriptionPayment;
    }

    @PrePersist
    public void onCreate() {
        var now = LocalDateTime.now();
        createdAt = now;
        lastUpdated = now;
    }

    @PreUpdate
    public void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Coach coach = (Coach) o;
        return getId() != null && Objects.equals(getId(), coach.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
