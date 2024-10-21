package com.isc.multithreading.service;

import com.isc.multithreading.entity.Account;
import com.isc.multithreading.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    Object target;
    Logger logger= LoggerFactory.getLogger(AccountService.class);

    @Async
    public CompletableFuture<List<Account>> saveAccounts(MultipartFile file) throws Exception {
        long start=System.currentTimeMillis();
        List<Account> accounts=parseCSVFile(file);
        logger.info("saving list of users if size{}" , accounts.size() , ""+Thread.currentThread().getName());
        accounts= accountRepository.saveAll(accounts);
        long end=System.currentTimeMillis();
        logger.info("Total time{}" ,(end-start));
        return CompletableFuture.completedFuture(accounts);

    }

    @Async
    public CompletableFuture<List<Account>> findAllAccounts(){
        logger.info("get list of Accounts by" + Thread.currentThread().getName());
        List<Account> accounts=accountRepository.findAll();
        return CompletableFuture.completedFuture(accounts);
    }

    private List<Account> parseCSVFile(final MultipartFile file) throws Exception {
        final List<Account> accounts = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final Account account = new Account();
                    account.setAccCustID(data[0]);
                    account.setAccNum(data[1]);
                    account.setAccType(data[2]);
                    account.setAccBalance(data[3]);
                    account.setRecordNum(data[4]);
                    account.setAccOpenDate(data[5]);
                    accounts.add(account);
                }
                return accounts;
            }
        } catch (final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }


    }

}
