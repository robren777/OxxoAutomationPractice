package WorkFlows.util.PlanogramasNexxo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import utils.SFTPUtil;

public class conectSFTP {
	
	
    SFTPUtil sftpUtils = new SFTPUtil();
	String BASE_PATH = System.getProperty("user.dir");


	
	
	public boolean fileExist(String ip,String user, String ps, String puerto, String ruta, String fileName) {
	      
        sftpUtils.setHostName(ip);
        sftpUtils.setHostPort(puerto);
        sftpUtils.setUserName(user);
        sftpUtils.setPassWord(ps);
        sftpUtils.setDestinationDir(ruta);
        boolean existe = sftpUtils.checkExist(fileName);
        

        return existe;
	}
	
	
	/**
	 * @author José Zárate
	 * Realiza la conexión y carga de archivos a un servidor Sftp
	 * @param bExecution ------> Clase para la captura de evidencias y generación de los reportes
	 * @param host ------------> Servidor al cual se realizará la conexión 
	 * @param port ------------> Puerto de conexión
	 * @param user ------------> Usuario para conexión al servidor
	 * @param pass ------------> Contraseña para conexión al servidor
	 * @param filePath --------> Ruta del archivo a cargar
	 * @param fileName --------> Nombre del archivo a cargar
	 * @param workingDirPath --> Ruta del servidor donde se cargara el archivo
	 * @throws NumberFormatException
	 * @throws JSchException
	 * @throws SftpException
	 */
	public void sftpCarga(String host, String port, String user, String pass, String filePath, String fileName, String workingDirPath) throws NumberFormatException, JSchException, SftpException {
	
		String localPath = filePath;
		String Name = fileName;
		String sftpPath = workingDirPath;
		String sftpHost = host;
		String sftpPort = port;
		String sftpUser = user;
		String sftpPass = pass;
		
	
	    JSch jsch = new JSch();
	    Session session = jsch.getSession(sftpUser, sftpHost, Integer.valueOf(sftpPort));
	    session.setConfig("StrictHostKeyChecking", "no");
	    session.setPassword(sftpPass);
	    System.out.println("Conectando ------------------");
	    session.connect();
	
	    System.out.println("Se establecio la conexión");
	    
	    Channel channel = session.openChannel("sftp");
	    ChannelSftp sftpChannel = (ChannelSftp) channel;
	    sftpChannel.connect();
	    System.out.println("Canal abierto");
	    
	    sftpChannel.put(localPath+Name, sftpPath);
	    System.out.println("Archivo copiado al sftp");
	    
	    sftpChannel.disconnect();
	    session.disconnect();
	    
	    System.out.println("sftp Desconectado");
	}

}
