package org.springboot.orderservice.certifications;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest (
        @NotNull(message = "Certification is mandatory")
        Integer certificationId,
        @Positive(message = "Quantity is mandatory")
        double quantity
){
}
