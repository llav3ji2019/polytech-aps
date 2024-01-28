package com.polytech.aps.statistic.service.db.service;

import com.polytech.aps.model.SourceStatistic;
import com.polytech.aps.statistic.service.db.repository.SourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SourceService {
    private final SourceRepository repository;

    public void create(List<SourceStatistic> sourceStatistic) {
        repository.saveAll(sourceStatistic);
    }
}
