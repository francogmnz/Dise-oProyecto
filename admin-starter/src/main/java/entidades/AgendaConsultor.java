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
public class AgendaConsultor extends Entidad {
    
    private int codAgendaConsultor;
    private Timestamp fechaAgenda;
    private Timestamp fechaAltaAgendaConsultor;
    private Timestamp fechaBajaAgendaConsultor;
    private List<Consultor> consultores;

    public AgendaConsultor() {
    }

    public int getCodAgendaConsultor() {
        return codAgendaConsultor;
    }

    public void setCodAgendaConsultor(int codAgendaConsultor) {
        this.codAgendaConsultor = codAgendaConsultor;
    }

    public Timestamp getFechaAgenda() {
        return fechaAgenda;
    }

    public void setFechaAgenda(Timestamp fechaAgenda) {
        this.fechaAgenda = fechaAgenda;
    }

    public Timestamp getFechaAltaAgendaConsultor() {
        return fechaAltaAgendaConsultor;
    }

    public void setFechaAltaAgendaConsultor(Timestamp fechaAltaAgendaConsultor) {
        this.fechaAltaAgendaConsultor = fechaAltaAgendaConsultor;
    }

    public Timestamp getFechaBajaAgendaConsultor() {
        return fechaBajaAgendaConsultor;
    }

    public void setFechaBajaAgendaConsultor(Timestamp fechaBajaAgendaConsultor) {
        this.fechaBajaAgendaConsultor = fechaBajaAgendaConsultor;
    }

    public List<Consultor> getConsultores() {
        return consultores;
    }

    public void setConsultores(List<Consultor> consultores) {
        this.consultores = consultores;
    }



    
    
}
