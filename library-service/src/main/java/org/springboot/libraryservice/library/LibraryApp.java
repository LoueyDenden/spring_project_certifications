package org.springboot.libraryservice.library;


import jakarta.persistence.*;
import lombok.*;
import org.springboot.libraryservice.certifications.Certifications;
import org.springboot.libraryservice.libraryline.LibraryLine;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class LibraryApp {
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    @OneToMany(mappedBy = "library")
    private List<LibraryLine> certifications;
}
