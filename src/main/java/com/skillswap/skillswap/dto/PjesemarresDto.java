package com.skillswap.skillswap.dto;

public class PjesemarresDto {
    private Long perdoruesId;
    private String emri;
    private String mbiemri;
    private String email;

    public PjesemarresDto() {}

    public PjesemarresDto(Long perdoruesId, String emri, String mbiemri, String email) {
        this.perdoruesId = perdoruesId;
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.email = email;
    }

    public Long getPerdoruesId() { return perdoruesId; }
    public String getEmri() { return emri; }
    public String getMbiemri() { return mbiemri; }
    public String getEmail() { return email; }
}
