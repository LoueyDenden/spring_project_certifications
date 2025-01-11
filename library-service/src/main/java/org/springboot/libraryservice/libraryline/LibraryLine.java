package org.springboot.libraryservice.libraryline;

import jakarta.persistence.*;
import lombok.*;
import org.apache.tomcat.jni.Library;
import org.springboot.libraryservice.library.LibraryApp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class LibraryLine {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "library_id")
    private LibraryApp library;
    private Integer certificationId;
}
