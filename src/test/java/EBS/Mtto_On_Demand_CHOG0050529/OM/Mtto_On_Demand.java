package EBS.Mtto_On_Demand_CHOG0050529.OM;

import static org.testng.Assert.assertTrue;

import java.io.File; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.AssertJUnit;

import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import exceptions.FrameworkException;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;




/**
 * DESCRIPCION: Clase de utileria para manejo EBS Mtto on Demand
 * 
 * @author Michelle Vásquez
 * @date 01/Abril/2025
 */
public class Mtto_On_Demand {
	
	SikuliUtil sk;
	Pattern pathimg;
	ArrayList<String> ListImage;
	HashMap<String, Pattern> ImgPattern;
	ArrayList<Pattern> patterns;
	File Fol;
	int shortWait = 4;
	HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
	TestCase testCase;
	
	
	Screen s = new Screen();
	
	SeleniumUtil u;
	
	File FileFol;
	SQLResult queryResult;
	
	
	public Mtto_On_Demand (SeleniumUtil u, TestCase testCase) {
		this.u = new SeleniumUtil(new IETest(), true);
		this.testCase = testCase;		
		this.sk = new SikuliUtil();
	}
	
	/**
	 * Metodo para seleccionar organizacion
	 * @param organizacion agregar organizacion a validars
	 * @author michelle.vasquez@gruptimexico.com
	 * @throws Exception 
	 * @since 31/04/2025
	 */
	public void selecOrganizacion (String organizacion) throws Exception {
		try{
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		sk.getRegion("EBS\\\\buscarOrg").click();
		u.hardWait(shortWait);
		sk.screen.type(organizacion);
		u.hardWait(shortWait);
		sk.getRegion("EBS\\\\buscarOrga").click();
		testCase.addScreenShotCurrentStep("");		
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	public void cerrarVenOrga () throws Exception {
		try{			
			sk.getRegion("EBS\\\\cerrar2").click();
			u.hardWait(2);		
			sk.getRegion("EBS\\\\cerrar2").click();
			u.hardWait(2);
			sk.getRegion("EBS\\\\cerrar2").click();
			u.hardWait(2);
			sk.getRegion("EBS\\\\siEBS").click();
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
			
	/**
	 * Metodo para ejecutar el concurrente
	 * @param concurrente para ejeutar el concurrente
	 * @author michelle.vasquez@gruptimexico.com
	 * @throws Exception 
	 * @since 31/04/2025
	 */
	public void ejecutarConcurrenteXXFCProcesoGeneracionOTEAM (String concurrente) throws Exception {
		try{
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);		
		sk.getRegion("EBS\\\\ver.png").click();
		u.hardWait(shortWait);
		testCase.addScreenShotCurrentStep("");	
		sk.getRegion("EBS\\\\solicitudes.png").click();
		u.hardWait(shortWait);
		testCase.addScreenShotCurrentStep("");
		sk.getRegion("EBS\\\\ejecutarNuevaSolicitudButton.png").click();
		u.hardWait(shortWait);
		testCase.addScreenShotCurrentStep("");
		sk.getRegion("EBS\\\\nomConru.png").click();
		testCase.addScreenShotCurrentStep("");
		p.typeAndTab(concurrente);			
		sk.getRegion("EBS\\\\ejecutar.png").click();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		sk.screen.type(Key.ENTER);
		u.hardWait(shortWait);
		sk.getRegion("EBS\\\\no.png").click();
		u.hardWait(shortWait);
		sk.getRegion("EBS\\\\cerrar2");
		testCase.addScreenShotCurrentStep("");
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Metodo para revisar que se ejecutando el concurrente
	 *@param concurrente para ejecutar el concurrente
	 * @author michelle.vasquez@gruptimexico.com
	 * @throws Exception 
	 * @since 31/04/2025
	 */
	public void validarEjecConcurrenteEBS (String concurrente) throws Exception {
		try{
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		sk.getRegion("EBS\\\\solicitudesEspecificas.png").click();
		u.hardWait(shortWait);
		testCase.addScreenShotCurrentStep("");
		sk.getRegion("EBS\\\\nomSolConcu.png").click();
		u.hardWait(1);		
		sk.screen.type(concurrente);
		u.hardWait(1);
		sk.getRegion("EBS\\\\encontrar.png").click();		
		testCase.addScreenShotCurrentStep("");
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Obtiene el WINENTITY 
	 * @param username usarioBD, password credenciales para acceder da la BD  
	 * @param idSolicitud para consultar el WINENTITY de la OT generada
	 * @author michelle.vasquez@gruptimexico.com
	 * @return
	 * @throws Exception 
	 * @throws FrameworkException  
	 * @since 31/04/2025
	 */
	public boolean obSoliIMA (String idSolicitud) throws Exception {
		try {
		utils.sql.SQLUtil ebsOracle = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);		
		SQLResult WIPEnity;				
		String queryWPEntity = " SELECT WIP_ENTITY_ID \r\n"
				+ "FROM APPS.XXFC_EAM_OTMA_ALL WHERE ESTATUS IN ('S') AND  ID_SOLICITUD_IMA = "+idSolicitud+""
				+ " ORDER BY LAST_UPDATE_DATE DESC ";
		System.out.print(queryWPEntity);
		WIPEnity = ebsOracle.executeQuery(queryWPEntity);
		testCase.addQueryEvidenceCurrentStep(WIPEnity);
		//String idIMASol = WIPEnity.getData(0, "WIP_ENTITY_ID").toString();
		Boolean validacion = false;
        
		if (WIPEnity.getRowCount()== 0) {				 				
			 validacion = true;	 
		 }		
		}catch (SQLException e) {
			// TODO: handle exception			
		}
		return false;		
			
	}
	
	/**
	 * Valida el IDSolicitud 
	 * @param username usarioBD, password credenciales para acceder da la BD  
	 * @param idSolicitud para consultar el WINENTITY de la OT generada
	 * @author michelle.vasquez@gruptimexico.com
	 * @return
	 * @throws Exception 
	 * @throws FrameworkException  
	 * @since 31/04/2025
	 
	public void valIDSolicitud (String idSolicitud) throws Exception {
		try {
		boolean idSol = obSoliIMA(idSolicitud);
		System.out.print(idSol);
		if(idSol == null) {			
		} else {
            // Si el estatus es otro valor
        	throw new NoSuchElementException("Fallo, revissar ID no se borro " + idSol);            
        }
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	*/
	/**
	 * Obtiene los intentos de un ID procesado 
	 * @param username usarioBD, password credenciales para acceder da la BD  
	 * @param idSolicitud para consultar el WINENTITY de la OT generada
	 * @author michelle.vasquez@gruptimexico.com
	 * @return
	 * @throws Exception 
	 * @throws FrameworkException  
	 * @since 31/04/2025
	 */
	public boolean idSolIntentos (String idSolicitud) throws Exception {
		try {			
		utils.sql.SQLUtil ebsOracle = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		SQLResult IDSol;		
		String queryIntentos =" SELECT INTENTO FROM APPS.XXFC_EAM_OTMA_ALL WHERE ESTATUS IN ('E') AND  ID_SOLICITUD_IMA = "+idSolicitud+" ";
		System.out.println(queryIntentos);
		IDSol = ebsOracle.executeQuery(queryIntentos);
		testCase.addQueryEvidenceCurrentStep(IDSol);
		String idIntentos = IDSol.getData(0,"INTENTO").toString();
		System.out.println(idIntentos);
		Boolean validacion = false;
        
		if (IDSol.getRowCount()> 0) {				 				
			 validacion = true;			 
		 }		
		}catch (SQLException e) {
		// TODO: handle exception
		
		}
		return false;
	}
	

	
	/**
	 * Obtiene el WINENTITY_NAME 
	 * @param username usarioBD, password credenciales para acceder da la BD  
	 * @param WINENTITY de la OT generada
	 * @author michelle.vasquez@gruptimexico.com
	 * @return
	 * @throws Exception 
	 * @throws FrameworkException  
	 * @since 31/04/2025
	 */
	public String obOTMA (String idSolicitud, String username, String password) throws Exception {
		try {
		boolean WINENTITY = obSoliIMA(idSolicitud);
		String oTMA = null;
		Connection conec = null;
		Statement stmt = null;
		ResultSet otMA = null;		
		String urlBD = "jdbc:oracle:thin:@//10.184.48.61:1532/AVEBQA";
		String query = "SELECT * FROM apps.xxfc_eam_OS_ALL WHERE WIP_ENTITY_ID ="+WINENTITY+";";		
		try {
			conec = DriverManager.getConnection(urlBD, username, password);					
			stmt = conec.createStatement();
			otMA = stmt.executeQuery(query);			
			if (otMA.next()) {
				oTMA = otMA.getNString("WIP_ENTITY_NAME");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (otMA != null) otMA.close();
			if (stmt != null) stmt.close();
			if (conec != null) conec.close();
		}
		return oTMA;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Obtiene la OT con prefijoMA 
	 * @param username usarioBD, password credenciales para acceder da la BD  
	 * @param solicitudIMA insumo que debe proporcionar IMA, de preferencia utilizar un DOMI
	 * @author michelle.vasquez@gruptimexico.com
	 * @return
	 * @throws Exception 
	 * @throws FrameworkException  
	 * @since 31/04/2025
	 */
	public boolean veryOTMto (String solicitudIMA) throws Exception {
		try{
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		sk.getRegion("EBS\\\\workOder").doubleClick();
		u.hardWait(shortWait);
		sk.getRegion("EBS\\\\workOrder").doubleClick();
		u.hardWait(shortWait);
		sk.getRegion("EBS\\\\PediTrabajo").click();
		u.hardWait(shortWait);		
		p.typeAndTab(solicitudIMA);
		u.hardWait(1);
		u.hardWait(shortWait);
		sk.getRegion("EBS\\\\encontrar").click();
		u.hardWait(shortWait);				
		testCase.addScreenShotCurrentStep("testImage");
		return true;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Obtiene cerraVenSolicitud 
	 * 
	 * @author michelle.vasquez@gruptimexico.com
	 * @return
	 * @throws FindFailed 
	 * @throws Exception 
	 * @throws FrameworkException  
	 * @since 31/04/2025
	 */
	public void cerraVenSolicitud () throws FindFailed {
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		sk.getRegion("EBS\\\\cerrar2").click();
		u.hardWait(2);	
	}
	
	
	/**
	 * Metodo para revisar asignacion SuperUSuario
	 * @author michelle.vasquez@gruptimexico.com
	 * @throws Exception 
	 * @since 31/04/2025
	 */
	public boolean validaAsignacion () throws Exception {
		try{
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		u.hardWait(shortWait);
		sk.getRegion("EBS\\\\SuperUsuario").click();
		testCase.addScreenShotCurrentStep("testImage");
		return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * Obtiene el ultimo ID solicitud ejecutado
	 * @implNote este ejecutarlo despues del metodo 
	 * @param username usarioBD, password credenciales para acceder da la BD 
	 * @param concurrente variable para determinar el concurrente a cosultar 
	 * @param idSolicitud id a consultar del concurrente correspondiente
	 * @author michelle.vasquez@gruptimexico.com
	 * @return
	 * @throws FrameworkException  
	 * @since 31/04/2025
	 */
	public String rsStatusIdConcurrente (String concurrente, String idSolicitud) throws Exception {
		try{
		utils.sql.SQLUtil ebsOracle = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		String statusIdSolicitud;
		SQLResult statusConcurrente;		
		String queryConcu = " SELECT DISTINCT \r\n"
				+ "    t.user_concurrent_program_name,\r\n"
				+ "    r.REQUEST_ID,\r\n"
				+ "    TO_CHAR(r.ACTUAL_START_DATE, 'dd-mm-yy hh24:mi:ss') Started,\r\n"
				+ "    TO_CHAR(r.ACTUAL_COMPLETION_DATE, 'dd-mm-yy hh24:mi:ss') Completed,\r\n"
				+ "    DECODE(r.PHASE_CODE, 'C', 'Completed', 'I', 'Inactive', 'P', 'Pending', 'R', 'Running', 'NA') phasecode,\r\n"
				+ "    DECODE(r.STATUS_CODE, \r\n"
				+ "        'A', 'Waiting', \r\n"
				+ "        'B', 'Resuming', \r\n"
				+ "        'C', 'Normal', \r\n"
				+ "        'D', 'Cancelled', \r\n"
				+ "        'E', 'Error', \r\n"
				+ "        'F', 'Scheduled', \r\n"
				+ "        'G', 'Warning', \r\n"
				+ "        'H', 'On Hold', \r\n"
				+ "        'I', 'Normal',\r\n"
				+ "        'M', 'No Manager', \r\n"
				+ "        'Q', 'Standby', \r\n"
				+ "        'R', 'Normal', \r\n"
				+ "        'S', 'Suspended', \r\n"
				+ "        'T', 'Terminating', \r\n"
				+ "        'U', 'Disabled', \r\n"
				+ "        'W', 'Paused', \r\n"
				+ "        'X', 'Terminated', \r\n"
				+ "        'Z', 'Waiting') Status,\r\n"
				+ "    SUBSTR(u.description, 1, 25) Whosubmitted,\r\n"
				+ "    ROUND(((NVL(r.actual_completion_date, SYSDATE) - r.actual_start_date) * 24 * 60)) Etime\r\n"
				+ "FROM \r\n"
				+ "    apps.fnd_concurrent_requests r\r\n"
				+ "JOIN \r\n"
				+ "    apps.fnd_concurrent_programs p ON r.CONCURRENT_PROGRAM_ID = p.CONCURRENT_PROGRAM_ID\r\n"
				+ "JOIN \r\n"
				+ "    apps.fnd_concurrent_programs_tl t ON t.concurrent_program_id = r.concurrent_program_id\r\n"
				+ "JOIN \r\n"
				+ "    apps.fnd_user u ON r.REQUESTED_BY = u.user_id\r\n"
				+ "WHERE \r\n"
				+ "    t.user_concurrent_program_name LIKE '"+concurrente+"'\r\n"
				+ "    AND r.REQUEST_ID = "+idSolicitud+"\r\n"
				+ "ORDER BY \r\n"
				+ "    TO_CHAR(r.ACTUAL_COMPLETION_DATE, 'dd-mm-yy hh24:mi:ss') DESC\r\n"
				+ " FETCH FIRST ROW ONLY ";
		
		statusConcurrente = ebsOracle.executeQuery(queryConcu);
		statusIdSolicitud = statusConcurrente.getData(0, "STATUS").toString();		
		testCase.addQueryEvidenceCurrentStep(statusConcurrente);
		Boolean validacion = false;            
		if (statusConcurrente.getRowCount()> 0) {				 				
			 validacion = true;
			 assertTrue(validacion, "No se encontraron datos");
			 }						
		return statusIdSolicitud;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}	
	
	}
		

	/**
	 * Obtiene el ultimo ID solicitud ejecutado
	 * @param username usarioBD, password credenciales para acceder da la BD 
	 * @param concurrente variable para determinar el concurrente a cosultar 
	 * @author michelle.vasquez@gruptimexico.com
	 * @return 
	 * @throws FrameworkException 
	 * @since 31/04/2025
	 */
	public String obtenerIdSolicitudConcurrente (String concurrente) throws Exception {
		try{
		utils.sql.SQLUtil ebsOracle = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		String idSolicitud;
		SQLResult idSolic;	
		String queryConcu = " SELECT DISTINCT     \r\n"
				+ "    r.REQUEST_ID \r\n"
				+ "FROM \r\n"
				+ "    apps.fnd_concurrent_requests r\r\n"
				+ "JOIN \r\n"
				+ "    apps.fnd_concurrent_programs p ON r.CONCURRENT_PROGRAM_ID = p.CONCURRENT_PROGRAM_ID\r\n"
				+ "JOIN \r\n"
				+ "    apps.fnd_concurrent_programs_tl t ON t.concurrent_program_id = r.concurrent_program_id\r\n"
				+ "JOIN \r\n"
				+ "    apps.fnd_user u ON r.REQUESTED_BY = u.user_id\r\n"
				+ "WHERE \r\n"
				+ "    t.user_concurrent_program_name LIKE '"+concurrente+"'\r\n"
				+ "ORDER BY \r\n"
				+ "    TO_CHAR(r.ACTUAL_COMPLETION_DATE, 'dd-mm-yy hh24:mi:ss') DESC\r\n"
				+ "FETCH FIRST ROW ONLY ";			
			idSolic = ebsOracle.executeQuery(queryConcu);			
			testCase.addQueryEvidenceCurrentStep(idSolic);
			idSolicitud = idSolic.getData(0, "REQUEST_ID");			
			Boolean validacion = false;            
			if (idSolic.getRowCount()> 0) {				 				
				 validacion = true;				 
			 }			
			return idSolicitud;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		
	}
		
	/**
	 * Valida Fecha y datos de un IDSolicitud
	 * @author michelle.vasquez@gruptimexico.com
	 * @return 
	 * @throws FrameworkException 
	 * @since 31/04/2025
	 */
	public boolean validaFechaIMA () throws Exception {
		try {
			SQLResult infoFecha;
			SQLResult infoIDSolIMA;
			utils.sql.SQLUtil ebsOracle = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);			
			String queryFechaIMA = " SELECT ID, CR_TIENDA, ASSET_NUMBER, ID_SOLICITUD_IMA, WIP_ENTITY_ID\r\n"
					+ "FROM APPS.XXFC_EAM_OTMA_ALL\r\n"
					+ "WHERE ESTATUS NOT IN ('E', 'I')\r\n"
					+ "ORDER BY LAST_UPDATE_DATE DESC\r\n"
					+ "FETCH FIRST 10 ROWS ONLY ";
			String queryIDIMA = " SELECT ID,FLAG, ESTATUS, FECHA_INI_PROG, CREATION_DATE, LAST_UPDATE_DATE\r\n"
					+ "FROM APPS.XXFC_EAM_OTMA_ALL\r\n"
					+ "WHERE ESTATUS NOT IN ('E', 'I')\r\n"
					+ "ORDER BY LAST_UPDATE_DATE DESC\r\n"
					+ "FETCH FIRST 10 ROWS ONLY ";
			System.out.println(queryFechaIMA);
			System.out.println(queryIDIMA);
			infoFecha = ebsOracle.executeQuery(queryFechaIMA);
			infoIDSolIMA = ebsOracle.executeQuery(queryIDIMA);
			testCase.addQueryEvidenceCurrentStep(infoFecha);
			testCase.addQueryEvidenceCurrentStep(infoIDSolIMA);
			String infFechSoli = infoIDSolIMA.getData(0, "FECHA_INI_PROG").toString();
			String id = infoFecha.getData(0,"ID").toString();				 
			String crTienda = infoFecha.getData(0,"CR_TIENDA").toString();				 
            String assetNumber = infoFecha.getData(0,"ASSET_NUMBER").toString();
            String idSolicitudIma = infoFecha.getData(0,"ID_SOLICITUD_IMA").toString();
            String wipEntityId = infoFecha.getData(0,"WIP_ENTITY_ID").toString();
            String estatus = infoIDSolIMA.getData(0,"ESTATUS").toString();
            String flagIMA = infoIDSolIMA.getData(0,"FLAG").toString();
            String fechaIniProg = infoIDSolIMA.getData(0,"FECHA_INI_PROG").toString();
            String creationDate = infoIDSolIMA.getData(0,"CREATION_DATE").toString();
            String lastUpdateDate = infoIDSolIMA.getData(0,"LAST_UPDATE_DATE").toString();
            System.out.println(infFechSoli);		
            System.out.println(id + " | " + crTienda + " | " + assetNumber + " | " + idSolicitudIma + " | " + 
                    wipEntityId + " | " + estatus + " | " + fechaIniProg + " | " + creationDate + " | " + lastUpdateDate);
			
            Boolean validacion = true;
            
			if (infoFecha.getRowCount()> 0) {				 				
				 validacion = false;				 
			 }
			
		}catch (SQLException e) {
			// TODO: handle exception			
		}
		
		return true;		

	}
	
	
	/**
	 * Valida estatus de la ultima corrida del concurrente XXFC- Proceso de generacion de OT en EAM (origen IMA)
	 * @param username usarioBD, password credenciales para acceder da la BD 
	 * @param concurrente variable para determinar el concurrente a cosultar 
	 * @author michelle.vasquez@gruptimexico.com
	 * @return 
	 * @throws FrameworkException  
	 * @since 31/04/2025
	 */
	public void validarEstatusConcurrente (String concurrente) throws Exception {
		try{
		u.hardWait(4);
		String idSolicitud = obtenerIdSolicitudConcurrente(concurrente);		
		String statusIdSolicCote = rsStatusIdConcurrente(concurrente, idSolicitud);		
		if ("Normal".equals(statusIdSolicCote) || "Warning".equals(statusIdSolicCote)) {
            // Si el estatus es Normal o Warning, la prueba es válida
			u.hardWait(3);
			org.testng.Assert.assertTrue("Normal".equals(statusIdSolicCote) || "Warning".equals(statusIdSolicCote));
			testCase.addScreenShotCurrentStep("");
        } else if ("Error".equals(statusIdSolicCote)) {
            // Si el estatus es Error, la prueba falla
        	AssertJUnit.fail("El estatus marco fallado");
        	throw new NoSuchElementException("Fallo el concurrente " + concurrente);            
        } else {
            // Si el estatus es otro valor
        	throw new NoSuchElementException("Estatus desconocido del concurrente " + concurrente);            
        }
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Obtiene el mensaje del ID Solicitud trabajada
	 * @param username usarioBD, password credenciales para acceder da la BD 
	 * @param concurrente variable para determinar el concurrente a cosultar 
	 * @author michelle.vasquez@gruptimexico.com
	 * @return 
	 * @return 
	 * @throws FrameworkException  
	 * @since 31/04/2025
	 */
	public String menjaIDSol (String idSolicitudOT) throws Exception {
		try{
		utils.sql.SQLUtil ebsOracle = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);					
		SQLResult mensjIdSolic;			
		String queryMensj = " SELECT MENSAJE FROM APPS.XXFC_EAM_OTMA_ALL WHERE ID_SOLICITUD_IMA = "+idSolicitudOT+" ";
		mensjIdSolic = ebsOracle.executeQuery(queryMensj);	
		testCase.addQueryEvidenceCurrentStep(mensjIdSolic);
		String mensjSolit = mensjIdSolic.getData(0,"MENSAJE").toString();
		System.out.println(mensjSolit);
		Boolean validacion = false;
        
		if (mensjIdSolic.getRowCount()> 0) {				 				
			 validacion = true;
			 assertTrue(validacion, "No se encontraron datos");
		 }
		return mensjSolit;
		}catch (SQLException e) {
			// TODO: handle exception
			throw new AssertionError("Fallo al ejecutar el query");
		}
	
		
	}

	/**
	 * Valida solicitudIMA pertenezca a OXXOMexico, depende del CR Tienda
	 * @param username usarioBD, password credenciales para acceder da la BD 
	 * @param concurrente variable para determinar el concurrente a cosultar 
	 * @param La varabiel idSolicitudOT, se obtiene de los insumos Domi insertados, importa el CR Tienda 
	 * @author michelle.vasquez@gruptimexico.com
	 * @return 
	 * @return 
	 * @throws FrameworkException  
	 * @since 31/04/2025
	 */
	public boolean validarOTCR (String idSolicitudOT) throws Exception {
		try{
		String mssEsperadoError = "Error, no esta configurado la organizacion: 1413 ";
		String mensajeObtenido  = menjaIDSol(idSolicitudOT);
		if (mensajeObtenido.equals(mssEsperadoError)) {
            // Si es el mensaje espero, la prueba es valida
			u.hardWait(3);
			System.out.println("Entro aqui Si");
			testCase.addScreenShotCurrentStep("");
			return true;			
		} else {
            // Si el estatus es otro valor
        	throw new NoSuchElementException("Fallo, revissar ID el tipo de organización " + idSolicitudOT);        	
        }
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida intentosIDSolici
	 * @param username usarioBD, password credenciales para acceder da la BD 
	 * @param concurrente variable para determinar el concurrente a cosultar 
	 * @param La varabiel idSolicitudOT, se obtiene de los insumos Domi insertados, importa el CR Tienda 
	 * @author michelle.vasquez@gruptimexico.com
	 * @return 
	 * @return 
	 * @throws FrameworkException  
	 * @since 31/04/2025
	 */
	public boolean valIntentos (String idSolicitud) throws Exception {
		try{
		boolean idSoliIntentos = idSolIntentos(idSolicitud);
		if("3".equals(idSoliIntentos)) {
			// Si es el mensaje espero, la prueba es valida
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");
			return true;
		} else {
            // Si el estatus es otro valor
			throw new NoSuchElementException("Fallo, revissar ID si se proceso la soliciud " + idSolicitud);
        }
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Lo jale de EBSPaginaInicial
	 *  
	 * @author michelle.vasquez@gruptimexico.com
	 * @return 
	 * @return 
	 * @throws FrameworkException  
	 * @since 31/04/2025
	 */
	public void appletMaximize() {
		sk.screen.keyDown(Key.WIN + Key.UP);
		sk.screen.keyUp(Key.WIN + Key.UP);
	}
	
	/**
	 * runApplet intento de ejecutar EAM
	 *  
	 * @author michelle.vasquez@gruptimexico.com
	 * @return 
	 * @return 
	 * @throws FrameworkException  
	 * @since 31/04/2025
	 */
	public void runApplet() {
		System.out.println("Run Applet");		
		try {					
			sk.getRegion("EBS\\\\conservar.png").click();
			u.hardWait(3);
			sk.getRegion("EBS\\\\archvieje").click();
			sk.getRegion("EBS\\\\dessArc").click();
			sk.getRegion("EBS\\\\desscargar").click();
			sk.getRegion("EBS\\\\archvieje").click();
			u.hardWait(5);
			sk.getRegion("EBS\\\\aceptoTerminos").click();
			sk.screen.type(Key.ENTER);
			u.hardWait(10);
		} catch (FindFailed e) {
			//logger.error("Tiempo de ejecucion excedido " + e);
			System.out.println("Tiempo de ejecucion excedido " + e);
		}
		u.hardWait(5);
		//appletMaximize();
	}
	
}
