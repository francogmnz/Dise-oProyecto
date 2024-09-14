
package ABCListaPrecios.dtos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NuevaListaPreciosDTO {
    
    private int codListaPrecios;
    private Timestamp fechaHoraDesdeListaPrecios;
    private Timestamp fechaHoraHastaListaPrecios;
    private List<DetalleListaPreciosDTO> detalles = new ArrayList<>();
    
    public int getCodListaPrecios() {
        return codListaPrecios;
    }

    public void setCodListaPrecios(int codListaPrecios) {
        this.codListaPrecios = codListaPrecios;
    }

    public Timestamp getFechaHoraDesdeListaPrecios() {
        return fechaHoraDesdeListaPrecios;
    }

    public void setFechaHoraDesdeListaPrecios(Timestamp fechaHoraDesdeListaPrecios) {
        this.fechaHoraDesdeListaPrecios = fechaHoraDesdeListaPrecios;
    }

    public Timestamp getFechaHoraHastaListaPrecios() {
        return fechaHoraHastaListaPrecios;
    }

    public void setFechaHoraHastaListaPrecios(Timestamp fechaHoraHastaListaPrecios) {
        this.fechaHoraHastaListaPrecios = fechaHoraHastaListaPrecios;
    }

    public List<DetalleListaPreciosDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleListaPreciosDTO> detalles) {
        this.detalles = detalles;
    }
    
    public void addDetalle(DetalleListaPreciosDTO detalle){
        detalles.add(detalle);
    }
    
}
