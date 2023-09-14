package com.example.bankaccount.services.impl;

import com.example.bankaccount.data.TransactionData;
import com.example.bankaccount.data.TransactionType;
import com.example.bankaccount.data.dto.AdvancedAccountDTO;
import com.example.bankaccount.data.dto.BaseAccountDTO;
import com.example.bankaccount.data.entity.AccountEntity;
import com.example.bankaccount.data.payloads.AdvancedTransactionPayload;
import com.example.bankaccount.data.payloads.BaseTransactionPayload;
import com.example.bankaccount.exceptions.BankAccountException;
import com.example.bankaccount.repositories.IAccountRepository;
import com.example.bankaccount.services.IAccountService;
import com.example.bankaccount.services.ITransactionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private static final int MIN_PIN_CODE = 0;
    private static final int MAX_PIN_CODE = 9999;

    private final IAccountRepository accountRepository;
    private final ITransactionService transactionService;

    @Override
    public void createAccount(@NonNull BaseTransactionPayload aPayload) throws BankAccountException {
        String name = aPayload.getName();
        if (accountRepository.existsByName(name)) {
            throw new BankAccountException("Счёт с именем '%s' уже существует", name);
        }
        int code = aPayload.getPinCode();
        if (code < MIN_PIN_CODE || code > MAX_PIN_CODE) {
            throw new BankAccountException("PIN-код должен быть от 0000 до 9999");
        }
        accountRepository.save(creatAccountEntity(aPayload));
    }

    @Override
    public void fillBalanceAccount(@NonNull AdvancedTransactionPayload aPayload) throws BankAccountException {
        AccountEntity account = getAccount(aPayload.getName());
        validatePinCode(account.getPinCode(), aPayload.getPinCode());
        double sum = aPayload.getSum();
        if (sum <= 0) {
            throw new BankAccountException("Сумма должна быть больше нуля");
        }
        account.setBalance(account.getBalance() + sum);
        accountRepository.save(account);
        transactionService.saveTransaction(TransactionData.builder()
                .type(TransactionType.FILL)
                .accountFrom(account)
                .sum(sum)
                .build());
    }

    @Override
    public void returnSum(@NonNull AdvancedTransactionPayload aPayload) throws BankAccountException {
        AccountEntity account = getAccount(aPayload.getName());
        validatePinCode(account.getPinCode(), aPayload.getPinCode());
        double sum = aPayload.getSum();
        validateSum(sum);
        Double currentBalance = account.getBalance();
        if ((currentBalance - sum) < 0) {
            throw new BankAccountException("Недостаточно средств");
        }
        account.setBalance(currentBalance - sum);
        accountRepository.save(account);
        transactionService.saveTransaction(TransactionData.builder()
                .type(TransactionType.RETURN)
                .accountFrom(account)
                .sum(sum)
                .build());
    }

    @Override
    public void sendSumToAccount(@NonNull AdvancedTransactionPayload aPayload) throws BankAccountException {
        AccountEntity accountFrom = getAccount(aPayload.getName());
        AccountEntity accountTo = getAccount(aPayload.getSendNumber());
        validatePinCode(accountFrom.getPinCode(), aPayload.getPinCode());
        double sum = aPayload.getSum();
        validateSum(sum);
        Double currentBalance = accountFrom.getBalance();
        if ((currentBalance - sum) < 0) {
            throw new BankAccountException("Недостаточно средств");
        }
        accountFrom.setBalance(accountFrom.getBalance() - sum);
        accountTo.setBalance(accountTo.getBalance() + sum);
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
        transactionService.saveTransaction(TransactionData.builder()
                .type(TransactionType.SEND)
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .sum(sum)
                .build());
    }

    @Override
    public List<BaseAccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(BaseAccountDTO::of)
                .collect(Collectors.toList());
    }

    @Override
    public void loadAccounts(List<AdvancedAccountDTO> aAccounts) {
        accountRepository.saveAll(aAccounts.stream()
                .map(AccountServiceImpl::creatAccountEntity)
                .collect(Collectors.toList()));
    }

    private static AccountEntity creatAccountEntity(AdvancedAccountDTO aAccount) {
        AccountEntity entity = new AccountEntity();
        entity.setName(aAccount.getName());
        entity.setPinCode(aAccount.getPin());
        entity.setNumber(aAccount.getNumber());
        entity.setBalance(aAccount.getBalance());
        return entity;
    }

    private AccountEntity creatAccountEntity(BaseTransactionPayload aPayload) throws BankAccountException {
        int number = findSmallestNumber(accountRepository.findAll()
                .stream()
                .mapToInt(AccountEntity::getNumber)
                .toArray());
        if (number < 0) {
            throw new BankAccountException("Превышен пул номеров счётов");
        }
        AccountEntity entity = new AccountEntity();
        entity.setName(aPayload.getName());
        entity.setPinCode(aPayload.getPinCode());
        entity.setNumber(number);
        entity.setBalance(0.0);
        return entity;
    }

    private AccountEntity getAccount(String aName) throws BankAccountException {
        if (!accountRepository.existsByName(aName)) {
            throw new BankAccountException("Счёт с именем '%s' не существует", aName);
        }
        return accountRepository.findByName(aName);
    }

    private AccountEntity getAccount(int aNumber) throws BankAccountException {
        if (!accountRepository.existsByNumber(aNumber)) {
            throw new BankAccountException("Счёт с номером '%s' не существует", aNumber);
        }
        return accountRepository.findByNumber(aNumber);
    }

    private void validatePinCode(int aPin1, int aPin2) throws BankAccountException {
        if (aPin1 != aPin2) {
            throw new BankAccountException("Неверный PIN-код");
        }
    }

    private void validateSum(double aSum) throws BankAccountException {
        if (aSum <= 0) {
            throw new BankAccountException("Сумма должна быть больше нуля");
        }
    }

    private static int findSmallestNumber(int[] aInts) {
        boolean[] dp = new boolean[aInts.length + 1];
        for (int number : aInts) {
            if (number < dp.length) {
                dp[number] = true;
            }
        }
        for (int i = 1; i < dp.length; i++) {
            if (!dp[i]) {
                return i;
            }
        }
        return dp.length;
    }

}
