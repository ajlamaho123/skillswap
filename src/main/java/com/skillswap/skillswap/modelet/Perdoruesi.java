package com.skillswap.skillswap.modelet;

import com.skillswap.skillswap.modelet.enums.RoliPerdoruesit;
import jakarta.persistence.*;

@Entity
@Table(name = "perdoruesit")
public class Perdoruesi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String emri;

    @Column(nullable = false)
    private String mbiemri;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String fjalekalim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoliPerdoruesit roli;

    // --- Konstruktore ---
    public Perdoruesi() {}

    public Perdoruesi(String emri, String mbiemri, String email, String fjalekalim, RoliPerdoruesit roli) {
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.email = email;
        this.fjalekalim = fjalekalim;
        this.roli = roli;
    }

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmri() { return emri; }
    public void setEmri(String emri) { this.emri = emri; }

    public String getMbiemri() { return mbiemri; }
    public void setMbiemri(String mbiemri) { this.mbiemri = mbiemri; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFjalekalim() { return fjalekalim; }
    public void setFjalekalim(String fjalekalim) { this.fjalekalim = fjalekalim; }

    public RoliPerdoruesit getRoli() { return roli; }
    public void setRoli(RoliPerdoruesit roli) { this.roli = roli; }
}
