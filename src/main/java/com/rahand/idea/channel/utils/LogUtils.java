package com.rahand.idea.channel.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
    public static Logger getLogger() {
        return LoggerFactory.getLogger("com.rahand.idea.channel");
    }

    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

}
