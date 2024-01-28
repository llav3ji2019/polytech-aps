package com.polytech.aps.buffer;

import com.polytech.aps.callback.Callback;
import com.polytech.aps.request.Request;
import com.polytech.aps.request.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.PriorityQueue;
import java.util.Queue;

@Component
@RequiredArgsConstructor
public class Buffer {
    @Value("${env.maxBufferSize}")
    private int bufferCapacity;

    private final Queue<Request> requests;

    private double prevTime;
    private double curTime;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private final Callback bufferCallback;

    public void add(Request request) {
        if (isFull()) {
            deleteOldestElementFromQueue();
        }

        if (request.getBeginTime() > curTime) {
            prevTime = curTime;
            curTime = request.getBeginTime();

            updateRequestsTime();
        }


        request.setStatus(RequestStatus.BUFFER_RECEIVED);
        requests.add(request);
        bufferCallback.callback(request.toString());
        support.firePropertyChange("requests", requests, request);
    }

    public Request poll() {
        return requests.poll();
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public long getSize() {
        return requests.size();
    }

    private void updateRequestsTime() {
        double difference = curTime - prevTime;
        requests.forEach((elem) -> elem.increaseEndTime(difference));
    }

    private boolean isFull() {
        return requests.size() == bufferCapacity;
    }

    private void deleteOldestElementFromQueue() {
        PriorityQueue<Request> newQueue = getElementsWithHigherPriority();
        Request removedRequest = requests.poll();
        requests.addAll(newQueue);

        removedRequest.setStatus(RequestStatus.BUFFER_REFUSAL);
        bufferCallback.callback(removedRequest.toString());
    }

    private PriorityQueue<Request> getElementsWithHigherPriority() {
        PriorityQueue<Request> newQueue = new PriorityQueue<>();
        for (int i = 0; i < bufferCapacity - 1; i++) {
            newQueue.add(requests.poll());
        }
        return newQueue;
    }
}
