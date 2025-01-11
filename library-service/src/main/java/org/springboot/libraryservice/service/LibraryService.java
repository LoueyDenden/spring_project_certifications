package org.springboot.libraryservice.service;


import lombok.RequiredArgsConstructor;
import org.springboot.libraryservice.certifications.Certifications;
import org.springboot.libraryservice.library.LibraryApp;
import org.springboot.libraryservice.libraryline.LibraryLine;
import org.springboot.libraryservice.purchase.PurchaseRequest;
import org.springboot.libraryservice.repository.LibraryRepo;
import org.springboot.libraryservice.user.UserApp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepo libraryRepo;
    private final LibraryLineService libraryLineService;

    public void createLibrary(UserApp user) {
        LibraryApp libraryToSave = new LibraryApp();
        libraryToSave.setCertifications(null);
        libraryToSave.setUsername(user.username());
        libraryRepo.save(libraryToSave);
    }

    public void updateLibrary(PurchaseRequest request) {
        LibraryApp libraryToUpdate = libraryRepo.findByUsername(request.username()).orElse(null);
        for (LibraryLineRequest certification:request.certifications()){
            libraryLineService.saveLibraryLine(
                    new LibraryLineRequest(
                            libraryToUpdate.getId(),
                            certification.certificationId()
                    )
            );
        }

    }
}
