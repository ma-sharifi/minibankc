package com.example.minibankc.repository;

import com.example.minibankc.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */
/**
 * Spring Data SQL repository for the AccountTransaction entity.
 */
@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {}

