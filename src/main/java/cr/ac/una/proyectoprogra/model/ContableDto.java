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
public class ContableDto {
    private String tipo;
    private String detalle;
    private LocalDate fecha;
    private BigDecimal monto;
    private String factura;
    private Boolean tributa;
    private String origen;

    public ContableDto() {
    }

    public ContableDto(String tipo, String detalle, LocalDate fecha, BigDecimal monto, String factura, Boolean tributa, String origen) {
        this.tipo = tipo;
        this.detalle = detalle;
        this.fecha = fecha;
        this.monto = monto;
        this.factura = factura;
        this.tributa = tributa;
        this.origen = origen;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public Boolean getTributa() {
        return tributa;
    }

    public void setTributa(Boolean tributa) {
        this.tributa = tributa;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
    
}
