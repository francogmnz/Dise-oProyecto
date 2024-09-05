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
public class Consultor extends Entidad {
    
    private int legajoConsultor;
    private String nombreConsultor;
    private int numMaximoTramites;
    private Timestamp fechaAltaConsultor;
    private Timestamp fechaBajaConsultor;
    private List<AgendaConsultor> agendas;  // Relación ManyToMany No se si va..
    

    public Consultor() {
    }

    public int getLegajoConsultor() {
        return legajoConsultor;
    }

    public void setLegajoConsultor(int legajoConsultor) {
        this.legajoConsultor = legajoConsultor;
    }

    public String getNombreConsultor() {
        return nombreConsultor;
    }

    public void setNombreConsultor(String nombreConsultor) {
        this.nombreConsultor = nombreConsultor;
    }

    public int getNumMaximoTramites() {
        return numMaximoTramites;
    }

    public void setNumMaximoTramites(int numMaximoTramites) {
        this.numMaximoTramites = numMaximoTramites;
    }

    public Timestamp getFechaBajaConsultor() {
        return fechaBajaConsultor;
    }

    public void setFechaBajaConsultor(Timestamp fechaBajaConsultor) {
        this.fechaBajaConsultor = fechaBajaConsultor;
    }

    public Timestamp getFechaAltaConsultor() {
        return fechaAltaConsultor;
    }

    public void setFechaAltaConsultor(Timestamp fechaAltaConsultor) {
        this.fechaAltaConsultor = fechaAltaConsultor;
    }
    

    public List<AgendaConsultor> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<AgendaConsultor> agendas) {
        this.agendas = agendas;
    }
  
}
