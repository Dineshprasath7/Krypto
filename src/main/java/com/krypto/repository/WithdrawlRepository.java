package com.krypto.repository;

import com.krypto.model.Withdrawl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawlRepository extends JpaRepository<Withdrawl,Long> {
    List<Withdrawl> findByUserId(Long userId);
}
