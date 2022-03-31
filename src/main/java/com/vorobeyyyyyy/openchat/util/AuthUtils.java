package com.vorobeyyyyyy.openchat.util;

import com.vorobeyyyyyy.openchat.security.AuthInformation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class AuthUtils {

    public static UUID getUserUuid() {
        return getAuthInformation().getUserUuid();
    }

    public static void setAuth(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static AuthInformation getAuthInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthInformation authInformation = (AuthInformation) authentication.getPrincipal();
        return authInformation;
    }
}
