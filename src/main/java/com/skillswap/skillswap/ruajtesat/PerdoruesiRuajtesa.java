package com.skillswap.skillswap.ruajtesat;

import com.skillswap.skillswap.modelet.Perdoruesi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerdoruesiRuajtesa extends JpaRepository<Perdoruesi, Long> {

    Optional<Perdoruesi> findByEmail(String email);

    boolean existsByEmail(String email);
}
