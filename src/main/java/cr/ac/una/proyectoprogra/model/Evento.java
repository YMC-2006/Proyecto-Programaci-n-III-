/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

import java.time.LocalDateTime;

/**
 *
 * @author vjjcu
 */
public class Evento {

    private Long eveId;
    private Long eveInstrumentoId;
    private String eveDetalle;
    private LocalDateTime eveFechaHora;
    private Boolean eveAtendido;    // falso por defecto
    private String eveInstrumentoTipo;
    private String eveInstrumentoEstado;

    public Evento(String eveDetalle, LocalDateTime eveFechaHora, Boolean eveAtendido, String eveInstrumentoTipo, String eveInstrumentoEstado) {
        this.eveDetalle = eveDetalle;
        this.eveFechaHora = eveFechaHora;
        this.eveAtendido = eveAtendido;
        this.eveInstrumentoTipo = eveInstrumentoTipo;
        this.eveInstrumentoEstado = eveInstrumentoEstado;
    }

    public Evento() {
    }

    public Long getEveId() {
        return eveId;
    }

    public void setEveId(Long eveId) {
        this.eveId = eveId;
    }

    public Long getEveInstrumentoId() {
        return eveInstrumentoId;
    }

    public void setEveInstrumentoId(Long eveInstrumentoId) {
        this.eveInstrumentoId = eveInstrumentoId;
    }

    public String getEveDetalle() {
        return eveDetalle;
    }

    public void setEveDetalle(String eveDetalle) {
        this.eveDetalle = eveDetalle;
    }

    public LocalDateTime getEveFechaHora() {
        return eveFechaHora;
    }

    public void setEveFechaHora(LocalDateTime eveFechaHora) {
        this.eveFechaHora = eveFechaHora;
    }

    public Boolean getEveAtendido() {
        return eveAtendido;
    }

    public void setEveAtendido(Boolean eveAtendido) {
        this.eveAtendido = eveAtendido;
    }

    public String getEveInstrumentoTipo() {
        return eveInstrumentoTipo;
    }

    public void setEveInstrumentoTipo(String eveInstrumentoTipo) {
        this.eveInstrumentoTipo = eveInstrumentoTipo;
    }

    public String getEveInstrumentoEstado() {
        return eveInstrumentoEstado;
    }

    public void setEveInstrumentoEstado(String eveInstrumentoEstado) {
        this.eveInstrumentoEstado = eveInstrumentoEstado;
    }
    
    
    

}