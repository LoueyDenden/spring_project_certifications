package org.springboot.certificationsservice.controller;


import lombok.RequiredArgsConstructor;
import org.springboot.certificationsservice.certifications.*;
import org.springboot.certificationsservice.services.CertificationsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certifications")
public class CertificationsController {

    private final CertificationsService service;


    @PostMapping("/purchase")
    public ResponseEntity<List<CertificationPurchaseResponse>> purchaseCertifications(
            @RequestBody List<CertificationPurchaseRequest> request
    ) {
        return ResponseEntity.ok(service.purchaseCertifications(request));
    }

    @GetMapping("/{certification-id}")
    public ResponseEntity<CertificationsApp> findById(
            @PathVariable("certification-id") Integer certificationId
    ) {
        return ResponseEntity.ok(service.findById(certificationId));
    }

    @GetMapping
    public ResponseEntity<List<CertificationsApp>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<CertificationsApp>> findAllByNameContaining(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable= PageRequest.of(page,size);
        return ResponseEntity.ok(service.getCertificationsPagination(name,pageable));
    }
}
