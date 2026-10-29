/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

import java.io.Serializable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author alond
 */
public class UsuarioDto {
    public SimpleStringProperty usrId;
    public SimpleStringProperty usrBufeteId;
    public SimpleStringProperty usrBufeteNombre;
    public SimpleStringProperty usrTipoIdentificacion;
    public SimpleStringProperty usrIdentificacion;
    public SimpleStringProperty usrNombre;
    public SimpleStringProperty usrApellido1;
    public SimpleStringProperty usrApellido2;
    public SimpleStringProperty usrUsuario;
    public SimpleStringProperty usrClave;
    public SimpleStringProperty usrCorreo;
    public SimpleStringProperty usrIdioma;
    public SimpleStringProperty usrCelular;
    public SimpleBooleanProperty usrActivo;
    public SimpleBooleanProperty usrAdmin;
    public SimpleBooleanProperty usrCambioClave;
    private String token;
    private Boolean modificado;

    public UsuarioDto() {
        this.modificado = false;
        this.usrId = new SimpleStringProperty("");
        this.usrBufeteId = new SimpleStringProperty("");
        this.usrBufeteNombre = new SimpleStringProperty("");
        this.usrTipoIdentificacion = new SimpleStringProperty("");
        this.usrIdentificacion = new SimpleStringProperty("");
        this.usrNombre = new SimpleStringProperty("");
        this.usrApellido1 = new SimpleStringProperty("");
        this.usrApellido2 = new SimpleStringProperty("");
        this.usrUsuario = new SimpleStringProperty("");
        this.usrClave = new SimpleStringProperty("");
        this.usrCorreo = new SimpleStringProperty("");
        this.usrIdioma = new SimpleStringProperty("");
        this.usrCelular = new SimpleStringProperty("");
        this.usrActivo = new SimpleBooleanProperty(false);
        this.usrAdmin = new SimpleBooleanProperty(false);
        this.usrCambioClave = new SimpleBooleanProperty(false);
    }

    public Long getUsrId() {
        if (usrId.get() != null && !usrId.get().isBlank()) {
            return Long.valueOf(usrId.get());
        } else {
            return null;
        }
    }

    public void setUsrId(Long usrId) {
        this.usrId.set(usrId.toString());
    }

    public Long getUsrBufeteId() {
        if (usrBufeteId.get() != null && !usrBufeteId.get().isBlank()) {
            return Long.valueOf(usrBufeteId.get());
        } else {
            return null;
        }
    }

    public void setUsrBufeteId(Long usrBufeteId) {
        this.usrBufeteId.set(usrBufeteId.toString());
    }

    public String getUsrBufeteNombre() {
        return usrBufeteNombre.get();
    }

    public void setUsrBufeteNombre(String usrBufeteNombre) {
        this.usrBufeteNombre.set(usrBufeteNombre);
    }

    public String getUsrTipoIdentificacion() {
        return usrTipoIdentificacion.get();
    }

    public void setUsrTipoIdentificacion(String usrTipoIdentificacion) {
        this.usrTipoIdentificacion.set(usrTipoIdentificacion);
    }

    public String getUsrIdentificacion() {
        return usrIdentificacion.get();
    }

    public void setUsrIdentificacion(String usrIdentificacion) {
        this.usrIdentificacion.set(usrIdentificacion);
    }

    public String getUsrNombre() {
        return usrNombre.get();
    }

    public void setUsrNombre(String usrNombre) {
        this.usrNombre.set(usrNombre);
    }

    public String getUsrApellido1() {
        return usrApellido1.get();
    }

    public void setUsrApellido1(String usrApellido1) {
        this.usrApellido1.set(usrApellido1);
    }

    public String getUsrApellido2() {
        return usrApellido2.get();
    }

    public void setUsrApellido2(String usrApellido2) {
        this.usrApellido2.set(usrApellido2);
    }

    public String getUsrUsuario() {
        return usrUsuario.get();
    }

    public void setUsrUsuario(String usrUsuario) {
        this.usrUsuario.set(usrUsuario);
    }

    public String getUsrClave() {
        return usrClave.get();
    }

    public void setUsrClave(String usrClave) {
        this.usrClave.set(usrClave);
    }

    public String getUsrCorreo() {
        return usrCorreo.get();
    }

    public void setUsrCorreo(String usrCorreo) {
        this.usrCorreo.set(usrCorreo);
    }

    public String getUsrIdioma() {
        return usrIdioma.get();
    }

    public void setUsrIdioma(String usrIdioma) {
        this.usrIdioma.set(usrIdioma);
    }

    public String getUsrCelular() {
        return usrCelular.get();
    }

    public void setUsrCelular(String usrCelular) {
        this.usrCelular.set(usrCelular);
    }

    public Boolean getUsrActivo() {
        return usrActivo.get();
    }

    public void setUsrActivo(Boolean usrActivo) {
        this.usrActivo.set(usrActivo);
    }

    public Boolean getUsrAdmin() {
        return usrAdmin.get();
    }

    public void setUsrAdmin(Boolean usrAdmin) {
        this.usrAdmin.set(usrAdmin);
    }

    public Boolean getUsrCambioClave() {
        return usrCambioClave.get();
    }

    public void setUsrCambioClave(Boolean usrCambioClave) {
        this.usrCambioClave.set(usrCambioClave);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    } 
    
    private byte[] usrFoto;

    public byte[] getUsrFoto() {
        return usrFoto;
    }

    public void setUsrFoto(byte[] usrFoto) {
        this.usrFoto = usrFoto;
    }
}
