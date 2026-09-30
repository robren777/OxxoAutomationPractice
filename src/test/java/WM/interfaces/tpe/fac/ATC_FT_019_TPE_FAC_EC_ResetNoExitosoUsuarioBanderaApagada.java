package WM.interfaces.tpe.fac;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.TPE_FAC;
import WM.util.GlobalVariables;
import WM.util.RequestUtil;
import modelo.BaseExecution;
import utils.sql.SQLResult;

/**
 * Encriptacion de corresponsalias FAC: 
 * MTC-FT-012 - Reset no exitoso de usuario con bandera apagada y contraseña no encriptada en BD(QRY12 -SADEL-"flagEncriptado"=FALSE)
 * Desc:
 * - Se valida proceso de reseto de contraseña(QRY09) cuando este con bandera apagada y contraseña no encriptada en la BD enviandoel
 * usuario erroneo para que muestre error 001 de usuario no existe
 * @author Gilberto Martinez
 * @date   2022/09/14
 */



public class ATC_FT_019_TPE_FAC_EC_ResetNoExitosoUsuarioBanderaApagada extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_019_TPE_FAC_ResetNoExitosoUsuarioBanderaApagada(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerías
		 *********************************************************************/

		utils.sql.SQLUtil dbFCACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCACQA,
				GlobalVariables.DB_USER_FCACQA, GlobalVariables.DB_PASSWORD_FCACQA);
		TPE_FAC facUtil = new TPE_FAC(data, testCase, dbFCACQA);


		/*
		 * Variables
		 *********************************************************************/

		String wmCodeQRY09 = data.get("wmCodeConsult");

		String ValidTiendas = "SELECT CR_TIENDA,CR_PLAZA,FAC_VERSION " + "FROM TPEUSER.config_tiendas_sadel "
				+ "where CR_PLAZA ='" + data.get("plaza") + "' " + "and CR_TIENDA='" + data.get("tienda") + "'";
		
	
		
		String SelectUs =	"select * from (SELECT E.ID_EMPLEADO,U.ID_USUARIO, E.NOMBRE, E.FUNCION, E.POSICION, E.ESTADO, E.CR_PLAZA, E.CR_TIENDA, U.CONTRASENA  "
				+ "FROM XXADCJ.XXADCJ_EMPLEADOS E,  XXADCJ.XXADCJ_USUARIOS U "
				+ "where E.ID_EMPLEADO = U.ID_EMPLEADO  "
				+ "AND E.ESTADO = 'Activo' "
				+ "AND U.ESTADO = 'A' "
				+ "AND E.CR_TIENDA= '%s'  "
				+ "AND E.CR_PLAZA = '%s') where rownum = 1";
		
		String ValidTrans=	"select * (SELECT APPLICATION,ENTITY,OPERATION,SOURCE,FOLIO,CREATION_DATE,WM_CODE,WM_DESC "
				+ " FROM TPEUSER.TPE_FR_TRANSACTION  "
				+ "WHERE CREATION_DATE >= TRUNC(SYSDATE) "
				+ "AND APPLICATION ='FAC' "
				+ "AND FOLIO = '%s' "
				+ "AND ENTITY='BANK' "
				+ "ORDER BY CREATION_DATE DESC) where rownum = 1";
	
		
		

		String wmCodeRequestQRY09;

		String folio = "1";
		String uri = data.get("uri");
		String UserInexistente = data.get("idusuario");
		testCase.setTest_Description(data.get("NAME")+data.get("Caso"));

		/*
		 * Paso 1
		 *****************************************************************************************/

		addStep("Ejectar query para validar que las tiendas esten en SADEL En la BD FCACQA");

		System.out.println(GlobalVariables.DB_HOST_FCACQA);
		System.out.println(ValidTiendas);
		SQLResult ValidResult = executeQuery(dbFCACQA, ValidTiendas);
		String plaza = "";
		String tienda = "";

		boolean ValidBool = ValidResult.isEmpty();

		if (!ValidBool) {
			plaza = ValidResult.getData(0, "CR_PLAZA");
			tienda = ValidResult.getData(0, "CR_TIENDA");
			testCase.addBoldTextEvidenceCurrentStep(
					"Se encontraron datos para validar que las tiendas esten en SADEL en la BD FCACQA");
		}

		testCase.addQueryEvidenceCurrentStep(ValidResult);

		System.out.println(ValidBool);

		assertFalse(ValidBool, "La tabla no contiene información.");

		/* Paso 2 *********************************************************/
		
		addStep("Ejecutar el siguiente query en la BD FCACQA para seleccionar un usuario activo para realizar corresponsalias:");

		System.out.println(GlobalVariables.DB_HOST_FCACQA);
		String SelectUser= String.format(SelectUs, tienda, plaza);
		System.out.println(SelectUser);
		SQLResult SelectUserRes = executeQuery(dbFCACQA, SelectUser);
		String CONTRASENA= "";
		
		
		boolean SelectUserResBool = SelectUserRes.isEmpty();

		if (!SelectUserResBool) {
			CONTRASENA = SelectUserRes.getData(0, "CONTRASENA");
			testCase.addBoldTextEvidenceCurrentStep(
					"Se encontro usuario activo para realizar corresponsalia");
		}

		testCase.addQueryEvidenceCurrentStep(SelectUserRes);
		
		System.out.println(SelectUserResBool);
		assertFalse(SelectUserResBool, "La tabla no contiene información.");



///*Paso 3 *********************************************************/
		/*
			LA ENCRIPTACION DE PASS PARA ESTE CASO NO ES NECESARIO POR QUE LA BANDERA ESTA EN FALSE ADEMAS EUN NO EST CLARO EL PASO
		*/
//		addStep("Ejecutar servicio sec.enc.pub:request de la interfaz FEMSA_SEC_ENC para encriptar contraseña");
//
//		     	ApiMethodsUtil api = new ApiMethodsUtil(uri);
//				String body1 = data.get("json_enc");
//				System.out.println("body1: "+body1);
//				Response JsonRequest= api.postRequestMethod(uri, body1);
//				
//				String JsonResponse =String.valueOf(JsonRequest.asPrettyString());
//
//				
//				//**********
//				//Para obtener la contraseña encriptada que devuelva(VER EN QUE ETIQUETAS REGRESA EL DATO PARA OBTENERLO CORRECTAMENTE)
//				Document Encr_Pass = ReadRequest.convertStringToXMLDocument(JsonResponse);
//				Element eElement = (Element) Encr_Pass.getElementsByTagName("Pass").item(0);
//				String Pass_Encr = eElement.getAttribute("Pass");
//				//**********
//	
//		
//				System.out.println("JsonResponse: \n"+JsonResponse);
//				testCase.addTextEvidenceCurrentStep(body1);				
//				testCase.addBoldTextEvidenceCurrentStep("Se muestra la respuesta del request enviado.");		
//			
//				//respuesta del request
//				testCase.addTextEvidenceCurrentStep(JsonResponse);		

				

	
	///*Paso 4 *********************************************************/
				addStep("Ejecutar la siguiente peticion desde el navegador con la url del F5");

				
				String respuestaQRY09 = facUtil.LoginQRY12(UserInexistente, CONTRASENA);
				System.out.println("Respuesta: " + respuestaQRY09);

				wmCodeRequestQRY09 = RequestUtil.getWmCodeXml(respuestaQRY09);
			//	folio = RequestUtil.getFolioXml(respuestaQRY09);
						
				boolean validationRequest = wmCodeQRY09.equals(wmCodeRequestQRY09);
				System.out.println(validationRequest + " - wmCode request: "+wmCodeRequestQRY09 );
							
			
				assertTrue(validationRequest , "wmCodeQRY09 no es Igual a wmCodeRequestQRY09");	
								

	///*Paso 5 *********************************************************/
					
					addStep("Realizar la siguiente consulta para validar cada una de nuestras transacciones(QRYS)");

					System.out.println(GlobalVariables.DB_HOST_FCACQA);
					String ValidTransFormat= String.format(ValidTrans, folio);
					System.out.println(ValidTransFormat);
					SQLResult ValidTransRes = executeQuery(dbFCACQA, ValidTransFormat);


					boolean ValidTransResBool = ValidTransRes.isEmpty();

					if (!ValidTransResBool) {
						testCase.addBoldTextEvidenceCurrentStep(
								"Se encontro registro de las transacciones");
					}

					testCase.addQueryEvidenceCurrentStep(ValidTransRes);
					
					System.out.println(ValidTransResBool);
					assertFalse(ValidTransResBool, "La tabla no contiene información.");

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
		return null;
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_019_TPE_FAC_ResetNoExitosoUsuarioBanderaApagada";
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
