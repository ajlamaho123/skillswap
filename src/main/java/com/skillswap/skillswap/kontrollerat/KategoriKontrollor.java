package com.skillswap.skillswap.kontrollerat;

import com.skillswap.skillswap.dto.KategoriDto;
import com.skillswap.skillswap.dto.KategoriResponseDto;
import com.skillswap.skillswap.sherbimet.KategoriSherbimi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kategori")
public class KategoriKontrollor {

    private final KategoriSherbimi kategoriSherbimi;

    public KategoriKontrollor(KategoriSherbimi kategoriSherbimi) {
        this.kategoriSherbimi = kategoriSherbimi;
    }

    @GetMapping
    public ResponseEntity<List<KategoriResponseDto>> listo() {
        return ResponseEntity.ok(kategoriSherbimi.listo());
    }

    @PostMapping
    public ResponseEntity<?> shto(@RequestBody KategoriDto dto) {
        try {
            return ResponseEntity.ok(kategoriSherbimi.shto(dto));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("gabim", ex.getMessage()));
        }
    }
}
