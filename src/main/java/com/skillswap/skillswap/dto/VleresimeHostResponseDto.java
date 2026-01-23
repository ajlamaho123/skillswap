package com.skillswap.skillswap.dto;

import java.util.List;

public class VleresimeHostResponseDto {
    private Long hostId;
    private double mesatareRating;
    private int numerVleresimesh;
    private List<VleresimItemDto> vleresime;

    public VleresimeHostResponseDto() {}

    public VleresimeHostResponseDto(Long hostId, double mesatareRating, int numerVleresimesh, List<VleresimItemDto> vleresime) {
        this.hostId = hostId;
        this.mesatareRating = mesatareRating;
        this.numerVleresimesh = numerVleresimesh;
        this.vleresime = vleresime;
    }

    public Long getHostId() { return hostId; }
    public double getMesatareRating() { return mesatareRating; }
    public int getNumerVleresimesh() { return numerVleresimesh; }
    public List<VleresimItemDto> getVleresime() { return vleresime; }
}
