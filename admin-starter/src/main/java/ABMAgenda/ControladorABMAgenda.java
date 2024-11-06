package ABMAgenda;

import ABMAgenda.dtos.AgendaDTO;
import ABMAgenda.dtos.DTOConsultorListaIzq;
import ABMAgenda.dtos.DTODatosInicialesAgenda;
import ABMAgenda.dtos.DTODatosInicialesAgendaIn;
import ABMAgenda.exceptions.AgendaException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ControladorABMAgenda {

    private ExpertoABMAgenda expertoABMAgenda = new ExpertoABMAgenda();

    public boolean existeAgendaParaMesYAnio(int mes, int anio) {
        return expertoABMAgenda.existeAgendaParaMesYAnio(mes, anio);
    }
    
    public Map<String, Date> obtenerPrimerYUltimoDiaDelMes(int mes, int anio) {
        // Delegar al experto para obtener las fechas
        return expertoABMAgenda.obtenerPrimerYUltimoDiaDelMes(mes, anio);
    }

    public DTODatosInicialesAgenda obtenerAgendaConsultor(int mes, int anio) throws AgendaException {
        return expertoABMAgenda.obtenerAgendaConsultor(mes, anio);
    }

    public List<DTOConsultorListaIzq> obtenerConsultoresActivos() throws AgendaException {

        // Llamar al método del experto para buscar los consultores activos
        List<DTOConsultorListaIzq> consultoresActivos = expertoABMAgenda.buscarConsultoresActivos();

        // Devolver la lista de consultores activos en formato DTO
        return consultoresActivos;
    }

    public void persistirDatosInicialesAgenda(DTODatosInicialesAgendaIn datosInicialesAgendaIn) throws AgendaException {
        try {
            // Llamada al método en ExpertoABMAgenda
            expertoABMAgenda.persistirDatosInicialesAgenda(datosInicialesAgendaIn);
        } catch (Exception e) {
            throw new AgendaException("Ocurrió un error al persistir los datos iniciales de la agenda.");
        }
    }
}
