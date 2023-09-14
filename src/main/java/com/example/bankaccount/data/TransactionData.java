package com.example.bankaccount.data;

import com.example.bankaccount.data.entity.AccountEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionData {

    private final TransactionType type;
    private final AccountEntity accountFrom;
    private final AccountEntity accountTo;
    private final Double sum;

}
