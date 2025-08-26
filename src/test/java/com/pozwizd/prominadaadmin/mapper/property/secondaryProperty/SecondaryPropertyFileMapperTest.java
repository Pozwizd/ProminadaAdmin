package com.pozwizd.prominadaadmin.mapper.property.secondaryProperty;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyFile;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.request.SecondaryPropertyFileRequest;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyFileResponse;
import com.pozwizd.prominadaadmin.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecondaryPropertyFileMapperTest {

    @Mock
    private FileService fileService;

    private SecondaryPropertyFileMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(SecondaryPropertyFileMapper.class);
        ReflectionTestUtils.setField(mapper, "fileService", fileService);
    }

    @Test
    void toEntity_happyPath() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "doc.pdf", "application/pdf", "pdf".getBytes());
        when(fileService.uploadFile(any(MultipartFile.class))).thenReturn("uploads/doc.pdf");

        SecondaryPropertyFileRequest req = new SecondaryPropertyFileRequest();
        req.setId(100L);
        req.setName("Doc");
        req.setFilePath(file);

        SecondaryPropertyFile entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertEquals(100L, entity.getId());
        assertEquals("Doc", entity.getName());
        assertEquals("uploads/doc.pdf", entity.getPath());
        assertNull(entity.getSecondaryProperty());
    }

    @Test
    void toEntity_nullFile() {
        SecondaryPropertyFileRequest req = new SecondaryPropertyFileRequest();
        req.setId(101L);
        req.setName("NoFile");
        req.setFilePath(null);

        SecondaryPropertyFile entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertEquals(101L, entity.getId());
        assertEquals("NoFile", entity.getName());
        assertNull(entity.getPath());
    }

    @Test
    void toResponse() {
        SecondaryPropertyFile entity = SecondaryPropertyFile.builder()
                .id(200L)
                .name("Resp")
                .path("uploads/r.pdf")
                .build();

        SecondaryPropertyFileResponse resp = mapper.toResponse(entity);

        assertNotNull(resp);
        assertEquals(200L, resp.getId());
        assertEquals("Resp", resp.getName());
        assertEquals("uploads/r.pdf", resp.getPath());
    }

    @Test
    void partialUpdate_withFile() throws IOException {
        SecondaryPropertyFile entity = new SecondaryPropertyFile();
        entity.setId(300L);
        entity.setName("Old");
        entity.setPath("old.pdf");

        SecondaryPropertyFileRequest req = new SecondaryPropertyFileRequest();
        req.setName("New");
        MockMultipartFile file = new MockMultipartFile("file", "new.pdf", "application/pdf", "new".getBytes());
        req.setFilePath(file);

        when(fileService.uploadFileIfPresent(any(MultipartFile.class))).thenReturn("uploads/new.pdf");

        mapper.partialUpdate(req, entity);

        assertEquals(300L, entity.getId());
        assertEquals("New", entity.getName());
        assertEquals("uploads/new.pdf", entity.getPath());
        verify(fileService, times(1)).uploadFileIfPresent(file);
    }

    @Test
    void partialUpdate_withoutFileOrName() throws IOException {
        SecondaryPropertyFile entity = new SecondaryPropertyFile();
        entity.setId(301L);
        entity.setName("Keep");
        entity.setPath("keep.pdf");

        SecondaryPropertyFileRequest req = new SecondaryPropertyFileRequest();
        req.setName(null);

        mapper.partialUpdate(req, entity);

        assertEquals(301L, entity.getId());
        assertEquals("Keep", entity.getName());
        assertEquals("keep.pdf", entity.getPath());
        verify(fileService, never()).uploadFileIfPresent(any());
    }

    @Test
    void partialUpdateList_add_update_remove() {
        List<SecondaryPropertyFile> entities = new ArrayList<>();
        SecondaryPropertyFile existing = SecondaryPropertyFile.builder()
                .id(1L).name("E1").path("p1.pdf").build();
        entities.add(existing);

        SecondaryPropertyFileRequest upd = new SecondaryPropertyFileRequest();
        upd.setId(1L);
        upd.setName("E1-upd");

        SecondaryPropertyFileRequest add = new SecondaryPropertyFileRequest();
        add.setId(null);
        add.setName("E2");

        List<SecondaryPropertyFileRequest> requests = List.of(upd, add);

        mapper.partialUpdateList(requests, entities);

        assertEquals(2, entities.size());
        SecondaryPropertyFile e1 = entities.stream().filter(e -> Long.valueOf(1L).equals(e.getId())).findFirst().orElseThrow();
        assertEquals("E1-upd", e1.getName());
        SecondaryPropertyFile e2 = entities.stream().filter(e -> e.getId() == null).findFirst().orElseThrow();
        assertEquals("E2", e2.getName());
    }
}