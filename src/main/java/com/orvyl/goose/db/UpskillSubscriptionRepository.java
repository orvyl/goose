package com.orvyl.goose.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpskillSubscriptionRepository extends CrudRepository<UpskillSubscription, Long> {
}
