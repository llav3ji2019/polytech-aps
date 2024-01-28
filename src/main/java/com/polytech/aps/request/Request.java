package com.polytech.aps.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Request implements Comparable<Request> {
    private final int id;
    private final int srcId;
    private final double beginTime;

    @Setter
    private RequestStatus status = RequestStatus.GENERATED;
    private double endTime;

    public Request(int id, int srcId, double beginTime) {
        this.id = id;
        this.beginTime = beginTime;
        this.srcId = srcId;
        endTime = beginTime;
    }

    public void increaseEndTime(double time) {
        endTime += time;
    }

    @Override
    public String toString() {
        return "requestId = " + id + " srcId = " + srcId + " generationTime = " + beginTime + " currentTime = " + endTime + " | " + status.getMsg();
    }

    @Override
    public int compareTo(Request o) {
        return (int) (o.getBeginTime() - getBeginTime()) * 1000;
    }
}
