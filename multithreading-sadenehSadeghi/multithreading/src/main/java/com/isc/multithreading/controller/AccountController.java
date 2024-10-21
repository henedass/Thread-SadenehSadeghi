package com.isc.multithreading.controller;

import com.isc.multithreading.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/accounts" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} , produces = "application/json")
    public ResponseEntity saveAccounts(@RequestParam(value = "files")MultipartFile[] files) throws Exception {
        for( MultipartFile file:files){
            accountService.saveAccounts(file);

        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/accounts" , produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllAccounts(){
       return accountService.findAllAccounts().thenApply(ResponseEntity::ok);
    }
}
