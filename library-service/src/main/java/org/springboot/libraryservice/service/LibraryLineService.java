package org.springboot.libraryservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springboot.libraryservice.libraryline.LibraryLine;
import org.springboot.libraryservice.mapper.LibraryLineMapper;
import org.springboot.libraryservice.repository.LibraryLineRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryLineService{
    private final LibraryLineRepo repository;
    private final LibraryLineMapper mapper;

    @Transactional
    public Integer saveLibraryLine(LibraryLineRequest request) {
        var libraryLine = mapper.toLibraryLine(request);
        var libraryLineId= repository.save(libraryLine).getId();
        repository.flush();
        return libraryLineId;
    }


    public List<LibraryLine> findAllByLibraryId(Integer libraryId) {
        return repository.findAllByLibraryId(libraryId);
    }
}
