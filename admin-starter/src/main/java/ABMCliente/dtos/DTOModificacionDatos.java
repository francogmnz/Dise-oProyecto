package ABMCliente.dtos;

public class DTOModificacionDatos {

    private int dniCliente;
    private int dniOriginalCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String mailCliente;

    public int getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(int dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getMailCliente() {
        return mailCliente;
    }

    public void setMailCliente(String mailCliente) {
        this.mailCliente = mailCliente;
    }

    public int getDniOriginalCliente() {
        return dniOriginalCliente;
    }

    public void setDniOriginalCliente(int dniOriginalCliente) {
        this.dniOriginalCliente = dniOriginalCliente;
    }

}
