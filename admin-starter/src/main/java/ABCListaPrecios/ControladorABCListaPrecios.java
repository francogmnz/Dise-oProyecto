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

    public List<ListaPreciosDTO> buscarListasPrecios(Timestamp fechaHoraHastaListaPreciosFiltro) {
        return expertoABCListaPrecios.buscarListasPrecios(fechaHoraHastaListaPreciosFiltro);
    }

    public List<ListaPrecios> buscarListasPreciosSinBaja() {
        return expertoABCListaPrecios.buscarListasPreciosSinBaja();
    }
    
    public ListaPrecios buscarUltimaLista(){
        return expertoABCListaPrecios.buscarUltimaLista();
    }

    public void darDeBajaListaPrecios(int codigo) throws ListaPreciosException {
        expertoABCListaPrecios.darDeBajaListaPrecios(codigo);
    }

    public StreamedContent exportarListaPrecios(int codigo) throws ListaPreciosException {
        return expertoABCListaPrecios.exportarListaPrecios(codigo);
    }

}
