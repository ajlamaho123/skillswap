package com.skillswap.skillswap.dto;

public class EventListResponseDto {
    private Long id;
    private String titulli;
    private String pershkrimiShkurt;
    private String data;
    private String ora;
    private String vendndodhja;
    private int kufiriPjesemarresve;
    private String statusi;

    private Long kategoriId;
    private String kategoriEmri;

    private Long hostId;
    private String hostEmri;
    private String hostMbiemri;

    private String fotoUrl;

    public EventListResponseDto() {} // ✅ e rëndësishme

    public EventListResponseDto(Long id, String titulli, String pershkrimiShkurt, String data, String ora,
                                String vendndodhja, int kufiriPjesemarresve, String statusi,
                                Long kategoriId, String kategoriEmri,
                                Long hostId, String hostEmri, String hostMbiemri, String fotoUrl) {
        this.id = id;
        this.titulli = titulli;
        this.pershkrimiShkurt = pershkrimiShkurt;
        this.data = data;
        this.ora = ora;
        this.vendndodhja = vendndodhja;
        this.kufiriPjesemarresve = kufiriPjesemarresve;
        this.statusi = statusi;
        this.kategoriId = kategoriId;
        this.kategoriEmri = kategoriEmri;
        this.hostId = hostId;
        this.hostEmri = hostEmri;
        this.hostMbiemri = hostMbiemri;
        this.fotoUrl = fotoUrl;
    }

    public Long getId() { return id; }
    public String getTitulli() { return titulli; }
    public String getPershkrimiShkurt() { return pershkrimiShkurt; }
    public String getData() { return data; }
    public String getOra() { return ora; }
    public String getVendndodhja() { return vendndodhja; }
    public int getKufiriPjesemarresve() { return kufiriPjesemarresve; }
    public String getStatusi() { return statusi; }
    public Long getKategoriId() { return kategoriId; }
    public String getKategoriEmri() { return kategoriEmri; }
    public Long getHostId() { return hostId; }
    public String getHostEmri() { return hostEmri; }
    public String getHostMbiemri() { return hostMbiemri; }
    public String getFotoUrl() { return fotoUrl; }
}
