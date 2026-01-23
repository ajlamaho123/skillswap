package com.skillswap.skillswap.ruajtesat;

import com.skillswap.skillswap.modelet.Eventi;
import com.skillswap.skillswap.modelet.enums.StatusEventi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventiRuajtesa extends JpaRepository<Eventi, Long> {

    List<Eventi> findByStatusi(StatusEventi statusi);

    List<Eventi> findByKategoria_IdAndStatusi(Long kategoriId, StatusEventi statusi);

    List<Eventi> findByHost_Id(Long hostId);
}
