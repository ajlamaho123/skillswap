package com.skillswap.skillswap.kontrollerat;

import com.skillswap.skillswap.dto.EventetEMiaDto;
import com.skillswap.skillswap.dto.PjesemarresDto;
import com.skillswap.skillswap.dto.RegjistrimKrijoDto;
import com.skillswap.skillswap.sherbimet.RegjistrimiSherbimi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RegjistrimiKontrollor {

    private final RegjistrimiSherbimi regjistrimiSherbimi;

    public RegjistrimiKontrollor(RegjistrimiSherbimi regjistrimiSherbimi) {
        this.regjistrimiSherbimi = regjistrimiSherbimi;
    }

    // Regjistrohu ne event
    @PostMapping("/api/evente/{id}/regjistrime")
    public ResponseEntity<?> regjistro(@PathVariable Long id, @RequestBody RegjistrimKrijoDto dto) {
        try {
            if (dto.getPerdoruesId() == null) {
                return ResponseEntity.badRequest().body(Map.of("gabim", "perdoruesId eshte i detyrueshem."));
            }
            regjistrimiSherbimi.regjistro(id, dto.getPerdoruesId());
            return ResponseEntity.ok(Map.of("mesazh", "U regjistruat me sukses."));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("gabim", ex.getMessage()));
        }
    }

    // Eventet e mia
    @GetMapping("/api/perdorues/{id}/regjistrime")
    public ResponseEntity<List<EventetEMiaDto>> eventetEMia(@PathVariable Long id) {
        return ResponseEntity.ok(regjistrimiSherbimi.eventetEMia(id));
    }

    // Pjesemarresit (vetem host)
    @GetMapping("/api/evente/{id}/pjesemarres")
    public ResponseEntity<?> pjesemarresit(@PathVariable Long id, @RequestParam Long hostId) {
        try {
            List<PjesemarresDto> lista = regjistrimiSherbimi.pjesemarresitNeEvent(id, hostId);
            return ResponseEntity.ok(lista);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("gabim", ex.getMessage()));
        }
    }
}
