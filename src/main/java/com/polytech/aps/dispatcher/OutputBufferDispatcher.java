package com.polytech.aps.dispatcher;

import com.polytech.aps.buffer.Buffer;
import com.polytech.aps.callback.Callback;
import com.polytech.aps.device.DeviceStorage;
import com.polytech.aps.request.Request;
import com.polytech.aps.request.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@Component
@RequiredArgsConstructor
public class OutputBufferDispatcher implements PropertyChangeListener {
    private final DeviceStorage deviceStorage;

    private final Buffer buffer;

    private Request request;
    private long amountInBuffer;

    private final Callback infoCallback;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Request newRequest = (Request) evt.getNewValue();
        if (isFull()) {
            updateRequestTime(newRequest);
            sendRequestToDevice();
            return;
        }

        newRequest.setStatus(RequestStatus.BUFFER_SENT);
        infoCallback.callback(newRequest.toString());

        this.request = buffer.poll();
        amountInBuffer = buffer.getSize();

        sendRequestToDevice();
    }

    private void updateRequestTime(Request newRequest) {
        double additionTime = newRequest.getEndTime() - request.getEndTime();
        request.increaseEndTime(additionTime);
    }

    private void sendRequestToDevice() {
        while (deviceStorage.getCurDeviceStartTime() < request.getEndTime()) {
            if (deviceStorage.handle(request)) {
                request = null;
            }
            if (isEmpty() && amountInBuffer != 0) {
                this.request = buffer.poll();
                amountInBuffer = buffer.getSize();
                request.setStatus(RequestStatus.BUFFER_SENT);
                infoCallback.callback(request.toString());
                continue;
            }
            if (isEmpty()) {
                return;
            }
        }
    }

    private boolean isEmpty() {
        return request == null;
    }
    private boolean isFull() {
        return !isEmpty();
    }
}
