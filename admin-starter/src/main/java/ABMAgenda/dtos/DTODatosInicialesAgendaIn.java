
package ABMAgenda.dtos;

import ABMAgenda.dtos.DTOConsultor;
import ABMAgenda.dtos.AgendaDTO;
import java.util.ArrayList;
import java.util.List;


public class DTODatosInicialesAgendaIn {
    
    private DTOConsultor dtoConsultor;
    private List<AgendaDTOIn> agendaDTOIn=new ArrayList<>();

    public DTOConsultor getDtoConsultor() {
        return dtoConsultor;
    }

    public void setDtoConsultor(DTOConsultor dtoConsultor) {
        this.dtoConsultor = dtoConsultor;
    }

    public List<AgendaDTOIn> getAgendaDTO() {
        return agendaDTOIn;
    }

    public void setAgendaDTO(List<AgendaDTOIn> agendaDTO) {
        this.agendaDTOIn = agendaDTO;
    }
     public void addAgendaDTO(AgendaDTOIn agendaDTO) {
        this.agendaDTOIn.add(agendaDTO);
    }   

}
