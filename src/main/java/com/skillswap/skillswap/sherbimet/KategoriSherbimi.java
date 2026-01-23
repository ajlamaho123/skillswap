package com.skillswap.skillswap.sherbimet;

import com.skillswap.skillswap.dto.KategoriDto;
import com.skillswap.skillswap.dto.KategoriResponseDto;
import com.skillswap.skillswap.modelet.KategoriAftesie;
import com.skillswap.skillswap.ruajtesat.KategoriAftesieRuajtesa;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KategoriSherbimi {

    private final KategoriAftesieRuajtesa kategoriRuajtesa;

    public KategoriSherbimi(KategoriAftesieRuajtesa kategoriRuajtesa) {
        this.kategoriRuajtesa = kategoriRuajtesa;
    }

    public List<KategoriResponseDto> listo() {
        return kategoriRuajtesa.findAll()
                .stream()
                .map(k -> new KategoriResponseDto(k.getId(), k.getEmri()))
                .toList();
    }

    public KategoriResponseDto shto(KategoriDto dto) {
        if (dto.getEmri() == null || dto.getEmri().isBlank()) {
            throw new RuntimeException("Emri i kategorise eshte i detyrueshem.");
        }
        if (kategoriRuajtesa.existsByEmri(dto.getEmri())) {
            throw new RuntimeException("Kjo kategori ekziston.");
        }

        KategoriAftesie k = new KategoriAftesie(dto.getEmri().trim());
        KategoriAftesie eRuajtur = kategoriRuajtesa.save(k);

        return new KategoriResponseDto(eRuajtur.getId(), eRuajtur.getEmri());
    }
}
