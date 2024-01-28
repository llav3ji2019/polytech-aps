package com.polytech.aps.statistic.service.db.repository;

import com.polytech.aps.model.SourceStatistic;
import org.springframework.data.repository.CrudRepository;

public interface SourceRepository extends CrudRepository<SourceStatistic, Long> {
}
