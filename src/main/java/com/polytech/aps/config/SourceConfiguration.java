package com.polytech.aps.config;

import com.polytech.aps.source.Source;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.PriorityQueue;
import java.util.Queue;

@Configuration
public class SourceConfiguration {

    @Value("${env.srcAmount}")
    private int sourcesAmount;

    @Value("${env.requestAmount}")
    private int maxRequestsAmount;

    @Value("${statistic.a}")
    private int a;

    @Value("${statistic.b}")
    private int b;

    @Bean
    public Queue<Source> sources() {
        Queue<Source> sources = new PriorityQueue<>((src1, src2) -> (int) Math.round((src1.getCurTime() - src2.getCurTime()) * 1000));
        for (int i = 0; i < sourcesAmount; ++i) {
            sources.add(new Source(a, b, sourcesAmount, maxRequestsAmount));
        }
        return sources;
    }
}
