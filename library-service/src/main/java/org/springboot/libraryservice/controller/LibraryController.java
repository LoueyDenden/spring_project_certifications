package org.springboot.libraryservice.controller;


import lombok.RequiredArgsConstructor;
import org.springboot.libraryservice.purchase.PurchaseRequest;
import org.springboot.libraryservice.service.LibraryService;
import org.springboot.libraryservice.user.UserApp;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/library")
public class LibraryController {
    private final LibraryService service;

    @PostMapping
    public void createLibrary(
            @RequestBody UserApp request,
            @RequestHeader("Authorization") String token
    ){
         service.createLibrary(request);
    }

    @PutMapping("/purchase")
    public void getOrdersByUserId(
            @RequestBody PurchaseRequest request
    )
    {
       service.updateLibrary(request);
    }
}
