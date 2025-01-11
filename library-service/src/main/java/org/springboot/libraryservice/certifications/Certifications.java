package org.springboot.libraryservice.certifications;

import jakarta.persistence.Entity;
import lombok.*;
import org.springboot.libraryservice.library.LibraryApp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Certifications{
    private Integer gameId;
    private String name;
    private String description;
    private double price;
    private double quantity;
    private LibraryApp libraryApp;
}
