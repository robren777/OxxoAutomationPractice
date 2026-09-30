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



public class ATC_FT_020_TPE_FAC_EC_CambioPasswNoExitosoUsuarioBanderaON extends BaseExecution {


	@Test(dataProvider = "data-provider")
	public void ATC_FT_020_TPE_FAC_CambioPasswNoExitosoUsuarioBanderaON_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerias
		 *********************************************************************/

		utils.sql.SQLUtil dbFCACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCACQA,
				GlobalVariables.DB_USER_FCACQA, GlobalVariables.DB_PASSWORD_FCACQA);
		TPE_FAC facUtil = new TPE_FAC(data, testCase,dbFCACQA);
		
		/**
		 * Encriptacion de corresponsalias FAC: 
		 * MTC-FT-009 - Cambio de contrasena exitoso de usuario con bandera encendida y contrasena encriptada en BD
		   (QRY13 - SADEL-"flagEncriptado"=TRUE)  
		 * Desc:
		 * Se valida proceso de cambio de contrasena(QR13) cuando este con bandera encedida y contraseña no encriptada en la BD para mostrar 
		 * codigoWM 101 de exitoso
		   (QRY13 - SADEL-"flagEncriptado"=TRUE) 
		 * @author Gilberto Martinez
		 * @date   2023/02/24
		 */



		/*
		 * Variables
		 *********************************************************************/
		
		String SelectUs = "SELECT A.ID_USUARIO, B.DESC_ROL, A.ESTADO, A.CONTRASENA, C.CR_PLAZA, C.CR_TIENDA, C.ID_EMPLEADO\r\n"
				+ "FROM XXADCJ.XXADCJ_USUARIOS A, \r\n"
				+ "XXADCJ.XXADCJ_ROLES B, \r\n"
				+ "XXADCJ.XXADCJ_EMPLEADOS C, \r\n"
				+ "XXADCJ.XXADCJ_ROL_FUNCION D, \r\n"
				+ "TPEUSER.CONFIG_TIENDAS_SADEL SA,\r\n"
				+ "TPEUSER.TPE_STORE ST\r\n"
				+ "WHERE A.ID_EMPLEADO = C.ID_EMPLEADO\r\n"
				+ "AND ST.PLAZA = sa.cr_plaza\r\n"
				+ "AND sa.cr_plaza = '"+data.get("plaza")+"'\r\n"
				+ "AND ST.TIENDA = SA.CR_TIENDA\r\n"
				+ "AND c.CR_TIENDA = SA.CR_TIENDA\r\n"
				+ "AND SA.CR_TIENDA = '"+data.get("tienda")+"'\r\n"
				+ "AND A.ID_USUARIO = '"+data.get("idusuario")+"'\r\n"
				+ "AND C.ID_FUNCION = D.CVE_FUNCION\r\n"
				+ "AND D.CVE_ROL = B.CVE_ROL\r\n"
				+ "AND A.ESTADO = 'A'\r\n"
				+ "AND B.DESC_ROL = 'Cajero'";
		
		
		String ValidStore = "SELECT PLAZA,TIENDA,CREATION_DATE,RETEK_CR,AFFILIATION,MODIFICATION_DATE,STATUS "
				+ "FROM TPEUSER.TPE_STORE "
				+ "WHERE PLAZA = '%s' "
				+ "AND TIENDA = '%s'";
				

		
		String ValidTrans=	"select APPLICATION, ENTITY, OPERATION, SOURCE, FOLIO,\r\n"
				+ "CREATION_DATE, WM_CODE, WM_DESC \r\n"
				+ "from TPEUSER.TPE_FR_TRANSACTION \r\n"
				+ "where CREATION_DATE BETWEEN TO_DATE('','dd/mm/yyyy hh24:mi:ss')\r\n"
				+ "AND TO_DATE('%s','dd/mm/yyyy hh24:mi:ss') \r\n"
				+ "AND ENTITY='BANK' \r\n"
				+ "AND OPERATION = 'QRY13'\r\n"
				+ "AND APPLICATION = 'FAC'\r\n"
				+ "AND WM_CODE = '"+ data.get("wmCodeQRY13")+"'\r\n"
				+ "ORDER BY CREATION_DATE DESC";
		
		//Revisar fechas inicio y fin del request
		Date fechaInicio ;
		Date fechaFin;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		formatter.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));


		String wmCodeRequestQRY13;
		String wmCodeQRY13 = data.get("wmCodeQRY13");
		testCase.setTest_Description(data.get("id")+data.get("Caso"));
		testCase.setProject_Name("Encriptacion corresponsalias");
		testCase.setPrerequisites(data.get("prerequicitos"));

		/*
		 * Paso 1
		 *****************************************************************************************/
		/*
		 * addStep("Ejectar query para validar que las tiendas esten en SADEL En la BD FCACQA"
		 * );
		 * 
		 * System.out.println(GlobalVariables.DB_HOST_FCACQA);
		 * System.out.println(ValidTiendas); SQLResult ValidResult =
		 * executeQuery(dbFCACQA, ValidTiendas); String plaza = ""; String tienda = "";
		 * 
		 * boolean ValidBool = ValidResult.isEmpty();
		 * 
		 * if (!ValidBool) { plaza = ValidResult.getData(0, "CR_PLAZA"); tienda =
		 * ValidResult.getData(0, "CR_TIENDA"); testCase.addBoldTextEvidenceCurrentStep(
		 * "Se encontraron datos para validar que las tiendas esten en SADEL en la BD FCACQA"
		 * ); }
		 * 
		 * testCase.addQueryEvidenceCurrentStep(ValidResult);
		 * 
		 * System.out.println(ValidBool);
		 * 
		 * assertFalse(ValidBool, "La tabla no contiene informacion.");
		 */

		/* Paso 2 *********************************************************/
	

		addStep("Ejecutar el siguiente query en la BD FCACQA para seleccionar un usuario activo para realizar corresponsalias:");
		
		System.out.println(GlobalVariables.DB_HOST_FCACQA);
		System.out.println(SelectUs);
		SQLResult SelectUserRes = executeQuery(dbFCACQA, SelectUs);
		String idUsuario=null, plaza = null,  tienda = null, contrasena = null;
		 
		boolean SelectUserResBool = SelectUserRes.isEmpty();

		if (!SelectUserResBool) {
			testCase.addBoldTextEvidenceCurrentStep(
					"Se encontro usuario activo para realizar corresponsalia");
			idUsuario = SelectUserRes.getData(0, "ID_USUARIO");
			plaza = SelectUserRes.getData(0, "CR_PLAZA"); 
			tienda =SelectUserRes.getData(0, "CR_TIENDA");  
			contrasena =SelectUserRes.getData(0, "CONTRASENA");  
			
			System.out.println("ID_Usuario: " + idUsuario);
			System.out.println("Constrasena: " + contrasena);			
			System.out.println("Plaza : " + plaza);
			System.out.println("Tienda " + tienda);
			
		}

		testCase.addQueryEvidenceCurrentStep(SelectUserRes);
		
		System.out.println(SelectUserResBool);
		assertFalse(SelectUserResBool, "La tabla no contiene informacion.");
		
		//Obtener fecha Inicial ***************************************************************************************
		
		
		fechaInicio = new Date(); //Se Saca la fecha inicial del request
		
        String fechaInicial = formatter.format(fechaInicio);
		
		System.out.println("Fecha inicial: "+ fechaInicial);

///*Paso 3 *********************************************************/
		
	addStep("Validar doblemente en el siguiente query; que en la TPE_STORE  aveces tiene la misma tienda que SADEL pero diferente plaza");
		
		System.out.println(GlobalVariables.DB_HOST_FCACQA);
		String FormatValidStore = String.format(ValidStore, plaza, tienda);
		System.out.println(FormatValidStore);
		SQLResult ValidStoreRes = executeQuery(dbFCACQA, FormatValidStore);
		
		 
		boolean ValidStoreResBool = ValidStoreRes.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ValidStoreRes);
		System.out.println(ValidStoreResBool);
		
		assertFalse(ValidStoreResBool, "La tabla TPE_STORE no contiene informacion.");
		
	
	///*Paso 4 *********************************************************/
				addStep("Ejecutar la siguiente peticion desde el navegador con la url del F5");

				
				String respuestaQRY13 = facUtil.LoginQRY13(plaza, tienda, idUsuario,contrasena);
				
				wmCodeRequestQRY13 = RequestUtil.getWmCodeXml(respuestaQRY13);
				
				boolean validationRequest = wmCodeQRY13.equals(wmCodeRequestQRY13);
				
				System.out.println(validationRequest + " - wmCode request: "+wmCodeRequestQRY13 );
				
				testCase.addTextEvidenceCurrentStep("Codigo esperado: " + wmCodeQRY13  + "\r\n" + "Codigo respuesta: " + wmCodeRequestQRY13);
							
			
				assertTrue(validationRequest , "wmCodeQRY13 no es Igual a " + wmCodeQRY13);	
								
				
				
	//Obtiene fecha fin de ejecucion ********************************************************************************
				
				 fechaFin = new Date();
					
			     String fechaFinal = formatter.format(fechaFin);
					
			     System.out.println("Fecha final: "+fechaFinal);
			
								

	///*Paso 5 *********************************************************/
					
					addStep("Realizar la siguiente consulta para validar cada una de nuestras transacciones(QRYS)");

					System.out.println(GlobalVariables.DB_HOST_FCACQA);
					String ValidTransFormat= String.format(ValidTrans, fechaInicial, fechaFinal);
					System.out.println(ValidTransFormat);
					SQLResult ValidTransRes = executeQuery(dbFCACQA, ValidTransFormat);


                     boolean ValidTransResBool = ValidTransRes.isEmpty();
					
					testCase.addQueryEvidenceCurrentStep(ValidTransRes);

					if (!ValidTransResBool) {
						testCase.addBoldTextEvidenceCurrentStep("Se encontro registro de las transacciones");
						
					} else {

					
					testCase.addBoldTextEvidenceCurrentStep("No se encontro registro de las transacciones");
					} 
					
					System.out.println(ValidTransResBool);
					
					assertFalse(ValidTransResBool, "La tabla no contiene informacion.");

	


	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

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
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_020_TPE_FAC_CambioPasswNoExitosoUsuarioBanderaON_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}
