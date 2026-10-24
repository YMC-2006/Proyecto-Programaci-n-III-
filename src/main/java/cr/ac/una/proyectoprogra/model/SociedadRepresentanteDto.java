/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

/**
 *
 * @author Nikole
 */
public class SociedadRepresentanteDto {
    private Long sreId;
    private String sreCargo;
    private Long sreClienteId;
    private Long sreSociedadId;
    private String sreNombre;
    private String sreCedula;

    public SociedadRepresentanteDto() {
    }

    public SociedadRepresentanteDto(Long sreId, String sreCargo, Long sreClienteId, Long sreSociedadId, String sreNombre, String sreCedula) {
        this.sreId = sreId;
        this.sreCargo = sreCargo;
        this.sreClienteId = sreClienteId;
        this.sreSociedadId = sreSociedadId;
        this.sreNombre = sreNombre;
        this.sreCedula = sreCedula;
    }

    public Long getSreId() {
        return sreId;
    }

    public void setSreId(Long sreId) {
        this.sreId = sreId;
    }

    public String getSreCargo() {
        return sreCargo;
    }

    public void setSreCargo(String sreCargo) {
        this.sreCargo = sreCargo;
    }

    public Long getSreClienteId() {
        return sreClienteId;
    }

    public void setSreClienteId(Long sreClienteId) {
        this.sreClienteId = sreClienteId;
    }

    public Long getSreSociedadId() {
        return sreSociedadId;
    }

    public void setSreSociedadId(Long sreSociedadId) {
        this.sreSociedadId = sreSociedadId;
    }

    public String getSreNombre() {
        return sreNombre;
    }

    public void setSreNombre(String sreNombre) {
        this.sreNombre = sreNombre;
    }

    public String getSreCedula() {
        return sreCedula;
    }

    public void setSreCedula(String sreCedula) {
        this.sreCedula = sreCedula;
    }
    
}
