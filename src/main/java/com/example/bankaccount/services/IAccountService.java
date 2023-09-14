package com.example.bankaccount.services;

import com.example.bankaccount.data.dto.AdvancedAccountDTO;
import com.example.bankaccount.data.dto.BaseAccountDTO;
import com.example.bankaccount.data.payloads.AdvancedTransactionPayload;
import com.example.bankaccount.data.payloads.BaseTransactionPayload;
import com.example.bankaccount.exceptions.BankAccountException;
import lombok.NonNull;

import java.util.List;

public interface IAccountService {

    void createAccount(@NonNull BaseTransactionPayload aPayload) throws BankAccountException;

    void fillBalanceAccount(@NonNull AdvancedTransactionPayload aPayload) throws BankAccountException;

    void returnSum(@NonNull AdvancedTransactionPayload aPayload) throws BankAccountException;

    void sendSumToAccount(@NonNull AdvancedTransactionPayload aPayload) throws BankAccountException;

    void loadAccounts(List<AdvancedAccountDTO> aAccounts);

    List<BaseAccountDTO> getAllAccounts();

}
