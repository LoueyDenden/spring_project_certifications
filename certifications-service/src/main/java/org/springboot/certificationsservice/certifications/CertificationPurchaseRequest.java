package org.springboot.certificationsservice.certifications;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CertificationPurchaseRequest(
        @NotNull(message = "Certification is mandatory")
        Integer certificationId,
        @Positive(message = "Quantity is mandatory")
        double quantity
) {
}
