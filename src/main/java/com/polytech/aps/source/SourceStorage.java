package com.polytech.aps.source;

import com.polytech.aps.callback.Callback;
import com.polytech.aps.dispatcher.InputBufferDispatcher;
import com.polytech.aps.request.Request;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Component
@RequiredArgsConstructor
public class SourceStorage {

    private final Queue<Source> sources;

    @Value("${env.requestAmount}")
    private int maxRequestsAmount;

    @Getter
    private int curRequestsAmount;

    private final InputBufferDispatcher inputBufferDispatcher;

    private final Callback callback;

    private Request generate() {
        Source curSrc = sources.poll();
        Request request = curSrc.generateRequest();
        curRequestsAmount++;
        callback.callback(request.toString());
        if (curSrc.canBeUsed()) {
            curSrc.updateRequestTime();
            sources.add(curSrc);
        }
        return request;
    }

    public void sentRequestToInputDispatcher() {
        inputBufferDispatcher.setRequest(generate());
    }

    public boolean shouldContinue() {
        return maxRequestsAmount > curRequestsAmount;
    }
}
