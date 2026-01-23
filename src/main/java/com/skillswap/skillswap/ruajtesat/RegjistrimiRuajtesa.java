package com.skillswap.skillswap.ruajtesat;

import com.skillswap.skillswap.modelet.Regjistrimi;
import com.skillswap.skillswap.modelet.enums.StatusRegjistrimi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegjistrimiRuajtesa extends JpaRepository<Regjistrimi, Long> {

    boolean existsByEventi_IdAndPerdoruesi_Id(Long eventId, Long perdoruesId);

    List<Regjistrimi> findByPerdoruesi_Id(Long perdoruesId);

    List<Regjistrimi> findByEventi_Id(Long eventId);

    @Query("SELECT COUNT(r) FROM Regjistrimi r WHERE r.eventi.id = :eventId AND r.statusi = :statusi")
    long countAktiveByEvent(@Param("eventId") Long eventId, @Param("statusi") StatusRegjistrimi statusi);
}
