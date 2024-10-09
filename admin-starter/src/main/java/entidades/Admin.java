/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.sql.Timestamp;

/**
 *
 * @author licciardi
 */
public class Admin extends Entidad{
    
    private int legajoAdmin;
    private String nombreAdmin;
    private Timestamp fechaHoraBajaAdmin;
    
    private Usuario usuario;

    public int getLegajoAdmin() {
        return legajoAdmin;
    }

    public void setLegajoAdmin(int legajoAdmin) {
        this.legajoAdmin = legajoAdmin;
    }

    public String getNombreAdmin() {
        return nombreAdmin;
    }

    public void setNombreAdmin(String nombreAdmin) {
        this.nombreAdmin = nombreAdmin;
    }

    public Timestamp getFechaHoraBajaAdmin() {
        return fechaHoraBajaAdmin;
    }

    public void setFechaHoraBajaAdmin(Timestamp fechaHoraBajaAdmin) {
        this.fechaHoraBajaAdmin = fechaHoraBajaAdmin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
