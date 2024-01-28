package com.polytech.aps.config;

import com.polytech.aps.request.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class BufferConfiguration {
    @Value("${env.maxBufferSize}")
    private int bufferCapacity;

    @Bean
    public Queue<Request> requests() {
        return new PriorityQueue<>(bufferCapacity);
    }
}
