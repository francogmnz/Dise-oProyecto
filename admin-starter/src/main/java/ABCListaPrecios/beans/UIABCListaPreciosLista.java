package ABCListaPrecios.beans;

import ABCListaPrecios.ControladorABCListaPrecios;
import ABCListaPrecios.dtos.ListaPreciosDTO;
import ABCListaPrecios.exceptions.ListaPreciosException;
import entidades.ListaPrecios;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.omnifaces.util.Messages;
import org.primefaces.model.StreamedContent;
import utils.BeansUtils;

@Named("uiabmListaPreciosLista")
@ViewScoped
public class UIABCListaPreciosLista implements Serializable {

    private ControladorABCListaPrecios controladorABCListaPrecios = new ControladorABCListaPrecios();
    private Date fechaHoraHastaListaPreciosFiltro = new Date();
    private String criterio = "";

//    GETTERS Y SETTERS
    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public ControladorABCListaPrecios getControladorABCListaPrecios() {
        return controladorABCListaPrecios;
    }

    public void setControladorABCListaPrecios(ControladorABCListaPrecios controladorABCListaPrecios) {
        this.controladorABCListaPrecios = controladorABCListaPrecios;
    }

    public Date getFechaHoraHastaListaPreciosFiltro() {
        return fechaHoraHastaListaPreciosFiltro;
    }

    public void setFechaHoraHastaListaPreciosFiltro(Date fechaHoraHastaListaPreciosFiltro) {
        this.fechaHoraHastaListaPreciosFiltro = fechaHoraHastaListaPreciosFiltro;
    }

    public StreamedContent exportarListaPrecios(int codigo) throws ListaPreciosException {
        return controladorABCListaPrecios.exportarListaPrecios(codigo);
    }

    public void filtrar() {
    }

    public List<ListaPreciosGrillaUI> mostrarListasPrecios() {

        List<ListaPreciosGrillaUI> listasPreciosGrilla = new ArrayList<>();
        List<ListaPreciosDTO> listasPreciosDTO = controladorABCListaPrecios.mostrarListasPrecios(new Timestamp(fechaHoraHastaListaPreciosFiltro.getTime()));
        for (ListaPreciosDTO listaPreciosDTO : listasPreciosDTO) {
            ListaPreciosGrillaUI listaPreciosGrilla = new ListaPreciosGrillaUI();
            listaPreciosGrilla.setCodListaPrecios(listaPreciosDTO.getCodListaPrecios());
            listaPreciosGrilla.setFechaHoraDesdeListaPrecios(listaPreciosDTO.getFechaHoraDesdeListaPrecios());
            listaPreciosGrilla.setFechaHoraHastaListaPrecios(listaPreciosDTO.getFechaHoraHastaListaPrecios());
            listaPreciosGrilla.setFechaHoraBajaListaPrecios(listaPreciosDTO.getFechaHoraBajaListaPrecios());
            listasPreciosGrilla.add(listaPreciosGrilla);
        }
        return ordenarListaPrecios(listasPreciosGrilla);
    }

//    SUMA UN MINUTO AL TIMESTAMP EN PARAMETRO, PARA CUANDO SE CREA LA NUEVA LP SE PONGA ESTA FHASTAVIEJA EN FDESDENUEVA
    public static Timestamp sumarMinuto(Timestamp t) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(t.getTime());
        calendar.add(Calendar.MINUTE, 1); // Suma un minuto
        return new Timestamp(calendar.getTimeInMillis());
    }

//    NOS REDIRECCIONA A LA IMPORTACION DE LA NUEVA LISTA
    public String irAgregarListaPrecios() {
        BeansUtils.guardarUrlAnterior();
        ListaPrecios ultimaLP = controladorABCListaPrecios.buscarUltimaLista("");
        String newCod = Integer.toString(ultimaLP.getCodListaPrecios() + 1);
        Timestamp newFDesde = sumarMinuto(ultimaLP.getFechaHoraHastaListaPrecios());

//        SI LA ULTIMA LP ES NULA MANDA LOS VALORES DE FECHADESDE AHORA Y CODIGO 1
        if (ultimaLP == null) {
            newCod = Integer.toString(1);
            newFDesde = sumarMinuto(Timestamp.from(Instant.now()));
        }
        return "abmListaPrecios?faces-redirect=true&codLP=" + newCod + "&fDesde=" + newFDesde + "";

    }

    public void darDeBajaListaPrecios(int codigo) {
        try {
            controladorABCListaPrecios.darDeBajaListaPrecios(codigo);
            Messages.create("Anulado").detail("Anulado").add();
        } catch (ListaPreciosException e) {
            Messages.create("Error!").error().detail("AdminFaces Error message.").add();
        }
    }

//    ORDENA LA LISTA DE PRECIOS DE ACUERDO AL VALOR DEL COMBOBOX SELECCIONADO, POR DEFECTO ORDENA POR FECHADESDE ASC
    public List<ListaPreciosGrillaUI> ordenarListaPrecios(List<ListaPreciosGrillaUI> lpGrilla) {
        switch (criterio) {

            case "codAsc":
                lpGrilla.sort((lp1, lp2) -> {
                    if (lp1.getFechaHoraBajaListaPrecios() != null && lp2.getFechaHoraBajaListaPrecios() == null) {
                        return 1;
                    }
                    if (lp1.getFechaHoraBajaListaPrecios() == null && lp2.getFechaHoraBajaListaPrecios() != null) {
                        return -1;
                    }
                    return Integer.compare(lp1.getCodListaPrecios(), lp2.getCodListaPrecios());
                });
                break;
            case "codDsc":
                lpGrilla.sort((lp1, lp2) -> {
                    if (lp1.getFechaHoraBajaListaPrecios() != null && lp2.getFechaHoraBajaListaPrecios() == null) {
                        return 1;
                    }
                    if (lp1.getFechaHoraBajaListaPrecios() == null && lp2.getFechaHoraBajaListaPrecios() != null) {
                        return -1;
                    }
                    return Integer.compare(lp2.getCodListaPrecios(), lp1.getCodListaPrecios());
                });
                break;
            case "fDAsc":
                lpGrilla.sort((lp1, lp2) -> {
                    if (lp1.getFechaHoraBajaListaPrecios() != null && lp2.getFechaHoraBajaListaPrecios() == null) {
                        return 1;
                    }
                    if (lp1.getFechaHoraBajaListaPrecios() == null && lp2.getFechaHoraBajaListaPrecios() != null) {
                        return -1;
                    }
                    return lp1.getFechaHoraDesdeListaPrecios().compareTo(lp2.getFechaHoraDesdeListaPrecios());
                });
                break;
            case "fDDsc":
                lpGrilla.sort((lp1, lp2) -> {
                    if (lp1.getFechaHoraBajaListaPrecios() != null && lp2.getFechaHoraBajaListaPrecios() == null) {
                        return 1;
                    }
                    if (lp1.getFechaHoraBajaListaPrecios() == null && lp2.getFechaHoraBajaListaPrecios() != null) {
                        return -1;
                    }
                    return lp2.getFechaHoraDesdeListaPrecios().compareTo(lp1.getFechaHoraDesdeListaPrecios());
                });
                break;
            case "fHAsc":
                lpGrilla.sort((lp1, lp2) -> {
                    if (lp1.getFechaHoraBajaListaPrecios() != null && lp2.getFechaHoraBajaListaPrecios() == null) {
                        return 1;
                    }
                    if (lp1.getFechaHoraBajaListaPrecios() == null && lp2.getFechaHoraBajaListaPrecios() != null) {
                        return -1;
                    }
                    return lp1.getFechaHoraHastaListaPrecios().compareTo(lp2.getFechaHoraHastaListaPrecios());
                });
                break;
            case "fHDsc":
                lpGrilla.sort((lp1, lp2) -> {
                    if (lp1.getFechaHoraBajaListaPrecios() != null && lp2.getFechaHoraBajaListaPrecios() == null) {
                        return 1;
                    }
                    if (lp1.getFechaHoraBajaListaPrecios() == null && lp2.getFechaHoraBajaListaPrecios() != null) {
                        return -1;
                    }
                    return lp2.getFechaHoraHastaListaPrecios().compareTo(lp1.getFechaHoraHastaListaPrecios());
                });
                break;
            default:
                lpGrilla.sort((lp1, lp2) -> {
                    if (lp1.getFechaHoraBajaListaPrecios() != null && lp2.getFechaHoraBajaListaPrecios() == null) {
                        return 1;
                    }
                    if (lp1.getFechaHoraBajaListaPrecios() == null && lp2.getFechaHoraBajaListaPrecios() != null) {
                        return -1;
                    }
                    return lp2.getFechaHoraDesdeListaPrecios().compareTo(lp1.getFechaHoraDesdeListaPrecios());
                });
        }
        return lpGrilla;
    }

//    RENDERIZA EL BOTON DARDEBAJA SI ES LA ULTIMA LISTA DE PRECIOS, NO ESTA VIGENTE Y NO ES UNA LISTA PRECIOS PASADA
    public boolean habilitarBtnBaja(ListaPreciosGrillaUI listaEnviada) {
        Timestamp hoy = new Timestamp(System.currentTimeMillis());
        if (listaEnviada.getFechaHoraBajaListaPrecios() == null && listaEnviada.getFechaHoraHastaListaPrecios().after(hoy) && !isLaActiva(listaEnviada)) {
            ListaPrecios ultimaLP = controladorABCListaPrecios.buscarUltimaLista("noNulas");
            return ultimaLP.getCodListaPrecios() == listaEnviada.getCodListaPrecios();
        }
        return false;
    }

//    DEVUELVE TRUE SI LA LISTA DE PRECIOS ES LA VIGENTE 
    public boolean isLaActiva(ListaPreciosGrillaUI listaEnviada) {
        if (listaEnviada != null) {
            Timestamp fd = listaEnviada.getFechaHoraDesdeListaPrecios();
            Timestamp fh = listaEnviada.getFechaHoraHastaListaPrecios();
            Timestamp fb = listaEnviada.getFechaHoraBajaListaPrecios();
            if (fb == null) {
                Timestamp hoy = new Timestamp(System.currentTimeMillis());
                if (fd.before(hoy) && fh.after(hoy)) {
                    return true;
                }
            }
        }
        return false;
    }
//    DEVUELVE TRUE SI LA LISTA DE PRECIOS ESTA ANULADA

    public boolean isAnulada(ListaPreciosGrillaUI listaEnviada) {
        if (listaEnviada.getFechaHoraBajaListaPrecios() != null) {
            return true;
        } else {
            return false;
        }
    }
//    DEVUELVE TRUE SI LA LISTA DE PRECIOS ES A FUTURO

    public boolean isPasada(ListaPreciosGrillaUI listaEnviada) {
        Timestamp hoy = new Timestamp(System.currentTimeMillis());
        if (listaEnviada.getFechaHoraDesdeListaPrecios().before(hoy)) {
            return true;
        } else {
            return false;
        }
    }

}
