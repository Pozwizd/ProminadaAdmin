package com.pozwizd.prominadaadmin.mapper.property.secondaryProperty;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyGalleryImage;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.request.SecondaryPropertyGalleryImageRequest;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyGalleryImageResponse;
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
class SecondaryPropertyGalleryImageMapperTest {

    @Mock
    private FileService fileService;

    private SecondaryPropertyGalleryImageMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(SecondaryPropertyGalleryImageMapper.class);
        ReflectionTestUtils.setField(mapper, "fileService", fileService);
    }

    @Test
    void toEntity_happyPath() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "img.jpg", "image/jpeg", "data".getBytes());
        when(fileService.uploadFile(any(MultipartFile.class))).thenReturn("uploads/img.jpg");

        SecondaryPropertyGalleryImageRequest req = new SecondaryPropertyGalleryImageRequest();
        req.setId(10L);
        req.setName("Image");
        req.setPathImage(file);

        SecondaryPropertyGalleryImage entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertEquals(10L, entity.getId());
        assertEquals("Image", entity.getName());
        assertEquals("uploads/img.jpg", entity.getPathImage());
        assertNull(entity.getSecondaryProperty());
    }

    @Test
    void toEntity_nullFile() {
        SecondaryPropertyGalleryImageRequest req = new SecondaryPropertyGalleryImageRequest();
        req.setId(11L);
        req.setName("NoFile");
        req.setPathImage(null);

        SecondaryPropertyGalleryImage entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertEquals(11L, entity.getId());
        assertEquals("NoFile", entity.getName());
        assertNull(entity.getPathImage());
    }

    @Test
    void toResponse() {
        SecondaryPropertyGalleryImage entity = SecondaryPropertyGalleryImage.builder()
                .id(12L)
                .name("Resp")
                .pathImage("uploads/r.jpg")
                .build();

        SecondaryPropertyGalleryImageResponse resp = mapper.toResponse(entity);

        assertNotNull(resp);
        assertEquals(12L, resp.getId());
        assertEquals("Resp", resp.getName());
        assertEquals("uploads/r.jpg", resp.getPathImage());
    }

    @Test
    void partialUpdate_withFile() throws IOException {
        SecondaryPropertyGalleryImage entity = new SecondaryPropertyGalleryImage();
        entity.setId(13L);
        entity.setName("Old");
        entity.setPathImage("old.jpg");

        SecondaryPropertyGalleryImageRequest req = new SecondaryPropertyGalleryImageRequest();
        req.setName("New");
        MockMultipartFile file = new MockMultipartFile("file", "new.jpg", "image/jpeg", "new".getBytes());
        req.setPathImage(file);

        when(fileService.uploadFileIfPresent(any(MultipartFile.class))).thenReturn("uploads/new.jpg");

        mapper.partialUpdate(req, entity);

        assertEquals(13L, entity.getId());
        assertEquals("New", entity.getName());
        assertEquals("uploads/new.jpg", entity.getPathImage());
        verify(fileService, times(1)).uploadFileIfPresent(file);
    }

    @Test
    void partialUpdate_withoutFileOrName() throws IOException {
        SecondaryPropertyGalleryImage entity = new SecondaryPropertyGalleryImage();
        entity.setId(14L);
        entity.setName("Keep");
        entity.setPathImage("keep.jpg");

        SecondaryPropertyGalleryImageRequest req = new SecondaryPropertyGalleryImageRequest();
        req.setName(null);

        mapper.partialUpdate(req, entity);

        assertEquals(14L, entity.getId());
        assertEquals("Keep", entity.getName());
        assertEquals("keep.jpg", entity.getPathImage());
        verify(fileService, never()).uploadFileIfPresent(any());
    }

    @Test
    void partialUpdateList_add_update_remove() {
        List<SecondaryPropertyGalleryImage> entities = new ArrayList<>();
        SecondaryPropertyGalleryImage existing = SecondaryPropertyGalleryImage.builder()
                .id(1L).name("E1").pathImage("p1.jpg").build();
        entities.add(existing);

        SecondaryPropertyGalleryImageRequest upd = new SecondaryPropertyGalleryImageRequest();
        upd.setId(1L);
        upd.setName("E1-upd");

        SecondaryPropertyGalleryImageRequest add = new SecondaryPropertyGalleryImageRequest();
        add.setId(null);
        add.setName("E2");

        List<SecondaryPropertyGalleryImageRequest> requests = List.of(upd, add);

        mapper.partialUpdateList(requests, entities);

        assertEquals(2, entities.size());
        SecondaryPropertyGalleryImage e1 = entities.stream().filter(e -> Long.valueOf(1L).equals(e.getId())).findFirst().orElseThrow();
        assertEquals("E1-upd", e1.getName());
        SecondaryPropertyGalleryImage e2 = entities.stream().filter(e -> e.getId() == null).findFirst().orElseThrow();
        assertEquals("E2", e2.getName());
    }
}