package com.example.minibankc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */
/**
 * save Transaction of account
 */
@Entity
@Table(name = "account_transaction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountTransaction extends BaseEntity {

    @NotNull
    @Column(name = "amount", nullable = false)
    private long amount;

    @NotNull
    @Column(name = "new_balance", nullable = false)
    private long newBalance;

    @Column(name = "reference_no")
    private Long referenceNo;

    @ManyToOne
    @JsonIgnoreProperties(value = {  "accountTransactions", "customer" }, allowSetters = true)
    private Account account;

    public AccountTransaction(long amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountTransaction)) {
            return false;
        }
        return getId() != null && getId().equals(((AccountTransaction) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountTransaction{" +
                "id=" + getId() +
                ", amount=" + getAmount() +
                ", referenceNo=" + getReferenceNo() +
                ", newBalancd='" + getNewBalance() + "'" +
                ", createdAt='" + getCreatedAt() + "'" +
                "}";
    }
}