package org.springboot.certificationsservice.certifications;

public record CertificationsRequest(
         Integer id,
         String name,
         String description,
         double avaiblity,
         double price,
         String image,
         Integer categoryId
){
}
