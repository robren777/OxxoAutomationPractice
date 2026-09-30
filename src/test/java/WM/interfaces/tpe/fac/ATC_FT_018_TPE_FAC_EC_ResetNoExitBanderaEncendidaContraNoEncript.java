package WM.interfaces.tpe.fac;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.testng.annotations.Test;

import WM.om.TPE_FAC;
import WM.util.GlobalVariables;
import WM.util.RequestUtil;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_018_TPE_FAC_EC_ResetNoExitBanderaEncendidaContraNoEncript extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_018_TPE_FAC_EC_ResetNoExitBanderaEncendidaContraNoEncript_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerias
		 *********************************************************************/
		SQLUtil dbFCACQA = new SQLUtil(GlobalVariables.DB_HOST_FCACQA, GlobalVariables.DB_USER_FCACQA, GlobalVariables.DB_PASSWORD_FCACQA);
		
		TPE_FAC facUtil = new TPE_FAC(data, testCase, dbFCACQA);
		/**
		 * Proyecto:  Encripcion de corresponsalias FAC
		 * CP: MTC-FT-007 - Reset no exitoso de usuario con bandera encendida y contrasena encriptada en BD
		 * Desc:Se valida proceso de reseto de contraseña(QRY09) cuando este con bandera encendida y contrasena no 
		 * encriptada en la BD enviando la contrasena erronea para mostrar codigoWM 002 de fallo (QRY12 - SADEL-"flagEncriptado"=TRUE) 
		 * Autor: Marisol Rodriguez
		 * Date: 27-03-2023
		 */
		
		/*
		 * Variables
		 *********************************************************************/
		

//		String ValidTiendas = "SELECT CR_TIENDA,CR_PLAZA,FAC_VERSION \r\n" 
//		        + "FROM TPEUSER.config_tiendas_sadel \r\n"
//		        + "where CR_PLAZA ='"+data.get("plaza") + "' \r\n" 
//				+ "and CR_TIENDA='"+data.get("tienda") + "'";
		
		
		//Paso 2
		String SelectUsuario = "SELECT A.ID_USUARIO, B.DESC_ROL, A.ESTADO, A.CONTRASENA, C.CR_PLAZA, C.CR_TIENDA, C.ID_EMPLEADO\r\n"
				+ "FROM XXADCJ.XXADCJ_USUARIOS A,\r\n"
				+ "XXADCJ.XXADCJ_ROLES B,\r\n"
				+ "XXADCJ.XXADCJ_EMPLEADOS C,\r\n"
				+ "XXADCJ.XXADCJ_ROL_FUNCION D,\r\n"
				+ "TPEUSER.CONFIG_TIENDAS_SADEL SA,\r\n"
				+ "TPEUSER.TPE_STORE ST\r\n"
				+ "WHERE A.ID_EMPLEADO = C.ID_EMPLEADO\r\n"
				+ "AND ST.PLAZA = sa.cr_plaza\r\n"
				+ "AND sa.cr_plaza = '"+data.get("plaza")+"'\r\n"
				+ "AND ST.TIENDA = SA.CR_TIENDA\r\n"
				+ "AND c.CR_TIENDA = SA.CR_TIENDA\r\n"
				+ "AND SA.CR_TIENDA = '"+ data.get("tienda")+"'\r\n"
				+ "AND A.ID_USUARIO = '"+data.get("usuario")+"'\r\n"
				+ "AND C.ID_FUNCION = D.CVE_FUNCION\r\n"
				+ "AND D.CVE_ROL = B.CVE_ROL\r\n"
				+ "AND A.ESTADO = 'A'\r\n"
				+ "AND B.DESC_ROL = 'Cajero'\r\n"
				+ "And rownum = 1";
		
		//Paso 3
				String  store = "SELECT plaza, tienda, creation_date, retek_cr, status\r\n"
						+ "FROM TPEUSER.TPE_STORE\r\n"
						+ "WHERE PLAZA = '%s'\r\n"
						+ "AND TIENDA = '%s'";
				
		//Paso 5
				String transaction = "SELECT application, entity, operation, folio, wm_code, creation_date\r\n"
						+ "FROM TPEUSER.TPE_FR_TRANSACTION \r\n"
						+ "WHERE  APPLICATION ='FAC' \r\n"
						+ "AND CREATION_DATE BETWEEN TO_DATE('%s','dd/mm/yyyy hh24:mi:ss') \r\n"
						+ "and TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n"
						+ "AND FOLIO = '"+data.get("folio")+"'\r\n"
						+ "AND ENTITY='BANK' \r\n"
						+ "AND OPERATION = 'QRY12'\r\n"
						+ "AND APPLICATION = 'FAC'\r\n"
						+ "AND WM_CODE = '"+data.get("wmCodeQRY12")+"' \r\n"
						+ "ORDER BY CREATION_DATE DESC ";
		
		//QRY12
				String wmCodeRequestQRY12;
				String wmCodeToValidateQRY12 = data.get("wmCodeQRY12"); //002 
				String respuestaQRY12;
				
		//Revisar fechas inicio y fin del request
			    Date fechaInicio ;
				Date fechaFin;
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
				formatter.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
	
		
		testCase.setProject_Name("Encriptacion corresponsalias");
		testCase.setTest_Description(data.get("id") + data.get("description"));
		testCase.setPrerequisites(data.get("prerequicitos"));
		
		/*
		 * Paso 1
		 *****************************************************************************************/

//		addStep("Ejectar query para validar que las tiendas esten en SADEL En la BD FCACQA");
//
//		System.out.println(GlobalVariables.DB_HOST_FCACQA);
//		System.out.println(ValidTiendas);
//		SQLResult ValidResult = executeQuery(dbFCACQA, ValidTiendas);
//
//		boolean ValidBool = ValidResult.isEmpty();
//
//		if (!ValidBool) {
//			testCase.addBoldTextEvidenceCurrentStep(
//					"Se encontraron datos para validar que las tiendas esten en SADEL en la BD FCACQA");
//		}
//
//		testCase.addQueryEvidenceCurrentStep(ValidResult);
//
//		System.out.println(ValidBool);
//
//		assertFalse(ValidBool, "La tabla no contiene informacion.");

		/* Paso 2 *********************************************************/
		
		addStep("Ejecutar el siguiente query en la BD FCACQA para seleccionar un usuario activo para realizar corresponsalias:");

		System.out.println(GlobalVariables.DB_HOST_FCACQA);
		System.out.println(SelectUsuario);
		SQLResult SelectUserRespuesta = executeQuery(dbFCACQA, SelectUsuario );
		String idUser= "";
		String Password= "";
	    String plaza = "";
	    String tienda = "";

		boolean SelectUserResBool = SelectUserRespuesta.isEmpty();

		if (!SelectUserResBool) {
			testCase.addBoldTextEvidenceCurrentStep(
					"Se encontro usuario activo para realizar corresponsalia");
			idUser = SelectUserRespuesta.getData(0, "ID_USUARIO");
			Password = SelectUserRespuesta.getData(0, "CONTRASENA");
			plaza = SelectUserRespuesta.getData(0, "CR_PLAZA");
			tienda = SelectUserRespuesta.getData(0, "CR_TIENDA");
			
			System.out.println("User: " + idUser);
			System.out.println("Password: " + Password);
			System.out.println("Plaza : " + plaza);
			System.out.println("Tienda " + tienda);
			
			
		}

		testCase.addQueryEvidenceCurrentStep(SelectUserRespuesta);
		
		System.out.println(SelectUserResBool);
		assertFalse(SelectUserResBool, "La tabla no contiene informacion.");
		
	//Obtener fecha Inicial ***************************************************************************************
		
		
		fechaInicio = new Date(); //Se Saca la fecha inicial del request
				
		String fechaInicial = formatter.format(fechaInicio);
				
	    System.out.println("Fecha incial: "+ fechaInicial);

		
		///*Paso 3 *********************************************************/
		addStep("Validar doblemente en el siguiente query  ya que en la TPE_STORE aveces tiene la misma "
				+ "tienda que SADEL pero diferente plaza");		
		String store_f = String.format(store, plaza, tienda);
        System.out.println(store_f);
   		SQLResult store_result = executeQuery(dbFCACQA,store_f);
   		boolean validastore = store_result.isEmpty();
   		
   		testCase.addQueryEvidenceCurrentStep(store_result);
   		
   		assertFalse(validastore,"No se mostraron plazas");
			

	///*Paso 4 *********************************************************/
		addStep("Ejecutar la siguiente peticion desde el navegador con la url del F5");
		
		respuestaQRY12 = facUtil.QRY12(idUser, data.get("contrasenaIncorrecta"));			
		wmCodeRequestQRY12 = RequestUtil.getWmCodeXml(respuestaQRY12);
						
		// Se valida que haya respuesta
		boolean validaRespuestaQRY12 = true;
		if (respuestaQRY12 != null) {
			validaRespuestaQRY12 = false;
		}
		assertFalse(validaRespuestaQRY12); 
				
		// Se valida el codigo de respuesta sea el correcto
		
		addStep("Verificar que el XML de respuesta tenga como wmCode: " + wmCodeToValidateQRY12);

		boolean validationRequestQRY12 = wmCodeRequestQRY12.equals(wmCodeToValidateQRY12);
		
		System.out.println(validationRequestQRY12 + " - wmCode request QRY12 : " + wmCodeRequestQRY12);
			
		testCase.addTextEvidenceCurrentStep(
				"Codigo Esperado: " + wmCodeToValidateQRY12  + "\r\n" + "Codigo Resultado: " + wmCodeRequestQRY12);

		assertTrue(validationRequestQRY12, "El codigo wmCode no es el esperado: " + wmCodeRequestQRY12);
		
	//Obtiene fecha fin de ejecucion ********************************************************************************
		
		 fechaFin = new Date();
			
	     String fechaFinal = formatter.format(fechaFin);
			
	     System.out.println("Fecha final: "+fechaFinal);
	
		
		// Paso 5 ****************************************************
		
		addStep("Se valida el registro de las Transacciones realizadas");
		
		String consultaTransaccion_f = String.format(transaction, fechaInicial, fechaFinal); 
		
		System.out.println(consultaTransaccion_f);
		
		SQLResult consultaRefTransaccion_r = executeQuery(dbFCACQA, consultaTransaccion_f);
		
		boolean validaTransaccion = consultaRefTransaccion_r.isEmpty();
		
				
		if(!validaTransaccion) {
			
			testCase.addQueryEvidenceCurrentStep(consultaRefTransaccion_r);
			
			
		} else  {
			
			testCase.addTextEvidenceCurrentStep("No se insertaron las transacciones realizadas en ");
		}
		
		assertFalse(validaTransaccion , "No se registro la transaccion TPEUSER.TPE_FR_TRANSACTION");
	}
	

	@Override
	public String setTestFullName() {
		return "ATC_FT_018_TPE_FAC_EC_ResetNoExitBanderaEncendidaContraNoEncript_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Equipo automatizacion";
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
