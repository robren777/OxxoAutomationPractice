package Mobile.simpEtiqueteo.om;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import io.appium.java_client.AppiumDriver;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
/**
 * DESCRIPCION: Clase para metodos relacionados para proyecto Simplificacion Etiqueteo
 * 
 * @author Mario A. De Leon Rdz.
 * @date 15/octubre/2024
 */
public class CSVSimpEtiqueteo {
	AppiumUtil u;
	AppiumDriver driver;
	TestCase testCase;
	SQLResult sqlResult;
	SQLUtil sqlUtil;
	BaseExecution baseExecution;
	SimpEtiqueteo simp;
	SQLSimpEtiqueteo sqlSimpEtiq;

	public CSVSimpEtiqueteo (AppiumUtil u, TestCase testCase, BaseExecution baseExecution) {
		this.u = u;
		this.testCase = testCase;
		this.baseExecution = baseExecution;
		this.sqlSimpEtiq = new SQLSimpEtiqueteo(testCase, null);
		this.simp = new SimpEtiqueteo(testCase, u, null);
	}	
	/**
	 * Método para cargar un archivo CSV a un servidor SFTP.
	 * Este método genera la ruta de un archivo CSV basado en un nombre de prueba y un identificador, 
	 * y luego lo sube a un directorio remoto en un servidor SFTP. 
	 * Incluye una verificación para garantizar que el archivo se haya subido correctamente.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 20/noviembre/2024
	 * @param testname Nombre de la prueba, utilizado para generar el nombre del archivo CSV.
	 * @param idCSV Identificador del archivo CSV, utilizado para diferenciar archivos dentro de una misma prueba.
	 * @throws Exception Si ocurre algún error durante el proceso de conexión SFTP, subida o verificación del archivo.
	 */
	public void loadCSVtoFTP(String testname, String idCSV) throws Exception {
	    // Configuración de rutas
	    Path directorioTrabajo = Paths.get("").toAbsolutePath();
	    Path rutaConfig = directorioTrabajo.resolve("Config\\Input\\DataProviders\\Mobile");
	    String filePath = rutaConfig + "\\simpEtiqueteo\\CSVs\\";
	    String csvFilename = testname + idCSV + ".csv";
	    String csvUpload = filePath + csvFilename;

	    // Configuración de conexión SFTP
	    String sftpHost = "oxvctmpq1.femcom.net";
	    int sftpPort = 22;
	    String sftpUser = "usradmpl";
	    String sftpPassword = "n8MF3_jQx2qo";
	    String remoteDir = "/u11/usrplan/.usrplan/csv";

	    // Crea la sesión y el canal SFTP usando JSch
	    JSch jsch = new JSch();
	    Session session = jsch.getSession(sftpUser, sftpHost, sftpPort);
	    session.setPassword(sftpPassword);

	    // Configuración para no verificar claves del servidor (solo en entornos de prueba)
	    java.util.Properties config = new java.util.Properties();
	    config.put("StrictHostKeyChecking", "no");
	    session.setConfig(config);

	    // Conectar la sesión
	    session.connect();
	    System.out.println("Conexión SFTP establecida con éxito.");

	    // Abre el canal SFTP
	    ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
	    channelSftp.connect();

	    try (FileInputStream inputStream = new FileInputStream(csvUpload)) {
	        // Cambia al directorio remoto y sube el archivo
	        channelSftp.cd(remoteDir);
	        channelSftp.put(inputStream, csvFilename);
	        // Verificación de la subida
	        @SuppressWarnings("unchecked")
			Vector<ChannelSftp.LsEntry> archivosRemotos = channelSftp.ls(remoteDir);
	        boolean archivoEncontrado = archivosRemotos.stream()
	                .anyMatch(entry -> entry.getFilename().equals(csvFilename));
	        System.out.println("El archivo se ha subido correctamente al servidor SFTP.");
	        testCase.addTextEvidenceCurrentStep("El archivo se ha subido correctamente al servidor SFTP.");
	        if (archivoEncontrado) {
	            System.out.println("Verificación exitosa: El archivo '" + csvFilename + "' está en el servidor.");
	        } else {
	            System.err.println("Error: El archivo '" + csvFilename + "' no se encontró en el servidor.");
	            throw new Exception("El archivo no se encontró en el servidor tras la subida.");
	        }
	    } catch (Exception e) {
	        throw new Exception("Error al subir el archivo al servidor SFTP.", e);
	    } finally {
	        // Cierra el canal y desconecta la sesión
	        channelSftp.disconnect();
	        session.disconnect();
	        System.out.println("Conexión SFTP cerrada.");
	    }
	}
	
	/**
	* Método para editar un archivo CSV basado en un conjunto de columnas y valores.
	* Este método permite modificar los valores de columnas específicas en todas las filas del archivo CSV. 
	* Las modificaciones se definen en un arreglo de pares (columna, valor).
	* 
	* @author Mario A. De León Rdz.
	* @date 20/noviembre/2024
	* @param testname Nombre de la prueba, utilizado para generar el nombre del archivo CSV.
	* @param idCSV Identificador del archivo CSV, utilizado para diferenciar archivos dentro de una misma prueba.
	* @param columnasYValores Arreglo bidimensional donde cada par representa una columna y su nuevo valor.
	* @throws Exception Si ocurre algún error al leer, modificar o escribir el archivo CSV.
	*/
	public void editarCSV2(String testname, String idCSV, String[][] columnasYValores) throws Exception {
	    try {
	        // Configuración de rutas
	        Path directorioTrabajo = Paths.get("").toAbsolutePath();
	        Path rutaConfig = directorioTrabajo.resolve("Config\\Input\\DataProviders\\\\Mobile");
		    String filePath = rutaConfig + "\\simpEtiqueteo\\CSVs\\";
	        String csvFilename = testname + idCSV + ".csv";
	        Path rutaCompleta = Paths.get(filePath, csvFilename);

	        // Validar si el archivo existe
	        if (!Files.exists(rutaCompleta)) {
	            throw new Exception("El archivo CSV no existe: " + rutaCompleta);
	        }

	        // Leer archivo y preparar modificaciones
	        List<String> updatedLines = new ArrayList<>();
	        try (BufferedReader reader = Files.newBufferedReader(rutaCompleta, StandardCharsets.ISO_8859_1)) {
	            // Leer encabezado
	            String header = reader.readLine();
	            if (header == null) {
	                throw new Exception("El archivo CSV está vacío: " + rutaCompleta);
	            }

	            String[] columnas = header.split(",");
	            updatedLines.add(header); // Mantener encabezados en la salida

	            // Crear mapa de índices para columnas
	            Map<String, Integer> indicesColumnas = new HashMap<>();
	            for (int i = 0; i < columnas.length; i++) {
	                indicesColumnas.put(columnas[i].trim(), i);
	            }

	            // Leer y actualizar las líneas
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] campos = line.split(",");
	                if (campos.length != columnas.length) {
	                    throw new Exception("La fila tiene un número diferente de columnas que el encabezado.");
	                }

	                // Modificar la fila
	                for (String[] columnaValor : columnasYValores) {
	                    String columna = columnaValor[0];
	                    String valor = columnaValor[1];

	                    // Verificar si la columna existe
	                    Integer index = indicesColumnas.get(columna);
	                    if (index == null) {
	                        throw new Exception("La columna '" + columna + "' no existe en el archivo CSV.");
	                    }

	                    // Actualizar el valor en la fila
	                    campos[index] = valor;
	                }
	                updatedLines.add(String.join(",", campos));
	            }
	        }

	        // Escribir cambios al archivo
	        try (BufferedWriter writer = Files.newBufferedWriter(rutaCompleta, StandardCharsets.ISO_8859_1)) {
	            for (String updatedLine : updatedLines) {
	                writer.write(updatedLine);
	                writer.newLine();
	            }
	        }
	        testCase.addTextEvidenceCurrentStep("Se edita archivo CSV correctamente");
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Método para editar un archivo CSV específico modificando las filas 2 y 3 con los valores proporcionados.
	 * Este método permite actualizar las filas 2 y 3 de un archivo CSV con los nuevos valores definidos en los arreglos `fila2` y `fila3`.
	 * Se realizan verificaciones para asegurar que el archivo existe y que las filas tienen la misma cantidad de columnas que el encabezado.
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 20/noviembre/2024
	 * @param testname Nombre de la prueba, utilizado para generar el nombre del archivo CSV.
	 * @param idCSV Identificador del archivo CSV, utilizado para diferenciar archivos dentro de una misma prueba.
	 * @param fila2 Arreglo bidimensional con las modificaciones a aplicar en la fila 2 del CSV.
	 * @param fila3 Arreglo bidimensional con las modificaciones a aplicar en la fila 3 del CSV.
	 * @throws Exception Si ocurre algún error al leer, modificar o escribir el archivo CSV.
	 */
	public void editarCSVPorFila(String testname, String idCSV, String[][] fila2, String[][] fila3) throws Exception {
	    try {
	        Path directorioTrabajo = Paths.get("").toAbsolutePath();
	        Path rutaConfig = directorioTrabajo.resolve("Config\\Input\\DataProviders\\\\Mobile");
		    String filePath = rutaConfig + "\\simpEtiqueteo\\CSVs\\";
	        String csvFilename = testname + idCSV + ".csv";
	        Path rutaCompleta = Paths.get(filePath, csvFilename);

	        if (!Files.exists(rutaCompleta)) {
	            throw new Exception("El archivo CSV no existe: " + rutaCompleta);
	        }

	        List<String> updatedLines = new ArrayList<>();
	        try (BufferedReader reader = Files.newBufferedReader(rutaCompleta, StandardCharsets.ISO_8859_1)) {
	            String header = reader.readLine();
	            if (header == null) {
	                throw new Exception("El archivo CSV está vacío: " + rutaCompleta);
	            }

	            String[] columnas = header.split(",");
	            updatedLines.add(header);

	            Map<String, Integer> indicesColumnas = new HashMap<>();
	            for (int i = 0; i < columnas.length; i++) {
	                indicesColumnas.put(columnas[i].trim(), i);
	            }

	            String line;
	            int filaActual = 0;
	            while ((line = reader.readLine()) != null) {
	                filaActual++;
	                String[] campos = line.split(",");
	                if (campos.length != columnas.length) {
	                    throw new Exception("La fila tiene un número diferente de columnas que el encabezado.");
	                }

	                if (filaActual == 2) {
	                    for (String[] columnaValor : fila2) {
	                        String columna = columnaValor[0];
	                        String valor = columnaValor[1];
	                        Integer index = indicesColumnas.get(columna);
	                        if (index != null) {
	                            campos[index] = valor;
	                        }
	                    }
	                } else if (filaActual == 3) {
	                    for (String[] columnaValor : fila3) {
	                        String columna = columnaValor[0];
	                        String valor = columnaValor[1];
	                        Integer index = indicesColumnas.get(columna);
	                        if (index != null) {
	                            campos[index] = valor;
	                        }
	                    }
	                }
	                updatedLines.add(String.join(",", campos));
	            }
	        }

	        try (BufferedWriter writer = Files.newBufferedWriter(rutaCompleta, StandardCharsets.ISO_8859_1)) {
	            for (String updatedLine : updatedLines) {
	                writer.write(updatedLine);
	                writer.newLine();
	            }
	        }
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método para validar el estatus de una modificacion por CSV solo utilizando el SKU
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 20/noviembre/2024
	 * @throws Exception Si ocurre un error al realizar la consulta
	 */
    public String StatusCSV(String sku, String dbSatelites) throws InterruptedException, SQLException {
		try {
			String consultaSQL = "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS FROM [Satelites].[dbo].[CO_LB_INTL_OX] \n"
					+ "WHERE sku='" + sku + "'";
	
			SQLResult resultadoSQL = sqlSimpEtiq.sqlExecuteSinEv(consultaSQL, dbSatelites, sqlSimpEtiq.DB_USER_XPOS, sqlSimpEtiq.DB_PASSWORD_XPOS);
	
			String statusCSV = null;
			if (resultadoSQL.getRowCount() > 0) {
				statusCSV = resultadoSQL.getData(0, "PLANOGROUP");
			} else {
				statusCSV = null;
			}
			return statusCSV;
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta SQL en StatusCSV: " + e.getMessage());
			e.printStackTrace();
			throw e; // Relanzamos la excepción para manejo superior
		} catch (Exception e) {
			System.err.println("Error inesperado en StatusCSV: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Error inesperado al consultar StatusCSV", e);
		}
	}

	/**
	 * Método para validar el estatus de una modificacion por CSV utilizando el SKU y teniedno como datos el campo y valor que se modificaron
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 20/noviembre/2024
	 * @throws Exception Si ocurre un error al realizar la consulta
	 */
    public String StatusCSV(String sku, String campo, String valor, String dbSatelites) throws InterruptedException, SQLException {
		try {
			String consultaSQL = "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS FROM [Satelites].[dbo].[CO_LB_INTL_OX] "
					+ "WHERE sku='" + sku + "'";
			SQLResult resultadoSQL = sqlSimpEtiq.sqlExecuteSinEv(consultaSQL, dbSatelites, sqlSimpEtiq.DB_USER_XPOS, sqlSimpEtiq.DB_PASSWORD_XPOS);
			
			String statusCSV = null;
			if (resultadoSQL.getData(0, campo).equals(valor)) {
				statusCSV = resultadoSQL.getData(0, campo);
			} else {
				System.out.println(resultadoSQL.getData(0, campo));
				statusCSV = "0";
			}
			return statusCSV;
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta SQL en StatusCSV: " + e.getMessage());
			e.printStackTrace();
			throw e; // Relanzamos la excepción para manejo superior
		} catch (Exception e) {
			System.err.println("Error inesperado en StatusCSV: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Error inesperado al consultar StatusCSV", e);
		}
	}
	/**
	 * Método para validar el estatus de una modificacion por CSV utilizando el SKU y teniedno como datos el campo y valor que se modificaron
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 20/noviembre/2024
	 * @throws Exception Si ocurre un error al realizar la consulta
	 */
    public String StatusCSV(String sku, String campo, String valor, String dbSatelites, String ordenValidacion) throws InterruptedException, SQLException {
		try {
			String consultaSQL = "SELECT ITEM_ID, SKU, NAME, AMOUNT, INVENTORY, STATUS_CODE, PLANOGROUP, FORNITURE, BAYS, TRAY, SHARED_TRAY, LOCATION, FRONTS FROM [Satelites].[dbo].[CO_LB_INTL_OX] \n"
					+ "WHERE sku='" + sku + "' "+ordenValidacion;
			SQLResult resultadoSQL = sqlSimpEtiq.sqlExecuteSinEv(consultaSQL, dbSatelites, sqlSimpEtiq.DB_USER_XPOS, sqlSimpEtiq.DB_PASSWORD_XPOS);
			
			String statusCSV = null;
			if (resultadoSQL.getData(0, campo).equals(valor)) {
				statusCSV = resultadoSQL.getData(0, campo);
			} else {
				System.out.println("Se esperaba el valor: ["+valor+"] en el campo: "+campo+" pero se encuentra el valor: ["+resultadoSQL.getData(0, campo)+"]");
				sqlSimpEtiq.imprimirResult(resultadoSQL);
				statusCSV = "0";
			}
			return statusCSV;
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta SQL en StatusCSV: " + e.getMessage());
			e.printStackTrace();
			throw e; // Relanzamos la excepción para manejo superior
		} catch (Exception e) {
			System.err.println("Error inesperado en StatusCSV: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Error inesperado al consultar StatusCSV", e);
		}
	}

    /**
	 * Método para validar el cambio del estatus del CSV utlizando StatusCSV(sku), se utiliza normalmente al asignar un planogrupo a un articulo sin planogrupo
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 20/noviembre/2024
	 * @throws Exception Si ocurre un error al realizar la consulta
	 */
	public void cambioStatusCSV(String sku, String testname, String idCSV, String dbSatelites) throws InterruptedException, Exception {
		try {
			int status = 0;
			int flagstatus = 0;
			String estatus = null;
			long startTime = System.currentTimeMillis(); // Tiempo de inicio
			do {
				estatus = StatusCSV(sku, dbSatelites);
				if (estatus == null) {
					Aviso("Se aplica CSV correctamente", 3000);
					System.out.println("Se aplica CSV correctamente");
					flagstatus = 1;
				} else {
					Aviso("El CSV no se ha reflejado en BD int status = " + status, 3000);
					simp.clickBusquedaFechaSinEv();
					u.wait(3);
					simp.regresarApp();
					sqlSimpEtiq.sqlActualizaFechaPlanograma(dbSatelites);
					simp.esperaPantallaDeCargaSinEv();
				}
				// Verifica si han pasado 10 minutos (600,000 ms)
				long currentTime = System.currentTimeMillis();
				if (currentTime - startTime >= 300000) {
					Aviso("Han pasado 5 minutos. Saliendo del ciclo.", 3000);
					System.out.println("Han pasado 5 minutos. Saliendo del ciclo.");
					break; // Sale del ciclo
				}
			} while (flagstatus == 0);
		} catch (SQLException e) {
			System.err.println("Error al ejecutar consulta SQL en cambioStatusCSV: " + e.getMessage());
			e.printStackTrace();
			throw e; // Relanzamos la excepción para manejo superior
		} catch (Exception e) {
			System.err.println("Error inesperado en cambioStatusCSV: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Error inesperado al cambiar el estado del CSV", e);
		}
	}	
	 
	/**
	 * Método para validar el cambio del estatus del CSV utlizando StatusCSV(sku, valor, testname)
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 20/noviembre/2024
	 * @throws Exception Si ocurre un error al realizar la consulta
	 */
	public void cambioStatusCSV(String sku, String campo, String valor, String testname, String idCSV, String dbSatelites) throws InterruptedException, Exception {
		try {
			int status = 0;
			int flagstatus = 0;
			String estatus = null;
			long startTime = System.currentTimeMillis(); // Tiempo de inicio
			do {
				estatus = StatusCSV(sku, campo, valor, dbSatelites);
				if (estatus.equals(valor)) {
					Aviso("Se muestran cambios relacionado al archivo .csv en BDD", 3000);
					System.out.println("Se muestran cambios relacionado al archivo .csv en BDD");
					testCase.addTextEvidenceCurrentStep("Se muestran cambios relacionado al archivo .csv en BDD");
					flagstatus = 1;
				} else {
					Aviso("El CSV no se ha reflejado en BD int status = " + status, 3000);
					simp.clickBusquedaFechaSinEv();
					u.wait(3);
					simp.regresarApp();
					sqlSimpEtiq.sqlActualizaFechaPlanograma(dbSatelites);
					simp.esperaPantallaDeCargaSinEv();
				}
				// Verifica si han pasado 10 minutos (600,000 ms)
				long currentTime = System.currentTimeMillis();
				if (currentTime - startTime >= 300000) {
					Aviso("Han pasado 5 minutos. Saliendo del ciclo.", 3000);
					System.out.println("Han pasado 5 minutos. Saliendo del ciclo.");
					break; // Sale del ciclo
				}
			} while (flagstatus == 0);
		} catch (SQLException e) {
			System.err.println("Error al ejecutar consulta SQL en cambioStatusCSV: " + e.getMessage());
			e.printStackTrace();
			throw e; // Relanzamos la excepción para manejo superior
		} catch (Exception e) {
			System.err.println("Error inesperado en cambioStatusCSV: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Error inesperado al cambiar el estado del CSV", e);
		}
	}
	
	/**
	 * Método para validar el cambio del estatus del CSV utlizando StatusCSV(sku, valor, testname)
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 20/noviembre/2024
	 * @throws Exception Si ocurre un error al realizar la consulta
	 */
	public void cambioStatusCSV(String sku, String campo, String valor, String testname, String idCSV, String dbSatelites, String ordenValidacion) throws InterruptedException, Exception {
		try {
			int status = 0;
			int flagstatus = 0;
			String estatus = null;
			long startTime = System.currentTimeMillis(); // Tiempo de inicio
			do {
				estatus = StatusCSV(sku, campo, valor, dbSatelites, ordenValidacion);
				if (estatus.equals(valor)) {
					Aviso("Se muestran cambios relacionado al archivo .csv en BDD", 3000);
					System.out.println("Se muestran cambios relacionado al archivo .csv en BDD");
					testCase.addTextEvidenceCurrentStep("Se muestran cambios relacionado al archivo .csv en BDD");
					flagstatus = 1;
				} else {
					Aviso("El CSV no se ha reflejado en BD int status = " + status, 3000);
					simp.clickBusquedaFechaSinEv();
					u.wait(3);
					simp.regresarApp();
					sqlSimpEtiq.sqlActualizaFechaPlanograma(dbSatelites);
					simp.esperaPantallaDeCargaSinEv();
				}
				// Verifica si han pasado 10 minutos (600,000 ms)
				long currentTime = System.currentTimeMillis();
				if (currentTime - startTime >= 300000) {
					Aviso("Han pasado 5 minutos. Saliendo del ciclo.", 3000);
					System.out.println("Han pasado 5 minutos. Saliendo del ciclo.");
					break; // Sale del ciclo
				}
			} while (flagstatus == 0);
		} catch (SQLException e) {
			System.err.println("Error al ejecutar consulta SQL en cambioStatusCSV: " + e.getMessage());
			e.printStackTrace();
			throw e; // Relanzamos la excepción para manejo superior
		} catch (Exception e) {
			System.err.println("Error inesperado en cambioStatusCSV: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Error inesperado al cambiar el estado del CSV", e);
		}
	}
	
	/**
	 * Método para mostrar un aviso del estatus en el que se encuentra el XML en BD
	 * 
	 * @author Mario A. De León Rdz.
	 * @date 20/noviembre/2024
	 * @throws Exception Si ocurre un error al realizar la consulta
	 */
	public void Aviso(String mensaje, int duracionMilisegundos) {
		try {
			JFrame frame = new JFrame("Aviso");
			frame.setSize(300, 200);
			frame.setVisible(false);
	
			final JOptionPane optionPane = new JOptionPane(mensaje, JOptionPane.INFORMATION_MESSAGE,
					JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
			final JDialog dialog = optionPane.createDialog(frame, "Mensaje");
	
			Timer timer = new Timer(duracionMilisegundos, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
				}
			});
	
			timer.setRepeats(false);
			timer.start();
	
			dialog.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (Exception e) {
			System.err.println("Error al mostrar el aviso: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
