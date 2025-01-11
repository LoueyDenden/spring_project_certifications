package org.springboot.libraryservice.repository;

import org.springboot.libraryservice.libraryline.LibraryLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryLineRepo extends JpaRepository<LibraryLine, Integer> {
    List<LibraryLine> findAllByLibraryId(Integer libraryId);
}
