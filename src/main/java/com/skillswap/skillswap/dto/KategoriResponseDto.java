package com.skillswap.skillswap.dto;

public class KategoriResponseDto {
    private Long id;
    private String emri;

    public KategoriResponseDto() {}

    public KategoriResponseDto(Long id, String emri) {
        this.id = id;
        this.emri = emri;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmri() { return emri; }
    public void setEmri(String emri) { this.emri = emri; }
}
