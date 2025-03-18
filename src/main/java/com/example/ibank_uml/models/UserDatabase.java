package com.example.ibank_uml.models;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private static final Map<String, UserProfile> users = new HashMap<>();

    static {
        // Predefined users
        users.put("123456789", new UserProfile("123456789", "1234", 500.00));
        users.put("987654321", new UserProfile("987654321", "5678", 1200.50));
        users.put("555555555", new UserProfile("555555555", "9999", 300.75));
        users.put("111122223", new UserProfile("111122223", "1111", 750.50));
    }

    public static UserProfile getUser(String cardNumber) {
        return users.get(cardNumber);
    }
}
