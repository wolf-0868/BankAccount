package com.example.bankaccount.repositories;

import com.example.bankaccount.data.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<AccountEntity, Long> {

    boolean existsByNumber(int aNumber);

    boolean existsByName(String aName);

    AccountEntity findByNumber(int aNumber);

    AccountEntity findByName(String aName);

}
