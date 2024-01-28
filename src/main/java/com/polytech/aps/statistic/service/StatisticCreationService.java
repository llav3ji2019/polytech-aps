package com.polytech.aps.statistic.service;

import com.polytech.aps.device.DeviceStorage;
import com.polytech.aps.statistic.StatisticStorage;
import com.polytech.aps.statistic.service.db.service.ApplicationService;
import com.polytech.aps.statistic.service.db.service.DeviceService;
import com.polytech.aps.statistic.service.db.service.SourceService;
import com.polytech.aps.utils.file.FileSaverUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticCreationService {
    private final ApplicationService applicationService;
    private final DeviceService deviceService;
    private final SourceService sourceService;
    private final DeviceStorage deviceStorage;

    @Value("${env.srcAmount}")
    private int srcAmount;

    @Transactional
    public void createStatistic(List<String> appStatistic) {
        StatisticStorage statisticStorage = new StatisticStorage(appStatistic, deviceStorage, srcAmount);

        applicationService.create(statisticStorage.getApplicationStatistic());
        deviceService.create(statisticStorage.getDeviceStatistics());
        sourceService.create(statisticStorage.getSourceStatistics());

        FileSaverUtils.saveData(appStatistic);
    }
}
