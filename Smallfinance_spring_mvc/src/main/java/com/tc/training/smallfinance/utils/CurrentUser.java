package com.tc.training.smallfinance.utils;

import com.tc.training.smallfinance.model.User;

public class CurrentUser {
    private static final ThreadLocal<User> threadUser = new ThreadLocal<>();
    public static void set(User user) {
        threadUser.set(user);
    }
    public static User get() {
        if (threadUser.get() == null) {
//            throw new ElementNotFound("user does not exist currently");
        } return threadUser.get();
    }
}

