package com.pozwizd.prominadaadmin.models;

import com.pozwizd.prominadaadmin.entity.Personal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
@Entity
@AllArgsConstructor
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String originalFilename;

    private String contentType;

    private Long size;

    private String path;

    private String tempId;

    private boolean temporary;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "personal_id")
    private Personal personal;

    public FileInfo() {

    }
}