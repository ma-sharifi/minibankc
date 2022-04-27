package com.example.minibankc.entity;

import com.example.minibankc.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */
class AccountTest {
    Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void getBalance() {
        Long balance = 2L;
        account.setBalance(balance );
        assertEquals(balance, account.getBalance());
    }

    @Test
    void getCustomer() {
        Customer customer = new Customer();
        customer.setName("Mahdi");
        account.setCustomer(customer);
        assertEquals("Mahdi", account.getCustomer().getName());
        assertNotNull(account.getCustomer());
    }

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Account.class);
        Account account1 = new Account();
        account1.setId(1L);

        Account accounts2 = new Account();
        accounts2.setId(account1.getId());

        assertThat(account1).isEqualTo(accounts2);
        accounts2.setId(2L);
        assertThat(account1).isNotEqualTo(accounts2);
        account1.setId(null);
        assertThat(account1).isNotEqualTo(accounts2);
    }
}
