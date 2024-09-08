package com.etaxi.core.user;

public enum Role {
    NULL,
    ADMIN,
    DRIVER,
    PASSENGER;

    String getRoleName() {
        return "ROLE_" + name();
    }

}
