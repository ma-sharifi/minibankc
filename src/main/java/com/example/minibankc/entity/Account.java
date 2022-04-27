package com.example.minibankc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * save customers account data
 */
@Entity
@Table(name = "account")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {

    @NotNull
    @Column(name = "balance", nullable = false)
    private long balance;

    //https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = { "account" }, allowSetters = true)
    private Set<AccountTransaction> accountTransactions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "account" }, allowSetters = true)
    @JsonIgnore
    private Customer customer;

    public Account(Customer customer) {
        this.customer = customer;
    }

    public void addTransaction(AccountTransaction transaction) {
        accountTransactions.add(transaction);
        balance= balance+transaction.getAmount();
        transaction.setNewBalance(balance);
        transaction.setAccount(this);
    }

    public void removeTransaction(AccountTransaction transaction) {
        accountTransactions.remove(transaction);
        transaction.setAccount(null);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        return super.getId() != null && super.getId().equals(((Account) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Account{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", transactions='" + getAccountTransactions() + "'" +
            "}";
    }
}
