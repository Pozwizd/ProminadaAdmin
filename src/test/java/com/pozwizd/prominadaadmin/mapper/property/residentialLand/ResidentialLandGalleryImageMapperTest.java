package com.pozwizd.prominadaadmin.mapper.property.residentialLand;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandGalleryImage;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandGalleryImageRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandGalleryImageResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResidentialLandGalleryImageMapperTest {

    @Mock
    private FileService fileService;

    private ResidentialLandGalleryImageMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(ResidentialLandGalleryImageMapper.class);
        ReflectionTestUtils.setField(mapper, "fileService", fileService);
    }

    @Test
    void toEntity_happyPath() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test content".getBytes());

        when(fileService.uploadFile(any(MultipartFile.class))).thenReturn("uploads/test.jpg");

        ResidentialLandGalleryImageRequest request = new ResidentialLandGalleryImageRequest();
        request.setId(1L);
        request.setName("Test Image");
        request.setPathImage(file);

        ResidentialLandGalleryImage entity = mapper.toEntity(request);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Test Image", entity.getName());
        assertEquals("uploads/test.jpg", entity.getPathImage());
        assertNull(entity.getResidentialLand());
    }

    @Test
    void toEntity_nullFile() {
        ResidentialLandGalleryImageRequest request = new ResidentialLandGalleryImageRequest();
        request.setId(2L);
        request.setName("No Image");
        request.setPathImage(null);

        ResidentialLandGalleryImage entity = mapper.toEntity(request);

        assertNotNull(entity);
        assertEquals(2L, entity.getId());
        assertEquals("No Image", entity.getName());
        assertNull(entity.getPathImage());
    }

    @Test
    void toResponse() {
        ResidentialLandGalleryImage entity = ResidentialLandGalleryImage.builder()
                .id(3L)
                .name("Response Test")
                .pathImage("uploads/response.jpg")
                .build();

        ResidentialLandGalleryImageResponse response = mapper.toResponse(entity);

        assertNotNull(response);
        assertEquals(3L, response.getId());
        assertEquals("Response Test", response.getName());
        assertEquals("uploads/response.jpg", response.getPathImage());
    }

    @Test
    void update_withFile() throws IOException {
        // Существующая entity
        ResidentialLandGalleryImage entity = new ResidentialLandGalleryImage();
        entity.setId(4L);
        entity.setName("Old Name");
        entity.setPathImage("old/path.jpg");

        ResidentialLandGalleryImageRequest request = new ResidentialLandGalleryImageRequest();
        request.setName("New Name");
        MockMultipartFile file = new MockMultipartFile("file", "new.jpg", "image/jpeg", "new content".getBytes());
        request.setPathImage(file);

        when(fileService.uploadFileIfPresent(any(MultipartFile.class))).thenReturn("new/path.jpg");

        mapper.partialUpdate(request, entity);

        assertEquals(4L, entity.getId());
        assertEquals("New Name", entity.getName());
        assertEquals("new/path.jpg", entity.getPathImage());
        verify(fileService, times(1)).uploadFileIfPresent(file);
    }
    @Test
    void update_withoutFile() throws IOException {
        ResidentialLandGalleryImage entity = new ResidentialLandGalleryImage();
        entity.setId(5L);
        entity.setName("Old Name");
        entity.setPathImage("old/path.jpg");

        ResidentialLandGalleryImageRequest request = new ResidentialLandGalleryImageRequest();
        request.setName("New Name");
        request.setPathImage(null);


        mapper.partialUpdate(request, entity);

        assertEquals(5L, entity.getId());
        assertEquals("New Name", entity.getName());
        assertEquals("old/path.jpg", entity.getPathImage());

        verify(fileService, never()).uploadFileIfPresent(any());
    }

    @Test
    void update_nullFields() throws IOException {
        ResidentialLandGalleryImage entity = new ResidentialLandGalleryImage();
        entity.setId(6L);
        entity.setName("Old Name");
        entity.setPathImage("old/path.jpg");

        ResidentialLandGalleryImageRequest request = new ResidentialLandGalleryImageRequest();
        request.setName(null);
        MockMultipartFile emptyFile = new MockMultipartFile("file", "", "", new byte[0]);
        request.setPathImage(emptyFile);


        mapper.partialUpdate(request, entity);

        assertEquals(6L, entity.getId());
        assertEquals("Old Name", entity.getName());
        assertEquals("old/path.jpg", entity.getPathImage());

        verify(fileService, never()).uploadFileIfPresent(any());
    }

}