package com.skillswap.skillswap.modelet;

import jakarta.persistence.*;

@Entity
@Table(name = "kategorite_aftesive")
public class KategoriAftesie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String emri;

    public KategoriAftesie() {}

    public KategoriAftesie(String emri) {
        this.emri = emri;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmri() { return emri; }
    public void setEmri(String emri) { this.emri = emri; }
}
