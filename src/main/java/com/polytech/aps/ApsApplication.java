package com.polytech.aps;

import com.polytech.aps.application.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.netty.NettyAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(
        exclude = {
                GsonAutoConfiguration.class,
                MultipartAutoConfiguration.class,
                WebSocketServletAutoConfiguration.class,
                NettyAutoConfiguration.class,
                RestTemplateAutoConfiguration.class,
                HttpMessageConvertersAutoConfiguration.class,
                HttpEncodingAutoConfiguration.class,
                ErrorMvcAutoConfiguration.class
        }
)
@RequiredArgsConstructor
public class ApsApplication implements CommandLineRunner {
    private final Application application;

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApsApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        application.start();
    }
}
