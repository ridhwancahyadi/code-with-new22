package org.acme.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Student extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ini menunjukkan ID auto-increment
    private Long id;
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;

    private String name;
    private String jurusan;
    private Float ipk;
    private Date tanggalLahir;
    private BigDecimal balance;

    // public Long getId() {
    // return id;
    // }

    // public void setId(Long id) {
    // this.id = id;
    // }

    // Getter dan setter untuk name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter dan setter untuk nim
    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    // Getter dan setter untuk jurusan
    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public Float getIpk() {
        return ipk;
    }

    public void setIpk(Float ipk) {
        this.ipk = ipk;
    }

    // Getter dan setter untuk balance
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
