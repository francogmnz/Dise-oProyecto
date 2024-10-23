package CambioEstado.beans;

import CambioEstado.ControladorCambioEstado;
import CambioEstado.dtos.DTOTramitesVigentes;
import CambioEstado.dtos.TramiteDTO;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("uicambioEstadoLista")
@ViewScoped
public class UICambioEstadoLista implements Serializable {

    private ControladorCambioEstado controladorCambioEstado = new ControladorCambioEstado();
    private int codigoFiltro = 0;
    private String nombreFiltro = "";

    public ControladorCambioEstado getControladorCambioEstado() {
        return controladorCambioEstado;
    }

    public void setControladorCambioEstado(ControladorCambioEstado controladorCambioEstado) {
        this.controladorCambioEstado = controladorCambioEstado;
    }

    public int getCodigoFiltro() {
        return codigoFiltro;
    }

    public void setCodigoFiltro(int codigoFiltro) {
        this.codigoFiltro = codigoFiltro;
    }

    public String getNombreFiltro() {
        return nombreFiltro;
    }

    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }

    public void filtrar() {
        // Implementar lógica de filtrado según los filtros si es necesario
    }

    public List<CambioEstadoGrillaUI> buscarTramites() {
        System.out.println(codigoFiltro); // Código del consultor
        System.out.println(nombreFiltro); // Filtro de nombre (si es que lo usas)

        // Lista para la grilla
        List<CambioEstadoGrillaUI> cambioEstadoGrilla = new ArrayList<>();

        // Obtener los trámites vigentes del consultor filtrado
        List<DTOTramitesVigentes> dtoTramitesVigentesList = controladorCambioEstado.buscarTramites(codigoFiltro);

        // Recorrer cada DTOTramitesVigentes
        for (DTOTramitesVigentes dtoTramitesVigentes : dtoTramitesVigentesList) {
            // Recorrer cada trámite en el DTOTramitesVigentes
            for (TramiteDTO tramiteDTO : dtoTramitesVigentes.getTramites()) {
                // Crear un nuevo objeto para la interfaz de la grilla
                CambioEstadoGrillaUI cambioEstadoGrillaUI = new CambioEstadoGrillaUI();
                // Setear los atributos del trámite en el objeto de la grilla
                cambioEstadoGrillaUI.setCodEstadoTramite(tramiteDTO.getEstadoTramite().getCodEstadoTramite());
                cambioEstadoGrillaUI.setNombreEstadoTramite(tramiteDTO.getEstadoTramite().getNombreEstadoTramite());
                cambioEstadoGrillaUI.setFechaInicioTramite(tramiteDTO.getFechaInicioTramite());
                cambioEstadoGrillaUI.setFechaRecepcionTramite(tramiteDTO.getFechaRecepcionTramite());
                cambioEstadoGrillaUI.setNroTramite(tramiteDTO.getNroTramite());

                // Agregar el objeto a la lista que se mostrará en la grilla
                cambioEstadoGrilla.add(cambioEstadoGrillaUI);
            }
        }

        // Retornar la lista de objetos para mostrar en la grilla
        return cambioEstadoGrilla;
    }



   /* public String irAgregarArticulo() {
        BeansUtils.guardarUrlAnterior();
        return "abmArticulo?faces-redirect=true&codigo=0";
    }

    public String irModificarArticulo(int codigo) {
        BeansUtils.guardarUrlAnterior();
        return "abmArticulo?faces-redirect=true&codigo=" + codigo;
    }

    /*public void darDeBajaArticulo(int codigo) {
        try {
            controladorABMArticulo.darDeBajaArticulo(codigo);
            Messages.create("Anulado").detail("Anulado").add();
        } catch (ArticuloException e) {
            Messages.create("Error!").error().detail("AdminFaces Error message.").add();

        }
    }*/
}