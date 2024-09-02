/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author licciardi
 */
class TipoTramiteListaPrecios extends Entidad {
    private int precioTipoTramite;
    private TipoTramite tipoTramite;
    

    public TipoTramiteListaPrecios() {
    }

    public int getPrecioTipoTramite() {
        return precioTipoTramite;
    }

    public void setPrecioTipoTramite(int precioTipoTramite) {
        this.precioTipoTramite = precioTipoTramite;
       
    }

    public TipoTramite getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(TipoTramite tipoTramite) {
        this.tipoTramite = tipoTramite;
    }


    
}
