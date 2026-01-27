package com.skillswap.skillswap.sherbimet;

import com.skillswap.skillswap.dto.LoginDto;
import com.skillswap.skillswap.dto.PerdoruesResponseDto;
import com.skillswap.skillswap.dto.RegjistroDto;
import com.skillswap.skillswap.modelet.Perdoruesi;
import com.skillswap.skillswap.modelet.enums.RoliPerdoruesit;
import com.skillswap.skillswap.ruajtesat.PerdoruesiRuajtesa;
import org.springframework.stereotype.Service;

@Service
public class AutentifikimSherbimi {

    private final PerdoruesiRuajtesa perdoruesiRuajtesa;

    public AutentifikimSherbimi(PerdoruesiRuajtesa perdoruesiRuajtesa) {
        this.perdoruesiRuajtesa = perdoruesiRuajtesa;
    }

    public PerdoruesResponseDto regjistro(RegjistroDto dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new RuntimeException("Emaili eshte i detyrueshem.");
        }
        if (perdoruesiRuajtesa.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ekziston nje perdorues me kete email.");
        }
        if (dto.getFjalekalim() == null || dto.getFjalekalim().isBlank()) {
            throw new RuntimeException("Fjalekalimi eshte i detyrueshem.");
        }
        if (dto.getEmri() == null || dto.getEmri().isBlank()) {
            throw new RuntimeException("Emri eshte i detyrueshem.");
        }
        if (dto.getMbiemri() == null || dto.getMbiemri().isBlank()) {
            throw new RuntimeException("Mbiemri eshte i detyrueshem.");
        }

        RoliPerdoruesit roli = dto.getRoli();
        if (roli == null)
            roli = RoliPerdoruesit.PERDORUES;

        Perdoruesi p = new Perdoruesi(
                dto.getEmri(),
                dto.getMbiemri(),
                dto.getEmail(),
                dto.getFjalekalim(),
                roli //  përdor rolin që vjen nga register
        );

        Perdoruesi iRuajtur = perdoruesiRuajtesa.save(p);

        return new PerdoruesResponseDto(
                iRuajtur.getId(),
                iRuajtur.getEmri(),
                iRuajtur.getMbiemri(),
                iRuajtur.getEmail(),
                iRuajtur.getRoli().name());
    }

    public PerdoruesResponseDto hyr(LoginDto dto) {
        Perdoruesi p = perdoruesiRuajtesa.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ose fjalekalim i pasakte."));

        if (!p.getFjalekalim().equals(dto.getFjalekalim())) {
            throw new RuntimeException("Email ose fjalekalim i pasakte.");
        }

        if (dto.getEmail() == null || dto.getEmail().isBlank()
                || dto.getFjalekalim() == null || dto.getFjalekalim().isBlank()) {
            throw new RuntimeException("Email ose fjalekalim i pasakte.");
        }

        return new PerdoruesResponseDto(
                p.getId(),
                p.getEmri(),
                p.getMbiemri(),
                p.getEmail(),
                p.getRoli().name());
    }
}
