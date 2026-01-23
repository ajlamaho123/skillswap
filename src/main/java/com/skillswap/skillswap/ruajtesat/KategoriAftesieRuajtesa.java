package com.skillswap.skillswap.ruajtesat;

import com.skillswap.skillswap.modelet.KategoriAftesie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KategoriAftesieRuajtesa extends JpaRepository<KategoriAftesie, Long> {

    boolean existsByEmri(String emri);
}
