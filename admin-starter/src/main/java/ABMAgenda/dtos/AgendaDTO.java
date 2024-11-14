package ABMAgenda.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgendaDTO {

    //private int codAgendaConsultor;
    private List<DTOConsultor> consultores;
    private int mesAgendaConsultor;
    private int añoAgendaConsultor;
    private int semAgendaConsultor;
    private Date fechaDesdeSemana;
    private Date fechaHastaSemana;

   /* public int getCodAgendaConsultor() {
        return codAgendaConsultor;
    }*/
    /*
    public void setCodAgendaConsultor(int codAgendaConsultor) {
        this.codAgendaConsultor = codAgendaConsultor;
    }*/

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
        this.añoAgendaConsultor = añoAgendaConsultor;
    }
    
    public List<DTOConsultor> getConsultores() { // Cambiado a List<DTOConsultor>
        return consultores;
    }

    public void setConsultores(List<DTOConsultor> consultores) { // Cambiado a List<DTOConsultor>
        this.consultores = consultores;
    }

    public int getSemAgendaConsultor() {
        return semAgendaConsultor;
    }

    public void setSemAgendaConsultor(int semAgendaConsultor) {
        this.semAgendaConsultor = semAgendaConsultor;
    }
    public void addDTOConsultor(DTOConsultor consultor) {
      if (this.consultores == null) {
            this.consultores = new ArrayList<>();
        }
      this.consultores.add(consultor);
    }
}
