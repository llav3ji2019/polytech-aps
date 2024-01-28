package com.polytech.aps.callback;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class LinkedListCallback implements Callback {
    private final List<String> eventsList = new LinkedList<>();

    @Override
    public void callback(String msg) {
        eventsList.addLast(msg);
    }

    @Override
    public List<String> getResult() {
        return eventsList;
    }
}
