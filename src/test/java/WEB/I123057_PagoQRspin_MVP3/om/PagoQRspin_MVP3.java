package WEB.I123057_PagoQRspin_MVP3.om;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


public class PagoQRspin_MVP3 {
	
	
	//Variables
	TestCase testCase;
	SeleniumUtil u;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);
	
	//Objects
	By usernameField = By.id("usernameField");
	By passwordField = By.id("passwordField");
	By logInBtnXpath = By.xpath("//*[@id='ButtonBox']//button[@class='OraButton left']");
	By logInBtnEBSColXpath = By.xpath("//*[@id=\"SubmitButton\"]");
	By slangXpath = By.xpath("//div[@id='LanguagePickerBox']//select[@id='slang']");
	By SpanishSlangXpath = By.xpath("//div[@id='LanguagePickerBox']//select[@id='slang']//option[@lang='es']");
	By SpanishSlangColombiaXpath = By.xpath("//*[@id=\"langOptionsTable\"]/tbody/tr[3]/td[2]/a/img");
	By MXOXXOGL17ADMSERVICIOS = By.xpath("//div[@class='textdivresp' and text()='MXOXXO-GL17ADM-SERVICIOS']");
	By MenuTableIzq = By.id("menuTable");
	By carpetaInformes17ADMSERVICIOS = By.xpath("//div[@class='textdivresp' and text()='Informes']");
	By carpetaSolicitar17ADMSERVICIOS = By.xpath("//div[@class='textdivresp' and text()='Solicitar']");
	By btnStandar17ADMSERVICIOS = By.xpath("//div[@class='textdiv' and text()='Standard']");

	public PagoQRspin_MVP3(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}
	
	/**
	 * Conexion a BD ORACLE FCWMREQA
	 * 
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 05/08/2025
	 */
	
	public static final String DB_USER_FCWMREQA = "QAVIEW";
	public static final String DB_PASSWORD_FCWMREQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCWMREQA = "172.26.221.197:1521/FCWMREQA.FEMCOM.NET";
	
	/**
	 * Conexion a BD ORACLE WMINT
	 * 
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 06/08/2025
	 */
	
	public static final String DB_USER_WMINT = "QAVIEW";
	public static final String DB_PASSWORD_WMINT = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_WMINT = "oxzvmwmiracq02.femcom.net:1521/FCWM6QA.FEMCOM.NET";
	
	/**
	 * Conexion a BD ORACLE FCWMLQA
	 * 
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 06/08/2025
	 */
	
	public static final String DB_USER_FCWMLQA = "QAVIEW";
	public static final String DB_PASSWORD_FCWMLQA = PasswordUtil.decryptPassword("FD4327B8121ADFF5C3A0B5F509D0EEB1");
	public static final String DB_HOST_FCWMLQA = "172.26.221.200:1521/FCWMLQA.FEMCOM.NET";
	
	/**
	 * Conexion a BD ORACLE AVEBQA_OSI
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 05/08/2025
	 */
	
	public static final String DB_USER_AVEBQA_OSI = "appsview";
	public static final String DB_PASSWORD_AVEBQA_OSI = PasswordUtil.decryptPassword("92C4986E2465EDA27D5D04B227BE5FDE");
	public static final String DB_HOST_AVEBQA_OSI = "oxfdbfinq00.femcom.net:1532/AVEBQA";
	
	/**
	 * Conexion a BD ORACLE WMLOG
	 * @author Monica Soto
	 * @throws Exception 
	 * @date 05/08/2025
	 */
	public static final String DB_USER_LOG = "QAVIEW";
	public static final String DB_PASSWORD_LOG = PasswordUtil.decryptPassword("FD4327B8121ADFF5C3A0B5F509D0EEB1");
	public static final String DB_HOST_LOG = "oxzvmwmiracq01.femcom.net:1521/FCWMLQA.FEMCOM.NET";

	//BD_IMA
	public static final String DB_HOST_IMA = "fcobidb1q00.femcom.net:1535/FCODSQA.FEMCOM.NET";
	public static final String DB_USER_IMA = "BIETL";
	public static final String DB_PASSWORD_IMA = PasswordUtil.decryptPassword("8D136AB701B3D22EE706D74A2408A9E4");
		
	//BD FCTPEQA
	public static final String DB_USER_FCTPEQA = "QAVIEW";
	public static final String DB_HOST_FCTPEQA = "fcdbwmq1.femcom.net:1521/FCTPEQA.FEMCOM.NET";
	public static final String DB_PASSWORD_FCTPEQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	

	
	/**
	 * Validacion de TRX a procesar por la FT_TEMP
	 * 
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 08/08/2025
	 */
	
	public void validTrxToProcessFT_TEMP() throws Exception {
		try {
			String queryValidaREQA = "SELECT FOLIO, APPLICATION, OPERATION, WM_CODE, WM_STATUS_FT\r\n"
					+ "FROM TPEREP.TPG_TRANSACTIONS_SPIN\r\n"
					+ "WHERE WM_STATUS_FT = 'L'\r\n"
					+ "AND ADMIN_DATE >= TRUNC(SYSDATE - TO_NUMBER(30))\r\n"
					+ "AND OPERATION = 'TRN1'\r\n"
					+ "AND WM_CODE = 101\r\n"
					+ "ORDER BY CREATION_DATETIME DESC";
			SQLUtil dbWMREQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCWMREQA, PagoQRspin_MVP3.DB_USER_FCWMREQA,PagoQRspin_MVP3.DB_PASSWORD_FCWMREQA);
			SQLResult resultadoValidaREQA = dbWMREQA.executeQuery(queryValidaREQA);
			System.out.println(queryValidaREQA);
			testCase.addBoldTextEvidenceCurrentStep(queryValidaREQA);
			
			if (resultadoValidaREQA.getRowCount()>0) {
			System.out.println("SI se encontro informacion a procesar por la FT_TEMP");
			testCase.addQueryEvidenceCurrentStep(resultadoValidaREQA);	
			}
			else {
				throw new NoSuchElementException("No se encontro informacion a procesar para ejecucion de FT_TEMP");
			}
		}
		catch(Exception e) {
			 throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Validacion de TRX a procesar por la FT_TEMP
	 * 
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 08/08/2025
	 */
	
	public void validNOTTrxToProcessFT_TEMP() throws Exception {
		try {
			String queryValidaREQA = "SELECT FOLIO, APPLICATION, OPERATION, WM_CODE, WM_STATUS_FT\r\n"
					+ "FROM TPEREP.TPG_TRANSACTIONS_SPIN\r\n"
					+ "WHERE WM_STATUS_FT = 'L'\r\n"
					+ "AND ADMIN_DATE >= TRUNC(SYSDATE - TO_NUMBER(30))\r\n"
					+ "AND OPERATION = 'TRN1'\r\n"
					+ "AND WM_CODE = 101\r\n"
					+ "ORDER BY CREATION_DATETIME DESC";
			SQLUtil dbWMREQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCWMREQA, PagoQRspin_MVP3.DB_USER_FCWMREQA,PagoQRspin_MVP3.DB_PASSWORD_FCWMREQA);
			SQLResult resultadoValidaREQA = dbWMREQA.executeQuery(queryValidaREQA);
			System.out.println(queryValidaREQA);
			testCase.addBoldTextEvidenceCurrentStep(queryValidaREQA);
			
			if (resultadoValidaREQA.isEmpty()) {
				System.out.println("No hay informacion a procesar por la FT_TEMP, se puede continuar");
				testCase.addQueryEvidenceCurrentStep(resultadoValidaREQA);
			}else {
				throw new NoSuchElementException("Se encontro informacion a procesar por la FT_TEMP, no se puede continuar");
				
			}
		}
		catch(Exception e) {
			 throw new Exception("Error: " + e.getMessage());
		}
	}
	
	
	/**
	 * Validacion de ejecucion en log de BD (FCWMLQA)
	 * @param interfaz
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 08/08/2025
	 */
	
	public Map<String, String> validLogExecInDB(String interfaz) throws Exception {
	    try {
	        String queryValidaWMLQA = "SELECT *\r\n"
	        		+ "FROM (\r\n"
	        		+ "    SELECT run_id, interface, end_dt, status, server\r\n"
	        		+ "    FROM WMLOG.WM_LOG_RUN\r\n"
	        		+ "    WHERE interface = '"+ interfaz +"' \r\n"
	        		+ "        AND TRUNC(START_DT) >= TRUNC(SYSDATE)\r\n"
	        		+ "    ORDER BY START_DT DESC\r\n"
	        		+ ") \r\n"
	        		+ "WHERE ROWNUM = 1";

	        SQLUtil dbWMLQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCWMLQA, PagoQRspin_MVP3.DB_USER_FCWMLQA, PagoQRspin_MVP3.DB_PASSWORD_FCWMLQA);
	        SQLResult resultadoValidaWMLQA = dbWMLQA.executeQuery(queryValidaWMLQA);

	        if (resultadoValidaWMLQA != null && resultadoValidaWMLQA.getRowCount() > 0) {
	            Map<String, String> result = new HashMap<>();
	            result.put("RUN_ID", resultadoValidaWMLQA.getData(0, "RUN_ID"));
	            result.put("STATUS", resultadoValidaWMLQA.getData(0, "STATUS"));
	            testCase.addQueryEvidenceCurrentStep(resultadoValidaWMLQA);
	            return result;
	        } else {
	            throw new NoSuchElementException("No se pintó información de la ejecución de la interface FT_TEMP");
	        }
	    } catch(Exception e){
	        throw new Exception("Error en validLogExecInDB_Map: " + e.getMessage());
	    }
	}
	
	/**
	 * Validacion de regla de negocio para formato de INFO_HEADER e INFO_FOOTER en WMINT
	 * 
	 * @param runid
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 08/08/2025
	 */
	
	public Map<String, String> getInfoHeaderFooterFromWmIntFT_TEMP(String runid) throws Exception {
	    Map<String, String> result = new HashMap<>();

	    try {
	        String queryValidaWMINT = "SELECT ENTIDAD, NOMBRE_ARCHIVO, ESTATUS, INFO_HEADER, INFO_FOOTER\r\n "
	                + "FROM WMUSER.WM_FT_EXECUTION\r\n"
	                + "WHERE ENTIDAD = 'SPIN'\r\n"
	                + "AND ID = '"+ runid +"'\r\n"
	                + "AND CREATION_DATE >= TRUNC(SYSDATE)\r\n"
	                + "ORDER BY CREATION_DATE DESC ";

	        SQLUtil dbWMINT = new SQLUtil(PagoQRspin_MVP3.DB_HOST_WMINT, PagoQRspin_MVP3.DB_USER_WMINT, PagoQRspin_MVP3.DB_PASSWORD_WMINT);

	        SQLResult resultadoValidaWMINT = dbWMINT.executeQuery(queryValidaWMINT);

	        if (resultadoValidaWMINT != null && resultadoValidaWMINT.getRowCount() > 0) {
	            result.put("INFO_HEADER", resultadoValidaWMINT.getData(0, "INFO_HEADER"));
	            result.put("INFO_FOOTER", resultadoValidaWMINT.getData(0, "INFO_FOOTER"));
	        } else {
	            throw new NoSuchElementException("No se encontró información para RUN_ID: " + runid);
	        }

	    } catch (Exception e) {
	        System.err.println("Error al obtener INFO_HEADER/INFO_FOOTER para RUN_ID " + runid + ": " + e.getMessage());
	        throw e;
	    }

	    return result;
	}

	
	/**
	 * Validacion de TRX a procesar de reversas por la FT_TEMP
	 * 
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 08/08/2025
	 */
	
	public void validRevToProcessFT_TEMP() throws Exception {
		try {
			String queryValidaRevREQA = "SELECT FOLIO, APPLICATION, OPERATION, ENTITY, WM_CODE, WM_STATUS_FT\r\n"
					+ "FROM TPEREP.TPG_TRANSACTIONS_SPIN\r\n"
					+ "WHERE WM_STATUS_FT = 'L'\r\n"
					+ "AND ADMIN_DATE >= TRUNC(SYSDATE - TO_NUMBER(30))\r\n"
					+ "AND OPERATION = 'TRN1'\r\n"
					+ "AND WM_CODE = 102\r\n"
					+ "ORDER BY CREATION_DATETIME DESC";
			SQLUtil dbWMREQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCWMREQA, PagoQRspin_MVP3.DB_USER_FCWMREQA,PagoQRspin_MVP3.DB_PASSWORD_FCWMREQA);
			SQLResult resultadoValidaRevREQA = dbWMREQA.executeQuery(queryValidaRevREQA);
			System.out.println(queryValidaRevREQA);
			testCase.addBoldTextEvidenceCurrentStep(queryValidaRevREQA);
			
			if (resultadoValidaRevREQA != null && resultadoValidaRevREQA.getRowCount() > 0) {
			System.out.println("SI se encontro informacion a procesar de Reversos por la FT_TEMP");
			testCase.addQueryEvidenceCurrentStep(resultadoValidaRevREQA);
			}
			else {
				throw new NoSuchElementException("No se encontro informacion a procesar de reversos para ejecucion de RevFT_TEMP");
			}
		}
		catch(Exception e) {
			 throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 *Realiza la  ejecucion de Job en el portal Control M
	 * @author Gerardo Lopez
	 * @param job, pass, user, server, testCase
	 * @throws Exception 
	 * @date 11/08/2025
	 */
	
	public String ExecJob(String Njob, String ps, String user, String server, TestCase testCase) throws Exception {
		try {
			testCase.addBoldTextEvidenceCurrentStep("Acceder a control M y Ejecutar job");
			System.out.println("Acceder a control M y Ejecutar job");
			SeleniumUtil su = new SeleniumUtil(new ChromeTest(), true);
			String pssDecrypted = PasswordUtil.decryptPassword(ps); 
			Control_mInicio cm = new Control_mInicio(su, user, pssDecrypted);
			JSONObject obj = new JSONObject(Njob);
			ControlM job= new ControlM(su, testCase, obj);
			su.get(server);
			su.waitForLoadPage();
			su.waitForLoadPage();
			cm.logOn();
			boolean flag = job.searchJob();
			assertTrue(flag);
			String resultado1 = job.executeJob();
			System.out.println("Resultado de la ejecucion -> " + resultado1);
			System.out.println ("Valor de output :" +job.getOutput());
			assertEquals(resultado1, "Ended OK");
			job.closeViewpoint();
			testCase.addBoldTextEvidenceCurrentStep("Se ejecuta de manera exitosa el job");
			System.out.println("Se ejecuta de manera exitosa el job");
			testCase.addBoldTextEvidenceCurrentStep("La ejecución dura (500-1000) MiliSegundos");
			System.out.println("La ejecución dura (500-1000) MiliSegundos");
			su.quit();
			return resultado1;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 *Realiza la  ejecucion de Job en el portal Control M
	 * @author Gerardo Lopez
	 * @param job, pass, user, server, testCase
	 * @throws Exception 
	 * @date 11/08/2025
	 */
	
	public String ExecJobNewStatus(String Njob, String ps, String user, String server, TestCase testCase) throws Exception {
		try {
			testCase.addBoldTextEvidenceCurrentStep("Acceder a control M y Ejecutar job");
			System.out.println("Acceder a control M y Ejecutar job");
			SeleniumUtil su = new SeleniumUtil(new ChromeTest(), true);
			String pssDecrypted = PasswordUtil.decryptPassword(ps); 
			Control_mInicio cm = new Control_mInicio(su, user, pssDecrypted);
			JSONObject obj = new JSONObject(Njob);
			ControlM job= new ControlM(su, testCase, obj);
			su.get(server);
			su.waitForLoadPage();
			su.waitForLoadPage();
			cm.logOn();
			boolean flag = job.searchJob();
			assertTrue(flag);
			String resultado1 = job.executeJob();
			System.out.println("Resultado de la ejecucion -> " + resultado1);
			System.out.println ("Valor de output :" +job.getOutput());
			 if (!"Ended OK".equals(resultado1)) {
		            System.out.println("Resultado no fue 'Ended OK'. Esperando y reintentando...");
		            u.hardWait(60);
		            resultado1 = job.getNewStatus();
		            System.out.println("Nuevo resultado tras nueva revision -> " + resultado1);
		            System.out.println ("Valor de output :" +job.getOutput());
		        }
			assertEquals(resultado1, "Ended OK");
			job.closeViewpoint();
			testCase.addBoldTextEvidenceCurrentStep("Se ejecuta de manera exitosa el job");
			System.out.println("Se ejecuta de manera exitosa el job");
			testCase.addBoldTextEvidenceCurrentStep("La ejecución dura (500-1000) MiliSegundos");
			System.out.println("La ejecución dura (500-1000) MiliSegundos");
			su.quit();
			return resultado1;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 *Realiza la  ejecucion de Job en el portal Control M
	 * @author Gerardo Lopez
	 * @param job, pass, user, server, testCase
	 * @throws Exception 
	 * @date 11/08/2025
	 */
	
	public String ExecJobStatusWait(String Njob, String ps, String user, String server, TestCase testCase) throws Exception {
		try {
			testCase.addBoldTextEvidenceCurrentStep("Acceder a control M y Ejecutar job");
			System.out.println("Acceder a control M y Ejecutar job");
			SeleniumUtil su = new SeleniumUtil(new ChromeTest(), true);
			String pssDecrypted = PasswordUtil.decryptPassword(ps); 
			Control_mInicio cm = new Control_mInicio(su, user, pssDecrypted);
			JSONObject obj = new JSONObject(Njob);
			ControlM job= new ControlM(su, testCase, obj);
			su.get(server);
			su.waitForLoadPage();
			su.waitForLoadPage();
			cm.logOn();
			boolean flag = job.searchJob();
			assertTrue(flag);
			String resultado1 = job.executeJob();
			System.out.println("Resultado de la ejecucion -> " + resultado1);
			System.out.println ("Valor de output :" +job.getOutput());
			 if (!"Ended OK".equals(resultado1)) {
		            System.out.println("Resultado no fue 'Ended OK'. Esperando 5 segundos y reintentando...");
		            u.hardWait(5);
		            resultado1 = job.getNewStatus();
		            System.out.println("Nuevo resultado tras nueva revision -> " + resultado1);
		            System.out.println ("Valor de output :" +job.getOutput());
		        }
			assertEquals(resultado1, "Wait for Event");
			job.closeViewpoint();
			testCase.addBoldTextEvidenceCurrentStep("Se ejecuta de manera exitosa el job");
			System.out.println("Se ejecuta de manera exitosa el job");
			testCase.addBoldTextEvidenceCurrentStep("La ejecución dura (500-1000) MiliSegundos");
			System.out.println("La ejecución dura (500-1000) MiliSegundos");
			su.quit();
			return resultado1;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	
	/**
	 * Funcion para validar en BD que existan TRX a procesar de Reversas en TPG_TRANSACTIONS_SPIN en DB TPEREP
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 11/08/2025
	 */
	
	public void validRevNoToProcessFT_TEMP() throws Exception {
		try {
			String queryValidaRevREQA = "SELECT FOLIO, APPLICATION, OPERATION, WM_CODE, WM_STATUS_FT\r\n"
					+ "FROM TPEREP.TPG_TRANSACTIONS_SPIN\r\n"
					+ "WHERE WM_STATUS_FT = 'L'\r\n"
					+ "AND ADMIN_DATE >= TRUNC(SYSDATE - TO_NUMBER(35))\r\n"
					+ "AND OPERATION = 'TRN1'\r\n"
					+ "AND WM_CODE = 102\r\n"
					+ "ORDER BY CREATION_DATETIME DESC";
			SQLUtil dbWMREQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCWMREQA, PagoQRspin_MVP3.DB_USER_FCWMREQA,PagoQRspin_MVP3.DB_PASSWORD_FCWMREQA);
			SQLResult resultadoValidaRevREQA = dbWMREQA.executeQuery(queryValidaRevREQA);
			System.out.println(queryValidaRevREQA);
			testCase.addBoldTextEvidenceCurrentStep(queryValidaRevREQA);
			
			if (resultadoValidaRevREQA.isEmpty()) {
				System.out.println("No hay informacion a procesar por la FT_TEMP, se puede continuar");
				testCase.addQueryEvidenceCurrentStep(resultadoValidaRevREQA);
			}else {
				throw new NoSuchElementException("Se encontro informacion a procesar por la FT_TEMP, no se puede continuar");
				
			}
		}
		catch(Exception e) {
			 throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Funcion para validar en BD las TRX por procesar la PO21_TEMP
	 * @param statusPO21, store1, store2, store3
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 21/08/2025
	 */
	public Map<String, String> validTrxToProcessPO21_TEMP(String statusPO21, String store1, String store2, String store3) throws Exception {
	    try {
	        String queryValidTrxREQAPO21_TEMP = "SELECT FOLIO, APPLICATION, OPERATION, ENTITY, WM_CODE, WM_STATUS_PO21, VALUE\r\n"
	                + "FROM TPEREP.TPG_TRANSACTIONS_SPIN\r\n"
	                + "WHERE APPLICATION = 'SPN'\r\n"
	                + "AND WM_STATUS_PO21 = '"+ statusPO21 +"'\r\n"
	                + "AND ADMIN_DATE >= TRUNC(SYSDATE - TO_NUMBER(35))\r\n"
	                + "AND OPERATION = 'TRN1'\r\n"
	                + "AND WM_CODE = 101\r\n"
	                + "ORDER BY CREATION_DATETIME DESC ";
	        //+ "--AND STORE_ID IN ('"+ store1 +"', '"+ store2 +"', '"+ store3 +"')\r\n"
	        SQLUtil dbWMREQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCWMREQA,PagoQRspin_MVP3.DB_USER_FCWMREQA,PagoQRspin_MVP3.DB_PASSWORD_FCWMREQA);
	        SQLResult ressValidTrxREQAPO21_TEMP = dbWMREQA.executeQuery(queryValidTrxREQAPO21_TEMP);

	        if(ressValidTrxREQAPO21_TEMP != null && ressValidTrxREQAPO21_TEMP.getRowCount() > 0){
	            testCase.addQueryEvidenceCurrentStep(ressValidTrxREQAPO21_TEMP);
	            String folio = ressValidTrxREQAPO21_TEMP.getData(0, "FOLIO");
	            String status_po21 = ressValidTrxREQAPO21_TEMP.getData(0, "WM_STATUS_PO21");

	            Map<String, String> resultSave = new HashMap<>();
	            resultSave.put("FOLIO", folio);
	            resultSave.put("WM_STATUS_PO21", status_po21);

	            return resultSave;
	        } else {
	            throw new NoSuchElementException("El listado no contiene transacciones a procesar por la PO21_TEMP");
	        }

	    } catch(Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }    
	}

	
	/**
	 * Funcion para validar que se procesaron las trx correctamente por la PO21_TEMP
	 * @param folio, oldStatusPO21
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 21/08/2025
	 */
	public boolean validTrxProcessedByPO21_TEMP(String folio, String oldStatusPO21) throws Exception {
	    try {
	        String queryTrxProcessed = "SELECT FOLIO, WM_STATUS_PO21\r\n"
	        		+ "FROM TPEREP.TPG_TRANSACTIONS_SPIN\r\n"
	        		+ "WHERE FOLIO = '"+ folio +"'\r\n"
	        		+ "AND OPERATION = 'TRN1'\r\n"
	        		+ "ORDER BY CREATION_DATETIME DESC";

	        SQLUtil dbWMREQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCWMREQA,PagoQRspin_MVP3.DB_USER_FCWMREQA,PagoQRspin_MVP3.DB_PASSWORD_FCWMREQA);

	        SQLResult rsTrxProcessed = dbWMREQA.executeQuery(queryTrxProcessed);

	        if(rsTrxProcessed != null && rsTrxProcessed.getRowCount() > 0){
	            String newStatus = rsTrxProcessed.getData(0, "WM_STATUS_PO21");

	            if(newStatus.equals(oldStatusPO21)) {
	                //El status sigue igual, falla la validación
	            	System.out.println("No se proceso correctamente las TRX por la PO21_TEMP, resultado igual: " +newStatus);
	                testCase.addCodeEvidenceCurrentStep("El folio " + folio + " aún tiene el mismo status: " + newStatus);
	                return false;
	            } else {
	                //El status cambió, validación correcta
	                testCase.addCodeEvidenceCurrentStep("El folio " + folio + " cambió de status: " + oldStatusPO21 + " → " + newStatus);
	                return true;
	            }
	        } else {
	            throw new NoSuchElementException("No se encontró el folio " + folio + " en la validación de PO21_TEMP");
	        }

	    } catch(Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Funcion para validar que el folio a procesar por la PO21_TEMP no se encuntre en la tabla XXFC_MPC_GL_TPC
	 * @param folio
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 21/08/2025
	 */
	public boolean validFolioToProssNotInXXFC_MPC_GL_TPC(String folio) throws Exception {
	    try {
	        String queryXXFC_MPC_GL_TPC = "SELECT X.ID_MPC_GL_TPC,\r\n"
	        		+ "       X.ATRIBUTO5,\r\n"
	        		+ "       X.FOLIO_WM,\r\n"
	        		+ "       X.FORMA_PAGO,\r\n"
	        		+ "       X.CR_PLAZA,\r\n"
	        		+ "       X.CR_TIENDA,\r\n"
	        		+ "       X.ENTERED_CR\r\n"
	        		+ "FROM  XXFC.XXFC_MPC_GL_TPC X\r\n"
	        		+ "WHERE X.FORMA_PAGO = 'TTYP1814'\r\n"
	        		+ "  AND X.CR_TIENDA IN ('50AKO','50MTX','50AAM')\r\n"
	        		+ "  AND TO_DATE(X.ATRIBUTO5, 'YYYYMMDD') \r\n"
	        		+ "        BETWEEN TRUNC(SYSDATE - 35)\r\n"
	        		+ "        AND TRUNC(SYSDATE)\r\n"
	        		+ "ORDER BY X.CREATION_DATE DESC";

	        SQLUtil dbAVEBQA_OSI = new SQLUtil(PagoQRspin_MVP3.DB_HOST_AVEBQA_OSI,PagoQRspin_MVP3.DB_USER_AVEBQA_OSI,PagoQRspin_MVP3.DB_PASSWORD_AVEBQA_OSI);
	        SQLResult rsXXFC_MPC_GL_TPC = dbAVEBQA_OSI.executeQuery(queryXXFC_MPC_GL_TPC);

	        if(rsXXFC_MPC_GL_TPC == null || rsXXFC_MPC_GL_TPC.getRowCount() == 0){
	            testCase.addCodeEvidenceCurrentStep("La tabla no tiene registros, folio " + folio + " no existe.");
	            return true;
	        }

	        //Si hay registros, revisamos que el folio no esté presente
	        for(int i = 0; i < rsXXFC_MPC_GL_TPC.getRowCount(); i++){
	            String folioEnTabla = rsXXFC_MPC_GL_TPC.getData(i, "FOLIO_WM");
	            if(folio.equals(folioEnTabla)) {
	                testCase.addCodeEvidenceCurrentStep("El folio " + folio + " existe en la otra tabla.");
	                return false;
	            }
	        }

	        //Si recorrió todo y no encontró el folio
	        testCase.addCodeEvidenceCurrentStep("El folio " + folio + " no existe en la tabla.");
	        return true;

	    } catch(Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Validacion de creacion de threads al ejecutar la interfaz correctamente (FCWMLQA)
	 * @param parent_id
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 25/08/2025
	 */
	
	public Map<String, String> validThreadsfromIntz(String parent_id) throws Exception {
	    try {
	        String queryValidThreads = "SELECT THREAD_ID, PARENT_ID, NAME, STATUS\r\n"
	        						+ "FROM WMLOG.WM_LOG_THREAD\r\n"
	                                + "WHERE PARENT_ID = '" + parent_id + "'";
	        SQLUtil dbWMLQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCWMLQA, PagoQRspin_MVP3.DB_USER_FCWMLQA, PagoQRspin_MVP3.DB_PASSWORD_FCWMLQA);
	        SQLResult resultValidThreads = dbWMLQA.executeQuery(queryValidThreads);

	        System.out.println("Query: " + queryValidThreads);
	        System.out.println("Cantidad de filas: " + resultValidThreads.getRowCount());

	        if (resultValidThreads != null && resultValidThreads.getRowCount() > 0) {
	            Map<String, String> result = new HashMap<>();
	            result.put("PARENT_ID", resultValidThreads.getData(0, "PARENT_ID"));
	            result.put("STATUS", resultValidThreads.getData(0, "STATUS"));

	            System.out.println("STATUS del primer resultado: " + result.get("STATUS"));
	            System.out.println("El PARENT_ID del primer resultado es: " + result.get("PARENT_ID"));
	            testCase.addQueryEvidenceCurrentStep(resultValidThreads);
	            
	            return result;
	        } else {
	            throw new NoSuchElementException("No se pintó información de THREADS");
	        }
	    } catch(Exception e){
	        throw new Exception("Error en validThreadsfromIntz: " + e.getMessage());
	    }
	}
	
	/**
	 * Esta funcion valida que del resultado que se obtuvo de la consulta esta contenga una trx con admin_date diferente al dia actual
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 25/08/2025
	 */
	
	public void validaAdminDateTPEREP(String statusPO21, String store1, String store2, String store3) throws Exception {
	    try {
	        String queryValidAdminDateREQA = "SELECT FOLIO, APPLICATION, OPERATION, ENTITY, ADMIN_DATE, WM_CODE, WM_STATUS_PO21, VALUE\r\n"
	                + "FROM TPEREP.TPG_TRANSACTIONS_SPIN\r\n"
	                + "WHERE WM_STATUS_PO21 = '" + statusPO21 + "'\r\n"
	                + "AND ADMIN_DATE >= TRUNC(SYSDATE - 35)\r\n"
	                + "AND OPERATION = 'TRN1'\r\n"
	                + "AND WM_CODE = 101\r\n"
	                + "ORDER BY CREATION_DATETIME DESC";
	        		//+ "AND STORE_ID IN ('" + store1 + "', '" + store2 + "', '" + store3 + "')\r\n"
	        SQLUtil dbWMREQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCWMREQA, PagoQRspin_MVP3.DB_USER_FCWMREQA, PagoQRspin_MVP3.DB_PASSWORD_FCWMREQA);
	        SQLResult resultValidAdminDateREQA = dbWMREQA.executeQuery(queryValidAdminDateREQA);
	        
	        System.out.println(queryValidAdminDateREQA);
	        testCase.addBoldTextEvidenceCurrentStep(queryValidAdminDateREQA);

	        if (resultValidAdminDateREQA.isEmpty()) {
	            throw new Exception("No se encontró ningún registro en la consulta");
	        }

	        LocalDate hoy = LocalDate.now();
	        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String fechaHoy = hoy.format(formato);

	        boolean existeDistinto = false;

	        for (int i = 0; i < resultValidAdminDateREQA.getRowCount(); i++) {
	            String adminDate = resultValidAdminDateREQA.getData(i, "ADMIN_DATE");

	            if (!adminDate.equals(fechaHoy)) {
	                existeDistinto = true;
	                System.out.println("Registro con ADMIN_DATE distinto encontrado: " + adminDate + " (Fecha de hoy: " + fechaHoy + ")");
	                break;
	            }
	        }

	        if (!existeDistinto) {
	            throw new Exception("Todos los ADMIN_DATE son iguales a la fecha de hoy: " + fechaHoy);
	        }

	        testCase.addQueryEvidenceCurrentStep(resultValidAdminDateREQA);
	        System.out.println("Validación correcta: se encontró al menos un ADMIN_DATE distinto al día de hoy.");

	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Esta funcion valida que del resultado que se obtuvo de la consulta contenga una trx con admin_date mayor al creation_date
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 25/08/2025
	 */
	
	public void validAdminDateMayorTPEREP(String statusPO21, String store1, String store2, String store3) throws Exception {
	    try {
	        String queryValidAdminDateREQA = "SELECT FOLIO, APPLICATION, OPERATION, ENTITY, ADMIN_DATE, WM_CODE, WM_STATUS_PO21, VALUE\r\n"
	                + "FROM TPEREP.TPG_TRANSACTIONS_SPIN\r\n"
	                + "WHERE WM_STATUS_PO21 = '" + statusPO21 + "'\r\n"
	                + "AND STORE_ID IN ('" + store1 + "', '" + store2 + "', '" + store3 + "')\r\n"
	                + "AND ADMIN_DATE >= TRUNC(SYSDATE - 15)\r\n"
	                + "AND OPERATION = 'TRN1'\r\n"
	                + "AND WM_CODE = 101\r\n"
	                + "ORDER BY CREATION_DATETIME DESC";

	        SQLUtil dbWMREQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCWMREQA, PagoQRspin_MVP3.DB_USER_FCWMREQA, PagoQRspin_MVP3.DB_PASSWORD_FCWMREQA);
	        SQLResult resultValidAdminDateREQA = dbWMREQA.executeQuery(queryValidAdminDateREQA);
	        
	        System.out.println(queryValidAdminDateREQA);
	        testCase.addBoldTextEvidenceCurrentStep(queryValidAdminDateREQA);

	        if (resultValidAdminDateREQA.isEmpty()) {
	            throw new Exception("No se encontró ningún registro en la consulta");
	        }

	        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 

	        boolean existeMenor = false;

	        for (int i = 0; i < resultValidAdminDateREQA.getRowCount(); i++) {
	            String adminDateStr = resultValidAdminDateREQA.getData(i, "ADMIN_DATE");
	            String creationDateStr = resultValidAdminDateREQA.getData(i, "CREATION_DATETIME");

	            LocalDateTime adminDate = LocalDateTime.parse(adminDateStr, formato);
	            LocalDateTime creationDate = LocalDateTime.parse(creationDateStr, formato);

	            if (creationDate.isBefore(adminDate)) {
	                existeMenor = true;
	                System.out.println("Registro válido encontrado -> CREATION_DATETIME: " 
	                        + creationDate + " es menor que ADMIN_DATE: " + adminDate);
	                break;
	            }
	        }

	        if (!existeMenor) {
	            throw new Exception("No se encontró ningún registro donde CREATION_DATETIME sea menor que ADMIN_DATE.");
	        }

	        testCase.addQueryEvidenceCurrentStep(resultValidAdminDateREQA);
	        System.out.println("Validación correcta: se encontró al menos un registro con CREATION_DATETIME < ADMIN_DATE.");

	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Esta funcion valida que del resultado que se obtuvo de la consulta contenga una trx con admin_date mayor al creation_date
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 25/08/2025
	 */
	
	public void validAdminDateMenorTPEREP(String statusPO21, String store1, String store2, String store3) throws Exception {
	    try {
	        String queryValidAdminDateREQA = "SELECT FOLIO, APPLICATION, OPERATION, ENTITY, ADMIN_DATE, WM_CODE, WM_STATUS_PO21, VALUE\r\n"
	                + "FROM TPEREP.TPG_TRANSACTIONS_SPIN\r\n"
	                + "WHERE WM_STATUS_PO21 = '" + statusPO21 + "'\r\n"
	                + "AND STORE_ID IN ('" + store1 + "', '" + store2 + "', '" + store3 + "')\r\n"
	                + "AND ADMIN_DATE >= TRUNC(SYSDATE - 35)\r\n"
	                + "AND OPERATION = 'TRN1'\r\n"
	                + "AND WM_CODE = 101\r\n"
	                + "ORDER BY CREATION_DATETIME DESC";

	        SQLUtil dbWMREQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCWMREQA, PagoQRspin_MVP3.DB_USER_FCWMREQA, PagoQRspin_MVP3.DB_PASSWORD_FCWMREQA);
	        SQLResult resultValidAdminDateREQA = dbWMREQA.executeQuery(queryValidAdminDateREQA);
	        
	        System.out.println(queryValidAdminDateREQA);
	        testCase.addBoldTextEvidenceCurrentStep(queryValidAdminDateREQA);

	        if (resultValidAdminDateREQA.isEmpty()) {
	            throw new Exception("No se encontró ningún registro en la consulta");
	        }

	        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 

	        boolean existeMayor = false;

	        for (int i = 0; i < resultValidAdminDateREQA.getRowCount(); i++) {
	            String adminDateStr = resultValidAdminDateREQA.getData(i, "ADMIN_DATE");
	            String creationDateStr = resultValidAdminDateREQA.getData(i, "CREATION_DATETIME");

	            LocalDateTime adminDate = LocalDateTime.parse(adminDateStr, formato);
	            LocalDateTime creationDate = LocalDateTime.parse(creationDateStr, formato);

	            if (creationDate.isBefore(adminDate)) {
	                existeMayor = true;
	                System.out.println("Registro válido encontrado -> CREATION_DATETIME: " 
	                        + creationDate + " es menor que ADMIN_DATE: " + adminDate);
	                break;
	            }
	        }

	        if (!existeMayor) {
	            throw new Exception("No se encontró ningún registro donde CREATION_DATETIME sea menor que ADMIN_DATE.");
	        }

	        testCase.addQueryEvidenceCurrentStep(resultValidAdminDateREQA);
	        System.out.println("Validación correcta: se encontró al menos un registro con CREATION_DATETIME < ADMIN_DATE.");

	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Se valida la existencia del campo Atributo5 con formato YYYYMMDD en tabla MCP_GL_TPC con base en folio buscad
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 25/08/2025
	 */
	
	public void validExistAtributo5(String folio, String store1, String store2, String store3) throws Exception {
		try {
	        String query = "SELECT X.ID_MPC_GL_TPC,\r\n"
	                + "       X.ATRIBUTO5, \r\n"
	                + "       X.CREATION_DATE, \r\n"
	                + "       TO_CHAR(X.CREATION_DATE, 'HH24:MI'),\r\n"
	                + "       X.FOLIO_WM, \r\n"
	                + "       X.FORMA_PAGO,\r\n"
	                + "       X.CR_PLAZA,\r\n"
	                + "       X.CR_TIENDA,\r\n"
	                + "       X.ENTERED_CR,\r\n"
	                + "       X.ACCOUNTING_DATE\r\n"
	                + "FROM  XXFC.XXFC_MPC_GL_TPC X\r\n"
	                + "WHERE X.FOLIO_WM = '" + folio + "'\r\n"
	                + "AND X.FORMA_PAGO = 'TTYP1814'\r\n"
	                + "AND TRUNC(X.CREATION_DATE) >= TRUNC(SYSDATE-1)\r\n"
	                + "ORDER BY 2, X.CREATION_DATE DESC";
//	                + "AND X.CR_TIENDA IN ('" + store1 + "', '" + store2 + "', '" + store3 + "')\r\n"
	        SQLUtil dbXXFC_MPC_GL_TPC = new SQLUtil(PagoQRspin_MVP3.DB_HOST_AVEBQA_OSI, PagoQRspin_MVP3.DB_USER_AVEBQA_OSI, PagoQRspin_MVP3.DB_PASSWORD_AVEBQA_OSI);
	        SQLResult result = dbXXFC_MPC_GL_TPC.executeQuery(query);
	        System.out.println(query);
	        testCase.addBoldTextEvidenceCurrentStep(query);

	        if (result.isEmpty()) {
	            throw new Exception("No se encontró ningún registro en la consulta con el folio: " + folio);
	        }

	        String atributo5 = result.getData(0, "ATRIBUTO5");

	        if (atributo5 == null || atributo5.trim().isEmpty()) {
	            throw new Exception("El campo ATRIBUTO5 no existe o está vacío para el folio: " + folio);
	        }

	        // Validación de formato YYYYMMDD
	        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");
	        try {
	            LocalDate.parse(atributo5, formato);
	            System.out.println("ATRIBUTO5 válido con formato YYYYMMDD: " + atributo5);
	            testCase.addTextEvidenceCurrentStep("ATRIBUTO5 válido con formato YYYYMMDD: " + atributo5);
	        } catch (DateTimeParseException e) {
	            throw new Exception("ATRIBUTO5 no cumple con el formato YYYYMMDD. Valor: " + atributo5);
	        }
		}catch(Exception e) {
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Se realiza consulta a BD WMINT a la tabla WM_EB1_EXECUTION para validar que no exista ejecucion previa de la interface del proveedor
	 * @param Namearchivo
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 29/08/2025
	 */
	
	public boolean validPreviewExecEB1(String Namearchivo, boolean expected) throws Exception {
	    try {
	        String query = "SELECT CASE COUNT(*) \r\n"
	                + "WHEN 0 THEN 'false' \r\n"
	                + "ELSE 'true' \r\n"
	                + "END EXISTENTE \r\n"
	                + "FROM WMUSER.WM_EB1_EXECUTION \r\n"
	                + "WHERE NOMBRE_ARCHIVO = '" + Namearchivo + "'";

	        SQLUtil dbWMINT = new SQLUtil(PagoQRspin_MVP3.DB_HOST_WMINT,PagoQRspin_MVP3.DB_USER_WMINT,PagoQRspin_MVP3.DB_PASSWORD_WMINT);
	        SQLResult result = dbWMINT.executeQuery(query);
	        System.out.println(query);
	        testCase.addBoldTextEvidenceCurrentStep(query);

	        if (result.isEmpty()) {
	            throw new Exception("La consulta no devolvió resultados para el archivo: " + Namearchivo);
	        }
	        
	        String existente = result.getData(0, "EXISTENTE");
	        testCase.addTextEvidenceCurrentStep("Resultado de EXISTENTE: " + existente);
	        boolean actual = "true".equalsIgnoreCase(existente);

	        if (actual == expected) {
	            System.out.println("Validación correcta: se obtuvo " + actual + " como se esperaba.");
	            testCase.addTextEvidenceCurrentStep("Validación correcta: se obtuvo " + actual + " como se esperaba.");
	            return true;
	        } else {
	            System.out.println("Validación incorrecta: se obtuvo " + actual + " pero se esperaba " + expected);
	            testCase.addTextEvidenceCurrentStep("Validación incorrecta: se obtuvo " + actual + " pero se esperaba " + expected);
	            return false;
	        }

	    } catch (Exception e) {
	        throw new Exception("Error en validPreviewExecEB1: " + e.getMessage());
	    }
	}
	
	/**
	 * Se realiza consulta a BD WMINT a la tabla WM_EB1_EXECUTION para validar que no exista ejecucion previa de la interface del proveedor
	 * @param Namearchivo, entidad
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 29/08/2025
	 */
	
	public boolean validFileProcessEB1(String entidad, String Namearchivo, boolean shouldExist) throws Exception {
	    try {
	        String query = "SELECT * \r\n"
	                + "FROM WMUSER.WM_EB1_EXECUTION \r\n"
	                + "WHERE ENTIDAD = '" + entidad + "'\r\n"
	                + "AND NOMBRE_ARCHIVO = '" + Namearchivo + "'\r\n"
	                + "AND CREATION_DATE >= TRUNC(SYSDATE)\r\n"
	                + "ORDER BY CREATION_DATE DESC";

	        SQLUtil dbWMINT = new SQLUtil(PagoQRspin_MVP3.DB_HOST_WMINT,PagoQRspin_MVP3.DB_USER_WMINT,PagoQRspin_MVP3.DB_PASSWORD_WMINT);
	        SQLResult result = dbWMINT.executeQuery(query);

	        System.out.println(query);
	        testCase.addBoldTextEvidenceCurrentStep(query);

	        // Caso: NO debe existir
	        if (!shouldExist) {
	            if (result.isEmpty()) {
	                testCase.addTextEvidenceCurrentStep("El archivo " + Namearchivo + " no existe (validación correcta).");
	                return true;
	            } else {
	                String nameFile = result.getData(0, "NOMBRE_ARCHIVO");
	                testCase.addTextEvidenceCurrentStep("El archivo existe (" + nameFile + ") pero no debería.");
	                return false;
	            }
	        }

	        // Caso: SÍ debe existir
	        if (shouldExist) {
	            if (result.isEmpty()) {
	                testCase.addTextEvidenceCurrentStep("No se encontró el archivo " + Namearchivo + " pero debería existir.");
	                return false;
	            }

	            String nameFile = result.getData(0, "NOMBRE_ARCHIVO");

	            if (Namearchivo.equals(nameFile)) {
	                testCase.addTextEvidenceCurrentStep("El archivo existe y coincide: " + Namearchivo);
	                return true;
	            } else {
	                testCase.addTextEvidenceCurrentStep("El archivo encontrado (" + nameFile + ") no coincide con el esperado (" + Namearchivo + ").");
	                return false;
	            }
	        }

	        return false;

	    } catch (Exception e) {
	        throw new Exception("Error en validFileProcessEB1: " + e.getMessage());
	    }
	}
	
	/**
	 * Se valida el error que se obtiene al fallar una interface en la tabla WM_LOG_ERROR
	 * @param runid
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 29/08/2025
	 */
	
	public boolean validLogErrorInDB(String runId, String expectedDescription, String expectedMessage, boolean exactMatch) throws Exception {
	    try {
	        String query = "SELECT DESCRIPTION, MESSAGE " +
	                       "FROM WMLOG.WM_LOG_ERROR " +
	                       "WHERE RUN_ID = '" + runId + "' " +
	                       "ORDER BY ERROR_DATE DESC";

	        SQLUtil dbWMLQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCWMLQA,PagoQRspin_MVP3.DB_USER_FCWMLQA,PagoQRspin_MVP3.DB_PASSWORD_FCWMLQA);
	        SQLResult resultadoValidaWMLQA = dbWMLQA.executeQuery(query);

	        System.out.println("Query: " + query);
	        testCase.addBoldTextEvidenceCurrentStep("Consulta de log de error: " + query);

	        if (resultadoValidaWMLQA.isEmpty()) {
	            System.out.println("No se encontraron errores para RUN_ID: " + runId);
	            testCase.addTextEvidenceCurrentStep("No se encontraron errores para RUN_ID: " + runId);
	            return false;
	        }

	        boolean foundMatch = false;

	        for (int i = 0; i < resultadoValidaWMLQA.getRowCount(); i++) {
	            String description = resultadoValidaWMLQA.getData(i, "DESCRIPTION");
	            String message = resultadoValidaWMLQA.getData(i, "MESSAGE");

	            System.out.println("Registro " + (i+1));
	            System.out.println("DESCRIPTION: " + description);
	            System.out.println("MESSAGE: " + message);
	            testCase.addTextEvidenceCurrentStep("Registro " + (i+1) + "DESCRIPTION: " + description + "\r\n"
	            		+ " | MESSAGE: " + message);

	            boolean descMatch;
	            boolean msgMatch;

	            if (exactMatch) {
	                descMatch = description.contains(expectedDescription);
	                msgMatch  = message.contains(expectedMessage);
	            } else {
	                descMatch = description.contains(expectedDescription);
	                msgMatch  = message.contains(expectedMessage);
	            }

	            if (descMatch && msgMatch) {
	                foundMatch = true;
	                System.out.println("Coincidencia encontrada en el registro " + (i+1));
	                testCase.addTextEvidenceCurrentStep("Coincidencia encontrada en el registro " + (i+1));
	            }
	        }

	        if (foundMatch) {
	            return true;
	        } else {
	            System.out.println("Ningún registro coincide con lo esperado.");
	            System.out.println("Esperado DESCRIPTION: " + expectedDescription);
	            System.out.println("Esperado MESSAGE: " + expectedMessage);
	            return false;
	        }

	    } catch (Exception e) {
	        throw new Exception("Error en validLogErrorInDB: " + e.getMessage());
	    }
	}
	
	/**
	 * Validacion de ejecucion en log de BD (FCWMLQA)
	 * @param interfaz
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 08/08/2025
	 */
	
	public Map<String, String> validLogExecInDBEB1(String interfaz, String proveedor) throws Exception {
	    try {
	        String queryValidaWMLQA = "SELECT RUN_ID, INTERFACE, STATUS, SERVER, ATT1\r\n"
	                + "FROM WMLOG.WM_LOG_RUN\r\n"
	                + "WHERE INTERFACE = '" + interfaz + "'\r\n"
	                + "AND ATT1 = '" + proveedor + "'\r\n"
	                + "AND TRUNC(START_DT) >= TRUNC(SYSDATE)\r\n"
	                + "ORDER BY START_DT DESC ";

	        SQLUtil dbWMLQA = new SQLUtil(PagoQRspin_MVP3.DB_HOST_FCWMLQA, PagoQRspin_MVP3.DB_USER_FCWMLQA,PagoQRspin_MVP3.DB_PASSWORD_FCWMLQA);

	        SQLResult resultadoValidaWMLQA = dbWMLQA.executeQuery(queryValidaWMLQA);

	        System.out.println("Query: " + queryValidaWMLQA);
	        System.out.println("Cantidad de filas: " + resultadoValidaWMLQA.getRowCount());
	        testCase.addBoldTextEvidenceCurrentStep(queryValidaWMLQA);

	        if (resultadoValidaWMLQA.getRowCount() > 0) {
	            Map<String, String> result = new HashMap<>();
	            result.put("RUN_ID", resultadoValidaWMLQA.getData(0, "RUN_ID"));
	            result.put("STATUS", resultadoValidaWMLQA.getData(0, "STATUS"));
	            testCase.addQueryEvidenceCurrentStep(resultadoValidaWMLQA);
	            return result;
	        } else {
	            throw new NoSuchElementException("No se pintó información de la ejecución de la interface EB1");
	        }
	    } catch (Exception e) {
	        throw new Exception("Error en validLogExecInDBEB1: " + e.getMessage());
	    }
	}
	
	/**
	 * Validacion de generacion de proceso de dispersion en BD AVEBQA_OSI
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 05/09/2025
	 */
	
	public void validProcesoDispersionQRSPIN() throws Exception {
		String query = "SELECT\r\n"
				+ "   distinct user_concurrent_program_name,\r\n"
				+ "    user_name,\r\n"
				+ "    responsibility_name,\r\n"
				+ "    request_date,\r\n"
				+ "    argument_text,\r\n"
				+ "    request_id,\r\n"
				+ "    phase_code,\r\n"
				+ "    status_code,\r\n"
				+ "    logfile_name,\r\n"
				+ "    outfile_name,\r\n"
				+ "    output_file_type,\r\n"
				+ "    hold_flag,\r\n"
				+ "    fcr.*\r\n"
				+ "FROM\r\n"
				+ "    apps.fnd_concurrent_requests fcr,\r\n"
				+ "    apps.fnd_concurrent_programs_tl fcp,\r\n"
				+ "    apps.fnd_responsibility_tl fr,\r\n"
				+ "    apps.fnd_user fu\r\n"
				+ "WHERE\r\n"
				+ "    fcr.CONCURRENT_PROGRAM_ID = fcp.concurrent_program_id\r\n"
				+ "    and fcr.responsibility_id = fr.responsibility_id\r\n"
				+ "    and fcr.requested_by = fu.user_id\r\n"
				+ "    and user_concurrent_program_name = ('XXFC-Proceso de Dispersion Pago QR General')\r\n"
				+ "ORDER BY fcr.REQUEST_DATE DESC";
		
        SQLUtil dbAVEBQA_OSI = new SQLUtil(PagoQRspin_MVP3.DB_HOST_AVEBQA_OSI, PagoQRspin_MVP3.DB_USER_AVEBQA_OSI, PagoQRspin_MVP3.DB_PASSWORD_AVEBQA_OSI);
        SQLResult result = dbAVEBQA_OSI.executeQuery(query);
        System.out.println(query);
		
        if (result != null && result.getRowCount() > 0) {
            // tomar el primer registro (índice 0)
            String requestDate = result.getData(0, "REQUEST_DATE");
            String userName = result.getData(0, "USER_NAME");
            String argumentText = result.getData(0, "ARGUMENT_TEXT");
            String today = new SimpleDateFormat("dd/MM/yy").format(new Date());

            testCase.addTextEvidenceCurrentStep("REQUEST_DATE obtenido: " + requestDate);
            testCase.addTextEvidenceCurrentStep("Fecha esperada: " + today);
            testCase.addTextEvidenceCurrentStep("USER_NAME obtenido: " + userName);
            testCase.addTextEvidenceCurrentStep("ARGUMENT_TEXT obtenido: " + argumentText);

            if (!requestDate.contains(today)) {
                throw new Exception("El REQUEST_DATE (" + requestDate + ") no corresponde al día actual (" + today + ")");
            }

            if (!"INTERFACES-FC".equals(userName)) {
                throw new Exception("El USER_NAME esperado es INTERFACES-FC pero se obtuvo: " + userName);
            }

            if (!argumentText.contains("TTYP1814")) {
                throw new Exception("El ARGUMENT_TEXT no contiene 'TTYP1814'. Valor obtenido: " + argumentText);
            }

            testCase.addTextEvidenceCurrentStep("Todas las validaciones pasaron correctamente.");
        } else {
            throw new Exception("No se encontró ningún registro en la consulta.");
        }
    }
	
	/**
	 * Ingresar a URL de EBS
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 08/09/2025
	 */
	
	public void ingresarEBS() throws Exception {
		try {
			u.get("http://oxfapfinq1.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp");
			u.waitForLoadPage();
			testCase.addScreenShotCurrentStep(u, "Acceso a la URL");
		} catch (TimeoutException timeoutException) {
			logger.error("TimeOut " + timeoutException);
		}
	}
	
	/**
	 * Click en boton log in de EBS
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 09/09/2025
	 */
	
	public void clickLogIn() {
		try {
			u.highLight(logInBtnXpath);
			u.click(logInBtnXpath);
			u.waitForLoadPage();
			testCase.addScreenShotCurrentStep(u, "Se da clic en Conectar");
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}
	}
	
	/**
	 * Cambiar idioma de EBS a Español
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 09/09/2025
	 */
	
	public void setLanguage() {
		try {
			u.highLight(slangXpath);
			Select language = new Select(u.getDriver().findElement(slangXpath));
			language.selectByValue("ESA");
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}

	}
	
	/**
	 * Ingresar credenciales al LogIn
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 09/09/2025
	 */
	
	public void setCredentials(String user, String password) {
		String pssDecrypted = PasswordUtil.decryptPassword(password);
		try {
			u.highLight(usernameField);
			u.sendKeys(usernameField, user);
			u.highLight(passwordField);
			u.sendKeys(passwordField, pssDecrypted);
			testCase.addScreenShotCurrentStep(u, "Se agregan credenciales");
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}

	}
	
	/**
	 * Realizar scroll a la pantalla hsta encontrar el elemento
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 09/09/2025
	 */
	
	public void scrollToElementIfPresent() throws Exception {
	    try {
	    	u.waitWebElement(MenuTableIzq, 5, 0);
	    	if(u.isDisplayed(MenuTableIzq)) {
	    	u.highLight(MenuTableIzq);
	    	u.hardWait(2);
	    	u.scrollToElement(MXOXXOGL17ADMSERVICIOS);
	    	u.highLight(MXOXXOGL17ADMSERVICIOS);
	    	}
	    	else{
	    		throw new Exception("No se logro realizar el scroll al elemento");
	    	}
	    	
	    } catch (Exception e) {
	    	throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Abrir pestañas Solicitas, Informes y Standar
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 09/09/2025
	 */
	
	public void clickOnInformesSolicitudStandard() throws Exception {
		try {
		u.highLight(carpetaInformes17ADMSERVICIOS);
		u.click(carpetaInformes17ADMSERVICIOS);
		u.hardWait(5);
		u.highLight(carpetaSolicitar17ADMSERVICIOS);
		u.click(carpetaSolicitar17ADMSERVICIOS);
		u.hardWait(5);
		u.highLight(btnStandar17ADMSERVICIOS);
		u.click(btnStandar17ADMSERVICIOS);
		u.hardWait(10);
		}catch(Exception e) {
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Validacion de existencia de valores de no ejecución previas de interface EB1
	 * @param archivoConciliacion
	 * @author Monica Soto
	 * @throws Exception 
	 * @date 01/09/2025
	 */
	public boolean validateExistence(String archivoConciliacion) throws Exception {

		SQLUtil dbWMINT = new SQLUtil(PagoQRspin_MVP3.DB_HOST_WMINT, PagoQRspin_MVP3.DB_USER_WMINT,PagoQRspin_MVP3.DB_PASSWORD_WMINT);

		try {	
			String query = "SELECT CASE COUNT(*) WHEN 0 THEN 'False' ELSE 'True' END EXISTENTE FROM WMUSER.WM_EB1_EXECUTION WHERE NOMBRE_ARCHIVO = '"+archivoConciliacion+"'";

			SQLResult result = dbWMINT.executeQuery(query);
			String stringResult = result.getData(0, "EXISTENTE");
			Boolean validateResult = Boolean.valueOf(stringResult);
			
			testCase.addQueryEvidenceCurrentStep(result);

			return validateResult;
		}
		catch(Exception e) {
			 throw new Exception("Error: " + e.getMessage());
		}
	}
	
}