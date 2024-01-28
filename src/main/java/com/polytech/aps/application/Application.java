package com.polytech.aps.application;

import com.polytech.aps.buffer.Buffer;
import com.polytech.aps.callback.Callback;
import com.polytech.aps.dispatcher.InputBufferDispatcher;
import com.polytech.aps.dispatcher.OutputBufferDispatcher;
import com.polytech.aps.source.SourceStorage;
import com.polytech.aps.statistic.service.StatisticCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class Application {
    private final Mode mode = Mode.AUTO;

    private final SourceStorage sourceStorage;
    private final OutputBufferDispatcher outputBufferDispatcher;
    private final InputBufferDispatcher inputBufferDispatcher;
    private final Callback result;
    private final Buffer buffer;

    private final StatisticCreationService creationService;

    public void start() {
        setUpChangeListener();

        while (sourceStorage.shouldContinue()) {
            sourceStorage.sentRequestToInputDispatcher();
            inputBufferDispatcher.sendToBuffer();
        }

        switch (mode) {
            case AUTO -> creationService.createStatistic(result.getResult());
            case STEP -> startStepMode();
        }
    }

    private void setUpChangeListener() {
        buffer.addPropertyChangeListener(outputBufferDispatcher);
    }

    private void startStepMode() {
        List<String> appStatistic = result.getResult();
        System.out.println("Would you like to start step mode? (Y/[n])");
        Scanner in = new Scanner(System.in);
        String ans = in.next();
        for (int i = 0; i < appStatistic.size() && ans.equalsIgnoreCase("y"); i++) {
            System.out.println(appStatistic.get(i));
            System.out.println("Would you like to continue step mode? (Y/[n])");
            ans = in.next();
        }
    }
}
