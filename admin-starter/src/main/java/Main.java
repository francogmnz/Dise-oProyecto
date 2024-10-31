import entidades.Tramite;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.EjemplosPersistencia;
import utils.FachadaPersistencia;

public class Main {

    public static void main(String[] args) {
        
//        EjemplosPersistencia ejemplos = new EjemplosPersistencia();
//        ejemplos.crearElementos2();

        List<DTOCriterio> lCriterio = new ArrayList<>();
        
        DTOCriterio dto1 = new DTOCriterio();
        dto1.setAtributo("nroTramite");
        dto1.setOperacion("=");
        dto1.setValor(1005);
        
        lCriterio.add(dto1);
        
        Tramite tramite = (Tramite) FachadaPersistencia.getInstance().buscar("Tramite", lCriterio).get(0);
        
        System.out.println("FechaFinTramite: " + tramite.getFechaFinTramite());

        System.out.println("Funcionando");
    }

}
