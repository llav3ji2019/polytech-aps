package com.polytech.aps.request;

import lombok.Getter;

@Getter
public enum RequestStatus {
    GENERATED("Source generated request"),
    BUFFER_RECEIVED("Buffer received request and save it"),
    BUFFER_REFUSAL("Buffer delete request due to conflict"),
    BUFFER_SENT("Buffer sent request to dispatcher output"),
    DEVICE_RECEIVED("Device received request"),
    PROCESSED("Device processed request");

    private final String msg;

    RequestStatus(String msg) {
        this.msg = msg;
    }
}
