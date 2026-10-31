/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

import java.time.LocalDateTime;

/**
 *
 * @author alond
 */
public class ResultadoInstrumento {
    private Long rinId;
    private String rinEscritura; 
    private String rinTipo;
    private LocalDateTime rinFecha; 
    private String rinEstado;
    private String rinParte;

    public ResultadoInstrumento() {
    }

    public ResultadoInstrumento(Long rinId, String rinEscritura, String rinTipo, LocalDateTime rinFecha, String rinEstado, String rinPartes) {
        this.rinId = rinId;
        this.rinEscritura = rinEscritura;
        this.rinTipo = rinTipo;
        this.rinFecha = rinFecha;
        this.rinEstado = rinEstado;
        this.rinParte = rinPartes;
    }

    public Long getRinId() {
        return rinId;
    }

    public void setRinId(Long rinId) {
        this.rinId = rinId;
    }

    public String getRinEscritura() {
        return rinEscritura;
    }

    public void setRinEscritura(String rinEscritura) {
        this.rinEscritura = rinEscritura;
    }

    public String getRinTipo() {
        return rinTipo;
    }

    public void setRinTipo(String rinTipo) {
        this.rinTipo = rinTipo;
    }

    public LocalDateTime getRinFecha() {
        return rinFecha;
    }

    public void setRinFecha(LocalDateTime rinFecha) {
        this.rinFecha = rinFecha;
    }

    public String getRinEstado() {
        return rinEstado;
    }

    public void setRinEstado(String rinEstado) {
        this.rinEstado = rinEstado;
    }

    public String getRinPartes() {
        return rinParte;
    }

    public void setRinPartes(String rinPartes) {
        this.rinParte = rinPartes;
    }   
}
