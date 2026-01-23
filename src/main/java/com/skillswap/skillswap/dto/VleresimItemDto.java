package com.skillswap.skillswap.dto;

public class VleresimItemDto {
    private Long eventId;
    private int yje;
    private String koment;
    private String dataKrijimit;

    private Long ngaId;
    private String ngaEmri;
    private String ngaMbiemri;

    public VleresimItemDto() {}

    public VleresimItemDto(Long eventId, int yje, String koment, String dataKrijimit,
                           Long ngaId, String ngaEmri, String ngaMbiemri) {
        this.eventId = eventId;
        this.yje = yje;
        this.koment = koment;
        this.dataKrijimit = dataKrijimit;
        this.ngaId = ngaId;
        this.ngaEmri = ngaEmri;
        this.ngaMbiemri = ngaMbiemri;
    }

    public Long getEventId() { return eventId; }
    public int getYje() { return yje; }
    public String getKoment() { return koment; }
    public String getDataKrijimit() { return dataKrijimit; }
    public Long getNgaId() { return ngaId; }
    public String getNgaEmri() { return ngaEmri; }
    public String getNgaMbiemri() { return ngaMbiemri; }
}
