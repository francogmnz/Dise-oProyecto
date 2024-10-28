package CambioEstado.dtos;

import java.util.ArrayList;
import java.util.List;

public class DTOTramitesVigentes {
    private int codConsultor;
    private List<TramiteDTO> tramites = new ArrayList<>();

    // Getter y Setter para codConsultor
    public int getCodConsultor() {
        return codConsultor;
    }

    public void setCodConsultor(int codConsultor) {
        this.codConsultor = codConsultor;
    }

    // Getter y Setter para la lista de trámites
    public List<TramiteDTO> getTramites() {
        return tramites;
    }

    public void setTramites(List<TramiteDTO> tramites) {
        this.tramites = tramites;
    }

    // Método para agregar un DTOTramite individual
    public void addTramite(TramiteDTO tramite) {
        this.tramites.add(tramite);
    }
}
