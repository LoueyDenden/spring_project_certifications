package org.springboot.libraryservice.repository;

import org.springboot.libraryservice.library.LibraryApp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LibraryRepo extends JpaRepository<LibraryApp, Integer> {

    Optional<LibraryApp> findByUsername(String username);
}
