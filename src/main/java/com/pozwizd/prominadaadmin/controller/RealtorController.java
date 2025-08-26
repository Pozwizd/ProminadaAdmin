package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.models.filter.RealtorFilter;
import com.pozwizd.prominadaadmin.models.realtor.RealtorRequest;
import com.pozwizd.prominadaadmin.models.realtor.RealtorResponse;
import com.pozwizd.prominadaadmin.service.RealtorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/realtor")
@AllArgsConstructor
@Slf4j
public class RealtorController {

    private final RealtorService realtorService;


    @GetMapping("/getAll")
    public CompletableFuture<ResponseEntity<?>> getAllRealtors(@ModelAttribute RealtorFilter filter) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Page<RealtorResponse> realtors = realtorService.getPageableRealtors(filter);
                log.info("Успешно получена страница с {} риелторами", realtors.getContent().size());
                return ResponseEntity.ok(realtors);
            } catch (Exception e) {
                log.error("Ошибка при получении списка риелторов", e);
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Ошибка при получении списка риелторов");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        });
    }

    @GetMapping("/{id}")
    @Async
    public CompletableFuture<ResponseEntity<?>> getRealtorById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                RealtorResponse response = realtorService.readResponseById(id);
                log.info("Риелтор с ID {} успешно получен", id);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                log.error("Ошибка при получении риелтора с ID {}", id, e);
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Риелтор с ID " + id + " не найден");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        });
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<?>> createRealtor(@Valid @ModelAttribute RealtorRequest realtorRequest) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Object response = realtorService.create(realtorRequest);
                log.info("Новый риелтор успешно создан");
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                log.error("Ошибка при создании риелтора", e);
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Ошибка при создании риелтора");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        });
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<?>> updateRealtor(@Valid @ModelAttribute RealtorRequest realtorRequest,
                                                              @PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            if (realtorRequest.getId() != null && !realtorRequest.getId().equals(id)) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "ID в пути и в теле запроса не совпадают");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            try {
                Object response = realtorService.update(realtorRequest, id);
                log.info("Риелтор с ID {} успешно обновлен", id);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                log.error("Ошибка при обновлении риелтора с ID {}", id, e);
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Ошибка при обновлении риелтора");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        });
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Map<String, String>>> deleteRealtor(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                realtorService.deleteById(id);
                log.info("Realtor with ID {} successfully deleted", id);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                log.error("Error while deleting realtor with ID {}", id, e);
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Realtor with ID " + id + " not found or deletion error");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        });
    }
}
