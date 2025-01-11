package org.springboot.certificationsservice.certifications;

public record CertificationPurchaseResponse(
        Integer certificationId,
        String name,
        String description,
        double price,
        double quantity
) {
}
