package org.springboot.orderservice.library;


import org.springboot.orderservice.certifications.PurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(
        name = "library-service",
        url = "${application.config.library-url}"
)
public interface LibraryClient {
    @PutMapping("/purchase")
    List<PurchaseResponse> purchaseLibrary(@RequestBody PurchaseLibrary requestBody, @RequestHeader("Authorization") String token);
}
