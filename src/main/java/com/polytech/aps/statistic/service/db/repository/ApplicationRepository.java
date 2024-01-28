package com.polytech.aps.statistic.service.db.repository;

import com.polytech.aps.model.ApplicationStatistic;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<ApplicationStatistic, Long> {
}
