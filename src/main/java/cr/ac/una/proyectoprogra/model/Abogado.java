/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

/**
 *
 * @author vjjcu
 */
public class Abogado {

    private Long aboId;
    private Long aboBufeteId;
    private String aboNombre;
    private String aboCedula;
    private String aboTelefono;
    private String aboCelular;
    private String aboCorreo;
    private String aboDireccion;
    private Boolean aboPropietario;
    private Boolean aboNotario;

    public Abogado() {
    }

    public Abogado(String aboNombre, String aboCedula, String aboTelefono, String aboCelular, String aboCorreo, String aboDireccion, Boolean aboPropietario, Boolean aboNotario) {
        this.aboNombre = aboNombre;
        this.aboCedula = aboCedula;
        this.aboTelefono = aboTelefono;
        this.aboCelular = aboCelular;
        this.aboCorreo = aboCorreo;
        this.aboDireccion = aboDireccion;
        this.aboPropietario = aboPropietario;
        this.aboNotario = aboNotario;
    }

    public Long getAboId() {
        return aboId;
    }

    public void setAboId(Long aboId) {
        this.aboId = aboId;
    }

    public Long getAboBufeteId() {
        return aboBufeteId;
    }

    public void setAboBufeteId(Long aboBufeteId) {
        this.aboBufeteId = aboBufeteId;
    }

    public String getAboNombre() {
        return aboNombre;
    }

    public void setAboNombre(String aboNombre) {
        this.aboNombre = aboNombre;
    }

    public String getAboCedula() {
        return aboCedula;
    }

    public void setAboCedula(String aboCedula) {
        this.aboCedula = aboCedula;
    }

    public String getAboTelefono() {
        return aboTelefono;
    }

    public void setAboTelefono(String aboTelefono) {
        this.aboTelefono = aboTelefono;
    }

    public String getAboCelular() {
        return aboCelular;
    }

    public void setAboCelular(String aboCelular) {
        this.aboCelular = aboCelular;
    }

    public String getAboCorreo() {
        return aboCorreo;
    }

    public void setAboCorreo(String aboCorreo) {
        this.aboCorreo = aboCorreo;
    }

    public String getAboDireccion() {
        return aboDireccion;
    }

    public void setAboDireccion(String aboDireccion) {
        this.aboDireccion = aboDireccion;
    }

    public Boolean getAboPropietario() {
        return aboPropietario;
    }

    public void setAboPropietario(Boolean aboPropietario) {
        this.aboPropietario = aboPropietario;
    }

    public Boolean getAboNotario() {
        return aboNotario;
    }

    public void setAboNotario(Boolean aboNotario) {
        this.aboNotario = aboNotario;
    }
    
    
    
}
