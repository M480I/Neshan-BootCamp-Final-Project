package com.etaxi.core.security.user;

public enum Role {
    NULL,
    ADMIN,
    DRIVER,
    PASSENGER;

    String getRoleName() {
        return "ROLE_" + name();
    }

}
