
import ABMEstadoTramite.exceptions.EstadoTramiteException;
import RegistrarTramite.ExpertoRegistrarTramite;
import RegistrarTramite.dtos.DTOFile;
import RegistrarTramite.exceptions.RegistrarTramiteException;
import entidades.Tramite;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.EjemplosPersistencia;
import utils.FachadaPersistencia;

public class Main {

    public static void main(String[] args) {
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
    }

}
