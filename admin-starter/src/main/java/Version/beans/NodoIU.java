package Version.beans;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.List;

public class NodoIU {
   int codigo;
   String nombre="";
   double xpos=0;
   double ypos=0;

    public List<Integer> getDestinos() {
        return destinos;
    }

    public void setDestinos(List<Integer> destinos) {
        this.destinos = destinos;
    }

   List<Integer> destinos= new ArrayList<Integer>();

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getXpos() {
        return xpos;
    }

    public void setXpos(double xpos) {
        this.xpos = xpos;
    }

    public double getYpos() {
        return ypos;
    }

    public void setYpos(double ypos) {
        this.ypos = ypos;
    }


   public void addDestino(int destino) {
        this.destinos.add(destino);
    }
}
