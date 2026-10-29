/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alond
 */
public class BufeteDto implements Serializable{
    
    public SimpleStringProperty bufId;
    public SimpleStringProperty bufNombre;
    public SimpleStringProperty bufTelefono;
    public SimpleStringProperty bufEmail;
    public SimpleStringProperty bufDireccion;
    private Boolean modificado;

    public BufeteDto() {
        this.modificado = false;
        this.bufId = new SimpleStringProperty("");
        this.bufNombre = new SimpleStringProperty("");
        this.bufTelefono = new SimpleStringProperty("");
        this.bufEmail = new SimpleStringProperty("");
        this.bufDireccion = new SimpleStringProperty("");
    }

    public BufeteDto(SimpleStringProperty bufId, SimpleStringProperty bufNombre, SimpleStringProperty bufTelefono, SimpleStringProperty bufEmail, SimpleStringProperty bufDireccion, Boolean modificado) {
        this.bufId = bufId;
        this.bufNombre = bufNombre;
        this.bufTelefono = bufTelefono;
        this.bufEmail = bufEmail;
        this.bufDireccion = bufDireccion;
        this.modificado = modificado;
    }

    public Long getBufId() {
        if (bufId.get() != null && !bufId.get().isBlank()) {
            return Long.valueOf(bufId.get());
        } else {
            return null;
        }
    }

    public void setBufId(Long bufId) {
        this.bufId.set(bufId.toString());
    }

    public String getBufNombre() {
        return bufNombre.get();
    }

    public void setBufNombre(String bufNombre) {
        this.bufNombre.set(bufNombre);
    }

    public String getBufTelefono() {
        return bufTelefono.get();
    }

    public void setBufTelefono(String bufTelefono) {
        this.bufTelefono.set(bufTelefono);
    }

    public String getBufEmail() {
        return bufEmail.get();
    }

    public void setBufEmail(String bufEmail) {
        this.bufEmail.set(bufEmail);
    }

    public String getBufDireccion() {
        return bufDireccion.get();
    }

    public void setBufDireccion(String bufDireccion) {
        this.bufDireccion.set(bufDireccion);
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }
    
    private byte[] bufLogo;

    public byte[] getBufLogo() {
        return bufLogo;
    }

    public void setBufLogo(byte[] bufLogo) {
        this.bufLogo = bufLogo;
    }

    @Override
    public String toString() {
        return bufNombre.get();
    }
 
}
