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
 * MTC-FT-008 - Login no exitoso con bandera apagada y contrase�a no encriptada en BD(QRY09 - LDAP-SADEL-"flagEncriptado"=FALSE) 
 * Desc:
 * - Se valida proceso de validacion de contrase�a(QRY09) con usuario ASESOR cuando este con bandera apagada y contrase�a 
 * no encriptada en la BD enviando un usuario no existente para mostrar codigoWM 001 de fallo.
 * @author Gilberto Martinez
 * @date   2022/09/14
 */
public class ATC_FT_016_TPE_FAC_EC_LoginNOExitosoBanderaFalseUserInexistente extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_015_TPE_FAC_LoginNOExitosoBanderaFalseUserInexistente(HashMap<String, String> data) throws Exception {

		/*
		 * Utiler�as
		 *********************************************************************/

		utils.sql.SQLUtil dbFCACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCACQA,
				GlobalVariables.DB_USER_FCACQA, GlobalVariables.DB_PASSWORD_FCACQA);
		TPE_FAC facUtil = new TPE_FAC(data, testCase, dbFCACQA);


		/*
		 * Variables
		 *********************************************************************/

		String wmCodeQRY09 =  data.get("wmCodeConsult");

		String ValidTiendas = "SELECT CR_TIENDA,CR_PLAZA,FAC_VERSION " + "FROM TPEUSER.config_tiendas_sadel "
				+ "where CR_PLAZA ='" + data.get("plaza") + "' " + "and CR_TIENDA='" + data.get("tienda") + "'";
		
		String SelectUs =	"select * from (SELECT E.ID_EMPLEADO,U.ID_USUARIO, E.NOMBRE, E.FUNCION, E.POSICION, E.ESTADO, E.CR_PLAZA, E.CR_TIENDA, U.CONTRASENA  "
				+ "FROM XXADCJ.XXADCJ_EMPLEADOS E,  XXADCJ.XXADCJ_USUARIOS U "
				+ "where E.ID_EMPLEADO = U.ID_EMPLEADO  "
				+ "AND E.ESTADO = 'Activo' "
				+ "AND U.ESTADO = 'A' "
				+ "and E.FUNCION = 'ASESOR TIENDA' "
				+ "AND E.CR_TIENDA= '%s'  "
				+ "AND E.CR_PLAZA = '%s') where rownum = 1";
		
		String ValidTrans=	"select * from (SELECT APPLICATION,ENTITY,OPERATION,SOURCE,FOLIO,CREATION_DATE,WM_CODE,WM_DESC "
				+ " FROM TPEUSER.TPE_FR_TRANSACTION  "
				+ "WHERE CREATION_DATE >= trunc(SYSDATE) "
				+ "AND APPLICATION ='FAC' "
				+ "AND FOLIO = '%s' "
				+ "AND ENTITY='BANK' "
				+ "ORDER BY CREATION_DATE DESC) where rownum = 1";

		
		

		String wmCodeRequestQRY09;

		String folio = "1";
		String uri = data.get("uri");
		String idUsuario= data.get("User_Inexistente");
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

		assertFalse(ValidBool, "La tabla no contiene informaci�n.");

		/* Paso 2 *********************************************************/
		
		addStep("Ejecutar el siguiente query en la BD FCACQA para seleccionar un usuario activo para realizar corresponsalias:");

		System.out.println(GlobalVariables.DB_HOST_FCACQA);
		String SelectUser= String.format(SelectUs, tienda, plaza);
		System.out.println(SelectUser);
		SQLResult SelectUserRes = executeQuery(dbFCACQA, SelectUser);
		String Passw = "";

		boolean SelectUserResBool = SelectUserRes.isEmpty();

		if (!SelectUserResBool) {
			testCase.addBoldTextEvidenceCurrentStep(
					"Se encontro usuario activo para realizar corresponsalia");
			Passw = SelectUserRes.getData(0, "CONTRASENA");
		}

		testCase.addQueryEvidenceCurrentStep(SelectUserRes);
		
		System.out.println(SelectUserResBool);
		assertFalse(SelectUserResBool, "La tabla no contiene informaci�n.");



///*Paso 3 *********************************************************/
		/*
		 * SIRVE PARA ENCRIPTAR LAS CONTRASE�A, SE COMENTA YA QUE EL PASO SIGUE CON DUDA
		 */
		
//		addStep("Ejecutar servicio sec.enc.pub:request de la interfaz FEMSA_SEC_ENC para encriptar contrase�a");
//
//		     ApiMethodsUtil api = new ApiMethodsUtil(uri);
//				String body1 = data.get("json_enc");
//				System.out.println("body1: "+body1);
//				Response JsonRequest= api.postRequestMethod(uri, body1);
//				
//				String JsonResponse =String.valueOf(JsonRequest.asPrettyString());
//
//				
//				//**********
//				//Para obtener la contrase�a encriptada que devuelva(VER EN QUE ETIQUETAS REGRESA EL DATO PARA OBTENERLO CORRECTAMENTE)
//				Document Encr_Pass = ReadRequest.convertStringToXMLDocument(JsonResponse);
//				Element eElement = (Element) Encr_Pass.getElementsByTagName("Pass").item(0);
//				 Pass_Encr = eElement.getAttribute("Pass");
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

				
				String respuestaQRY09 = facUtil.LoginQRY29(idUsuario, Passw);
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
					assertFalse(ValidTransResBool, "La tabla no contiene informaci�n.");

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
		return "ATC_FT_015_TPE_FAC_LoginNOExitosoBanderaFalseUserInexistente";
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
