package com.example.minibankc.entity;

import com.example.minibankc.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */
class AccountTransactionTest {
    AccountTransaction accountTransaction;

    @BeforeEach
    void setUp() {
        accountTransaction = new AccountTransaction();
    }

    @Test
    void getAmount() {
        long amount = (1);
        accountTransaction.setAmount(amount);
        assertEquals(amount, accountTransaction.getAmount());
    }

    @Test
    void getReferenceNo() {
        accountTransaction.setReferenceNo(3L);
        assertEquals(3L, accountTransaction.getReferenceNo());
    }

    @Test
    void getAccount() {
        long amount = 1;
        Account account = new Account();
        account.setBalance(amount);
        accountTransaction.setAccount(account);
        assertEquals(amount, accountTransaction.getAccount().getBalance());
    }

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountTransaction.class);
        AccountTransaction accountTransaction1 = new AccountTransaction();
        accountTransaction1.setId(1L);
        AccountTransaction accountTransaction2 = new AccountTransaction();
        accountTransaction2.setId(accountTransaction1.getId());
        assertThat(accountTransaction1).isEqualTo(accountTransaction2);
        accountTransaction2.setId(2L);
        assertThat(accountTransaction1).isNotEqualTo(accountTransaction2);
        accountTransaction1.setId(null);
        assertThat(accountTransaction1).isNotEqualTo(accountTransaction2);
    }
}
