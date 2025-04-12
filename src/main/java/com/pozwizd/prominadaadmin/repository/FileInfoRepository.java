package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.models.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    
    Optional<FileInfo> findByTempId(String tempId);
    
    List<FileInfo> findByTempIdIn(List<String> tempIds);

}