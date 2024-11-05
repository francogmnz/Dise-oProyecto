package CambioEstado.dtos;

import java.util.ArrayList;
import java.util.List;


public class DTOEstadoOrigenCE {
    private int codEstadoOrigen;
    private String nombreEstadoOrigen;
    private List<DTOEstadoDestinoCE> estadosDestinos = new ArrayList<>();

    public int getCodEstadoOrigen() {
        return codEstadoOrigen;
    }

    public void setCodEstadoOrigen(int codEstadoOrigen) {
        this.codEstadoOrigen = codEstadoOrigen;
    }

    public String getNombreEstadoOrigen() {
        return nombreEstadoOrigen;
    }

    public void setNombreEstadoOrigen(String nombreEstadoOrigen) {
        this.nombreEstadoOrigen = nombreEstadoOrigen;
    }

    public List<DTOEstadoDestinoCE> getEstadosDestinos() {
        return estadosDestinos;
    }

    public void setEstadosDestinos(List<DTOEstadoDestinoCE> estadosDestinos) {
        this.estadosDestinos = estadosDestinos;
    }
       
    public void addEstadosDestinos(List<DTOEstadoDestinoCE> dtoEstadosDestino) {
        estadosDestinos.addAll(dtoEstadosDestino);
    }
}
