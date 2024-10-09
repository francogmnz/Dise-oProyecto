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
public class Rol extends Entidad {
    private String nombreRol;
    private Timestamp fechaHoraBajaRol;
   

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Timestamp getFechaHoraBajaRol() {
        return fechaHoraBajaRol;
    }

    public void setFechaHoraBajaRol(Timestamp fechaHoraBajaRol) {
        this.fechaHoraBajaRol = fechaHoraBajaRol;
    }

}
