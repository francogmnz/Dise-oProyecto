package ABMAgenda.dtos;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class AgendaDTOIn {

    //private int codAgendaConsultor;
    private List<DTOConsultorIn> consultores;
    private int mesAgendaConsultor;
    private int añoAgendaConsultor;
    private int semAgendaConsultor;
    private Date fechaDesdeSemana;
    private Date fechaHastaSemana;

  /*public int getCodAgendaConsultor() {
        return codAgendaConsultor;
    }
    
    public void setCodAgendaConsultor(int codAgendaConsultor) {
        this.codAgendaConsultor = codAgendaConsultor;
    }*/
    
    public int getMesAgendaConsultor() {
        return mesAgendaConsultor;
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
    
    public void setMesAgendaConsultor(int mesAgendaConsultor) {
        this.mesAgendaConsultor = mesAgendaConsultor;
    }

    public int getAñoAgendaConsultor() {
        return añoAgendaConsultor;
    }
    
    public void setAñoAgendaConsultor(int añoAgendaConsultor) {
        this.añoAgendaConsultor = añoAgendaConsultor;
    }

    public List<DTOConsultorIn> getConsultores() { // Cambiado a List<DTOConsultor>
        return consultores;
    }

    public void setConsultores(List<DTOConsultorIn> consultores) { // Cambiado a List<DTOConsultor>
        this.consultores = consultores;
    }

    public int getSemAgendaConsultor() {
        return semAgendaConsultor;
    }

    public void setSemAgendaConsultor(int semAgendaConsultor) {
        this.semAgendaConsultor = semAgendaConsultor;
    }
}
