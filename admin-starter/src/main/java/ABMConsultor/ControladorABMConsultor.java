
package ABMConsultor;

import ABMConsultor.dtos.DTOConsultor;
import ABMConsultor.dtos.DTOIngresoDatos;
import ABMConsultor.dtos.DTOModificacionDatos;
import ABMConsultor.dtos.DTOModificacionDatosIn;
import ABMConsultor.exceptions.ConsultorException;
import java.io.IOException;
import java.util.List;



public class ControladorABMConsultor {
    private ExpertoABMConsultor expertoABMConsultor = new ExpertoABMConsultor();
    public List<DTOConsultor> buscarConsultores(int legajo, String nombre, int numMaxTramites){
        return expertoABMConsultor.buscarConsultores(legajo,nombre,numMaxTramites);
    }

    public void agregarConsultor(DTOIngresoDatos dtoIngresoDatos) throws ConsultorException{
        expertoABMConsultor.agregarConsultor(dtoIngresoDatos);
    }

    public void modificarConsultor(DTOModificacionDatosIn dtoModificacionDatosIn) throws ConsultorException{
        expertoABMConsultor.modificarConsultor(dtoModificacionDatosIn);
    }

    public DTOModificacionDatos buscarConsultorAModificar(int legajo) throws IOException, ConsultorException{
        return expertoABMConsultor.buscarConsultorAModificar(legajo);
    }

    public void darDeBajaConsultor(int legajo) throws ConsultorException {
        expertoABMConsultor.darDeBajaConsultor(legajo);
    }
}
