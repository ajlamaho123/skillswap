package com.skillswap.skillswap.dto;

public class EventetEMiaDto {
    private Long eventId;
    private String titulli;
    private String data;
    private String ora;
    private String vendndodhja;
    private String statusiEventit;

    public EventetEMiaDto() {}

    public EventetEMiaDto(Long eventId, String titulli, String data, String ora, String vendndodhja, String statusiEventit) {
        this.eventId = eventId;
        this.titulli = titulli;
        this.data = data;
        this.ora = ora;
        this.vendndodhja = vendndodhja;
        this.statusiEventit = statusiEventit;
    }

    public Long getEventId() { return eventId; }
    public String getTitulli() { return titulli; }
    public String getData() { return data; }
    public String getOra() { return ora; }
    public String getVendndodhja() { return vendndodhja; }
    public String getStatusiEventit() { return statusiEventit; }
}
