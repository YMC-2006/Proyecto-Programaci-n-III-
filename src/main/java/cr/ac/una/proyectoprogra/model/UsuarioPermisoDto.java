/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author alond
 */
public class UsuarioPermisoDto {
    
    private Long upeId;
    private String upeVista;
    private SimpleBooleanProperty upeConsultar = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty upeCrear = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty upeModificar = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty upeEliminar = new SimpleBooleanProperty(false);

    public UsuarioPermisoDto() {
    }
    
    public UsuarioPermisoDto(String upeVista) {
        this.upeVista = upeVista;
    }

    public Long getUpeId() {
        return upeId;
    }

    public void setUpeId(Long upeId) {
        this.upeId = upeId;
    }

    public String getUpeVista() {
        return upeVista;
    }

    public void setUpeVista(String upeVista) {
        this.upeVista = upeVista;
    }

    public Boolean getUpeConsultar() {
        return upeConsultar.get();
    }

    public void setUpeConsultar(Boolean upeConsultar) {
        this.upeConsultar.set(upeConsultar);
    }

    public SimpleBooleanProperty upeConsultarProperty() {
        return upeConsultar;
    }

    public Boolean getUpeCrear() {
        return upeCrear.get();
    }

    public void setUpeCrear(Boolean upeCrear) {
        this.upeCrear.set(upeCrear);
    }

    public SimpleBooleanProperty upeCrearProperty() {
        return upeCrear;
    }

    public Boolean getUpeModificar() {
        return upeModificar.get();
    }

    public void setUpeModificar(Boolean upeModificar) {
        this.upeModificar.set(upeModificar);
    }

    public SimpleBooleanProperty upeModificarProperty() {
        return upeModificar;
    }

    public Boolean getUpeEliminar() {
        return upeEliminar.get();
    }

    public void setUpeEliminar(Boolean upeEliminar) {
        this.upeEliminar.set(upeEliminar);
    }

    public SimpleBooleanProperty upeEliminarProperty() {
        return upeEliminar;
    }
}
