package com.polytech.aps.dispatcher;

import com.polytech.aps.buffer.Buffer;
import com.polytech.aps.request.Request;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InputBufferDispatcher {
    private final Buffer buffer;

    @Setter
    private Request request;

    public void sendToBuffer() {
        buffer.add(request);
    }
}
