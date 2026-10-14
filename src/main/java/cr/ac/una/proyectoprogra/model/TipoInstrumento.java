/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

import java.util.Objects;

/**
 *
 * @author vjjcu
 */
public class TipoInstrumento {

    private Long tinId;
    private String tinNombre;
    private Boolean tinEstado;
    private String rutaMachote; // agregar al cons?
    private Long tinBufeteId;
    
    public TipoInstrumento(String nombre, Boolean estado){
        this.tinNombre = nombre;
        this.tinEstado = estado;
    }

    public Long getTinId() {
        return tinId;
    }

    public void setTinId(Long tinId) {
        this.tinId = tinId;
    }

    public String getTinNombre() {
        return tinNombre;
    }

    public void setTinNombre(String tinNombre) {
        this.tinNombre = tinNombre;
    }

    public Boolean getTinEstado() {
        return tinEstado;
    }

    public void setTinEstado(Boolean tinEstado) {
        this.tinEstado = tinEstado;
    }

    public Long getTinBufeteId() {
        return tinBufeteId;
    }

    public void setTinBufeteId(Long tinBufeteId) {
        this.tinBufeteId = tinBufeteId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoInstrumento other = (TipoInstrumento) obj;
        if (!Objects.equals(this.tinId, other.tinId)) {
            return false;
        }
        return Objects.equals(this.tinBufeteId, other.tinBufeteId);
    }
    
    
    
    
    
}
