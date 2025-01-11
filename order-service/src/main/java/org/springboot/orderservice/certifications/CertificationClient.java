package org.springboot.orderservice.certifications;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(
        name = "certifications-service",
        url = "${application.config.certifications-url}"
)
public interface CertificationClient {
    @PostMapping("/purchase")
    List<PurchaseResponse> purchaseCertifications(@RequestBody List<PurchaseRequest> requestBody, @RequestHeader("Authorization") String token);
}