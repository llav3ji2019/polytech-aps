package com.polytech.aps.callback;

import java.util.List;

public interface Callback {
    void callback(String msg);

    List<String> getResult();
}
