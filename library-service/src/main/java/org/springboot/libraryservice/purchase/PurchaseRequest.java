package org.springboot.libraryservice.purchase;

import org.springboot.libraryservice.certifications.Certifications;
import org.springboot.libraryservice.libraryline.LibraryLine;
import org.springboot.libraryservice.service.LibraryLineRequest;

import java.util.List;

public record PurchaseRequest (
        List<LibraryLineRequest> certifications,
        String username
){
}
