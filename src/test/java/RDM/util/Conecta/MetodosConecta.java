package RDM.util.Conecta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.jcraft.jsch.JSchException;

import RDM.util.GlobalVariables;
import RDM.util.SSHConnector;

public class MetodosConecta {

	private SSHConnector sshConnector = new SSHConnector();
	private String usuario = GlobalVariables.usuarioConecta;
	private String contrasena = GlobalVariables.contrasenaConecta;
	private String host = GlobalVariables.hostConecta;
	private int puerto = GlobalVariables.puerto;
	private Scanner scanner;

	public MetodosConecta() {

	}

	/**
	 * Metodo para iniciar conexion al repositorio
	 * 
	 * @author ricardo.palacios
	 * 
	 */
	public void abrirConexion() {

		try {
			sshConnector.connect(usuario, contrasena, host, puerto);
			if (sshConnector.isConected()) {
				assert sshConnector.isConected();
				
				System.out.println("Conectado al repositorio");
			}
			
		} catch (IllegalAccessException e) {
			System.out.println("error de acceso: " + e);
			e.printStackTrace();
		
		} catch (JSchException e) {
			System.out.println("error al accesar a sftp" + e);
			e.printStackTrace();
		}
	}

	/**
	 * Metodo validar columnas de el archivo
	 * 
	 * @param columnas
	 * @param columnasEnArchivo
	 * @param columnasFaltantes
	 * @return
	 * @author ricardo.palacios
	 */
	public boolean validarColumnas(String[] columnas, ArrayList<String> columnasEnArchivo,
			ArrayList<String> columnasFaltantes) {

		scanner = null;

		while (scanner.hasNext()) {
			columnasEnArchivo.add(scanner.next());
		}

		for (String columna : columnas) {
			if (!columnasEnArchivo.contains(columna)) {
				columnasFaltantes.add(columna);
			}
		}

		boolean hayFaltantes = columnasFaltantes.size() > 0;
		return hayFaltantes;
	}

	/**
	 * Metodo para cerrar conexion al repositorio
	 */
	public void cerrarConexion() {
		sshConnector.disconnect();
	}

	/**
	 * Metodo para buscar archivo en el repositorio
	 * 
	 * @param rutaRepositorio
	 * @param archivo
	 * @author ricardo.palacios
	 */
	public void BuscarArchivo(String rutaRepositorio, String archivo) {
		this.abrirConexion();
		
		try {
			String dirIncoming;
			String dirIncomingCsvFiles;
			String csvFile = null;

			// se busca bajando un nivel en la carpeta del repositorio
			dirIncoming = sshConnector.executeCommand("ls -l " + rutaRepositorio + " | grep '^-'");
			System.out.println("Archivos en el directorio \n" + rutaRepositorio + "\n \n" + dirIncoming);
			dirIncomingCsvFiles = sshConnector
					.executeCommand("find " + archivo + " -type f -name '*.csv' -maxdepth 1 -exec basename {}  \\;");
			csvFile = dirIncomingCsvFiles.substring(0, dirIncomingCsvFiles.length() - 1);
			System.out.println("El directorio contiene el archivo " + csvFile);
			this.cerrarConexion();

		} catch (IllegalAccessException e) {
			System.out.println("error de acceso: " + e);

		} catch (JSchException e) {
			System.out.println("error al accesar a sftp" + e);

		} catch (IOException e) {
			System.out.println("error de tipo file" + e);

		}

	}

}
