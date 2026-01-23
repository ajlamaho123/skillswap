package com.skillswap.skillswap.ruajtesat;

import com.skillswap.skillswap.modelet.Vleresimi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VleresimiRuajtesa extends JpaRepository<Vleresimi, Long> {

    boolean existsByEventi_IdAndVleresuesi_Id(Long eventId, Long vleresuesId);

    List<Vleresimi> findByHosti_Id(Long hostId);
}
