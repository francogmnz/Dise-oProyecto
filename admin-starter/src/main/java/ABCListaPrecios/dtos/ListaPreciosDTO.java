
package ABCListaPrecios.dtos;

import java.sql.Timestamp;

public class ListaPreciosDTO {
    
    private int codListaPrecios;
    private Timestamp fechaHoraDesdeListaPrecios;
    private Timestamp fechaHoraHastaListaPrecios;
    private Timestamp fechaHoraBajaListaPrecios;

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

    public Timestamp getFechaHoraBajaListaPrecios() {
        return fechaHoraBajaListaPrecios;
    }

    public void setFechaHoraBajaListaPrecios(Timestamp fechaHoraBajaListaPrecios) {
        this.fechaHoraBajaListaPrecios = fechaHoraBajaListaPrecios;
    }
    
}
