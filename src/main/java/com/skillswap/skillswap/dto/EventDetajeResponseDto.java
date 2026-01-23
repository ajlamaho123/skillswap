package com.skillswap.skillswap.dto;

public class EventDetajeResponseDto {
    private Long id;
    private String titulli;
    private String pershkrimi;
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

    public EventDetajeResponseDto() {}

    public EventDetajeResponseDto(Long id, String titulli, String pershkrimi, String data, String ora,
                                  String vendndodhja, int kufiriPjesemarresve, String statusi,
                                  Long kategoriId, String kategoriEmri,
                                  Long hostId, String hostEmri, String hostMbiemri) {
        this.id = id;
        this.titulli = titulli;
        this.pershkrimi = pershkrimi;
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
    }

    public Long getId() { return id; }
    public String getTitulli() { return titulli; }
    public String getPershkrimi() { return pershkrimi; }
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
}
