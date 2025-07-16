package com.pozwizd.prominadaadmin.service.serviceImp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.models.personal.PersonalRequest;
import com.pozwizd.prominadaadmin.repository.primary.PersonalRepository;
import com.pozwizd.prominadaadmin.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class PersonalServiceImpTest {

    @Mock
    private PersonalRepository personalRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private FileService fileService;
    @InjectMocks
    private PersonalServiceImp personalServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        when(personalRepository.findAll()).thenReturn(List.of(new Personal(), new Personal()));

        var result = personalServiceImp.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(personalRepository, times(1)).findAll();
    }

    @Test
    void findAll_throwsException() {
        when(personalRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        OperationException exception = assertThrows(OperationException.class, () -> personalServiceImp.findAll());
        assertEquals("получении списка пользователей", exception.getMessage());
    }

    @Test
    void findById() {
        Personal personal = new Personal();
        personal.setId(1L);
        when(personalRepository.findById(1L)).thenReturn(Optional.of(personal));

        var result = personalServiceImp.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(personalRepository, times(1)).findById(1L);
    }

    @Test
    void findById_notFound() {
        when(personalRepository.findById(1L)).thenReturn(Optional.empty());

        var result = personalServiceImp.findById(1L);

        assertFalse(result.isPresent());
        verify(personalRepository, times(1)).findById(1L);
    }

    @Test
    void findById_throwsException() {
        when(personalRepository.findById(1L)).thenThrow(new RuntimeException("Database error"));

        OperationException exception = assertThrows(OperationException.class, () -> personalServiceImp.findById(1L));
        assertEquals("поиске пользователя с ID 1", exception.getMessage());
    }

    @Test
    void findByEmail() {
        Personal personal = new Personal();
        personal.setEmail("test@example.com");
        when(personalRepository.findByEmail("test@example.com")).thenReturn(Optional.of(personal));

        var result = personalServiceImp.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        verify(personalRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void save() {
        Personal personal = new Personal();
        personal.setPassword("password123");
        when(passwordEncoder.encode(personal.getPassword())).thenReturn("encodedPassword");
        when(personalRepository.save(personal)).thenReturn(personal);

        var savedPersonal = personalServiceImp.save(personal);

        assertNotNull(savedPersonal);
        assertEquals("encodedPassword", savedPersonal.getPassword());
        verify(personalRepository, times(1)).save(personal);
    }

    @Test
    void save_throwsException() {
        Personal personal = new Personal();
        personal.setPassword("password123");
        when(passwordEncoder.encode(personal.getPassword())).thenReturn("encodedPassword");
        when(personalRepository.save(personal)).thenThrow(new RuntimeException("Database error"));

        OperationException exception = assertThrows(OperationException.class, () -> personalServiceImp.save(personal));
        assertEquals("сохранении пользователя", exception.getMessage());
    }

    @Test
    void deleteById() {
        doNothing().when(personalRepository).deleteById(1L);

        personalServiceImp.deleteById(1L);

        verify(personalRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_throwsException() {
        doThrow(new RuntimeException("Database error")).when(personalRepository).deleteById(1L);

        OperationException exception = assertThrows(OperationException.class, () -> personalServiceImp.deleteById(1L));
        assertEquals("удалении пользователя с ID 1", exception.getMessage());
    }

//    @Test
//    void getPageablePersonal() {
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        Page<Personal> personalPage = mock(Page.class);
//        when(personalRepository.findAll(any(), eq(pageRequest))).thenReturn(personalPage);
//
//        var result = personalServiceImp.getPageablePersonal(0, 10, null, null, null, null, null, null);
//
//        assertNotNull(result);
//        verify(personalRepository, times(1)).findAll(any(), eq(pageRequest));
//    }
//
//    @Test
//    void getPageablePersonal_throwsException() {
//        when(personalRepository.findAll(any(), any())).thenThrow(new RuntimeException("Database error"));
//
//        OperationException exception = assertThrows(OperationException.class,
//                () -> personalServiceImp.getPageablePersonal(0, 10, null, null, null, null, null, null));
//        assertEquals("получении постраничного списка пользователей", exception.getMessage());
//    }

    @Test
    void saveFromRequest() {
        PersonalRequest personalRequest = mock(PersonalRequest.class);
        Personal personal = new Personal();
        when(personalRepository.save(any(Personal.class))).thenReturn(personal);

        personalServiceImp.saveFromRequest(personalRequest);

        verify(personalRepository, times(1)).save(any(Personal.class));
    }

    @Test
    void saveFromRequest_throwsException() {
        PersonalRequest personalRequest = mock(PersonalRequest.class);
        when(personalRepository.save(any(Personal.class))).thenThrow(new RuntimeException("Database error"));

        OperationException exception = assertThrows(OperationException.class,
                () -> personalServiceImp.saveFromRequest(personalRequest));
        assertEquals("сохранении пользователя из PersonalRequest", exception.getMessage());
    }

    @Test
    void updatePersonal() {
        PersonalRequest personalRequest = mock(PersonalRequest.class);
        Personal personal = new Personal();
        when(personalRepository.findById(anyLong())).thenReturn(Optional.of(personal));
        when(personalRepository.save(any(Personal.class))).thenReturn(personal);

        personalServiceImp.updatePersonal(personalRequest);

        verify(personalRepository, times(1)).save(any(Personal.class));
    }

    @Test
    void updatePersonal_throwsException() {
        PersonalRequest personalRequest = mock(PersonalRequest.class);
        when(personalRepository.findById(anyLong())).thenThrow(new RuntimeException("Database error"));

        OperationException exception = assertThrows(OperationException.class,
                () -> personalServiceImp.updatePersonal(personalRequest));
        assertEquals("обновлении пользователя", exception.getMessage());
    }
}
