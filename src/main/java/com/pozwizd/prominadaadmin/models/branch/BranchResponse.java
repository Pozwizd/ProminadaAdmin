package com.pozwizd.prominadaadmin.models.branch;

import lombok.Data;

@Data
public class BranchResponse {
    private Long id;
    private String code;
    private String phoneNumber;
    private String name;
    private String email;
    private String address;
    private String imagePath;
}