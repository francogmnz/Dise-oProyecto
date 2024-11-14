package CambioEstado.dtos;

import java.util.ArrayList;
import java.util.List;

public class DTOMostrarHistorial {
    private List<DTOHistorialEstado> historialEstados = new ArrayList<>();

    // Getters y Setters

    public List<DTOHistorialEstado> getHistorialEstados() {
        return historialEstados;
    }

    public void setHistorialEstados(List<DTOHistorialEstado> historialEstados) {
        this.historialEstados = historialEstados;
    }

    // Método para agregar un estado al historial
    public void addHistorialEstado(DTOHistorialEstado historialEstado) {
        this.historialEstados.add(historialEstado);
    }
}
