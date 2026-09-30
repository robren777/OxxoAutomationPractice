package WorkFlows.util.Planogramas;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.sikuli.script.FindFailed;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import WorkFlows.util.GlobalVariables;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class PomPlanogramas {
	
	private HashMap<String, JSONObject> jobs;
	private String dbUser = GlobalVariables.DB_USER_PLANOGRAMAS_FCPVEQA;
	private String dbPass = GlobalVariables.DB_PASS_PLANOGRAMAS_FCPVEQA;
	private String dbHost = GlobalVariables.DB_HOST_PLANOGRAMAS_FCPVEQA;
	
	private String userControlM = GlobalVariables.CTRLM_USER;
	private String passControlM = GlobalVariables.CTRLM_PASS;
	private String linea;
	private String headers[];
	private String data[];
	private String charola;
	private String compartidos;
	private String upc;
	private String ubicacion;
	private String numFrentes;
	private String numFrentesVertical;
	private String numProfundos;
	private String tipoMercadeo;
	private String capacidad;
	private String alto;
	private String ancho;
	private String profundo;
	private int numFiles;
	private int id;
	
	private BufferedReader lector;
	private SeleniumUtil u;
	private String query;
	
	private ChannelSftp sftpChannel;
    private Session session;
    private Channel channel;
	
	private TestCase testCase;
	private File[] files;
		
	By inputPass = By.id("login-user-password");
	
	
	/**
	 * @author José Zárate
	 * Constructor de la clase
	 * @param u ---------> Objeto selenium con el driver del navegador a utilizar
	 * @param testCase --> Objeto para el manejo de las evidencias 
	 */
	public PomPlanogramas(SeleniumUtil u, TestCase testCase, HashMap<String, String> dataProvider) {
		this.u = u;
		this.testCase = testCase;
	}
	
	
	/**
	 * @author José Zarate
	 * Verifica la existencia y la cantidada de archivos para la carga en el ftp
	 * @param pathFile --> Ruta donde se encuentra el archivo de carga
	 * @return --> True si el archivo existe, False en caso contrario
	 */
	public Boolean validateFileExist(String filePath) throws Exception {
		File archivo = new File(filePath);
		File[] lista = archivo.listFiles();
		numFiles = lista.length;	
		
		if(numFiles > 0) {
			files = archivo.listFiles(new FilenameFilter() {
		        public boolean accept(File dir, String name) {
		            return name.toLowerCase().endsWith(".csv");
		        }
		    });
			return true;
		}else {
			return false;
		}
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
	public void sftpCarga(BaseExecution bExecution, String host, String port, String user, String pass, String filePath, String fileName, String workingDirPath) throws NumberFormatException, JSchException, SftpException {
		String localPath = filePath;
		String Name = fileName;
		String sftpPath = workingDirPath;
		String sftpHost = host;
		String sftpPort = port;
		String sftpUser = user;
		String sftpPass = pass;
		
		bExecution.addStep("Acceder al FTP con servidor fcptlqa.femcom.net");
		testCase.addBoldTextEvidenceCurrentStep("Resultado esperado: Se accede al servidor correctamente. oxvctmpq1.femcom.net puerto 22 user: usradmpl Password n8MF3_jQx2qo");
	    JSch jsch = new JSch();
	    session = jsch.getSession(sftpUser, sftpHost, Integer.valueOf(sftpPort));
	    session.setConfig("StrictHostKeyChecking", "no");
	    session.setPassword(sftpPass);
	    System.out.println("Conectando ------------------");
	    session.connect();
	    assertTrue(session.isConnected(), "No se pudo conectar al ftp");
	    testCase.addTextEvidenceCurrentStep("Resultado: Se accedió al servidor de forma correcta");
	    System.out.println("Se establecio la conexión");
	    
	    channel = session.openChannel("sftp");
	    sftpChannel = (ChannelSftp) channel;
	    sftpChannel.connect();
	    System.out.println("Canal abierto");
	    
	    bExecution.addStep("Cargar los archivos correspondientes al set de legos <<<Set>>> en la ruta oxvctmpq1.femcom.net puerto 22.user: usradmpl / Password n8MF3_jQx2qo");
	    testCase.addBoldTextEvidenceCurrentStep("Resultado esperado:El archivo es cargado correctamente");
	    System.out.println("Copiando el archivo al sftp ---------");
	    sftpChannel.put(localPath+Name, sftpPath);
	    testCase.addTextEvidenceCurrentStep("Resultado: Se cargó de forma exitosa el archivo: "+ Name + " en la ruta: "+sftpPath);
	    System.out.println("Archivo copiado al sftp");
	    
	    sftpChannel.disconnect();
	    session.disconnect();
	    
	    System.out.println("sftp Desconectado");
	}
	
	/**
	 * @author José Zárate
	 * Realiza la carga de multiples archivos al servidor sftp
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
	public void sftpCargaMultiple(BaseExecution bExecution, String host, String port, String user, String pass, String filePath, String workingDirPath) throws NumberFormatException, JSchException, SftpException {
		
		String localPath = filePath;
		String sftpPath = workingDirPath;
		String sftpHost = host;
		String sftpPort = port;
		String sftpUser = user;
		String sftpPass = pass;
		
		for(int i = 0; i < numFiles; i++) {
			
			String[] names = files[i].toString().split("\\\\");
	        String name = names[names.length -1];
	        			
			if(i == 0) {
				bExecution.addStep("Acceder al FTP con servidor fcptlqa.femcom.net\r\n"
						+ "Resultado esperado: Se accede al servidor correctamente. oxvctmpq1.femcom.net puerto 22 user: usradmpl Password n8MF3_jQx2qo");
			}
			
		    JSch jsch = new JSch();
		    session = jsch.getSession(sftpUser, sftpHost, Integer.valueOf(sftpPort));
		    session.setConfig("StrictHostKeyChecking", "no");
		    session.setPassword(sftpPass);
		    System.out.println("Conectando ------------------");
		    session.connect();
		    assertTrue(session.isConnected(), "No se pudo conectar al ftp");
		    
		    if(i == 0) {		    	
			    testCase.addTextEvidenceCurrentStep("Se accedió al servidor de forma correcta");
		    }		    
		    System.out.println("Se establecio la conexión");
		    
		    channel = session.openChannel("sftp");
		    sftpChannel = (ChannelSftp) channel;
		    sftpChannel.connect();
		    System.out.println("Canal abierto");
		    if(i == 0) {
		    	bExecution.addStep("Cargar los archivos correspondientes al set de legos <<<Set>>> en la ruta oxvctmpq1.femcom.net puerto 22.user: usradmpl / Password n8MF3_jQx2qo\r\n"
						+ "Resultado esperado:El archivo es cargado correctamente.");
		    }		
		    
		    System.out.println("Copiando el archivo al sftp ---------");
		    sftpChannel.put(localPath+name, sftpPath);
		    testCase.addTextEvidenceCurrentStep("Se cargó de forma exitosa el archivo: "+ name + " en la ruta: "+sftpPath);
		    System.out.println("Archivo copiado al sftp");
		    
		    sftpChannel.disconnect();
		    session.disconnect();		    
		    System.out.println("sftp Desconectado");
		}
	}
	
	/**
     * Verifica que el archivo se haya cargado con exito
     * @param fileName --> Nombre del archivo a buscar
     * @return --> True en caso de encontrar el archivo, False en caso contrario
     * @throws JSchException 
     * @throws SftpException 
     */
    public boolean checkExist(String fileName, String workingDirPath) throws JSchException, SftpException {
        boolean existed = false;
 
        if (!session.isConnected()) {
            session.connect();
        }
        
        channel = session.openChannel("sftp");
        channel.connect();
        sftpChannel = (ChannelSftp) channel;
        sftpChannel.cd(workingDirPath+"/readFiles");

        Vector ls = sftpChannel.ls(workingDirPath+"/readFiles");
        
        if (ls != null) {
            for (int i = 0; i < ls.size(); i++) {

                LsEntry entry = (LsEntry) ls.elementAt(i);
                String file_name = entry.getFilename();
                if (!entry.getAttrs().isDir()) {

                    if (fileName.startsWith(file_name)) {
                        existed = true;
                        System.out.println("El archivo existe en la ruta del ftp");
                    }
                }
            }
        }

        sftpChannel.exit();
        channel.disconnect();
        session.disconnect();
 
        return existed;
    }
	
	/**
	 * Ejecuta el job especificado
	 * @param nombreJob --> Nombre del job a ejecutar.
	 * @return --> String con estatus del job ejecutado. 
	 */
	public Boolean ejecutarJob(String nombreJob) throws FindFailed, InterruptedException {
		testCase.addTextEvidenceCurrentStep("Ejecuta Job: "+nombreJob);
		
		JSONObject obj = new JSONObject(nombreJob);

		Control_mInicio CM = new Control_mInicio(u, this.userControlM, this.passControlM);
		
		this.u.get(GlobalVariables.CtrlM_URL);
		
		By inputPass = By.id("login-user-password");
		u.waitWebElement(inputPass, 120, 3);
		
		CM.logOn(); 

		ControlM control = new ControlM(u, testCase, obj); 

		control.searchJob(); 
		
		String resultadoEjecucion = control.executeJob(); 
		
		System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);
		String output = control.getOutput();
		System.out.println("Output: " +output);
		testCase.addTextEvidenceCurrentStep("Output: " +output);
				
		return true;
	}	
	
	/**
	 * Valida la carga de los legos con una consulta que debe retornar 12 mil registros
	 * @param bExecution   --> Clase para utilizar los métodos del framework
	 * @return --> True en caso de encontrar la totalidad de los registros, False en caso contrario
	 * @throws SQLException
	 */
	public Boolean validateCargaMultiple(BaseExecution bExecution, String tabla) throws SQLException {
		Boolean status = false;
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		query = "select count(*) From XXVA_SIV3."+tabla;
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean noExists = ExecuteQuery.isEmpty();
		
		if(!noExists) {
			if(ExecuteQuery.getRowCount() >= 12000) {
				testCase.addTextEvidenceCurrentStep("Se encontró la totalidad de los registros");
				db.close();
				status = true;
			}else {
				db.close();
				System.out.println("No se encontro la totaidad de los legos");
			}
		}else {
			db.close();
			System.out.println("No se encontro ningún registro en la tabla");
		}
		return status;
	}
	
	/**
	 * Valida que existan los registros de la carga diaria 
	 * @param bExecution --> Clase para utilizar los métodos del framework
	 * @return --> True en caso de encpontrar información, False en caso contrario
	 * @throws SQLException
	 */
	public Boolean validateCargaDiaria(BaseExecution bExecution) throws SQLException {
		Boolean status = false;
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		query = "SELECT A.CHAROLA, A.COMPARTIDOS, A.ITEM,A.ITEM_DESCRIPCION ,A.UBICACION_BANDEJA, A.NUM_FRENTES, A.NUM_FRENTES_VERTICAL, A.NUM_PROFUNDOS, \r\n"
				+ "A.TIPO_MERCADEO, A.TOTAL_UNIDADES, A.CAPACIDAD, A.ALTO, A.ANCHO, A.PROFUNDO, TO_CHAR(SYSDATE,'DD/MM/YYYY') AS FECHA \r\n"
				+ "FROM XXVA_SIV3.XXVA_ENG_PLANOGRAMA_APROBADO A, XXVA_SIV3.XXVA_SEM_LEGOS B \r\n"
				+ "WHERE A.LEGOS_ID = B.LEGOS_ID AND B.DESCRIPCION_LEGO = '10PGS_SIN_Refrescos-CF_(4)DI'";
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean noExists = ExecuteQuery.isEmpty();
		
		if(!noExists) {
			testCase.addTextEvidenceCurrentStep("Se encontró información en la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			db.close();
			status = true;
		}else {
			db.close();
			System.out.println("No se encontroaron registros");
		}
		return status;	
	}	
	
	/**
	 * Valida que existan legos sembrados 
	 * @param bExecution --> Clase para utilizar los metodos del framework
	 * @return --> True en caso de encontrar los legos sembrados
	 * @throws SQLException
	 */
	public Boolean validateLegosSembarados(BaseExecution bExecution) throws SQLException {
		Boolean status = false;
		SQLUtil db = new SQLUtil(dbHost, dbUser, dbPass);
		
		query = "select distinct legos_id from XXVA_SIV3.xxva_sem_sembrado_mueble A\r\n"
				+ "inner join XXVA_SIV3.xxva_sem_sembrado B on (A.sembrado_id = B.sembrado_id)\r\n"
				+ "inner join XXVA_SIV3.xxva_eng_tiendas_mv C on B.tienda_id= C.tienda_cr where legos_id in ()";
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean noExists = ExecuteQuery.isEmpty();
		
		if(!noExists) {
			testCase.addTextEvidenceCurrentStep("Existen legos sembrados");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			db.close();
			status = true;
		}else {
			db.close();
			System.out.println("No se encontroaron legos sembrados");
		}
		return status;			
	}
	
	/**
	 * @author José Zárate
	 * Valida que el archivo de carga contenga las cabeceras correctas y el valor del campo Accion sea el correcto
	 * @param filePath --> Ruta donde se encuentra el archivo
	 * @param fileName --> Nombre del archivo
	 * @param action ----> Tipo de acción a realizar
	 * @return --> True si el contenido del archivo es correcto, False en caso contrario
	 * @throws IOException
	 */
	public Boolean validateContenidoArchivo(String filePath, String fileName, String action) throws IOException {
		int count = 0;
		Boolean status = false;
		lector = new BufferedReader(new FileReader(filePath+fileName));

		while((linea = lector.readLine()) != null) {
			if(count == 0) {
				headers = linea.split(",");
				count ++;
			}else {
				data = linea.split(",");
			}			
		}
		
		String headersToString = String.join(" ", headers);
		String dataToString = String.join(" ", data);
		
		if(headersToString.contains("UPC Lego Charola Compartidos Ubicaci�n en la bandeja # Frentes #Fentes Verticales #_Profundos Tipo de Mercadeo Capacity Alto Ancho Profundo X_ITEM Y_ITEM Tipo_Fixture X_FIXTURE Y_FIXTURE Numero_Segmento Accion")
				&& dataToString.contains(action)) {
			lector.close();
			testCase.addTextEvidenceCurrentStep("Resultado: El contenido de las cabeceras es correcto, el valor del campo \"Acción\" es: "+action);
			status = true;
		}
				
		return status;
	}
	
	/**
	 * @author José Zárate
	 * Verifica que no exista el lego que se desea Agregar
	 * @param bExecution --> Clase para utilizar los metodos del framework
	 * @param filePath ----> Ruta donde se encuentra el archivo de carga
	 * @param fileName ----> Nombre del archivo de carga
	 * @return --> True en caso de no existir el lego, False en caso contrario
	 * @throws SQLException
	 * @throws IOException
	 */
	public Boolean validateExistenciaLegoAgregar(BaseExecution bExecution, String filePath, String fileName) throws SQLException, IOException {
				
		int count = 0;
		Boolean status = false;
		lector = new BufferedReader(new FileReader(filePath+fileName));

		while((linea = lector.readLine()) != null) {
			if(count == 0) {
				headers = linea.split(",");
				count ++;
			}else {
				data = linea.split(",");
			}			
		}
		
		charola = data[2].toString();
		compartidos = data[3].toString();
		upc = data[0].toString();
		ubicacion = data[4].toString();
		numFrentes = data[5].toString();
		numFrentesVertical = data[6].toString();
		numProfundos = data[7].toString();
		tipoMercadeo = data[8].toString();
		capacidad = data[9].toString();
		alto = data[10].toString();
		ancho = data[11].toString();
		profundo = data[12].toString();
	
		SQLUtil db = new SQLUtil(dbHost, dbUser, dbPass);
		
		query = "SELECT legos_id, charola, item, ubicacion_bandeja FROM XXVA_SIV3.XXVA_ENG_PLANOGRAMA_APROBADO WHERE CHAROLA = "+charola+" AND COMPARTIDOS = "+compartidos+" AND ITEM = "+upc
				+" AND UBICACION_BANDEJA = "+ubicacion+" AND NUM_FRENTES = "+numFrentes+" AND NUM_FRENTES_VERTICAL = "+numFrentesVertical+" AND NUM_PROFUNDOS = "+numProfundos+" AND TIPO_MERCADEO = '"+tipoMercadeo
				+"' AND CAPACIDAD = "+capacidad+" AND ALTO = "+alto+" AND ANCHO = "+ancho+" AND PROFUNDO = "+profundo;
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean empty = ExecuteQuery.isEmpty();
		
		if(empty) {
			testCase.addTextEvidenceCurrentStep("Resultado: No existe el registro en la base");
			db.close();
			status = true;
		}else {
			System.out.println("El lego ya existe en la base existe en la base");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			db.close();			
		}
		
		return status;
	}
	
	/**
	 * @author José Zárate
	 * Verifica que exista el lego y que no contenga la información que se desea modificar
	 * @param bExecution --> Clase para utilizar los métodos del framework
	 * @param filePath ----> Ruta donde se encuentra el archivo de carga
	 * @param fileName ----> Nombre del archivo de carga 
	 * @return --> True en caso de que exista el lego y no contenga la información a modificar, False en caso contrario
	 * @throws SQLException
	 * @throws IOException
	 */
	public Boolean validateExistenciaLegoModificar(BaseExecution bExecution, String filePath, String fileName) throws SQLException, IOException {
		
		int count = 0;
		Boolean status = true;
		lector = new BufferedReader(new FileReader(filePath+fileName));

		while((linea = lector.readLine()) != null) {
			if(count == 0) {
				headers = linea.split(",");
				count ++;
			}else {
				data = linea.split(",");
			}			
		}
		
		charola = data[2].toString();
		compartidos = data[3].toString();
		upc = data[0].toString();
		ubicacion = data[4].toString();
		numFrentes = data[5].toString();
		numFrentesVertical = data[6].toString();
		numProfundos = data[7].toString();
		tipoMercadeo = data[8].toString();
		capacidad = data[9].toString();
		alto = data[10].toString();
		ancho = data[11].toString();
		profundo = data[12].toString();
		
		getLegoId(bExecution, upc);
	
		SQLUtil db = new SQLUtil(dbHost, dbUser, dbPass);
		
		query = "select legos_id, charola, item, ubicacion_bandeja from xxva_siv3.XXVA_ENG_PLANOGRAMA_APROBADO where legos_id = "+id+" and item = "+upc+" and charola = "+charola+" and ubicacion_bandeja = "+ubicacion;
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean empty = ExecuteQuery.isEmpty();
		
		if(empty) {
			System.out.println("El lego no existe en la base");
			db.close();
			status = false;
		}else {
			testCase.addTextEvidenceCurrentStep("El lego existe en la base");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			db.close();			
			status = validateContenidoLego(bExecution, filePath, fileName);
		}
		
		return status;
	}

	
	public void getLegoId(BaseExecution bExecution, String upc) throws SQLException {
		 
		SQLUtil db = new SQLUtil(dbHost, dbUser, dbPass);
		query = "select * from xxva_siv3.xxva_eng_legos_mv where legos_desc = '"+upc+"'";
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean empty = ExecuteQuery.isEmpty();
		
		if(!empty) {
			id = Integer.parseInt(ExecuteQuery.getFirstData());
			System.out.println("El lego_id del lego es: "+id);
			db.close();
		}else {
			System.out.println("No se encontro un lego_id para el lego: "+upc);
			testCase.addTextEvidenceCurrentStep("No se encontro un lego_id para el lego: ");
			db.close();
		}
	}
	
	/**
	 * @author José Zárate
	 * Verifica que exista el lego que se desea eliminar
	 * @param bExecution --> Clase para utilizar los metodos del framework
	 * @param filePath ----> Ruta donde se encuentra el archivo de carga
	 * @param fileName ----> Nombre del archivo de carga
	 * @return --> True en caso de existir el lego, False en caso contrario
	 * @throws SQLException
	 * @throws IOException
	 */
	public Boolean validateExistenciaLegoEliminar(BaseExecution bExecution, String filePath, String fileName) throws SQLException, IOException {
				
		int count = 0;
		Boolean status = false;
		lector = new BufferedReader(new FileReader(filePath+fileName));

		while((linea = lector.readLine()) != null) {
			if(count == 0) {
				headers = linea.split(",");
				count ++;
			}else {
				data = linea.split(",");
			}			
		}
		
		charola = data[2].toString();
		compartidos = data[3].toString();
		upc = data[0].toString();
		ubicacion = data[4].toString();
		numFrentes = data[5].toString();
		numFrentesVertical = data[6].toString();
		numProfundos = data[7].toString();
		tipoMercadeo = data[8].toString();
		capacidad = data[9].toString();
		alto = data[10].toString();
		ancho = data[11].toString();
		profundo = data[12].toString();
	
		SQLUtil db = new SQLUtil(dbHost, dbUser, dbPass);
		
		query = "select legos_id, charola, item, ubicacion_bandeja from xxva_siv3.XXVA_ENG_PLANOGRAMA_APROBADO where legos_id = "+id+" and item = "+upc+" and charola = "+charola+" and ubicacion_bandeja = "+ubicacion;
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean empty = ExecuteQuery.isEmpty();
		
		if(empty) {
			System.out.println("El lego no existe en la base");
			db.close();			
		}else {
			testCase.addTextEvidenceCurrentStep("Resultado: El lego existe en la base");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			db.close();	
			status = true;
		}
		
		return status;
	}
	
	/**
	 * @author José Zárate
	 * Valida que el lego existente no contenga la información que se desea modificar
	 * @param bExecution --> Clase para utilizar los métodos del framework 
	 * @param filePath ----> Ruta donde se encuentra el archivo de carga
	 * @param fileName ----> Nombre del archivo de carga 
	 * @return --> True eb caso que el lego no contenga la información a modificar, False en caso contrario
	 * @throws SQLException
	 * @throws IOException
	 */
	public Boolean validateContenidoLego(BaseExecution bExecution, String filePath, String fileName) throws SQLException, IOException {
		
		int count = 0;
		Boolean status = false;
		lector = new BufferedReader(new FileReader(filePath+fileName));

		while((linea = lector.readLine()) != null) {
			if(count == 0) {
				headers = linea.split(",");
				count ++;
			}else {
				data = linea.split(",");
			}			
		}
		
		SQLUtil db = new SQLUtil(dbHost, dbUser, dbPass);
		
		query = "SELECT * FROM XXVA_SIV3.XXVA_ENG_PLANOGRAMA_APROBADO WHERE CHAROLA = "+charola+" AND COMPARTIDOS = "+compartidos+" AND ITEM = "+upc
				+" AND UBICACION_BANDEJA = "+ubicacion+" AND NUM_FRENTES = "+numFrentes+" AND NUM_FRENTES_VERTICAL = "+numFrentesVertical+" AND NUM_PROFUNDOS = "+numProfundos+" AND TIPO_MERCADEO = '"+tipoMercadeo
				+"' AND CAPACIDAD = "+capacidad+" AND ALTO = "+alto+" AND ANCHO = "+ancho+" AND PROFUNDO = "+profundo;
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean empty = ExecuteQuery.isEmpty();
		
		if(empty) {
			testCase.addTextEvidenceCurrentStep("El lego no contiene la información a modificar");
			db.close();
			status = true;
		}else {
			System.out.println("Resultado: El lego ya contiene la información a modificar");
			db.close();			
		}
		
		return status;
	}
	
	/**
	 * @author José Zárate
	 * Verifica que exista el lego cargado o modificado
	 * @param bExecution --> Clase para utilizar los métodos de framework
	 * @param tabla -------> Nombre de la tabla done se verifica la existencia del lego
	 * @param accion ------> Accion que realizara el lego en la base de datos
	 * @return --> True en caso de que exista el lego cargado o modificado, False en caso contrario
	 * @throws SQLException
	 */
	public Boolean validateLegoCargado(BaseExecution bExecution, String tabla, String accion) throws SQLException {
		Boolean status = false;
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		if(tabla.contains("XXVA_ENG_PLANOGRAMA_APROBADO")) {
			query = "SELECT LEGOS_ID, CHAROLA, ITEM, UBICACION_BANDEJA FROM XXVA_SIV3."+tabla+" WHERE CHAROLA = "+charola+" AND COMPARTIDOS = "+compartidos+" AND ITEM = "+upc
					+" AND UBICACION_BANDEJA = "+ubicacion+" AND NUM_FRENTES = "+numFrentes+" AND NUM_FRENTES_VERTICAL = "+numFrentesVertical+" AND NUM_PROFUNDOS = "+numProfundos+" AND TIPO_MERCADEO = '"+tipoMercadeo
					+"' AND CAPACIDAD = "+capacidad+" AND ALTO = "+alto+" AND ANCHO = "+ancho+" AND PROFUNDO = "+profundo;
		}else {
			query = "SELECT LEGOS_ID, CHAROLA, UPC, UBICACION_BANDEJA, ACCION FROM XXVA_SIV3."+tabla+" WHERE CHAROLA = "+charola+" AND COMPARTIDOS = "+compartidos+" AND UPC = "+upc
					+" AND UBICACION_BANDEJA = "+ubicacion+" AND NUM_FRENTES = "+numFrentes+" AND NUM_FRENTES_VERTICAL = "+numFrentesVertical+" AND NUM_PROFUNDOS = "+numProfundos+" AND TIPO_MERCADEO = '"+tipoMercadeo
					+"' AND CAPACIDAD = "+capacidad+" AND ALTO = "+alto+" AND ANCHO = "+ancho+" AND PROFUNDO = "+profundo;
		}		
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean empty = ExecuteQuery.isEmpty();
		
		if(!empty) {
			testCase.addTextEvidenceCurrentStep("Resultado: Se encontró el lego cargado");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			db.close();
			status = true;
		}else {
			System.out.println("Resultado: No se encontró el lego");
			db.close();
			
		}
		return status;
	}
	
	/**
	 * @author José Zárate
	 * Verifica que el lego se haya eliminado de forma correcta
	 * @param bExecution --> Clase para utilizar los métodos del framework
	 * @param tabla -------> Tabla donde ya no debe estar el lego
	 * @return --> True si el lego fue eliminado, false en caso contrario.
	 * @throws SQLException
	 */
	public Boolean validateLegoEliminado(BaseExecution bExecution, String tabla) throws SQLException {
		Boolean status = true;
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		query = "SELECT * FROM XXVA_SIV3."+tabla+" WHERE CHAROLA = "+charola+" AND COMPARTIDOS = "+compartidos+" AND ITEM = "+upc
				+" AND UBICACION_BANDEJA = "+ubicacion+" AND NUM_FRENTES = "+numFrentes+" AND NUM_FRENTES_VERTICAL = "+numFrentesVertical+" AND NUM_PROFUNDOS = "+numProfundos+" AND TIPO_MERCADEO = '"+tipoMercadeo
				+"' AND CAPACIDAD = "+capacidad+" AND ALTO = "+alto+" AND ANCHO = "+ancho+" AND PROFUNDO = "+profundo;
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean empty = ExecuteQuery.isEmpty();
		
		if(!empty) {
			testCase.addTextEvidenceCurrentStep("Resultado: Se encontró el lego cargado");
			testCase.addQueryEvidenceCurrentStep((SQLResult) ExecuteQuery.getAllData());
			db.close();
			status = false;
		}else {
			testCase.addTextEvidenceCurrentStep("Resultado: El archivo se borró de forma correcta");
			System.out.println("No se encontró el lego eliminado");
			db.close();
		}
		return status;
	}
}


