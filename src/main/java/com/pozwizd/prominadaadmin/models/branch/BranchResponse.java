package com.pozwizd.prominadaadmin.models.branch;

import lombok.Data;

@Data
public class BranchResponse {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
}