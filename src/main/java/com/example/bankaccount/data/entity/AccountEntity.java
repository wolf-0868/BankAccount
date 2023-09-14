package com.example.bankaccount.data.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "account")
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class AccountEntity implements Serializable {

    private static final long serialVersionUID = -7861715844715964836L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "pin_code", nullable = false)
    private Integer pinCode;

    @Column(name = "number", nullable = false, unique = true)
    private Integer number;

    @Column(name = "balance", nullable = false)
    private Double balance = 0.0;

}
