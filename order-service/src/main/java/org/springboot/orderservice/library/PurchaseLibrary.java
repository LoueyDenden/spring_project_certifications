package org.springboot.orderservice.library;

import org.springboot.orderservice.certifications.PurchaseResponse;

import java.util.List;

public record PurchaseLibrary(
        List<PurchaseResponse> certifications,
        String username
) {
}
