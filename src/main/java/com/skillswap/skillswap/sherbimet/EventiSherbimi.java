package com.skillswap.skillswap.sherbimet;

import com.skillswap.skillswap.dto.*;
import com.skillswap.skillswap.modelet.Eventi;
import com.skillswap.skillswap.modelet.KategoriAftesie;
import com.skillswap.skillswap.modelet.Perdoruesi;
import com.skillswap.skillswap.modelet.enums.StatusEventi;
import com.skillswap.skillswap.ruajtesat.EventiRuajtesa;
import com.skillswap.skillswap.ruajtesat.KategoriAftesieRuajtesa;
import com.skillswap.skillswap.ruajtesat.PerdoruesiRuajtesa;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SuppressWarnings("null")
@Service
public class EventiSherbimi {

    private final EventiRuajtesa eventiRuajtesa;
    private final PerdoruesiRuajtesa perdoruesiRuajtesa;
    private final KategoriAftesieRuajtesa kategoriRuajtesa;

    public EventiSherbimi(EventiRuajtesa eventiRuajtesa,
            PerdoruesiRuajtesa perdoruesiRuajtesa,
            KategoriAftesieRuajtesa kategoriRuajtesa) {
        this.eventiRuajtesa = eventiRuajtesa;
        this.perdoruesiRuajtesa = perdoruesiRuajtesa;
        this.kategoriRuajtesa = kategoriRuajtesa;
    }

    public List<EventListResponseDto> listoEvente(Long kategoriId, String kerkimi) {
        List<Eventi> evente;

        if (kategoriId != null) {
            evente = eventiRuajtesa.findByKategoria_IdAndStatusi(kategoriId, StatusEventi.AKTIV);
        } else {
            evente = eventiRuajtesa.findByStatusi(StatusEventi.AKTIV);
        }

        // filter search (kerkimi) ne memorje (simple)
        if (kerkimi != null && !kerkimi.isBlank()) {
            String k = kerkimi.trim().toLowerCase();
            evente = evente.stream()
                    .filter(e -> e.getTitulli().toLowerCase().contains(k)
                            || e.getPershkrimi().toLowerCase().contains(k)
                            || e.getVendndodhja().toLowerCase().contains(k))
                    .toList();
        }

        return evente.stream().map(this::toListDto).toList();
    }

    public EventDetajeResponseDto detaje(Long eventId) {
        Eventi e = eventiRuajtesa.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Eventi nuk u gjet."));

        return toDetajeDto(e);
    }

    public EventDetajeResponseDto shto(EventKrijoDto dto) {
        validoDto(dto);

        Perdoruesi host = perdoruesiRuajtesa.findById(dto.getHostId())
                .orElseThrow(() -> new RuntimeException("Hosti nuk u gjet."));

        KategoriAftesie kategori = kategoriRuajtesa.findById(dto.getKategoriId())
                .orElseThrow(() -> new RuntimeException("Kategoria nuk u gjet."));

        LocalDate data = LocalDate.parse(dto.getData());
        LocalTime ora = LocalTime.parse(dto.getOra());

        // Validim i thjeshte: jo ne te kaluaren
        if (data.isBefore(LocalDate.now())) {
            throw new RuntimeException("Data nuk mund te jete ne te kaluaren.");
        }

        Eventi e = new Eventi();
        e.setTitulli(dto.getTitulli().trim());
        e.setPershkrimi(dto.getPershkrimi().trim());
        e.setData(data);
        e.setOra(ora);
        e.setVendndodhja(dto.getVendndodhja().trim());
        e.setKufiriPjesemarresve(dto.getKufiriPjesemarresve());
        e.setStatusi(StatusEventi.AKTIV);
        e.setHost(host);
        e.setKategoria(kategori);
        e.setFotoUrl(dto.getFotoUrl());
        
        Eventi iRuajtur = eventiRuajtesa.save(e);
        return toDetajeDto(iRuajtur);
    }

    public EventDetajeResponseDto perditeso(Long eventId, Long hostId, EventPerditesoDto dto) {
        validoDto(dto);

        Eventi ekzistues = eventiRuajtesa.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Eventi nuk u gjet."));

        if (!ekzistues.getHost().getId().equals(hostId)) {
            throw new RuntimeException("Nuk keni te drejte te perditesoni kete event.");
        }

        KategoriAftesie kategori = kategoriRuajtesa.findById(dto.getKategoriId())
                .orElseThrow(() -> new RuntimeException("Kategoria nuk u gjet."));

        LocalDate data = LocalDate.parse(dto.getData());
        LocalTime ora = LocalTime.parse(dto.getOra());

        ekzistues.setTitulli(dto.getTitulli().trim());
        ekzistues.setPershkrimi(dto.getPershkrimi().trim());
        ekzistues.setData(data);
        ekzistues.setOra(ora);
        ekzistues.setVendndodhja(dto.getVendndodhja().trim());
        ekzistues.setKufiriPjesemarresve(dto.getKufiriPjesemarresve());
        ekzistues.setKategoria(kategori);
        ekzistues.setFotoUrl(dto.getFotoUrl());

        if (dto.getFotoUrl() != null && !dto.getFotoUrl().isBlank()) {
            ekzistues.setFotoUrl(dto.getFotoUrl().trim());
        }

        Eventi iRuajtur = eventiRuajtesa.save(ekzistues);
        return toDetajeDto(iRuajtur);
    }

    public void anulo(Long eventId, Long hostId) {
        Eventi ekzistues = eventiRuajtesa.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Eventi nuk u gjet."));

        if (!ekzistues.getHost().getId().equals(hostId)) {
            throw new RuntimeException("Nuk keni te drejte te anuloni kete event.");
        }

        ekzistues.setStatusi(StatusEventi.ANULUAR);
        eventiRuajtesa.save(ekzistues);
    }

    // --- Helpers ---
    private void validoDto(EventKrijoDto dto) {
        validimIBashket(dto.getHostId(), dto.getKategoriId(), dto.getTitulli(),
                dto.getPershkrimi(), dto.getData(), dto.getOra(),
                dto.getVendndodhja(), dto.getKufiriPjesemarresve(), dto.getFotoUrl());
    }

    private void validoDto(EventPerditesoDto dto) {
        validimIBashket(dto.getHostId(), dto.getKategoriId(), dto.getTitulli(),
                dto.getPershkrimi(), dto.getData(), dto.getOra(),
                dto.getVendndodhja(), dto.getKufiriPjesemarresve(), dto.getFotoUrl());
    }

    private void validimIBashket(Long hostId, Long kategoriId, String titulli,
        String pershkrimi, String data, String ora,
        String vendndodhja, int kufiri, String fotoUrl) {

    if (hostId == null) throw new RuntimeException("hostId eshte i detyrueshem.");
    if (kategoriId == null) throw new RuntimeException("kategoriId eshte i detyrueshem.");
    if (titulli == null || titulli.isBlank()) throw new RuntimeException("Titulli eshte i detyrueshem.");
    if (pershkrimi == null || pershkrimi.isBlank()) throw new RuntimeException("Pershkrimi eshte i detyrueshem.");
    if (data == null || data.isBlank()) throw new RuntimeException("Data eshte e detyrueshme.");
    if (ora == null || ora.isBlank()) throw new RuntimeException("Ora eshte e detyrueshme.");
    if (vendndodhja == null || vendndodhja.isBlank()) throw new RuntimeException("Vendndodhja eshte e detyrueshme.");
    if (kufiri < 0) throw new RuntimeException("Kufiri i pjesemarresve nuk mund te jete negativ.");

    // fotoUrl OPSIONALE (lejohet null/blank)
    if (fotoUrl != null && !fotoUrl.isBlank()) {
        String f = fotoUrl.trim();
        if (!(f.startsWith("http://") || f.startsWith("https://"))) {
            throw new RuntimeException("Foto URL duhet te jete link (http/https).");
        }
    }
}


    private EventListResponseDto toListDto(Eventi e) {
        String shkurt = e.getPershkrimi();
        if (shkurt.length() > 120)
            shkurt = shkurt.substring(0, 120) + "...";

        return new EventListResponseDto(
                e.getId(),
                e.getTitulli(),
                shkurt,
                e.getData().toString(),
                e.getOra().toString(),
                e.getVendndodhja(),
                e.getKufiriPjesemarresve(),
                e.getStatusi().name(),
                e.getKategoria().getId(),
                e.getKategoria().getEmri(),
                e.getHost().getId(),
                e.getHost().getEmri(),
                e.getHost().getMbiemri(),
                e.getFotoUrl());
    }

    private EventDetajeResponseDto toDetajeDto(Eventi e) {
        return new EventDetajeResponseDto(
                e.getId(),
                e.getTitulli(),
                e.getPershkrimi(),
                e.getData().toString(),
                e.getOra().toString(),
                e.getVendndodhja(),
                e.getKufiriPjesemarresve(),
                e.getStatusi().name(),
                e.getKategoria().getId(),
                e.getKategoria().getEmri(),
                e.getHost().getId(),
                e.getHost().getEmri(),
                e.getHost().getMbiemri(),
                e.getFotoUrl());
    }
}
