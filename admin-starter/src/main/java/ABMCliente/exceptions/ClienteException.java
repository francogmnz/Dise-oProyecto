
package ABMCliente.exceptions;

import ABMConsultor.exceptions.*;

  public class ClienteException extends Exception {
    public ClienteException(String mensaje) {
        super(mensaje);
    }    
}
