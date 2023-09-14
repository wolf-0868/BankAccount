package com.example.bankaccount.repositories;

import com.example.bankaccount.data.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends JpaRepository<TransactionEntity, Long> {}
