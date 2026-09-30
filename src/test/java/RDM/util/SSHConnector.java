package RDM.util;


import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
 
/**
* Clase encargada de establecer conexi�n y ejecutar comandos SSH.
*/
public class SSHConnector {

   /** 
    * Constante que representa un enter.
    */
   private static final String ENTER_KEY = "n";
   /**
    * Sesi�n SSH establecida.
    */
   private Session session;
 
   /**sshconnector
    * Establece una conexi�n SSH.
    *
    * @param username Nombre de usuario.
    * @param password Contrase�a.
    * @param host     Host a conectar.
    * @param port     Puerto del Host.
    *
    * @throws JSchException          Cualquier error al establecer
    *                                conexi�n SSH.
    * @throws IllegalAccessException Indica que ya existe una conexi�n
    *                                SSH establecida.
    */
   public void connect(String username, String password, String host, int port)
       throws JSchException, IllegalAccessException {
       if (this.session == null || !this.session.isConnected()) {
           JSch jsch = new JSch();

           this.session = jsch.getSession(username, host, port);
           this.session.setPassword(password);

           // Parametro para no validar key de conexion.
           this.session.setConfig("StrictHostKeyChecking", "no");

           this.session.connect();
       } else {
           throw new IllegalAccessException("Sesion SSH ya iniciada.");
       }
   }

   /**
    * Ejecuta un comando SSH.
    *
    * @param command Comando SSH a ejecutar.
    *
    * @return
    *
    * @throws IllegalAccessException Excepci�n lanzada cuando no hay
    *                                conexi�n establecida.
    * @throws JSchException          Excepci�n lanzada por alg�n
    *                                error en la ejecuci�n del comando
    *                                SSH.
    * @throws IOException            Excepci�n al leer el texto arrojado
    *                                luego de la ejecuci�n del comando
    *                                SSH.
    */
   public final String executeCommand(String command)
       throws IllegalAccessException, JSchException, IOException {
       if (this.session != null && this.session.isConnected()) {

           // Abrimos un canal SSH. Es como abrir una consola.
           ChannelExec channelExec = (ChannelExec) this.session.
               openChannel("exec");

           InputStream in = channelExec.getInputStream();

           // Ejecutamos el comando.
           channelExec.setCommand(command);
           channelExec.connect();

           // Obtenemos el texto impreso en la consola.
           BufferedReader reader = new BufferedReader(new InputStreamReader(in));
           StringBuilder builder = new StringBuilder();
           String linea;
           int x=0;
           while ((linea = reader.readLine()) != null) {
        	  // System.out.println(linea.toString());
               builder.append(linea);
               builder.append(ENTER_KEY);
               x=x+1;
           }
           String[] xml= new String[x];
//           reader.reset();
//           for(int i=0;i<x;i++) {
//        	   xml[i]=builder.;
//        	   System.out.println(xml[i]);
//
//           }

        

           // Cerramos el canal SSH.
           channelExec.disconnect();

           // Retornamos el texto impreso en la consola.
           return builder.toString();
       } else {
           throw new IllegalAccessException("No existe sesion SSH iniciada.");
       }
   }
   
   public boolean isConected() {
	   //System.out.println(this.session.getServerAliveInterval());
	   return this.session.isConnected();
   }

   /**
    * Cierra la sesi�n SSH.
    */
   public final void disconnect() {
       this.session.disconnect();
   }
}