

import ABMEstadoTramite.exceptions.EstadoTramiteException;
import utils.EjemplosPersistencia;

public class Main {

    public static void main(String[] args) throws EstadoTramiteException {
        EjemplosPersistencia ejemplos = new EjemplosPersistencia();
        ejemplos.crearElementos2();
        
        
        System.out.println("Funcionando");
    }

}
