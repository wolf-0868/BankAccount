package com.example.bankaccount.data.dto;

import com.example.bankaccount.data.entity.AccountEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
public class BaseAccountDTO implements Serializable {

    private static final long serialVersionUID = 6316341214166341415L;

    @JsonProperty("number")
    protected final int number;

    @JsonProperty("balance")
    protected final double balance;

    public static BaseAccountDTO of(AccountEntity aEntity) {
        return BaseAccountDTO.builder()
                .number(aEntity.getNumber())
                .balance(aEntity.getBalance())
                .build();
    }

}
