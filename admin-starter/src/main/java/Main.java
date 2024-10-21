
import ABMEstadoTramite.exceptions.EstadoTramiteException;
import RegistrarTramite.ExpertoRegistrarTramite;
import RegistrarTramite.dtos.DTOFile;
import RegistrarTramite.exceptions.RegistrarTramiteException;
import entidades.ListaPrecios;
import entidades.TipoTramite;
import entidades.TipoTramiteListaPrecios;
import entidades.Tramite;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.EjemplosPersistencia;
import utils.FachadaPersistencia;

public class Main {

    public static void main(String[] args) throws RegistrarTramiteException {
//        EjemplosPersistencia ejemplos = new EjemplosPersistencia();
//        ejemplos.crearElementos2();

//       ExpertoRegistrarTramite expertoRegistrarTramite = new ExpertoRegistrarTramite();
//       expertoRegistrarTramite.registrarTramite(12345678, 1);

        /*DTOFile file = new DTOFile();
         file.setContenidoB64("asdaskldjklj12jkl3e980saxczmxcm,m12lñjsadas112312123");
         expertoRegistrarTramite.registrarDocumentacion(11, file);/
        ExpertoRegistrarTramite expertoRegistrarTramite = new ExpertoRegistrarTramite();

        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();

        DTOCriterio dto1 = new DTOCriterio();

        dto1.setAtributo("nroTramite");
        dto1.setOperacion("=");
        dto1.setValor(1005);

        criterioList.add(dto1);
        
        Tramite tramiteEncontrado = (Tramite) FachadaPersistencia.getInstance().buscar("Tramite", criterioList).get(0);

        expertoRegistrarTramite.asignarConsultor(tramiteEncontrado);*/
        
         List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>(); // creamos la lista de criterios

            /* buscar("ListaPrecio", "fechaHoraBaja = " + null +
        "AND fechaHoraDesde < " + fechaActual + "AND fechaHoraHasta >" + fechaActual*/
            DTOCriterio dto1 = new DTOCriterio();
            dto1.setAtributo("fechaHoraDesdeListaPrecios");
            dto1.setOperacion("<");
            dto1.setValor(new Timestamp(System.currentTimeMillis()));
            criterioList.add(dto1);

            DTOCriterio dto2 = new DTOCriterio();
            dto2.setAtributo("fechaHoraHastaListaPrecios");
            dto2.setOperacion(">");
            dto2.setValor(new Timestamp(System.currentTimeMillis()));
            criterioList.add(dto2);

            DTOCriterio dto3 = new DTOCriterio();
            dto3.setAtributo("fechaHoraBajaListaPrecios");
            dto3.setOperacion("=");
            dto3.setValor(null);
            criterioList.add(dto3);

            List<Object> listaPrecios= FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioList);
            
            if (listaPrecios == null || listaPrecios.isEmpty()) {
                throw new RegistrarTramiteException("No se encontro una Lista de Precios valida.");
            }
                     
            ListaPrecios listaPreciosEncontrada = (ListaPrecios) listaPrecios.get(0);

            // getTipoTramiteListaPrecios(): List<TipoTramiteListaPrecios>
            
            List<TipoTramiteListaPrecios> precioTTList = listaPreciosEncontrada.getTipoTramiteListaPrecios();
            
            // loop por cada TipoTramiteListaPrecios
            boolean precioEncontrado = false;
            for (TipoTramiteListaPrecios tTP : precioTTList) {
                                
                TipoTramite tipoTramite = tTP.getTipoTramite();
                
                System.out.println("El codTT es: " + tipoTramite.getCodTipoTramite());
            }
    }

}
