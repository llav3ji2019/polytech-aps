package com.polytech.aps.config;

import com.polytech.aps.callback.Callback;
import com.polytech.aps.device.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DeviceConfiguration {
    @Value("${statistic.lambda}")
    private int lambda;

    @Value("${env.deviceAmount}")
    private int deviceAmount;

    private final Callback infoCallback;

    @Bean
    public List<Device> devices() {
        List<Device> devices = new ArrayList<>(deviceAmount);
        for (int i = 0; i < deviceAmount; i++) {
            devices.add(new Device(lambda, infoCallback));
        }
        return devices;
    }
}
