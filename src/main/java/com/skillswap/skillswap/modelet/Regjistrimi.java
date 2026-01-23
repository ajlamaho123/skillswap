package com.skillswap.skillswap.modelet;

import com.skillswap.skillswap.modelet.enums.StatusRegjistrimi;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "regjistrimet",
       uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "perdorues_id"}))
public class Regjistrimi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id")
    private Eventi eventi;

    @ManyToOne(optional = false)
    @JoinColumn(name = "perdorues_id")
    private Perdoruesi perdoruesi;

    @Column(nullable = false)
    private LocalDateTime dataRegjistrimit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRegjistrimi statusi;

    public Regjistrimi() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Eventi getEventi() { return eventi; }
    public void setEventi(Eventi eventi) { this.eventi = eventi; }

    public Perdoruesi getPerdoruesi() { return perdoruesi; }
    public void setPerdoruesi(Perdoruesi perdoruesi) { this.perdoruesi = perdoruesi; }

    public LocalDateTime getDataRegjistrimit() { return dataRegjistrimit; }
    public void setDataRegjistrimit(LocalDateTime dataRegjistrimit) { this.dataRegjistrimit = dataRegjistrimit; }

    public StatusRegjistrimi getStatusi() { return statusi; }
    public void setStatusi(StatusRegjistrimi statusi) { this.statusi = statusi; }
}
