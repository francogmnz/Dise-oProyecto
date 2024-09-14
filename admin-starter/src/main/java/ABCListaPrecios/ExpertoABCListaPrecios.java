
package ABCListaPrecios;

import ABCListaPrecios.dtos.DetalleListaPreciosDTO;
import ABCListaPrecios.dtos.ListaPreciosDTO;
import ABCListaPrecios.dtos.NuevaListaPreciosDTO;
import ABCListaPrecios.exceptions.ListaPreciosException;
import entidades.ListaPrecios;
import entidades.TipoTramite;
import entidades.TipoTramiteListaPrecios;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

public class ExpertoABCListaPrecios {

    public List<ListaPreciosDTO> buscarListasPrecios(Timestamp fechaHoraHastaListaPreciosFiltro) {
        List<DTOCriterio> lCriterio=new ArrayList<DTOCriterio>();
        if(fechaHoraHastaListaPreciosFiltro != null){
            DTOCriterio unCriterio=new DTOCriterio();
            unCriterio.setAtributo("fechaHoraHastaListaPrecios");
            unCriterio.setOperacion("=");
            unCriterio.setValor(fechaHoraHastaListaPreciosFiltro);
            lCriterio.add(unCriterio);
        }
        List objetoList = FachadaPersistencia.getInstance().buscar("ListaPrecios", lCriterio);
        List<ListaPreciosDTO> listasPreciosResultado = new ArrayList<>();
        for (Object x : objetoList) {
            ListaPrecios listaPrecios = (ListaPrecios) x;
            ListaPreciosDTO listaPreciosDTO = new ListaPreciosDTO();
            listaPreciosDTO.setCodListaPrecios(listaPrecios.getCodListaPrecios());
            listaPreciosDTO.setFechaHoraDesdeListaPrecios(listaPreciosDTO.getFechaHoraDesdeListaPrecios());
            listaPreciosDTO.setFechaHoraHastaListaPrecios(listaPrecios.getFechaHoraHastaListaPrecios());
            listaPreciosDTO.setFechaHoraBajaListaPrecios(listaPrecios.getFechaHoraBajaListaPrecios());
            listasPreciosResultado.add(listaPreciosDTO);
        }
        return listasPreciosResultado;
    }

    void agregarListaPrecios(NuevaListaPreciosDTO nuevaListaPreciosDTO) throws ListaPreciosException{
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("fechaHoraBajaListaPrecios");
        dto.setOperacion("=");
        dto.setValor(null);

        criterioList.add(dto);
        List objetoList=FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioList);
        ListaPrecios ultimaListaPrecios = null;
        for (Object x : objetoList) {
            ListaPrecios listaPrecios = (ListaPrecios) x;
            if(ultimaListaPrecios == null || listaPrecios.getFechaHoraDesdeListaPrecios().after(ultimaListaPrecios.getFechaHoraDesdeListaPrecios())){
                ultimaListaPrecios = listaPrecios;
            }
        }
        
        Timestamp ultimaFechaHoraDesde = ultimaListaPrecios.getFechaHoraDesdeListaPrecios();
        Timestamp nuevaFechaHoraDesde = nuevaListaPreciosDTO.getFechaHoraDesdeListaPrecios(); 
        if(nuevaFechaHoraDesde.before(new Date()) || nuevaFechaHoraDesde.before(ultimaFechaHoraDesde)){
            throw new ListaPreciosException("La fecha desde de la nueva lista de precios debe ser mayor a la fecha actual.");
        }
        
        Timestamp FechaHoraHasta = ultimaListaPrecios.getFechaHoraHastaListaPrecios();   
        if(nuevaFechaHoraDesde.after(FechaHoraHasta) || nuevaFechaHoraDesde.before(FechaHoraHasta)){
            ultimaListaPrecios.setFechaHoraHastaListaPrecios(nuevaFechaHoraDesde);
        }
        
        FachadaPersistencia.getInstance().guardar(ultimaListaPrecios);
        
        criterioList.clear();
        dto = new DTOCriterio();

        dto.setAtributo("codListaPrecios");
        dto.setOperacion("=");
        dto.setValor(nuevaListaPreciosDTO.getCodListaPrecios());

        criterioList.add(dto);
        List lListaPrecios=FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioList);
        
        if (lListaPrecios.size() > 0) {
            throw new ListaPreciosException("El código de estado ya existe");
        } else {
            ListaPrecios nuevaListaPrecios = new ListaPrecios();
            nuevaListaPrecios.setCodListaPrecios(nuevaListaPreciosDTO.getCodListaPrecios());
            nuevaListaPrecios.setFechaHoraDesdeListaPrecios(nuevaListaPreciosDTO.getFechaHoraDesdeListaPrecios());
            nuevaListaPrecios.setFechaHoraHastaListaPrecios(nuevaListaPreciosDTO.getFechaHoraHastaListaPrecios());
            
             criterioList.clear();
            dto = new DTOCriterio();

            dto.setAtributo("fechaHoraBajaTipoTramite");
            dto.setOperacion("=");
            dto.setValor(null);

            criterioList.add(dto);
            List objetoList2=FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList);
            
            for (Object x : objetoList2) {
                TipoTramite tipoTramite = (TipoTramite) x;
                TipoTramiteListaPrecios nuevoTipoTramiteListaPrecios = new TipoTramiteListaPrecios();
                nuevoTipoTramiteListaPrecios.setTipoTramite(tipoTramite);
                nuevoTipoTramiteListaPrecios.setPrecioTipoTramite(0);
                int codTipoTramite = tipoTramite.getCodTipoTramite();
                List<DetalleListaPreciosDTO> detalles = nuevaListaPreciosDTO.getDetalles();
                boolean isEncontrado = false;
                for (DetalleListaPreciosDTO detalle : detalles) {
                    int codigoTT = detalle.getCodTipoTramite();
                    if (codTipoTramite == codigoTT) {
                        nuevoTipoTramiteListaPrecios.setPrecioTipoTramite(detalle.getNuevoPrecioTipoTramite());
                        isEncontrado = true;
                        break;
                    }
                }
                if (!isEncontrado) {
                   List<TipoTramiteListaPrecios> tipoTramiteListaPrecios = ultimaListaPrecios.getTipoTramiteListaPrecios();
                    for (TipoTramiteListaPrecios tipoTramiteListaPrecio : tipoTramiteListaPrecios) {
                        TipoTramite tipoTramite1 = tipoTramiteListaPrecio.getTipoTramite();
                        int codTipoTramite1 = tipoTramite1.getCodTipoTramite();
                        if (codTipoTramite1 == codTipoTramite) {
                            nuevoTipoTramiteListaPrecios.setPrecioTipoTramite(tipoTramiteListaPrecio.getPrecioTipoTramite());                          
                        }
                    }
                }
                FachadaPersistencia.getInstance().guardar(nuevoTipoTramiteListaPrecios);
                nuevaListaPrecios.addTipoTramiteListaPrecios(nuevoTipoTramiteListaPrecios);
            }
            
            FachadaPersistencia.getInstance().guardar(nuevaListaPrecios);
            FachadaPersistencia.getInstance().finalizarTransaccion();
            
        }
        
    }

    public void darDeBajaListaPrecios(int codigo) {
        
    }

    public void exportarListaPrecios(int codigo) {
        
    }
    

    
}
