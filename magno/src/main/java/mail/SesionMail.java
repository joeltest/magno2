/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeSet;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author ihsa
 */
public class SesionMail {

    String para;   

    
    String cc;
    String asunto;
    String cuerpo;
    String rutaAdjunto = "";
    String nombreAdjunto = "";
    String suffixAdjunto = "";
    
    static Properties props;
    Session sesion;
//    private MimeBodyPart logoSia = new MimeBodyPart();
//    private MimeBodyPart archivoAdjunto = new MimeBodyPart();
    private BodyPart texto = new MimeBodyPart();
    private Multipart mpRelated = new MimeMultipart("related");
    private Logger logger;

    public SesionMail() {        
        props = new Properties();
        props.setProperty("mail.smtp.user", Constantes.mail_smtp_user);
        props.setProperty("mail.smtp.user.username", Constantes.mail_smtp_user_username);
        props.setProperty("mail.smtp.user.password", Constantes.mail_smtp_user_password);
        props.setProperty("mail.smtp.port",Constantes.mail_smtp_port);
        props.setProperty("mail.smtp.host", Constantes.mail_smtp_host);
        props.setProperty("smtp.mailTo", Constantes.smtp_mailTo);
        props.setProperty("mail.smtp.starttls.enable",Constantes.mail_smtp_starttls_enable);
        props.setProperty("mail.smtp.debug",Constantes.mail_smtp_debug);
        props.setProperty("mail.smtp.auth", Constantes.mail_smtp_auth);
        props.setProperty("mail.smtp.socketFactory.port", Constantes.mail_smtp_socketFactory_port);
        props.setProperty("mail.smtp.socketFactory.class", Constantes.mail_smtp_socketFactory_class);
        props.setProperty("mail.smtp.socketFactory.fallback",Constantes.mail_smtp_socketFactory_fallback);

        sesion = Session.getDefaultInstance(props, null);
        sesion.setDebug(Boolean.valueOf(Constantes.mail_smtp_debug));

    }

    public void enviarMensaje() throws UnsupportedEncodingException {
        log("Preparando correo...............");
        log("Para  :  " + para);
        log("CC  : " + cc);
        //Construimos el Mensaje
        MimeMessage mensaje = new MimeMessage(sesion);
        this.mpRelated = new MimeMultipart("related");
        try {
            if (!para.isEmpty()) {
                for (Iterator it = obtenerListaCorreoNoDuplicados(para).iterator(); it.hasNext();) {
                    String cp = (String) it.next();
                    mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(cp.trim()));
                }
            }
            if (!cc.isEmpty()) {
                for (Iterator it = obtenerListaCorreoNoDuplicados(cc).iterator(); it.hasNext();) {
                    String ccp = (String) it.next();
                    mensaje.addRecipient(Message.RecipientType.CC, new InternetAddress(ccp.trim()));
                }
            }
            InternetAddress from = new InternetAddress(props.getProperty("mail.smtp.user"));
            mensaje.setFrom(from);
            mensaje.setSubject(asunto.toUpperCase(), "utf-8");
            texto.setContent(cuerpo, "text/html; charset=" + "utf-8");
            mpRelated.addBodyPart(texto);
            mensaje.setContent(mpRelated);
//            if (adjuntarArchivo()) {

            Transport transport = sesion.getTransport("smtp");
            transport.connect(props.getProperty("mail.smtp.host"),
                    Integer.valueOf(props.getProperty("mail.smtp.port")),
                    props.getProperty("mail.smtp.user"),
                    props.getProperty("mail.smtp.user.password"));
            transport.sendMessage(mensaje, mensaje.getAllRecipients());
            transport.close();
            log("****************************CORREO ENVIADO CORRECTAMENTE...***********************");
//            }else{
//                log("****************************NO SE ENVIO EL CORREO DEBIDO A UN ERROR D ADJUNTO...***********************");
//            }

        } catch (MessagingException ex) {
            ex.printStackTrace();
            log("No se envio el msg    # # # # " + ex.getMessage() + "  CAUSA  : : : : : :   " + ex.getCause());

        }
    }

    private TreeSet obtenerListaCorreoNoDuplicados(String direcciones) {
        String[] listaDirecciones = direcciones.split(",");
        TreeSet noDuplicados = new TreeSet();
        noDuplicados.clear();
        for (String lista : listaDirecciones) {
            noDuplicados.add(lista.trim());
        }
        return noDuplicados;
    }

//    private boolean adjuntarArchivo() {
//        log("Preparando el adjunto...");
//        log("Archivo : " + rutaAdjunto + nombreAdjunto);
//        File adjunto = null;
//        adjunto = new File(new StringBuilder().append(rutaAdjunto).append(nombreAdjunto).toString());
//        try {
//            DataSource file = new FileDataSource(adjunto);
//            if (adjunto != null && adjunto.exists()) {
//                this.archivoAdjunto.setDataHandler(new DataHandler(file));
//                this.archivoAdjunto.setFileName(file.getName());
//                this.mpRelated.addBodyPart(this.archivoAdjunto);
//                log("Ruta valida, se adjunto exitosamente el archivo");
//                return true;
//            } else {
//                log("ERROR : No existe el archivo en la ruta definida :( ");
//                return false;
//            }
//        } catch (MessagingException ex) {
//            log("Excepción al adjuntar el archivo " + ex.getMessage());
//            return false;
//        }
//    }
//
//    private void procesarLogos(byte[] logo) {
//        try {
//            if (logo != null) {
//                DataSource dslogoSia = new ByteArrayDataSource(logo, "application/octet-stream");
//                logoSia.setDataHandler(new DataHandler(dslogoSia));
//                logoSia.setHeader("Content-ID", "<logoSia>");
//                mpRelated.addBodyPart(logoSia);
//            }
//        } catch (MessagingException ex) {
//            System.err.println(ex.getMessage());
//        }
//    }

    private void log(String mensaje) {
        System.out.println(">>>>>>>>>> " + mensaje);
    }
    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }
    
     public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public BodyPart getTexto() {
        return texto;
    }

    public void setTexto(BodyPart texto) {
        this.texto = texto;
    }
}
