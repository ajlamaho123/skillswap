package com.skillswap.skillswap.dto;

public class PerdoruesResponseDto {
    private Long id;
    private String emri;
    private String mbiemri;
    private String email;
    private String roli;

    public PerdoruesResponseDto() {}

    public PerdoruesResponseDto(Long id, String emri, String mbiemri, String email, String roli) {
        this.id = id;
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.email = email;
        this.roli = roli;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmri() { return emri; }
    public void setEmri(String emri) { this.emri = emri; }

    public String getMbiemri() { return mbiemri; }
    public void setMbiemri(String mbiemri) { this.mbiemri = mbiemri; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRoli() { return roli; }
    public void setRoli(String roli) { this.roli = roli; }
}
