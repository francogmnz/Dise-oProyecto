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
    private String nombreEstadoDestino;
    private int Xpos; // Nueva posición X
    private int Ypos; // Nueva posición Y

    public int getXpos() {
        return Xpos;
    }

    public void setXpos(int Xpos) {
        this.Xpos = Xpos;
    }

    public int getYpos() {
        return Ypos;
    }

    public void setYpos(int Ypos) {
        this.Ypos = Ypos;
    }

  

    public String getNombreEstadoDestino() {
        return nombreEstadoDestino;
    }

    public void setNombreEstadoDestino(String nombreEstadoDestino) {
        this.nombreEstadoDestino = nombreEstadoDestino;
    }

    public int getCodEstadoTramite() {
        return codEstadoTramite;
    }

    public void setCodEstadoTramite(int codigoEstadoTramite) {
        this.codEstadoTramite = codigoEstadoTramite;
    }
}
