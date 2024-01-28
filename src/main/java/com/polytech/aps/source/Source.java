package com.polytech.aps.source;

import com.polytech.aps.request.Request;
import lombok.Getter;

public class Source {
    private static int sourceIdentity = 1;

    @Getter
    private final int id;
    private final int maxRequestsAmount;

    @Getter
    private int curRequestsAmount;

    @Getter
    private double curTime;

    private final double a;

    private final double b;
    private final int sourcesAmount;

    public Source(int a, int b, int sourcesAmount, int maxRequestsAmount) {
        this.id = sourceIdentity++;
        curRequestsAmount = id;
        this.a = a;
        this.b = b;
        this.maxRequestsAmount = maxRequestsAmount;
        this.sourcesAmount = sourcesAmount;
        updateRequestTime();
    }

    public Request generateRequest() {
        var result = new Request(curRequestsAmount, id, curTime);
        curRequestsAmount += sourcesAmount;
        return result;
    }

    public void updateRequestTime() {
        curTime += Math.random() * (b - a) + a;
    }

    public boolean canBeUsed() {
        return curRequestsAmount <= maxRequestsAmount;
    }
}