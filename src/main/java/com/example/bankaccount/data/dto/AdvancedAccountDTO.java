package com.example.bankaccount.data.dto;

import com.example.bankaccount.data.entity.AccountEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AdvancedAccountDTO extends BaseAccountDTO {

    private static final long serialVersionUID = -529549592734270824L;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("pin")
    private final int pin;

    public AdvancedAccountDTO(String aName, int aPin, int aNumber, double aBalance) {
        super(aNumber, aBalance);
        name = aName;
        pin = aPin;
    }

    public static AdvancedAccountDTO of(AccountEntity aEntity) {
        return AdvancedAccountDTO.builder()
                .name(aEntity.getName())
                .pin(aEntity.getPinCode())
                .number(aEntity.getNumber())
                .balance(aEntity.getBalance())
                .build();
    }

}
