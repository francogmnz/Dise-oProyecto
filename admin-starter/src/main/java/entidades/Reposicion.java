package entidades;

import java.util.Date;
import java.util.*;

public class Reposicion extends Entidad {

    private int numero;
    private Date fecha;
    private Estado estado;
    private List<DetalleReposicion> detalleReposicionList = new ArrayList<>();

    public Reposicion() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<DetalleReposicion> getDetalleReposicionList() {
        return detalleReposicionList;
    }

    public void setDetalleReposicionList(List<DetalleReposicion> detalleReposicionList) {
        this.detalleReposicionList = detalleReposicionList;
    }

    public void addDetalleReposicionList(DetalleReposicion detalleReposicion) {
        detalleReposicionList.add(detalleReposicion);
    }

}
