
package ABMAgenda.dtos;

import ABMAgenda.dtos.DTOConsultor;
import ABMAgenda.dtos.DTOConsultorListaIzq;
import ABMAgenda.dtos.AgendaDTO;
import java.util.ArrayList;
import java.util.List;


public class DTODatosInicialesAgenda {
    
    private DTOConsultor dtoConsultor;
    private List<DTOConsultorListaIzq> dtoConsultorListaIzq;
    private List<AgendaDTO> agendaDTO=new ArrayList<>();

    public DTOConsultor getDtoConsultor() {
        return dtoConsultor;
    }

    public void setDtoConsultor(DTOConsultor dtoConsultor) {
        this.dtoConsultor = dtoConsultor;
    }

    public List<DTOConsultorListaIzq> getDtoConsultorListaIzq() {
        return dtoConsultorListaIzq;
    }

    public void setDtoConsultorListaIzq(List<DTOConsultorListaIzq> dtoConsultorListaIzq) {
        this.dtoConsultorListaIzq = dtoConsultorListaIzq;
    }
    public void addDtoConsultorListaIzq(DTOConsultorListaIzq dtoConsultorListaIzq) {
        this.dtoConsultorListaIzq.add(dtoConsultorListaIzq);
    }
    public List<AgendaDTO> getAgendaDTO() {
        return agendaDTO;
    }

    public void setAgendaDTO(List<AgendaDTO> agendaDTO) {
        this.agendaDTO = agendaDTO;
    }
     public void addAgendaDTO(AgendaDTO agendaDTO) {
        this.agendaDTO.add(agendaDTO);
    }   

}
