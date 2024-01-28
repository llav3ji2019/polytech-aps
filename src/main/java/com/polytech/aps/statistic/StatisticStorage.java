package com.polytech.aps.statistic;

import com.polytech.aps.device.DeviceStorage;
import com.polytech.aps.model.ApplicationStatistic;
import com.polytech.aps.model.DeviceStatistic;
import com.polytech.aps.model.SourceStatistic;
import com.polytech.aps.request.RequestStatus;
import com.polytech.aps.utils.calback.CallbackUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class StatisticStorage {
    private final List<String> statistic;
    private final DeviceStorage storage;

    private final int srcAmount; // количество генераторов заявок

    private double avgP1; // вероятность отказа нашей системы
    private double timeAverage; // Среднее время пребывания заявки каждого источника в системе.

    private final List<Double> commonDeviceTime = new ArrayList<>(); // сумарное время работы девайса.

    private final List<Integer> srcCommonAmount; // общее количество заявок, сгенерированных источником;
    private final List<Integer> srcRejectedRequestAmount; // количество заявок конкретного источника, получивших отказ.
    private final List<Double> srcAvgTimeInSystem; // среднее время пребывания в системе заявкок конкретного источника
    private List<Double> commonSrcTimeInSystem; // общее время пребывания в системе заявкок конкретного источника
    private final List<Double> p1; // вероятность отказа нашей системы для каждого ист

    @Getter
    private ApplicationStatistic applicationStatistic;

    @Getter
    private List<SourceStatistic> sourceStatistics;

    @Getter
    private List<DeviceStatistic> deviceStatistics;

    private final double commonSystemTime;

    public StatisticStorage(List<String> statistic, DeviceStorage storage, int srcAmount) {
        this.statistic = statistic;
        this.storage = storage;
        this.srcAmount = srcAmount;

        commonSystemTime = statistic.stream()
                .filter(el -> el.contains(RequestStatus.PROCESSED.getMsg()))
                .reduce((e1, e2) -> e2)
                .stream()
                .mapToDouble(CallbackUtils::getEndTime)
                .sum();

        srcCommonAmount = new ArrayList<>(srcAmount);
        commonSrcTimeInSystem = new ArrayList<>(srcAmount);
        srcRejectedRequestAmount = new ArrayList<>(srcAmount);
        srcAvgTimeInSystem = new ArrayList<>();
        p1 = new ArrayList<>();
        for (int i = 0; i < srcAmount; i++) {
            commonSrcTimeInSystem.add(0.0);
            srcRejectedRequestAmount.add(0);
            srcCommonAmount.add(0);
        }

        calculateStatistic();
    }

    private void calculateStatistic() {
        calculateRequestsAmountOfEveryType();

        for (int i = 0; i < srcAmount; i++) {
            srcAvgTimeInSystem.add((double) commonSrcTimeInSystem.get(i) / srcCommonAmount.get(i));

            p1.add((double) srcRejectedRequestAmount.get(i) / srcCommonAmount.get(i));
        }

        avgP1 = calculateAverageP1();
        timeAverage = calculateAverageSrcTimeInSystem();

        commonDeviceTime.addAll(
                storage.getDevicesWorkingTime()
                        .stream()
                        .map(wTime -> 1 - wTime / commonSystemTime / 5)
                        .toList()
        );

        applicationStatistic = generateApplicationStatistic();
        sourceStatistics = generateSourceStatistic();
        deviceStatistics = generateDeviceStatistic();
    }

    private void calculateRequestsAmountOfEveryType() {
        for (var stat: statistic) {
            int srcId = CallbackUtils.getSrcId(stat);

            if (stat.contains(RequestStatus.BUFFER_REFUSAL.getMsg())) {
                incrementListValueById(srcRejectedRequestAmount, srcId);
            } else if (stat.contains(RequestStatus.GENERATED.getMsg())) {
                incrementListValueById(srcCommonAmount, srcId);
            } else if (stat.contains(RequestStatus.PROCESSED.getMsg())) {
                double beginTime = CallbackUtils.getBeginTime(stat);
                double endTime = CallbackUtils.getEndTime(stat);
                double diff = endTime - beginTime;
                commonSrcTimeInSystem.set(srcId, commonSrcTimeInSystem.get(srcId) + diff);
            }
        }

        commonSrcTimeInSystem = commonSrcTimeInSystem.stream().map(el -> el / commonSystemTime).toList();
    }

    private static void incrementListValueById(List<Integer> list, int id) {
        list.set(id, list.get(id) + 1);
    }

    private double calculateAverageSrcTimeInSystem() {
        return srcAvgTimeInSystem.stream()
                .mapToDouble(el -> el / commonSystemTime)
                .average()
                .orElse(0);
    }

    private double calculateAverageP1() {
        return p1.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);
    }

    private ApplicationStatistic generateApplicationStatistic() {
        return ApplicationStatistic.builder()
                .systemRequestTimeAverage(timeAverage)
                .probabilityOfFailure(avgP1)
                .build();
    }

    private List<SourceStatistic> generateSourceStatistic() {
        List<SourceStatistic> result = new ArrayList<>(srcAmount);
        for (int i = 0; i < srcAmount; i++) {
            var currentSourceStat = SourceStatistic.builder()
                    .avgRequestsTime(srcAvgTimeInSystem.get(i))
                    .commonRequestsTime(commonSrcTimeInSystem.get(i))
                    .requestAmount(srcCommonAmount.get(i))
                    .probabilityOfFailure(p1.get(i))
                    .rejectedAmount(srcRejectedRequestAmount.get(i))
                    .build();
            result.add(currentSourceStat);
        }
        return result;
    }

    private List<DeviceStatistic> generateDeviceStatistic() {
        return commonDeviceTime.stream()
                .map((el) -> DeviceStatistic.builder()
                        .commonDeviceTime(el)
                        .build()
                )
                .toList();
    }
}
