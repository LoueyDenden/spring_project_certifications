package org.springboot.userservice.repository;

import org.springboot.userservice.user.Role;
import org.springboot.userservice.user.UserApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserApp, Integer> {
    Optional<UserApp> findByUsername(String username);

    @Query("select u from UserApp u where u.name like %:name%")
    Page<UserApp> findByNameContaining(@Param("name") String name, Pageable pageable);

}