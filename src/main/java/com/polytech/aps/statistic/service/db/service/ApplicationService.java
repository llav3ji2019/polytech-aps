package com.polytech.aps.statistic.service.db.service;

import com.polytech.aps.model.ApplicationStatistic;
import com.polytech.aps.statistic.service.db.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository repository;

    public void create(ApplicationStatistic applicationStatistic) {
        repository.save(applicationStatistic);
    }
}
