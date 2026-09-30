package RDM.util.Conecta;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import RDM.util.GlobalVariables;

public class SFTPUtil {

	String user = GlobalVariables.usuarioConecta;
    String password = GlobalVariables.contrasenaConecta;
    String host = GlobalVariables.hostConecta;
    int port = Integer.valueOf(GlobalVariables.puerto);
    InputStream inputStream = null;
    InputStreamReader inputStreamReader = null;
    ChannelSftp sftpChannel = null; 
    Session session = null;
    		
    /**
     * Se conecta al servidor FTP
     * 
     * @author guerrero.vargas
     */
    public void conectar() {
    	JSch jsch = new JSch();
                
		try {
			session = jsch.getSession(user, host, port);
			session.setPassword(password);
	        session.setConfig("StrictHostKeyChecking", "no");
	        session.connect();
	        sftpChannel = (ChannelSftp) session.openChannel("sftp");
	        sftpChannel.connect();
	        
		} catch (JSchException e) {
			System.out.println("Hubo un error al conectarse al SFTP");
			e.printStackTrace();
		}
      
        System.out.println("Conectado al SFTP");
    }
    
    /**
     * Termina la conexion con el FTP
     */
    public void desconectar() {
    	session.disconnect();
    	sftpChannel.disconnect();
    	System.out.println("Desconectado del SFTP");
    }
    
    /**
     * Copia un archivo local al servidor
     * 
     * @param nombreArchivo Nombre del archivo que queremos copiar
     * @param rutaArchivo Ruta donde se encuentra el archivo
     * @param destinoFtp Ruta a donde vamos a copiar el archivo
     */
    public void copiarArchivoLocalAFTP(String nombreArchivo, String rutaArchivo, String destinoFtp) {
    	try {		
    		System.out.println(rutaArchivo + "\\" + nombreArchivo);
			sftpChannel.put(rutaArchivo + "\\" + nombreArchivo, destinoFtp);
			System.out.println("Archivo: " + rutaArchivo + nombreArchivo);
			System.out.println("Copiado a: " + destinoFtp);
			
		} catch (SftpException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Verifica si el archivo se encuentra en el ftp
     *  
     * @param pathFtpDir Ruta del FTP donde se va a buscar el archivo
     * @param filename Nombre del archivo que se va a buscar
     * @return true si encuentra el archivo, de lo contrario false
     * @author guerrero.vargas
     */
    public boolean existeArchivoEnSFtp(String pathFtpDir, String filename) {
		try {
			Vector<?> files = sftpChannel.ls(pathFtpDir);
			
			if (files == null) {
	    		System.out.println("No hay archivos en dir: " + pathFtpDir);
	    		return false;
	    	}
	    	
	    	for (int i = 0; i < files.size(); i++) {
	    		LsEntry entry = (LsEntry) files.elementAt(i);
	    		String file = entry.getFilename();
	    		
	    		if (filename.equals(file)) {
	    			return true;
	    		}
	    	}
	    	
		} catch (SftpException e) {
			e.printStackTrace();
		}    	
		
		return false;
    	
    }
    

    /**
     * Recorre la lista de archivos en una ruta FTP, obtiene la fecha de creacion y si tiene antiguedad
     * mayor a 7 dias de la fecha actual, la agrega a un hashmap
     * 
     * @param pathFtpDir Ruta FTP donde se van a examinar los archivos
     * @return HashMap<String, String> donde la key es el valor del archivo y el value es la fecha
     * @author guerrero.vargas
     */
    public String existeArchivoErr(String pathFtpDir, String filename) {
    	String regEx = "((\\d{1,20})(\\.err))";
		String filenameReg = filename + regEx;

		Pattern pattern = Pattern.compile(filenameReg);
    	
    	try {
			Vector<?> files = sftpChannel.ls(pathFtpDir);
	    	
	    	for (int i = 0; i < files.size(); i++) {
	    		LsEntry entry = (LsEntry) files.elementAt(i);
	    		String file = entry.getFilename();
	    		Matcher matcher = pattern.matcher(file);
	    		
	    		if (matcher.matches()) {
	    			return file;
	    		}
	    		
	    	}
	    	
		} catch (SftpException e) {
			e.printStackTrace();
		}    	
		
		return null;
    }
    
    /**
     * Verifica si exsite un archivo con extension .log en el ftp
     * 
     * @param pathFtpDir Ruta del ftp
     * @param filename nombre del archivo
     * @return true si encuentra un archivo en la ruta, false de lo contrario
     * @author guerrero.vargas
     */
    public String existeArchivoLog(String pathFtpDir, String filename) {
    	String regEx = "((\\d{1,20})(\\.log))";
		String filenameReg = filename + regEx;

		Pattern pattern = Pattern.compile(filenameReg);
    	
    	try {
			Vector<?> files = sftpChannel.ls(pathFtpDir);
	    	
	    	for (int i = 0; i < files.size(); i++) {
	    		LsEntry entry = (LsEntry) files.elementAt(i);
	    		String file = entry.getFilename();
	    		System.out.println(file);
	    		Matcher matcher = pattern.matcher(file);
	    		
	    		if (matcher.matches()) {
	    			return file;
	    		}
	    		
	    	}
	    	
		} catch (SftpException e) {
			e.printStackTrace();
		}    	
		
		return null;
    }
    
    /**
     * Muestra si existe un archivo con fecha de creacion mayor a 7 dias
     * 
     * @param pathFtpDir Ruta del ftp donde se va a buscar
     * @return HashMap<String, String> Con llave = nombre del archivo y value = fecha de creacion del archivo
     * @author guerrero.vargas
     */
    public HashMap<String, String> infoMayorA7Dias(String pathFtpDir) {
    	DateTimeFormatter format = DateTimeFormatter.ofPattern("d/MM/uuuu");
    	HashMap<String, String> map = new HashMap<>();
    	
    	try {
			Vector<?> files = sftpChannel.ls(pathFtpDir);
			
			if (files == null) {
	    		System.out.println("No hay archivos en dir: " + pathFtpDir);
	    		return null;
	    	}
	    	
			System.out.println(pathFtpDir);
			
	    	for (int i = 2; i < files.size(); i++) {
	    		LsEntry entry = (LsEntry) files.elementAt(i);
	    		String filename = entry.getFilename();
	    		SftpATTRS attrs = entry.getAttrs();
	    		
	    		LocalDateTime date = LocalDateTime.ofEpochSecond(attrs.getMTime(), 0, ZoneOffset.UTC);
	    		int compararFechas = date.compareTo(LocalDateTime.now().minusDays(8));
	    		
	    		if (compararFechas < 0) {
	    			map.put(filename, date.format(format));
	    		}	    		
	    	}
	    	
		} catch (SftpException e) {
			e.printStackTrace();
		}    	
		
		return map;	
    }
}

