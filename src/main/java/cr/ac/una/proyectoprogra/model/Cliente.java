/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

/**
 *
 * @author alond
 */
public class Cliente {
    
    private Long cliId;
    private Long cliBufeteId;
    private String cliTipoIdentificacion;
    private String cliIdentificacion;
    private String cliNombre;
    private String cliApellido1;
    private String cliApellido2;
    private String cliEstadoCivil;
    private String cliOcupacion;
    private String cliCelular;
    private String cliCorreo;
    private String cliDireccion;
    private String cliObservaciones;

    public Cliente(Long cliId, Long cliBufeteId, String cliTipoIdentificacion, String cliIdentificacion, String cliNombre, String cliApellido1, String cliApellido2, String cliEstadoCivil, String cliOcupacion, String cliCelular, String cliCorreo, String cliDireccion, String cliObservaciones) {
        this.cliId = cliId;
        this.cliBufeteId = cliBufeteId;
        this.cliTipoIdentificacion = cliTipoIdentificacion;
        this.cliIdentificacion = cliIdentificacion;
        this.cliNombre = cliNombre;
        this.cliApellido1 = cliApellido1;
        this.cliApellido2 = cliApellido2;
        this.cliEstadoCivil = cliEstadoCivil;
        this.cliOcupacion = cliOcupacion;
        this.cliCelular = cliCelular;
        this.cliCorreo = cliCorreo;
        this.cliDireccion = cliDireccion;
        this.cliObservaciones = cliObservaciones;
    }
    
    public Cliente(){
        
    }

    public Long getCliId() {
        return cliId;
    }

    public void setCliId(Long cliId) {
        this.cliId = cliId;
    }

    public Long getCliBufeteId() {
        return cliBufeteId;
    }

    public void setCliBufeteId(Long cliBufeteId) {
        this.cliBufeteId = cliBufeteId;
    }

    public String getCliTipoIdentificacion() {
        return cliTipoIdentificacion;
    }

    public void setCliTipoIdentificacion(String cliTipoIdentificacion) {
        this.cliTipoIdentificacion = cliTipoIdentificacion;
    }

    public String getCliIdentificacion() {
        return cliIdentificacion;
    }

    public void setCliIdentificacion(String cliIdentificacion) {
        this.cliIdentificacion = cliIdentificacion;
    }

    public String getCliNombre() {
        return cliNombre;
    }

    public void setCliNombre(String cliNombre) {
        this.cliNombre = cliNombre;
    }

    public String getCliApellido1() {
        return cliApellido1;
    }

    public void setCliApellido1(String cliApellido1) {
        this.cliApellido1 = cliApellido1;
    }

    public String getCliApellido2() {
        return cliApellido2;
    }

    public void setCliApellido2(String cliApellido2) {
        this.cliApellido2 = cliApellido2;
    }

    public String getCliEstadoCivil() {
        return cliEstadoCivil;
    }

    public void setCliEstadoCivil(String cliEstadoCivil) {
        this.cliEstadoCivil = cliEstadoCivil;
    }

    public String getCliOcupacion() {
        return cliOcupacion;
    }

    public void setCliOcupacion(String cliOcupacion) {
        this.cliOcupacion = cliOcupacion;
    }

    public String getCliCelular() {
        return cliCelular;
    }

    public void setCliCelular(String cliCelular) {
        this.cliCelular = cliCelular;
    }

    public String getCliCorreo() {
        return cliCorreo;
    }

    public void setCliCorreo(String cliCorreo) {
        this.cliCorreo = cliCorreo;
    }

    public String getCliDireccion() {
        return cliDireccion;
    }

    public void setCliDireccion(String cliDireccion) {
        this.cliDireccion = cliDireccion;
    }

    public String getCliObservaciones() {
        return cliObservaciones;
    }

    public void setCliObservaciones(String cliObservaciones) {
        this.cliObservaciones = cliObservaciones;
    }
    
    
    
}
