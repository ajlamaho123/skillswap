package com.skillswap.skillswap.sherbimet;

import com.skillswap.skillswap.dto.EventetEMiaDto;
import com.skillswap.skillswap.dto.PjesemarresDto;
import com.skillswap.skillswap.modelet.Eventi;
import com.skillswap.skillswap.modelet.Perdoruesi;
import com.skillswap.skillswap.modelet.Regjistrimi;
import com.skillswap.skillswap.modelet.enums.StatusEventi;
import com.skillswap.skillswap.modelet.enums.StatusRegjistrimi;
import com.skillswap.skillswap.ruajtesat.EventiRuajtesa;
import com.skillswap.skillswap.ruajtesat.PerdoruesiRuajtesa;
import com.skillswap.skillswap.ruajtesat.RegjistrimiRuajtesa;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("null")
@Service
public class RegjistrimiSherbimi {

    private final RegjistrimiRuajtesa regjistrimiRuajtesa;
    private final EventiRuajtesa eventiRuajtesa;
    private final PerdoruesiRuajtesa perdoruesiRuajtesa;

    public RegjistrimiSherbimi(RegjistrimiRuajtesa regjistrimiRuajtesa,
                               EventiRuajtesa eventiRuajtesa,
                               PerdoruesiRuajtesa perdoruesiRuajtesa) {
        this.regjistrimiRuajtesa = regjistrimiRuajtesa;
        this.eventiRuajtesa = eventiRuajtesa;
        this.perdoruesiRuajtesa = perdoruesiRuajtesa;
    }

    public void regjistro(Long eventId, Long perdoruesId) {
        Eventi event = eventiRuajtesa.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Eventi nuk u gjet."));

        if (event.getStatusi() != StatusEventi.AKTIV) {
            throw new RuntimeException("Eventi nuk eshte aktiv.");
        }

        Perdoruesi user = perdoruesiRuajtesa.findById(perdoruesId)
                .orElseThrow(() -> new RuntimeException("Perdoruesi nuk u gjet."));

        // mos lejo host-in te regjistrohet te eventi i vet (opsionale)
        if (event.getHost().getId().equals(perdoruesId)) {
            throw new RuntimeException("Hosti nuk mund te regjistrohet ne eventin e vet.");
        }

        if (regjistrimiRuajtesa.existsByEventi_IdAndPerdoruesi_Id(eventId, perdoruesId)) {
            throw new RuntimeException("Ju jeni regjistruar me pare ne kete event.");
        }

        // kontrolli i limitit (0 = pa limit)
        if (event.getKufiriPjesemarresve() > 0) {
            long aktive = regjistrimiRuajtesa.countAktiveByEvent(eventId, StatusRegjistrimi.AKTIV);
            if (aktive >= event.getKufiriPjesemarresve()) {
                throw new RuntimeException("Eventi eshte i plote.");
            }
        }

        Regjistrimi r = new Regjistrimi();
        r.setEventi(event);
        r.setPerdoruesi(user);
        r.setDataRegjistrimit(LocalDateTime.now());
        r.setStatusi(StatusRegjistrimi.AKTIV);

        regjistrimiRuajtesa.save(r);
    }

    public List<EventetEMiaDto> eventetEMia(Long perdoruesId) {
        return regjistrimiRuajtesa.findByPerdoruesi_Id(perdoruesId)
                .stream()
                .filter(r -> r.getStatusi() == StatusRegjistrimi.AKTIV)
                .map(r -> new EventetEMiaDto(
                        r.getEventi().getId(),
                        r.getEventi().getTitulli(),
                        r.getEventi().getData().toString(),
                        r.getEventi().getOra().toString(),
                        r.getEventi().getVendndodhja(),
                        r.getEventi().getStatusi().name()
                ))
                .toList();
    }

    public List<PjesemarresDto> pjesemarresitNeEvent(Long eventId, Long hostId) {
        Eventi event = eventiRuajtesa.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Eventi nuk u gjet."));

        if (!event.getHost().getId().equals(hostId)) {
            throw new RuntimeException("Nuk keni te drejte te shihni pjesemarresit e ketij eventi.");
        }

        return regjistrimiRuajtesa.findByEventi_Id(eventId)
                .stream()
                .filter(r -> r.getStatusi() == StatusRegjistrimi.AKTIV)
                .map(r -> new PjesemarresDto(
                        r.getPerdoruesi().getId(),
                        r.getPerdoruesi().getEmri(),
                        r.getPerdoruesi().getMbiemri(),
                        r.getPerdoruesi().getEmail()
                ))
                .toList();
    }
}
