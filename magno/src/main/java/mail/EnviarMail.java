/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.io.IOException;

/**
 *
 * @author ihsa
 */
public class EnviarMail {
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
//        String rutaAdjunto = "/home/ihsa/proyectoEdwin/plantilla.xlsx";       
        //sesionMail.enviarMensaje("jorodriguez@ihsa.mx", "mluis@ihsa.mx", "prueba", "Haz ganado un bono de 50.00000.... jejeje no es cierto es una prueba de envio de correos...",rutaAdjunto);
        //sesionMail.enviarMensaje(args[0], args[1], args[2], args[3],args[4]);
        
        SesionMail sesionMail = new SesionMail();
        sesionMail.setPara("velocirraptor79@hotmail.com");
        sesionMail.setAsunto("Prueba");
        sesionMail.setCc("");
        sesionMail.setCuerpo("Hola esto es una prueba");
        sesionMail.enviarMensaje();
        
    }

}
