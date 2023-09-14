package com.example.bankaccount.data.entity;

import com.example.bankaccount.data.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "transaction")
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class TransactionEntity implements Serializable {

    private static final long serialVersionUID = 5136515278767564377L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TransactionType type;

    @JoinColumn(name = "from_accaount_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountEntity from;

    @JoinColumn(name = "to_accaount_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountEntity to;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "datetime", nullable = false)
    private LocalDateTime dateTime = LocalDateTime.now();

}
