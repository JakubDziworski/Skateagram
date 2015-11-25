package com.jdziworski.skateagramservice.utils;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by kuba on 24.11.2015.
 */
public class UserUtils {
    public static String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
