package org.springboot.certificationsservice.mapper;


import org.springboot.certificationsservice.category.CategoryApp;
import org.springboot.certificationsservice.certifications.*;
import org.springframework.stereotype.Service;

@Service

public class CertificationMapper {
    public CertificationsApp toCertification(CertificationsRequest request) {
        return CertificationsApp.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .avaiblity(request.avaiblity())
                .price(request.price())
                .category(
                        CategoryApp.builder()
                                .id(request.categoryId())
                                .build()
                )
                .build();
    }

    public CertificationPurchaseResponse toCertificationPurchaseResponse(CertificationsApp certification, double quantity) {
        return new CertificationPurchaseResponse(
                certification.getId(),
                certification.getName(),
                certification.getDescription(),
                certification.getPrice(),
                quantity
        );
    }
}
