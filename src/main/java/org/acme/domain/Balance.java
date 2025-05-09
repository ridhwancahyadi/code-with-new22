package org.acme.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Balance extends PanacheEntity {

    // Menggunakan PanacheEntity sudah otomatis menyediakan ID
    private BigDecimal balance;

    public Balance() {
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
