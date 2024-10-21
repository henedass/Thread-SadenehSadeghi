package com.isc.multithreading.repository;

import com.isc.multithreading.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
