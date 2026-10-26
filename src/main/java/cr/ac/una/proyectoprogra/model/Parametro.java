/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

/**
 *
 * @author vjjcu
 */
public class Parametro {

    private Long parId;
    private String parCodigo;
    private String parValor;
    private String parDescripcion;
    private Long parBufeteId;

    public Parametro() {
    }

    public Parametro(Long parId, String parCodigo, String parValor, String parDescripcion, Long parBufeteId) {
        this.parId = parId;
        this.parCodigo = parCodigo;
        this.parValor = parValor;
        this.parDescripcion = parDescripcion;
        this.parBufeteId = parBufeteId;
    }

    public Long getParId() {
        return parId;
    }

    public void setParId(Long parId) {
        this.parId = parId;
    }

    public String getParCodigo() {
        return parCodigo;
    }

    public void setParCodigo(String parCodigo) {
        this.parCodigo = parCodigo;
    }

    public String getParValor() {
        return parValor;
    }

    public void setParValor(String parValor) {
        this.parValor = parValor;
    }

    public String getParDescripcion() {
        return parDescripcion;
    }

    public void setParDescripcion(String parDescripcion) {
        this.parDescripcion = parDescripcion;
    }

    public Long getParBufeteId() {
        return parBufeteId;
    }

    public void setParBufeteId(Long parBufeteId) {
        this.parBufeteId = parBufeteId;
    }
    
    
    
}
