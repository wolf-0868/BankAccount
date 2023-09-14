package com.example.bankaccount.data.payloads;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@ToString
@SuperBuilder
@EqualsAndHashCode
public class BaseTransactionPayload implements Serializable {

    private static final long serialVersionUID = 2622924907829908045L;

    private String name;

    private int pinCode;

}
