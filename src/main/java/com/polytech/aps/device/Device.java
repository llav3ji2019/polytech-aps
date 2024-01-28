package com.polytech.aps.device;

import com.polytech.aps.callback.Callback;
import com.polytech.aps.request.Request;
import com.polytech.aps.request.RequestStatus;
import lombok.Getter;

public class Device {
    private boolean isTurnedOn = false;

    private final Callback infoCallback;

    private final int lambda;

    @Getter
    private double curTime;

    @Getter
    private double deviceWorked;

    public Device(int lambda, Callback infoCallback) {
        this.lambda = lambda;
        this.infoCallback = infoCallback;
    }

    public void handle(Request request) {
        double curWorkingTime = generateTime();

        request.setStatus(RequestStatus.DEVICE_RECEIVED);
        infoCallback.callback(request.toString());

        if (!isTurnedOn || curTime < request.getBeginTime()) {
            curTime = request.getBeginTime();
            isTurnedOn = true;
        } else {
            request.increaseEndTime(-request.getEndTime() + curTime);
        }
        request.increaseEndTime(curWorkingTime);

        request.setStatus(RequestStatus.PROCESSED);
        infoCallback.callback(request.toString());

        curTime = Math.max(request.getEndTime(), curTime);
        deviceWorked += curWorkingTime;
    }

    private double generateTime() {
        return -Math.log(1 - Math.random()) / lambda;
    }
}
