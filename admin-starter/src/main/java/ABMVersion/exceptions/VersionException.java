/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMVersion.exceptions;

/**
 *
 * @author licciardi
 */
public class VersionException extends Exception{
    
    public VersionException(String mensaje) {
        super(mensaje);
    }

    public VersionException(String string, Exception e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
