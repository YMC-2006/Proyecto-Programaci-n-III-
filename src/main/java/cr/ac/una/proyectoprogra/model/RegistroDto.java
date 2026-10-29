/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

/**
 *
 * @author alond
 */
public class RegistroDto {
    
    private BufeteDto bufete;
    private UsuarioDto usuario;

    public RegistroDto() {
    }

    public BufeteDto getBufete() {
        return bufete;
    }

    public void setBufete(BufeteDto bufete) {
        this.bufete = bufete;
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }
    
}
