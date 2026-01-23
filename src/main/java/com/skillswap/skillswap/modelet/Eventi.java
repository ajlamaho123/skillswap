package com.skillswap.skillswap.modelet;

import com.skillswap.skillswap.modelet.enums.StatusEventi;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "eventet")
public class Eventi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulli;

    @Column(nullable = false, length = 2000)
    private String pershkrimi;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime ora;

    @Column(nullable = false)
    private String vendndodhja;

    @Column(nullable = false)
    private int kufiriPjesemarresve; // 0 = pa limit

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEventi statusi;

    @ManyToOne(optional = false)
    @JoinColumn(name = "host_id")
    private Perdoruesi host;

    @ManyToOne(optional = false)
    @JoinColumn(name = "kategori_id")
    private KategoriAftesie kategoria;

    public Eventi() {}

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulli() { return titulli; }
    public void setTitulli(String titulli) { this.titulli = titulli; }

    public String getPershkrimi() { return pershkrimi; }
    public void setPershkrimi(String pershkrimi) { this.pershkrimi = pershkrimi; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getOra() { return ora; }
    public void setOra(LocalTime ora) { this.ora = ora; }

    public String getVendndodhja() { return vendndodhja; }
    public void setVendndodhja(String vendndodhja) { this.vendndodhja = vendndodhja; }

    public int getKufiriPjesemarresve() { return kufiriPjesemarresve; }
    public void setKufiriPjesemarresve(int kufiriPjesemarresve) { this.kufiriPjesemarresve = kufiriPjesemarresve; }

    public StatusEventi getStatusi() { return statusi; }
    public void setStatusi(StatusEventi statusi) { this.statusi = statusi; }

    public Perdoruesi getHost() { return host; }
    public void setHost(Perdoruesi host) { this.host = host; }

    public KategoriAftesie getKategoria() { return kategoria; }
    public void setKategoria(KategoriAftesie kategoria) { this.kategoria = kategoria; }
}
