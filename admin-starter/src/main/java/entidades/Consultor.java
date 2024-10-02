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
    private int nroMaximoTramites;
    private Timestamp fechaHoraBajaConsultor;
    private List<AgendaConsultor> agendas;  
    

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

    public int getNroMaximoTramites() {
        return nroMaximoTramites;
    }

    public void setNroMaximoTramites(int nroMaximoTramites) {
        this.nroMaximoTramites = nroMaximoTramites;
    }

    public Timestamp getFechaHoraBajaConsultor() {
        return fechaHoraBajaConsultor;
    }

    public void setFechaHoraBajaConsultor(Timestamp fechaHoraBajaConsultor) {
        this.fechaHoraBajaConsultor = fechaHoraBajaConsultor;
    }

    public List<AgendaConsultor> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<AgendaConsultor> agendas) {
        this.agendas = agendas;
    }
  
}
