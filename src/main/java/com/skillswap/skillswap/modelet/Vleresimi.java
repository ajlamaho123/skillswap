package com.skillswap.skillswap.modelet;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vleresimet",
       uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "vleresues_id"}))
public class Vleresimi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id")
    private Eventi eventi;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vleresues_id")
    private Perdoruesi vleresuesi;   // ai qe le vleresimin

    @ManyToOne(optional = false)
    @JoinColumn(name = "host_id")
    private Perdoruesi hosti;        // host i eventit

    @Column(nullable = false)
    private int yje; // 1-5

    @Column(length = 2000)
    private String koment;

    @Column(nullable = false)
    private LocalDateTime dataKrijimit;

    public Vleresimi() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Eventi getEventi() { return eventi; }
    public void setEventi(Eventi eventi) { this.eventi = eventi; }

    public Perdoruesi getVleresuesi() { return vleresuesi; }
    public void setVleresuesi(Perdoruesi vleresuesi) { this.vleresuesi = vleresuesi; }

    public Perdoruesi getHosti() { return hosti; }
    public void setHosti(Perdoruesi hosti) { this.hosti = hosti; }

    public int getYje() { return yje; }
    public void setYje(int yje) { this.yje = yje; }

    public String getKoment() { return koment; }
    public void setKoment(String koment) { this.koment = koment; }

    public LocalDateTime getDataKrijimit() { return dataKrijimit; }
    public void setDataKrijimit(LocalDateTime dataKrijimit) { this.dataKrijimit = dataKrijimit; }
}
