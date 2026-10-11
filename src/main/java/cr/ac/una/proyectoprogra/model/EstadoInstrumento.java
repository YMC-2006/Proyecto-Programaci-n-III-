/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

/**
 *
 * @author vjjcu
 */
public class EstadoInstrumento {
    
    private Long einId;
    private Long aboBufeteId;
    private String einDescripcion;
    private Boolean einActivo;
    private Boolean einNotifica;
    private Boolean einFinal;

    public EstadoInstrumento(String einDescripcion, Boolean einActivo, Boolean einNotifica, Boolean einFinal) {
        this.einDescripcion = einDescripcion;
        this.einActivo = einActivo;
        this.einNotifica = einNotifica;
        this.einFinal = einFinal;
    }

    public Long getEinId() {
        return einId;
    }

    public void setEinId(Long einId) {
        this.einId = einId;
    }

    public Long getAboBufeteId() {
        return aboBufeteId;
    }

    public void setAboBufeteId(Long aboBufeteId) {
        this.aboBufeteId = aboBufeteId;
    }
    
    

    public String getEinDescripcion() {
        return einDescripcion;
    }

    public void setEinDescripcion(String einDescripcion) {
        this.einDescripcion = einDescripcion;
    }

    public Boolean getEinActivo() {
        return einActivo;
    }

    public void setEinActivo(Boolean einActivo) {
        this.einActivo = einActivo;
    }

    public Boolean getEinNotifica() {
        return einNotifica;
    }

    public void setEinNotifica(Boolean einNotifica) {
        this.einNotifica = einNotifica;
    }

    public Boolean getEinFinal() {
        return einFinal;
    }

    public void setEinFinal(Boolean einFinal) {
        this.einFinal = einFinal;
    }

    
    
}
