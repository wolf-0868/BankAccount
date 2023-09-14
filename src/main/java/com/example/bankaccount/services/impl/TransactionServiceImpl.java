package com.example.bankaccount.services.impl;

import com.example.bankaccount.data.TransactionData;
import com.example.bankaccount.data.TransactionType;
import com.example.bankaccount.data.entity.TransactionEntity;
import com.example.bankaccount.repositories.ITransactionRepository;
import com.example.bankaccount.services.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionData aData) {
        TransactionEntity entity = new TransactionEntity();
        entity.setType(aData.getType());
        entity.setFrom(aData.getAccountFrom());
        if (TransactionType.SEND == aData.getType()) {
            entity.setTo(aData.getAccountTo());
        }
        entity.setDateTime(LocalDateTime.now());
        entity.setSum(aData.getSum());
        transactionRepository.save(entity);
    }


}
