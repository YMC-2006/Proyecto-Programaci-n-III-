/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vjjcu
 */
public class InstrumentoParte {
    
    private Long ipaId;
    private String ipaRol;
    private Boolean ipaDebePagar = Boolean.FALSE;
    private BigDecimal ipaMonto = BigDecimal.ZERO;
    private List<PartePago> partePagoList = new ArrayList<>();
    private Long ipaClienteId;
    private Long ipaSociedadId;
    private Long ipaInstrumentoId;
    private String ipaNombre;
    private String ipaIdentificacion;
    private BigDecimal ipaTotalPagado = BigDecimal.ZERO;
    private BigDecimal ipaSaldo = BigDecimal.ZERO;

    public InstrumentoParte(Long ipaId, String ipaRol, Long ipaClienteId, Long ipaSociedadId, Long ipaInstrumentoId, String ipaNombre, String ipaIdentificacion) {
        this.ipaId = ipaId;
        this.ipaRol = ipaRol;
        this.ipaClienteId = ipaClienteId;
        this.ipaSociedadId = ipaSociedadId;
        this.ipaInstrumentoId = ipaInstrumentoId;
        this.ipaNombre = ipaNombre;
        this.ipaIdentificacion = ipaIdentificacion;
    }

   
    public InstrumentoParte() {
    }

    public Long getIpaId() {
        return ipaId;
    }

    public void setIpaId(Long ipaId) {
        this.ipaId = ipaId;
    }

    public String getIpaRol() {
        return ipaRol;
    }

    public void setIpaRol(String ipaRol) {
        this.ipaRol = ipaRol;
    }

    public Boolean getIpaDebePagar() {
        return ipaDebePagar;
    }

    public void setIpaDebePagar(Boolean ipaDebePagar) {
        this.ipaDebePagar = ipaDebePagar;
    }

    public BigDecimal getIpaMonto() {
        return ipaMonto;
    }

    public void setIpaMonto(BigDecimal ipaMonto) {
        this.ipaMonto = ipaMonto;
    }

    public List<PartePago> getPartePagoList() {
        return partePagoList;
    }

    public void setPartePagoList(List<PartePago> partePagoList) {
        this.partePagoList = partePagoList;
    }

    public Long getIpaClienteId() {
        return ipaClienteId;
    }

    public void setIpaClienteId(Long ipaClienteId) {
        this.ipaClienteId = ipaClienteId;
    }

    public Long getIpaSociedadId() {
        return ipaSociedadId;
    }

    public void setIpaSociedadId(Long ipaSociedadId) {
        this.ipaSociedadId = ipaSociedadId;
    }

    public Long getIpaInstrumentoId() {
        return ipaInstrumentoId;
    }

    public void setIpaInstrumentoId(Long ipaInstrumentoId) {
        this.ipaInstrumentoId = ipaInstrumentoId;
    }

    public String getIpaNombre() {
        return ipaNombre;
    }

    public void setIpaNombre(String ipaNombre) {
        this.ipaNombre = ipaNombre;
    }

    public String getIpaIdentificacion() {
        return ipaIdentificacion;
    }

    public void setIpaIdentificacion(String ipaIdentificacion) {
        this.ipaIdentificacion = ipaIdentificacion;
    }

    public BigDecimal getIpaTotalPagado() {
        return ipaTotalPagado;
    }

    public void setIpaTotalPagado(BigDecimal ipaTotalPagado) {
        this.ipaTotalPagado = ipaTotalPagado;
    }

    public BigDecimal getIpaSaldo() {
        return ipaSaldo;
    }

    public void setIpaSaldo(BigDecimal ipaSaldo) {
        this.ipaSaldo = ipaSaldo;
    }
    
    
}
