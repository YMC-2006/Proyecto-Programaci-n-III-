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
public class PartePago {

    private Long ppaId;
    private String ppaDetalle;
    private LocalDate ppaFecha;
    private String ppaFactura;
    private BigDecimal ppaMonto;
    private Long ppaParteId;

    public PartePago(String ppaDetalle, LocalDate ppaFecha, String ppaFactura, BigDecimal ppaMonto) {
        this.ppaDetalle = ppaDetalle;
        this.ppaFecha = ppaFecha;
        this.ppaFactura = ppaFactura;
        this.ppaMonto = ppaMonto;
    }

    public PartePago() {
    }

    public Long getPpaId() {
        return ppaId;
    }

    public void setPpaId(Long ppaId) {
        this.ppaId = ppaId;
    }

    public String getPpaDetalle() {
        return ppaDetalle;
    }

    public void setPpaDetalle(String ppaDetalle) {
        this.ppaDetalle = ppaDetalle;
    }

    public LocalDate getPpaFecha() {
        return ppaFecha;
    }

    public void setPpaFecha(LocalDate ppaFecha) {
        this.ppaFecha = ppaFecha;
    }

    public String getPpaFactura() {
        return ppaFactura;
    }

    public void setPpaFactura(String ppaFactura) {
        this.ppaFactura = ppaFactura;
    }

    public BigDecimal getPpaMonto() {
        return ppaMonto;
    }

    public void setPpaMonto(BigDecimal ppaMonto) {
        this.ppaMonto = ppaMonto;
    }

    public Long getPpaParteId() {
        return ppaParteId;
    }

    public void setPpaParteId(Long ppaParteId) {
        this.ppaParteId = ppaParteId;
    }
    
    
    

    
    

}
