package com.skillswap.skillswap.kontrollerat;

import com.skillswap.skillswap.dto.LoginDto;
import com.skillswap.skillswap.dto.PerdoruesResponseDto;
import com.skillswap.skillswap.dto.RegjistroDto;
import com.skillswap.skillswap.sherbimet.AutentifikimSherbimi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AutentifikimKontrollor {

    private final AutentifikimSherbimi autentifikimSherbimi;

    public AutentifikimKontrollor(AutentifikimSherbimi autentifikimSherbimi) {
        this.autentifikimSherbimi = autentifikimSherbimi;
    }

    @PostMapping("/regjistro")
    public ResponseEntity<?> regjistro(@RequestBody RegjistroDto dto) {
        try {
            PerdoruesResponseDto response = autentifikimSherbimi.regjistro(dto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("gabim", ex.getMessage()));
        }
    }

    @PostMapping("/hyr")
    public ResponseEntity<?> hyr(@RequestBody LoginDto dto) {
        try {
            PerdoruesResponseDto response = autentifikimSherbimi.hyr(dto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("gabim", ex.getMessage()));
        }
    }
}
