package org.springboot.libraryservice.mapper;

import org.springboot.libraryservice.library.LibraryApp;
import org.springboot.libraryservice.libraryline.LibraryLine;
import org.springboot.libraryservice.service.LibraryLineRequest;
import org.springframework.stereotype.Service;

@Service
public class LibraryLineMapper {
    public LibraryLine toLibraryLine(LibraryLineRequest request) {
        return LibraryLine.builder()
                .certificationId(request.certificationId())
                .library(LibraryApp.builder()
                        .id(request.libraryId())
                        .build())
                .build();
    }
}
