/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

/**
 *
 * @author vjjcu
 */
public class TipoInstRequisito {

    private Long trqId;
    private String trqDescripcion;
    private int trqOrden;
    private Long trqTipoId;
    
    public TipoInstRequisito(){
        
    }

    public TipoInstRequisito(String trqDescripcion) {
        this.trqDescripcion = trqDescripcion;
    }
    
    public Long getTrqId() {
        return trqId;
    }

    public void setTrqId(Long trqId) {
        this.trqId = trqId;
    }

    public String getTrqDescripcion() {
        return trqDescripcion;
    }

    public void setTrqDescripcion(String trqDescripcion) {
        this.trqDescripcion = trqDescripcion;
    }

    public int getTrqOrden() {
        return trqOrden;
    }

    public void setTrqOrden(int trqOrden) {
        this.trqOrden = trqOrden;
    }

    public Long getTrqTipoId() {
        return trqTipoId;
    }

    public void setTrqTipoId(Long trqTipoId) {
        this.trqTipoId = trqTipoId;
    }
    
    @Override
    public String toString(){
        return trqDescripcion;    // para la listview que pueda mostrar bien el texto del tipo inst requisito
    }

}
