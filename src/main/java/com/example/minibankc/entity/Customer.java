package com.example.minibankc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/24/22
 */

@Entity
@Table(name = "customer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class Customer extends BaseEntity {

    @Size(min = 2, max = 60)
    @Column(name = "name", length = 60)
    private String name;

    @Size(min = 2, max = 60)
    @Column(name = "surname", length = 60)
    private String surname;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "customer",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = { "accountTransactions", "customer" }, allowSetters = true)
    private Set<Account> accounts = new HashSet<>();

    public void addAccount(Account account) {
        accounts.add(account);
        account.setCustomer(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.setCustomer(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return getId() != null && getId().equals(((Customer) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
