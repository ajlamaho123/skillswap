package com.skillswap.skillswap.kontrollerat;

import com.skillswap.skillswap.dto.EventDetajeResponseDto;
import com.skillswap.skillswap.dto.EventKrijoDto;
import com.skillswap.skillswap.dto.EventListResponseDto;
import com.skillswap.skillswap.dto.EventPerditesoDto;
import com.skillswap.skillswap.sherbimet.EventiSherbimi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/evente")
public class EventiKontrollor {

    private final EventiSherbimi eventiSherbimi;

    public EventiKontrollor(EventiSherbimi eventiSherbimi) {
        this.eventiSherbimi = eventiSherbimi;
    }

    @GetMapping
    public ResponseEntity<List<EventListResponseDto>> listo(
            @RequestParam(required = false) Long kategoriId,
            @RequestParam(required = false) String kerkimi
    ) {
        return ResponseEntity.ok(eventiSherbimi.listoEvente(kategoriId, kerkimi));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detaje(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(eventiSherbimi.detaje(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("gabim", ex.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> shto(@RequestBody EventKrijoDto dto) {
        try {
            EventDetajeResponseDto created = eventiSherbimi.shto(dto);
            return ResponseEntity.ok(created);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("gabim", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> perditeso(@PathVariable Long id, @RequestBody EventPerditesoDto dto) {
        try {
            if (dto.getHostId() == null) {
                return ResponseEntity.badRequest().body(Map.of("gabim", "hostId eshte i detyrueshem."));
            }
            return ResponseEntity.ok(eventiSherbimi.perditeso(id, dto.getHostId(), dto));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("gabim", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> anulo(@PathVariable Long id, @RequestParam Long hostId) {
        try {
            eventiSherbimi.anulo(id, hostId);
            return ResponseEntity.ok(Map.of("mesazh", "Eventi u anulua."));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("gabim", ex.getMessage()));
        }
    }
}
