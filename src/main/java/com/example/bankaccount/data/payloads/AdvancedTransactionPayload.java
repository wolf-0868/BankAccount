package com.example.bankaccount.data.payloads;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AdvancedTransactionPayload extends BaseTransactionPayload {

    private static final long serialVersionUID = 8616825471856252518L;

    private Integer sendNumber;

    private double sum;

}
