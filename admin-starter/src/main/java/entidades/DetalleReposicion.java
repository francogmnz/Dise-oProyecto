package entidades;

public class DetalleReposicion extends Entidad {

    private int cantidad;
    private Articulo articulo;

    public DetalleReposicion() {
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

}
