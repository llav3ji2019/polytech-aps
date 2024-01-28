package com.polytech.aps.statistic.service.db.repository;

import com.polytech.aps.model.ApplicationStatistic;
import com.polytech.aps.model.DeviceStatistic;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<DeviceStatistic, Long> {
}
