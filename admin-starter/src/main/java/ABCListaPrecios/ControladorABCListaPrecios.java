package ABCListaPrecios;

import ABCListaPrecios.dtos.ListaPreciosDTO;
import ABCListaPrecios.dtos.NuevaListaPreciosDTO;
import ABCListaPrecios.exceptions.ListaPreciosException;
import entidades.ListaPrecios;
import java.sql.Timestamp;
import java.util.List;
import org.primefaces.model.StreamedContent;

public class ControladorABCListaPrecios {

    private ExpertoABCListaPrecios expertoABCListaPrecios = new ExpertoABCListaPrecios();

    public void agregarListaPrecios(NuevaListaPreciosDTO nuevaListaPreciosDTO) throws ListaPreciosException {
        expertoABCListaPrecios.agregarListaPrecios(nuevaListaPreciosDTO);
    }

    public List<ListaPreciosDTO> mostrarListasPrecios(Timestamp fechaHoraHastaListaPreciosFiltro) throws ListaPreciosException {
        return expertoABCListaPrecios.mostrarListasPrecios(fechaHoraHastaListaPreciosFiltro);
    }
    
    public ListaPrecios buscarUltimaLista(String s){
        return expertoABCListaPrecios.buscarUltimaLista(s);
    }

    public void darDeBajaListaPrecios(int codigo) throws ListaPreciosException {
        expertoABCListaPrecios.darDeBajaListaPrecios(codigo);
    }

    public StreamedContent exportarListaPrecios(int codigo) throws ListaPreciosException {
        return expertoABCListaPrecios.exportarListaPrecios(codigo);
    }

}
