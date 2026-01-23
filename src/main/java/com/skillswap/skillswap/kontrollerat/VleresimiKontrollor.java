package com.skillswap.skillswap.kontrollerat;

import com.skillswap.skillswap.dto.VleresimKrijoDto;
import com.skillswap.skillswap.dto.VleresimeHostResponseDto;
import com.skillswap.skillswap.sherbimet.VleresimiSherbimi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class VleresimiKontrollor {

    private final VleresimiSherbimi vleresimiSherbimi;

    public VleresimiKontrollor(VleresimiSherbimi vleresimiSherbimi) {
        this.vleresimiSherbimi = vleresimiSherbimi;
    }

    // Shto vleresim per eventin
    @PostMapping("/api/evente/{id}/vleresime")
    public ResponseEntity<?> shto(@PathVariable Long id, @RequestBody VleresimKrijoDto dto) {
        try {
            vleresimiSherbimi.shtoVleresim(id, dto);
            return ResponseEntity.ok(Map.of("mesazh", "Vleresimi u ruajt me sukses."));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("gabim", ex.getMessage()));
        }
    }

    // Merr vleresimet e host-it + mesataren
    @GetMapping("/api/host/{id}/vleresime")
    public ResponseEntity<VleresimeHostResponseDto> vleresimetHostit(@PathVariable Long id) {
        return ResponseEntity.ok(vleresimiSherbimi.vleresimetEHostit(id));
    }
}
