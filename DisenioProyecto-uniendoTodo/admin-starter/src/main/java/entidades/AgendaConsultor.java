package entidades;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author licciardi
 */
public class AgendaConsultor extends Entidad {
    
    //private int codAgendaConsultor;
    //private Timestamp fechaAgenda;
    //private Timestamp fechaAltaAgendaConsultor;
    //private Timestamp fechaBajaAgendaConsultor;
    private List<Consultor> consultores = new ArrayList<>();
    private int mesAgendaConsultor;
    private int añoAgendaConsultor;
    public int semAgendaConsultor;
    private Date fechaDesdeSemana;
    private Date fechaHastaSemana;

    public AgendaConsultor() {
    }
    
    public int getMesAgendaConsultor() {
        return mesAgendaConsultor;
    }
    
    public void setMesAgendaConsultor(int mesAgendaConsultor) {
        this.mesAgendaConsultor = mesAgendaConsultor;
    }

    public int getAñoAgendaConsultor() {
        return añoAgendaConsultor;
    }
    public void setAñoAgendaConsultor(int añoAgendaConsultor) {
        this.añoAgendaConsultor= añoAgendaConsultor;
    }
    
    public Date getFechaDesdeSemana() {
        return fechaDesdeSemana;
    }

    public void setFechaDesdeSemana(Date fechaDesdeSemana) {
        this.fechaDesdeSemana = fechaDesdeSemana;
    }

    public Date getFechaHastaSemana() {
        return fechaHastaSemana;
    }

    public void setFechaHastaSemana(Date fechaHastaSemana) {
        this.fechaHastaSemana = fechaHastaSemana;
    }
/*
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
*/
    public List<Consultor> getConsultores() {
        return consultores;
    }

    public void setConsultores(List<Consultor> consultores) {
        this.consultores = consultores;
    }

    public void addConsultor(Consultor consultor) {
        consultores.add(consultor);   

   }

    public int getSemAgendaConsultor() {
        return semAgendaConsultor;
    }

    public void setSemAgendaConsultor(int semAgendaConsultor) {
        this.semAgendaConsultor = semAgendaConsultor;
    }
    
   
    
}
