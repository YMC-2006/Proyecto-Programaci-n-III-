/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.util;

import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.model.UsuarioPermisoDto;
import java.util.List;
/**
 *
 * @author alond
 */

public class Permisos {

    public static boolean tienePermiso(String vista, String tipo) {
        UsuarioDto usuario = (UsuarioDto) AppContext.getInstance().get("Usuario");
        if (usuario.getUsrAdmin()) {
            return true;
        }
        List<UsuarioPermisoDto> permisos = usuario.getUsuarioPermisoList();
        for (int i = 0; i < permisos.size(); i++) {
            UsuarioPermisoDto permiso = permisos.get(i);
            if (permiso.getUpeVista().equals(vista)) {
                if (tipo.equals("Consultar")) {
                    return permiso.getUpeConsultar();
                } else if (tipo.equals("Crear")) {
                    return permiso.getUpeCrear();
                } else if (tipo.equals("Modificar")) {
                    return permiso.getUpeModificar();
                } else if (tipo.equals("Eliminar")) {
                    return permiso.getUpeEliminar();
                }
            }
        }
        return false;
    }
}