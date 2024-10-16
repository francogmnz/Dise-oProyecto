package utils;

import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;

public class Errores {

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }
    

    private List<String> errores = new ArrayList<>();

    public void agregarError(String s) {
        errores.add(s);
    }

    public void mostrarErrores() {
        for (String err : errores) {
            Messages.create("Error!").error().detail(err).add();
        }
        errores.clear();
    }

}
