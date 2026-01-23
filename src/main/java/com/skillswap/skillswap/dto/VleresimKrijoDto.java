package com.skillswap.skillswap.dto;

public class VleresimKrijoDto {
    private Long perdoruesId;
    private int yje;
    private String koment;

    public VleresimKrijoDto() {}

    public Long getPerdoruesId() { return perdoruesId; }
    public void setPerdoruesId(Long perdoruesId) { this.perdoruesId = perdoruesId; }

    public int getYje() { return yje; }
    public void setYje(int yje) { this.yje = yje; }

    public String getKoment() { return koment; }
    public void setKoment(String koment) { this.koment = koment; }
}
