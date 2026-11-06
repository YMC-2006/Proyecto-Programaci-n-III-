/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
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
    private String numRecibo;
    private BigDecimal montoReal;
    private BigDecimal montoFacturado;

    public ContableDto() {
    }

    public ContableDto(String tipo, String detalle, LocalDate fecha, BigDecimal monto, String factura, Boolean tributa, String origen, String numRecibo, BigDecimal montoReal, BigDecimal montoFacturado) {
        this.tipo = tipo;
        this.detalle = detalle;
        this.fecha = fecha;
        if(monto != null){
            this.monto = monto;
        }else{
            this.monto = new BigDecimal(0);
        }
        if(factura != null){
            this.factura = factura;
        }else{
            this.factura = "No aplica";
        }
        this.tributa = tributa;
        this.origen = origen;
        if(tributa && numRecibo != null){
            this.numRecibo = numRecibo;
        }else{
            this.numRecibo = "No aplica";
        }
        
        if(montoReal != null){
            this.montoReal = montoReal;
        }else{
            this.montoReal = new BigDecimal(0);
        }
        
        if(montoFacturado != null){
            this.montoFacturado = montoFacturado;
        }else{
            this.montoFacturado = new BigDecimal(0);
        }
        
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

    public String getNumRecibo() {
        return numRecibo;
    }

    public void setNumRecibo(String numRecibo) {
        this.numRecibo = numRecibo;
    }

    public BigDecimal getMontoReal() {
        return montoReal;
    }

    public void setMontoReal(BigDecimal montoReal) {
        this.montoReal = montoReal;
    }

    public BigDecimal getMontoFacturado() {
        return montoFacturado;
    }

    public void setMontoFacturado(BigDecimal montoFacturado) {
        this.montoFacturado = montoFacturado;
    }
    
}
