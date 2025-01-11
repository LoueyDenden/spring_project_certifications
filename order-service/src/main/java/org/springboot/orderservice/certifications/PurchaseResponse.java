package org.springboot.orderservice.certifications;


public record PurchaseResponse(
        Integer certificationId,
        String name,
        String description,
        double price,
        double quantity
) {
}