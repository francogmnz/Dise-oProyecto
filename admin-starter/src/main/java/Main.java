

import ABMEstadoTramite.exceptions.EstadoTramiteException;
import RegistrarTramite.ExpertoRegistrarTramite;
import RegistrarTramite.exceptions.RegistrarTramiteException;


public class Main {

    public static void main(String[] args) throws EstadoTramiteException, RegistrarTramiteException {
        //EjemplosPersistencia ejemplos = new EjemplosPersistencia();
        //ejemplos.crearElementos2();
        
        ExpertoRegistrarTramite expertoRegistrarTramite = new ExpertoRegistrarTramite();
        expertoRegistrarTramite.registrarTramite(12345678, 1);
        
        
    }

}
