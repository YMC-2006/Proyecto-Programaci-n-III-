/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

/**
 *
 * @author vjjcu
 */
public class InstrumentoRequisito {
    
    private Long irqId;
    private Long irqInstrumentoId;
    private Long irqRequisitoId;
    private String irqEstado;
    private String irqDescripcion;
    private int irqOrden;

    public InstrumentoRequisito() {
    }

    public InstrumentoRequisito(Long irqId, Long irqInstrumentoId, Long irqRequisitoId, String irqEstado, String irqDescripcion, int irqOrden) {
        this.irqId = irqId;
        this.irqInstrumentoId = irqInstrumentoId;
        this.irqRequisitoId = irqRequisitoId;
        this.irqEstado = irqEstado;
        this.irqDescripcion = irqDescripcion;
        this.irqOrden = irqOrden;
    }

    public Long getIrqId() {
        return irqId;
    }

    public void setIrqId(Long irqId) {
        this.irqId = irqId;
    }

    public Long getIrqInstrumentoId() {
        return irqInstrumentoId;
    }

    public void setIrqInstrumentoId(Long irqInstrumentoId) {
        this.irqInstrumentoId = irqInstrumentoId;
    }

    public Long getIrqRequisitoId() {
        return irqRequisitoId;
    }

    public void setIrqRequisitoId(Long irqRequisitoId) {
        this.irqRequisitoId = irqRequisitoId;
    }

    public String getIrqEstado() {
        return irqEstado;
    }

    public void setIrqEstado(String irqEstado) {
        this.irqEstado = irqEstado;
    }

    public String getIrqDescripcion() {
        return irqDescripcion;
    }

    public void setIrqDescripcion(String irqDescripcion) {
        this.irqDescripcion = irqDescripcion;
    }

    public int getIrqOrden() {
        return irqOrden;
    }

    public void setIrqOrden(int irqOrden) {
        this.irqOrden = irqOrden;
    }
    
    
}
