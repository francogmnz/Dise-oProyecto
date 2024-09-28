/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMVersion.dtos;

/**
 *
 * @author Franco
 */
public class DTOEstadoDestinoOUT {
    int codEstadoTramite;
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

      public int getCodEstadoTramite() {
        return codEstadoTramite;
    }

    public void setCodEstadoTramite(int codigoEstadoTramite) {
        this.codEstadoTramite = codigoEstadoTramite;
    }
}
