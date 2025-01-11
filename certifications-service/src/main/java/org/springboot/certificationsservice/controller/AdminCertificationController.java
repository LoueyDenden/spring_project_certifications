package org.springboot.certificationsservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.certificationsservice.certifications.CertificationsRequest;
import org.springboot.certificationsservice.services.CertificationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certification/admin")
public class AdminCertificationController {

    private final CertificationsService service;

    //CRUD GAMES
    @PostMapping
    public ResponseEntity<Integer> createCertification(
            @ModelAttribute @Valid CertificationsRequest request
            , @RequestParam("file") MultipartFile file
    )  throws IOException {
        return ResponseEntity.ok(service.createCertification(request,file));
    }

    @PutMapping("/{certification-id}")
    public ResponseEntity<Integer> updateCertification(
           @PathVariable("certification-id") Integer id, @RequestBody @Valid CertificationsRequest request
    ){
        return ResponseEntity.ok(service.updateCertification(request,id));
    }

    @DeleteMapping("/{certification-id}")
    public ResponseEntity<Integer> deleteCertification(
            @PathVariable("certification-id") Integer id
    ){
        return ResponseEntity.ok(service.deleteCertification(id));
    }

}
