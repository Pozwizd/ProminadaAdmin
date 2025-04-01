package com.pozwizd.prominadaadmin.entity;

public enum Role {

    ADMIN, USER;

    public String getAuthority() {
        return name();
    }
}
