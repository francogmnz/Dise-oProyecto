package entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgendaConsultor extends Entidad {

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
