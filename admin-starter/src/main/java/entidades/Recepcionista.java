/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author licciardi
 */
public class Recepcionista extends Entidad{
    
    private int legajoRecepcionista;
    private String nombreRecepcionista;
    private Timestamp fechaHoraBajaRecepcionista;
    
    private Usuario usuario;

    public int getLegajoRecepcionista() {
        return legajoRecepcionista;
    }

    public void setLegajoRecepcionista(int legajoRecepcionista) {
        this.legajoRecepcionista = legajoRecepcionista;
    }

    public String getNombreRecepcionista() {
        return nombreRecepcionista;
    }

    public void setNombreRecepcionista(String nombreRecepcionista) {
        this.nombreRecepcionista = nombreRecepcionista;
    }

    public Timestamp getFechaHoraBajaRecepcionista() {
        return fechaHoraBajaRecepcionista;
    }

    public void setFechaHoraBajaRecepcionista(Timestamp fechaHoraBajaRecepcionista) {
        this.fechaHoraBajaRecepcionista = fechaHoraBajaRecepcionista;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
}
