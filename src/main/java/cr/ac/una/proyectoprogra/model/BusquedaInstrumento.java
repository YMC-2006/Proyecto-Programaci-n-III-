/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

/**
 *
 * @author alond
 */
public class BusquedaInstrumento {
    
    private String binCondicion;
    private String binCampo;
    private String binComparacion;
    private String binRango1;
    private String binRango2;

    public BusquedaInstrumento() {
        
    }

    public BusquedaInstrumento(String binCondicion, String binCampo, String binComparacion, String binRango1, String binRango2) {
        this.binCondicion = binCondicion;
        this.binCampo = binCampo;
        this.binComparacion = binComparacion;
        this.binRango1 = binRango1;
        this.binRango2 = binRango2;
    }

    public String getBinCondicion() {
        return binCondicion;
    }

    public void setBinCondicion(String binCondicion) {
        this.binCondicion = binCondicion;
    }

    public String getBinCampo() {
        return binCampo;
    }

    public void setBinCampo(String binCampo) {
        this.binCampo = binCampo;
    }

    public String getBinComparacion() {
        return binComparacion;
    }

    public void setBinComparacion(String binComparacion) {
        this.binComparacion = binComparacion;
    }

    public String getBinRango1() {
        return binRango1;
    }

    public void setBinRango1(String binRango1) {
        this.binRango1 = binRango1;
    }

    public String getBinRango2() {
        return binRango2;
    }

    public void setBinRango2(String binRango2) {
        this.binRango2 = binRango2;
    }    
     
}