package com.skillswap.skillswap.dto;

public class RegjistroDto {
    private String emri;
    private String mbiemri;
    private String email;
    private String fjalekalim;

    public RegjistroDto() {}

    public String getEmri() { return emri; }
    public void setEmri(String emri) { this.emri = emri; }

    public String getMbiemri() { return mbiemri; }
    public void setMbiemri(String mbiemri) { this.mbiemri = mbiemri; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFjalekalim() { return fjalekalim; }
    public void setFjalekalim(String fjalekalim) { this.fjalekalim = fjalekalim; }
}
