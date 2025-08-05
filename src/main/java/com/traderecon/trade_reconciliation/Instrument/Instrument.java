package com.traderecon.trade_reconciliation.Instrument;

import jakarta.persistence.*;
import lombok.*;

@Entity
public class Instrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private String name;
    private String isin;

    public Instrument(Long id, String symbol, String name, String isin) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.isin = isin;
    }

    public Instrument() {
    }

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getIsin() {
        return isin;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }
}
