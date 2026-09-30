package WM.interfaces.tpe.fac;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.testng.annotations.Test;

import WM.om.TPE_FAC;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_011_FAC_EC_LoginExitBanderaEncendidaContraNoEncript extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_FAC_EC_LoginExitBanderaEncendidaContraNoEncript_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerías
		 *********************************************************************/
		//SQLUtil dbMtyFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,GlobalVariables.DB_PASSWORD_FCTPE);
		SQLUtil dbFCACQA = new SQLUtil(GlobalVariables.DB_HOST_FCACQA, GlobalVariables.DB_USER_FCACQA, GlobalVariables.DB_PASSWORD_FCACQA);
		
		TPE_FAC facUtil = new TPE_FAC(data, testCase, dbFCACQA);
		/*
		 * Variables
		 *********************************************************************/
		
		String consultaSADEL = "select * from " 
				+ "TPEUSER.config_tiendas_sadel WHERE rownum <= 5";
		
		String consultaUsuario = "select * from "
				+ "XXADCJ.XXADCJ_USUARIOS WHERE ESTADO = 'A' and rownum = 1";
		
		String wmCodeRequestQRY09;
		
		String consultaLoginUsuario = "select * from "
				+ "TPEUSER.TPE_FAC_LOGIN_APLICACION where "
				+ "ID_USER='%s' and APLICACION ='FAC' ";
		
		String consultaTransaccion = "select APPLICATION, ENTITY, OPERATION, SOURCE, FOLIO, "
				+ "CREATION_DATE, WM_CODE, WM_DESC "
				+ "from TPEUSER.TPE_FR_TRANSACTION "
				+ "where CREATION_DATE BETWEEN TO_DATE('%s','dd/mm/yyyy hh24:mi:ss') "
				+ "AND TO_DATE('%s','dd/mm/yyyy hh24:mi:ss') "
				+ "AND FOLIO IN '%s' AND ENTITY='BANK' ORDER BY CREATION_DATE DESC";
		
		
		testCase.setTest_Description(data.get("id") + data.get("description"));
		
		String wmCodeToValidateQRY09 = "101";
		
		Date fechaInicio;
		Date fechaFin;
		Date fechaLogin;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		formatter.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		
		String user_id = data.get("provitionalUser");
		
		/********************************************************************************
		 * Consulta Tiendas que estan en SADEL
		 * 
		 ********************************************************************************/
		
		// Paso 1 ****************************************************
		addStep("Ejecutar Query para validar que las tiendas esten en SADEL");
		
		SQLResult dbMtyFCTPEResult = executeQuery(dbFCACQA, consultaSADEL);
		
		boolean validaConsultaSADEL = consultaSADEL.isEmpty();
		
		if(!validaConsultaSADEL) {
			
			testCase.addQueryEvidenceCurrentStep(dbMtyFCTPEResult);
		}
		
		System.out.println(validaConsultaSADEL);
		
		assertFalse(validaConsultaSADEL,"No se mostraron las tiendas activas para SADEL");
		
		
		/********************************************************************************
		 * Consulta para seleccionar usuario activo para realizar corresponsalias
		 * 
		 ********************************************************************************/
		
		// Paso 2 ****************************************************
		
		addStep("Ejecutar Query para seleccionar un usuario activo para realizar corresponsalias");
		
		SQLResult dbFCACQAResult = executeQuery(dbFCACQA,consultaUsuario);
		
		String id_usuario = "";
		String password_usuario = "";
		
		boolean validaConsultaUsuario = consultaUsuario.isEmpty();
		
		if(!validaConsultaUsuario) {
			
			id_usuario = dbFCACQAResult.getData(0, "id_usuario");
			password_usuario = dbFCACQAResult.getData(0, "contrasena");
			
			System.out.println("id_usuario: " + id_usuario);
			System.out.println("password_usuario: " + password_usuario);
			
			testCase.addQueryEvidenceCurrentStep(dbFCACQAResult);
		}
		
		System.out.println(validaConsultaUsuario);
		
		assertFalse(validaConsultaUsuario,"No se mostraron usuarios activos");
		
		
		/********************************************************************************
		 * Ejecucion de Servicio para encriptar contraseña
		 * 
		 ********************************************************************************/
		
		// Paso 3 ****************************************************
		
		
		addStep("Ejecutar servicio 'sec.enc.pub::request de la interfaz FEMSA_SEC_ENC para encriptar la contraseña'");
		
		/********************************************************************************
		 * Ejecucion de peticion desde el navegador
		 * 
		 ********************************************************************************/
		
		// Paso 4 ****************************************************
		
		addStep("Llamar al servicio de consulta QRY09");
		
		fechaLogin = new Date();
		
		SimpleDateFormat formatters = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); 
		formatters.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		
		String fechaUltimoAcceso = formatters.format(fechaLogin);
		
		System.out.println("Fecha Ultimo Acceso: " + fechaUltimoAcceso);
		/*
		String respuestaQRY09 = facUtil.QRY09_2(id_usuario, password_usuario);
		
		//String folioQRY09  = RequestUtil.getFolioXml(respuestaQRY09);
		
		String folioQRY09 = "";
		
		String fecha_Inicio_User = fechaInicial.replace("/", "-");
		
		wmCodeRequestQRY09 = RequestUtil.getWmCodeXml(respuestaQRY09);
		
		System.out.println("Respuesta: " + respuestaQRY09);
		
		System.out.println("CodeRequest: " + wmCodeRequestQRY09);
		
		
		System.out.println(fechaInicial);
		
		// Se valida que haya respuesta
		boolean validaRespuestaQRY09 = true;
		if (respuestaQRY09 != null) {
			validaRespuestaQRY09 = false;
		}
		assertFalse(validaRespuestaQRY09); 
		
		
		// Se valida el codigo de respuesta sea el correcto
		
		addStep("Verificar que el XML de respuesta tenga como wmCode: " + wmCodeToValidateQRY09);

		boolean validationRequestQRY09 = wmCodeRequestQRY09.equals(wmCodeToValidateQRY09);
		
		System.out.println(validationRequestQRY09 + " - wmCode request QRY09 : " + wmCodeRequestQRY09);
			
		testCase.addTextEvidenceCurrentStep(
				"Codigo Esperado: " + wmCodeToValidateQRY09  + "\r\n" + "Codigo XML: " + wmCodeRequestQRY09);

		assertTrue(validationRequestQRY09, "El codigo wmCode no es el esperado: " + data.get("wmCodeQRY09"));
		
		*/
		
		fechaFin = new Date();
		
        String fechaFinal = formatter.format(fechaFin);
		
		System.out.println("Fecha Final: " + fechaFinal);
		
		
		// Paso 5 ****************************************************
		
		// Consulta de Login de Usuario
		
		addStep("Se valida el registro del Login del usaurio");
		
		String ConsultaLoginUsuario_f = String.format(consultaLoginUsuario, user_id);
		
		System.out.println("Consulta de referencia : \r\n "+ ConsultaLoginUsuario_f);
	
		SQLResult ConsultaReferencias_r = executeQuery(dbFCACQA, ConsultaLoginUsuario_f);
		
		String bd_id_usuario = "";
		String fecha_acceso = "";
		
		boolean validaUserLogin = ConsultaReferencias_r.isEmpty();
		
		if(!validaUserLogin) {
			
			bd_id_usuario = ConsultaReferencias_r.getData(0, "ID_USER");
			fecha_acceso = ConsultaReferencias_r.getData(0, "FECHA_ACCESO");
			
			System.out.println("id_usuario: " + bd_id_usuario);
			System.out.println("fecha_acceso: " + fecha_acceso);
			
			testCase.addQueryEvidenceCurrentStep(ConsultaReferencias_r);
			
			testCase.addTextEvidenceCurrentStep(
					"Usuario Esperado: " + bd_id_usuario  + "\r\n" + "Usuario Actual: " + user_id
					+ "\r\n" + "Fecha Esperada: " + fechaUltimoAcceso + "\r\n" + "Fecha Actual: " + fecha_acceso);
			
		}
		
		
		assertEquals(bd_id_usuario, user_id);
		assertEquals(fechaUltimoAcceso, fecha_acceso);
		
		
		// Paso 6 ****************************************************
		
		addStep("Se valida el registro de las Transacciones realizadas");
		
		String consultaTransaccion_f = String.format(consultaTransaccion, fechaUltimoAcceso, fechaFinal, "1"); //1 = folioQRY09
		
		SQLResult consultaRefTransaccion_r = executeQuery(dbFCACQA, consultaTransaccion_f);
		
		boolean validaTransaccion = consultaTransaccion_f.isEmpty();
		
		String bd_folio = "";
		String bd_fecha_trans = "";
		
		if(!validaTransaccion) {
			
			bd_folio = consultaRefTransaccion_r.getData(0, "FOLIO");
			bd_fecha_trans = consultaRefTransaccion_r.getData(0, "CREATION_DATE");
			
			System.out.println("id_usuario: " + bd_folio);
			System.out.println("fecha_acceso: " + bd_fecha_trans);
			
			testCase.addQueryEvidenceCurrentStep(consultaRefTransaccion_r);
			
			testCase.addTextEvidenceCurrentStep(
					"Folio Esperado: " + bd_folio  + "\r\n" + "Folio Actual: " + "1"
					+ "\r\n" + "Fecha Esperada: " + fechaUltimoAcceso + "\r\n" + "Fecha Actual: " + bd_fecha_trans);
			
		}
		
		assertEquals(bd_folio, "1");
		assertEquals(fechaUltimoAcceso, bd_fecha_trans);
	}
	

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_FAC_EC_LoginExitBanderaEncendidaContraNoEncript_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseOnofre@Hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}
