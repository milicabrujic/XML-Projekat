package com.main.app.util;

import org.springframework.data.domain.Page;

import java.util.UUID;

public class Util {

    public static String generateUniqueString() {
        return UUID.randomUUID().toString();
    }
}
