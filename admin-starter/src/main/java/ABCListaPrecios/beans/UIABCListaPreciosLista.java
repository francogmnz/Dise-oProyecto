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

    public void filtrar() {
    }
    
     public StreamedContent exportarListaPrecios(int codigo) throws ListaPreciosException {
        return controladorABCListaPrecios.exportarListaPrecios(codigo);
    }

    public List<ListaPreciosGrillaUI> buscarListasPrecios() {

        List<ListaPreciosGrillaUI> listasPreciosGrilla = new ArrayList<>();
        List<ListaPreciosDTO> listasPreciosDTO = controladorABCListaPrecios.buscarListasPrecios(new Timestamp(fechaHoraHastaListaPreciosFiltro.getTime()));
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

//    public List<ListaPreciosGrillaUI> buscarListasPreciosSinBaja() {
//
//        List<ListaPreciosGrillaUI> listasPreciosGrilla = new ArrayList<>();
//        List<ListaPrecios> listasPreciosDTO = controladorABCListaPrecios.buscarListasPreciosSinBaja();
//        for (ListaPrecios listaPreciosDTO : listasPreciosDTO) {
//            ListaPreciosGrillaUI listaPreciosGrilla = new ListaPreciosGrillaUI();
//            listaPreciosGrilla.setCodListaPrecios(listaPreciosDTO.getCodListaPrecios());
//            listaPreciosGrilla.setFechaHoraDesdeListaPrecios(listaPreciosDTO.getFechaHoraDesdeListaPrecios());
//            listaPreciosGrilla.setFechaHoraHastaListaPrecios(listaPreciosDTO.getFechaHoraHastaListaPrecios());
//            listaPreciosGrilla.setFechaHoraBajaListaPrecios(listaPreciosDTO.getFechaHoraBajaListaPrecios());
//            listasPreciosGrilla.add(listaPreciosGrilla);
//        }
//        for (ListaPreciosGrillaUI l : listasPreciosGrilla) {
//            System.out.println(l.getCodListaPrecios());
//            System.out.println(l.getFechaHoraDesdeListaPrecios());
//            System.out.println(l.getFechaHoraHastaListaPrecios());
//            System.out.println(l.getFechaHoraBajaListaPrecios());
//
//        }
//        return listasPreciosGrilla;
//    }
    public static Timestamp sumarMinuto(Timestamp t) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(t.getTime());
        calendar.add(Calendar.MINUTE, 1); // Suma un minuto
        return new Timestamp(calendar.getTimeInMillis());
    }

    public String irAgregarListaPrecios() {
        BeansUtils.guardarUrlAnterior();
        ListaPrecios ultimaLP = controladorABCListaPrecios.buscarUltimaLista();
        String newCod = Integer.toString(ultimaLP.getCodListaPrecios());
        Timestamp newFDesde = sumarMinuto(ultimaLP.getFechaHoraHastaListaPrecios());
        System.out.println(newCod);
        System.out.println(newFDesde);

        if (ultimaLP == null) {
            newCod = Integer.toString(0);
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

    public List<ListaPreciosGrillaUI> ordenarListaPrecios(List<ListaPreciosGrillaUI> lpGrilla) {
        switch (criterio) {

            case "codAsc":
                lpGrilla.sort((lp1, lp2) -> Integer.compare(lp1.getCodListaPrecios(), lp2.getCodListaPrecios()));//Para ordenar las listas en orden ASC por el Codigo
                break;
            case "codDsc":
                lpGrilla.sort((lp1, lp2) -> Integer.compare(lp2.getCodListaPrecios(), lp1.getCodListaPrecios()));//Para ordenar las listas en orden DESC por el Codigo
                break;
            case "fDAsc":
                Collections.sort(lpGrilla, (lp1, lp2) -> lp1.getFechaHoraDesdeListaPrecios().compareTo(lp2.getFechaHoraDesdeListaPrecios()));  //Para ordenar las listas en orden ASC por el FDesde
                break;
            case "fDDsc":
                Collections.sort(lpGrilla, (lp1, lp2) -> lp2.getFechaHoraDesdeListaPrecios().compareTo(lp1.getFechaHoraDesdeListaPrecios()));  //Para ordenar las listas en orden DESC por el FDesde
                break;
            case "fHAsc":
                Collections.sort(lpGrilla, (lp1, lp2) -> lp1.getFechaHoraHastaListaPrecios().compareTo(lp2.getFechaHoraHastaListaPrecios()));  //Para ordenar las listas en orden ASC por el FHasta
                break;
            case "fHDsc":
                Collections.sort(lpGrilla, (lp1, lp2) -> lp2.getFechaHoraHastaListaPrecios().compareTo(lp1.getFechaHoraHastaListaPrecios()));  //Para ordenar las listas en orden DESC por el FHasta
                break;
            default:
                Collections.sort(lpGrilla, (lp1, lp2) -> lp2.getFechaHoraDesdeListaPrecios().compareTo(lp1.getFechaHoraDesdeListaPrecios()));  //Para ordenar las listas en orden ASC por el FDesde
        }
        return lpGrilla;
    }

    public boolean habilitarBtnBaja(ListaPreciosGrillaUI listaEnviada) {

        if (listaEnviada.getFechaHoraBajaListaPrecios() == null) {
            ListaPrecios ultimaLP = controladorABCListaPrecios.buscarUltimaLista();
            return ultimaLP.getCodListaPrecios() == listaEnviada.getCodListaPrecios();
        }
        return false;
    }

    public boolean isLaActiva(ListaPreciosGrillaUI listaEnviada) {
        if (listaEnviada != null) {

            Timestamp fd = listaEnviada.getFechaHoraDesdeListaPrecios();
            Timestamp fh = listaEnviada.getFechaHoraHastaListaPrecios();
            Timestamp fb = listaEnviada.getFechaHoraBajaListaPrecios();
            if (fd == null || fh == null || fb != null) {
                return false;
            } else {
                Timestamp hoy = new Timestamp(System.currentTimeMillis());
                if (fd.before(hoy) && fh.after(hoy)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAnulada(ListaPreciosGrillaUI listaEnviada) {
        if (listaEnviada.getFechaHoraBajaListaPrecios() != null) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isFutura(ListaPreciosGrillaUI listaEnviada) {
        Timestamp hoy = new Timestamp(System.currentTimeMillis());
        if (listaEnviada.getFechaHoraDesdeListaPrecios().after(hoy)) {
            return true;
        } else {
            return false;
        }
    }
    
}
