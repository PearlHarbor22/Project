package com.bank.publicinfo.repositories;

import com.bank.publicinfo.entities.ATM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmRepository extends JpaRepository<ATM, Long> {
}
