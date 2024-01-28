package com.polytech.aps.statistic.service.db.service;

import com.polytech.aps.model.DeviceStatistic;
import com.polytech.aps.statistic.service.db.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository repository;

    public void create(List<DeviceStatistic> deviceStatistic) {
        repository.saveAll(deviceStatistic);

    }
}
