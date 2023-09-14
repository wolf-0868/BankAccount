package com.example.bankaccount.controllers;

import com.example.bankaccount.data.dto.AdvancedAccountDTO;
import com.example.bankaccount.data.dto.BaseAccountDTO;
import com.example.bankaccount.data.payloads.AdvancedTransactionPayload;
import com.example.bankaccount.data.payloads.BaseTransactionPayload;
import com.example.bankaccount.exceptions.BankAccountException;
import com.example.bankaccount.services.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BankAccountController {

    private final IAccountService accountService;

    @GetMapping("/create")
    public String handleCreateAccount(@ModelAttribute("name") String aName,
                                      @ModelAttribute("pin") int aPin) {
        try {
            accountService.createAccount(BaseTransactionPayload.builder()
                    .name(aName)
                    .pinCode(aPin)
                    .build());
        } catch (BankAccountException e) {
            return e.getMessage();
        }
        return "OK";
    }


    @GetMapping("/fill")
    public String handleFillAccount(@ModelAttribute("name") String aName,
                                    @ModelAttribute("pin") int aPin,
                                    @ModelAttribute("sum") double aSum) {
        try {
            accountService.fillBalanceAccount(AdvancedTransactionPayload.builder()
                    .name(aName)
                    .pinCode(aPin)
                    .sum(aSum)
                    .build());
        } catch (BankAccountException e) {
            return e.getMessage();
        }
        return "OK";
    }

    @GetMapping("/return")
    public String handleReturnSum(@ModelAttribute("name") String aName,
                                  @ModelAttribute("pin") int aPin,
                                  @ModelAttribute("sum") double aSum) {
        try {
            accountService.returnSum(AdvancedTransactionPayload
                    .builder()
                    .name(aName)
                    .pinCode(aPin)
                    .sum(aSum)
                    .build());
        } catch (BankAccountException e) {
            return e.getMessage();
        }
        return "OK";
    }

    @GetMapping("/send")
    public String handleSend(@ModelAttribute("name") String aName,
                             @ModelAttribute("pin") int aPin,
                             @ModelAttribute("to") int aToNumber,
                             @ModelAttribute("sum") double aSum) {
        try {
            accountService.sendSumToAccount(AdvancedTransactionPayload
                    .builder()
                    .name(aName)
                    .pinCode(aPin)
                    .sendNumber(aToNumber)
                    .sum(aSum)
                    .build());
        } catch (BankAccountException e) {
            return e.getMessage();
        }
        return "OK";
    }

    @GetMapping("/all")
    public List<BaseAccountDTO> handleSend() {
        return accountService.getAllAccounts();
    }

    @PostMapping("/load")
    public void handleLoadAccounts(@RequestBody List<AdvancedAccountDTO> aAccounts) {
        accountService.loadAccounts(aAccounts);
    }

}
