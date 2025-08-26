package com.pozwizd.prominadaadmin.entity;

import lombok.Getter;

@Getter
public enum SourceInformation {

    ADVERTISING("Реклама"),

    SOCIAL_NETWORKS("Соцмережі"),

    INTERNET("Інтернет"),

    ACQUAINTANCES("Знайомі"),

    OTHER("Інше");


    private String name;

    SourceInformation(String name) {
        this.name = name;
    }
}
