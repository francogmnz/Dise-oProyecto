

import utils.EjemplosPersistencia;

public class Main {

    public static void main(String[] args) {
        
        EjemplosPersistencia ejemplos = new EjemplosPersistencia();
//        ejemplos.crearElementos2();
       ejemplos.inicializarRoles();
        ejemplos.inicializarAdmin();
        System.out.println("Funcionando");
    }

}
