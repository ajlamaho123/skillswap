package com.skillswap.skillswap.sherbimet;

import com.skillswap.skillswap.dto.*;
import com.skillswap.skillswap.modelet.Eventi;
import com.skillswap.skillswap.modelet.Perdoruesi;
import com.skillswap.skillswap.modelet.Regjistrimi;
import com.skillswap.skillswap.modelet.Vleresimi;
import com.skillswap.skillswap.modelet.enums.StatusRegjistrimi;
import com.skillswap.skillswap.ruajtesat.EventiRuajtesa;
import com.skillswap.skillswap.ruajtesat.PerdoruesiRuajtesa;
import com.skillswap.skillswap.ruajtesat.RegjistrimiRuajtesa;
import com.skillswap.skillswap.ruajtesat.VleresimiRuajtesa;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings({ "null", "unused" })
@Service
public class VleresimiSherbimi {

    private final VleresimiRuajtesa vleresimiRuajtesa;
    private final EventiRuajtesa eventiRuajtesa;
    private final PerdoruesiRuajtesa perdoruesiRuajtesa;
    private final RegjistrimiRuajtesa regjistrimiRuajtesa;

    public VleresimiSherbimi(VleresimiRuajtesa vleresimiRuajtesa,
                             EventiRuajtesa eventiRuajtesa,
                             PerdoruesiRuajtesa perdoruesiRuajtesa,
                             RegjistrimiRuajtesa regjistrimiRuajtesa) {
        this.vleresimiRuajtesa = vleresimiRuajtesa;
        this.eventiRuajtesa = eventiRuajtesa;
        this.perdoruesiRuajtesa = perdoruesiRuajtesa;
        this.regjistrimiRuajtesa = regjistrimiRuajtesa;
    }

    public void shtoVleresim(Long eventId, VleresimKrijoDto dto) {
        if (dto.getPerdoruesId() == null) {
            throw new RuntimeException("perdoruesId eshte i detyrueshem.");
        }
        if (dto.getYje() < 1 || dto.getYje() > 5) {
            throw new RuntimeException("Vleresimi duhet te jete nga 1 deri ne 5.");
        }

        Eventi event = eventiRuajtesa.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Eventi nuk u gjet."));

        Perdoruesi vleresues = perdoruesiRuajtesa.findById(dto.getPerdoruesId())
                .orElseThrow(() -> new RuntimeException("Perdoruesi nuk u gjet."));

        // kontroll: duhet te jete pjesemarres (regjistrim aktiv)
        boolean kaRegjistrim = regjistrimiRuajtesa.findByPerdoruesi_Id(dto.getPerdoruesId())
                .stream()
                .anyMatch(r -> r.getEventi().getId().equals(eventId) && r.getStatusi() == StatusRegjistrimi.AKTIV);

        if (!kaRegjistrim) {
            throw new RuntimeException("Nuk mund te vleresoni pa qene pjesemarres ne event.");
        }

        // mos lejo dy vleresime ne te njejtin event nga i njejti user
        if (vleresimiRuajtesa.existsByEventi_IdAndVleresuesi_Id(eventId, dto.getPerdoruesId())) {
            throw new RuntimeException("Ju e keni vleresuar me pare kete event.");
        }

        Vleresimi v = new Vleresimi();
        v.setEventi(event);
        v.setVleresuesi(vleresues);
        v.setHosti(event.getHost());
        v.setYje(dto.getYje());
        v.setKoment(dto.getKoment());
        v.setDataKrijimit(LocalDateTime.now());

        vleresimiRuajtesa.save(v);
    }

    public VleresimeHostResponseDto vleresimetEHostit(Long hostId) {
        List<Vleresimi> lista = vleresimiRuajtesa.findByHosti_Id(hostId);

        int numer = lista.size();
        double mesatare = 0.0;
        if (numer > 0) {
            int shuma = lista.stream().mapToInt(Vleresimi::getYje).sum();
            mesatare = (double) shuma / numer;
            mesatare = Math.round(mesatare * 10.0) / 10.0; // 1 decimal
        }

        List<VleresimItemDto> items = lista.stream()
                .map(v -> new VleresimItemDto(
                        v.getEventi().getId(),
                        v.getYje(),
                        v.getKoment(),
                        v.getDataKrijimit().toString(),
                        v.getVleresuesi().getId(),
                        v.getVleresuesi().getEmri(),
                        v.getVleresuesi().getMbiemri()
                ))
                .toList();

        return new VleresimeHostResponseDto(hostId, mesatare, numer, items);
    }
}
