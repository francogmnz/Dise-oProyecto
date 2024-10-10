package Email;

import entidades.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

@Named("uiCorreo")
@RequestScoped
public class enviarCorreo {

    private String datoBuscado = "";
    private boolean recuperarOcrear;    //recuperar = false, crear = true

    private String to;
    private String from;
    private String subject;
    private String descr;
    private String username;
    private String password;
    private String smtp;
    private int port;

    public enviarCorreo() {
        this.to = "";
        this.from = "synapsis.utn2024@gmail.com";
        this.subject = "Intento de envio automatico de Correo";
        this.descr = "Hola esto es un correo automatico";
        this.username = "synapsis.utn2024@gmail.com";
        this.password = "mblqrywhfnlehxda";
        this.smtp = "smtp.gmail.com";
        this.port = 587;
    }

    public String getDatoBuscado() {
        return datoBuscado;
    }

    public void setDatoBuscado(String datoBuscado) {
        this.datoBuscado = datoBuscado;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void enviarEmail() throws MessagingException {
//        FacesContext context = FacesContext.getCurrentInstance();
//        Flash flash = context.getExternalContext().getFlash();
//        flash.setKeepMessages(true);
//        setTo(from);
//        // Establecer la dirección de correo a la que quieres enviar el mensaje
//        // setTo("destinatario@ejemplo.com"); // Cambia esta línea a la dirección deseada
//
////        if (buscarUsuario(Integer.parseInt(datoBuscado))) {
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", smtp);
//        props.put("mail.smtp.port", port);
//
//        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//
//        MimeMessage message = new MimeMessage(session);
//        try {
//            message.setContent(getDescr(), "text/plain");
//            message.setSubject(getSubject());
//            message.setFrom(new InternetAddress(getFrom()));
//            InternetAddress toAddress = new InternetAddress("fabrizioazeglio96@gmail.com"); // Inicializar aquí   ERROR AQUI
//            message.setRecipient(Message.RecipientType.TO, toAddress);
//            message.saveChanges();
//
//            Transport transport = session.getTransport("smtp");
//            transport.connect(this.smtp, this.port, this.username, this.password);
//
//            if (transport.isConnected()) {
//                transport.sendMessage(message, message.getAllRecipients());
//                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ok!", "Se le ha enviado un correo, revise su casilla de mensajes."));
//            } else {
//                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No se pudo conectar al servidor de correo."));
//            }
//            transport.close();
//
//        } catch (MessagingException me) {
//            me.printStackTrace();
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo enviar el correo, revise los datos e intente nuevamente."));
//        }
        System.out.println("FABRI");
        System.out.println(to);
//        } else {
//            System.out.println(to);
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No tenemos un usuario con ese DNI, reviselo e intente nuevamente."));
//        }
    }

    public boolean buscarUsuario(int s) {

//        FacesContext context = FacesContext.getCurrentInstance();
//        Flash flash = context.getExternalContext().getFlash();
//        flash.setKeepMessages(true);
//
//        List objeto = null;
//        DTOUsuario dtoUs = new DTOUsuario();
//        List<DTOUsuario> usuarioResultado = new ArrayList<>();
//        List<DTOCriterio> lCriterio = new ArrayList<>();
//        try {
//            if (s > 0) {
//                DTOCriterio unCriterio = new DTOCriterio();
//                unCriterio.setAtributo("dni");
//                unCriterio.setOperacion("=");
//                unCriterio.setValor(s);
//                lCriterio.add(unCriterio);
//
//                objeto = FachadaPersistencia.getInstance().buscar("Usuario", lCriterio);
//
//                for (Object x : objeto) {
//                    Usuario usuario = (Usuario) x;
//                    dtoUs.setDni(usuario.getDni());
//                    dtoUs.setDni(usuario.getDni());
//                    dtoUs.setDni(usuario.getDni());
//                    dtoUs.setDni(usuario.getDni());
//
//                    usuarioResultado.add(dtoUs);
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e.toString());
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No tenemos un usuario con ese DNI, reviselo e intente nuevamente."));
//        }
//        if (objeto.size() == 0) {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No tenemos un usuario con ese DNI, reviselo e intente nuevamente."));
//            return false;
//        } else {
//            return true;
//        }
        return true;
    }
}
