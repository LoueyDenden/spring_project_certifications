package org.springboot.orderservice.user;

public record UserResponse(
        Integer id,
        String name,
        String username,
        String email
) {

}