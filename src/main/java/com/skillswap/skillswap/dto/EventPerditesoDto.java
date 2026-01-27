package com.skillswap.skillswap.dto;

public class EventPerditesoDto {

    private Long hostId;
    private Long kategoriId;
    private String titulli;
    private String pershkrimi;
    private String data; // "YYYY-MM-DD"
    private String ora;  // "HH:mm"
    private String vendndodhja;
    private int kufiriPjesemarresve;
    private String fotoUrl;

    public EventPerditesoDto() {}

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public Long getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(Long kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getTitulli() {
        return titulli;
    }

    public void setTitulli(String titulli) {
        this.titulli = titulli;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getVendndodhja() {
        return vendndodhja;
    }

    public void setVendndodhja(String vendndodhja) {
        this.vendndodhja = vendndodhja;
    }

    public int getKufiriPjesemarresve() {
        return kufiriPjesemarresve;
    }

    public void setKufiriPjesemarresve(int kufiriPjesemarresve) {
        this.kufiriPjesemarresve = kufiriPjesemarresve;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }
    
    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
