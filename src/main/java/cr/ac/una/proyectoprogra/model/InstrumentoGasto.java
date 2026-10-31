/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author vjjcu
 */
public class InstrumentoGasto {

    private Long igaId;
    private Long igaInstrumentoId;
    private String igaDetalle;
    private LocalDate igaFecha;
    private BigDecimal igaMonto;
    private String igaFactura;
    private Boolean igaTributa;

    public InstrumentoGasto(String igaDetalle, LocalDate igaFecha, BigDecimal igaMonto, String igaFactura, Boolean igaTributa) {
        this.igaDetalle = igaDetalle;
        this.igaFecha = igaFecha;
        this.igaMonto = igaMonto;
        this.igaFactura = igaFactura;
        this.igaTributa = igaTributa;
    }

    public InstrumentoGasto() {
        
    }
    
    public Long getIgaId() {
        return igaId;
    }

    public void setIgaId(Long igaId) {
        this.igaId = igaId;
    }

    public Long getIgaInstrumentoId() {
        return igaInstrumentoId;
    }

    public void setIgaInstrumentoId(Long igaInstrumentoId) {
        this.igaInstrumentoId = igaInstrumentoId;
    }

    public String getIgaDetalle() {
        return igaDetalle;
    }

    public void setIgaDetalle(String igaDetalle) {
        this.igaDetalle = igaDetalle;
    }

    public LocalDate getIgaFecha() {
        return igaFecha;
    }

    public void setIgaFecha(LocalDate igaFecha) {
        this.igaFecha = igaFecha;
    }

    public BigDecimal getIgaMonto() {
        return igaMonto;
    }

    public void setIgaMonto(BigDecimal igaMonto) {
        this.igaMonto = igaMonto;
    }

    public String getIgaFactura() {
        return igaFactura;
    }

    public void setIgaFactura(String igaFactura) {
        this.igaFactura = igaFactura;
    }

    public Boolean getIgaTributa() {
        return igaTributa;
    }

    public void setIgaTributa(Boolean igaTributa) {
        this.igaTributa = igaTributa;
    }  
}
