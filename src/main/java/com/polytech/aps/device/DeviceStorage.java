package com.polytech.aps.device;

import com.polytech.aps.request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeviceStorage {
    private final List<Device> devices;

    private Request request;
    private int curPos;

    @Value("${env.deviceAmount}")
    private int deviceAmount;

    public boolean handle(Request newRequest) {
        if (isFull()) {
            updateRequestTime(newRequest);
            return false;
        }
        this.request = newRequest;

        sendRequestToDevice();
        return true;
    }

    private void updateRequestTime(Request newRequest) {
        double additionTime = newRequest.getEndTime() - request.getEndTime();
        request.increaseEndTime(additionTime);
    }

    private void sendRequestToDevice() {
        devices.get(curPos).handle(request);
        curPos = (curPos + 1) % deviceAmount;
        request = null;
    }

    public List<Double> getDevicesWorkingTime() {
        return devices.stream().map(Device::getDeviceWorked).toList();
    }

    public double getCurDeviceStartTime() {
        return devices.get(curPos).getCurTime();
    }

    public boolean isFull() {
        return !isEmpty();
    }
    public boolean isEmpty() {
        return request == null;
    }
}
