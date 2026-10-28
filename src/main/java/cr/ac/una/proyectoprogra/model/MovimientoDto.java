/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Nikole
 */
public class MovimientoDto {
    private Long movId;
    private String movTipo;
    private String movDetalle;
    private LocalDate movFecha;
    private BigDecimal movMonto;
    private String movFactura;
    private Boolean movTributa = Boolean.FALSE;
    private Long movBufeteId;
    private String movOrigen;

    public MovimientoDto() {
    }

    public MovimientoDto(Long movId, String movTipo, String movDetalle, LocalDate movFecha, BigDecimal movMonto, String movFactura, Long movBufeteId, String movOrigen) {
        this.movId = movId;
        this.movTipo = movTipo;
        this.movDetalle = movDetalle;
        this.movFecha = movFecha;
        this.movMonto = movMonto;
        this.movFactura = movFactura;
        this.movBufeteId = movBufeteId;
        this.movOrigen = movOrigen;
    }

    public Long getMovId() {
        return movId;
    }

    public void setMovId(Long movId) {
        this.movId = movId;
    }

    public String getMovTipo() {
        return movTipo;
    }

    public void setMovTipo(String movTipo) {
        this.movTipo = movTipo;
    }

    public String getMovDetalle() {
        return movDetalle;
    }

    public void setMovDetalle(String movDetalle) {
        this.movDetalle = movDetalle;
    }

    public LocalDate getMovFecha() {
        return movFecha;
    }

    public void setMovFecha(LocalDate movFecha) {
        this.movFecha = movFecha;
    }

    public BigDecimal getMovMonto() {
        return movMonto;
    }

    public void setMovMonto(BigDecimal movMonto) {
        this.movMonto = movMonto;
    }

    public String getMovFactura() {
        return movFactura;
    }

    public void setMovFactura(String movFactura) {
        this.movFactura = movFactura;
    }

    public Boolean getMovTributa() {
        return movTributa;
    }

    public void setMovTributa(Boolean movTributa) {
        this.movTributa = movTributa;
    }

    public Long getMovBufeteId() {
        return movBufeteId;
    }

    public void setMovBufeteId(Long movBufeteId) {
        this.movBufeteId = movBufeteId;
    }

    public String getMovOrigen() {
        return movOrigen;
    }

    public void setMovOrigen(String movOrigen) {
        this.movOrigen = movOrigen;
    }
    
    
}
