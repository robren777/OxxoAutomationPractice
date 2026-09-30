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
 * MTC-FT-005 - Login exitoso con bandera encendida y contrasena  encriptada en BD(QRY09 - LDAP-SADEL-"flagEncriptado"=TRUE) 
   Usuario: ASESOR
 * MTC-FT-006 - Login exitoso  con bandera apagada y contrasena  encriptada en BD(QRY09 - LDAP-"flagEncriptado"= FALSE) 
 * Desc:
 * Se valida proceso de validacion de contraseña(QRY09) con usuario ASESOR cuando este con bandera Encendida/Apagada y contraseña 
 * no encriptada en la BD para mostrar codigoWM 101 de exitoso.
 * @author Gilberto Martinez
 * @date   2022/09/14
 */

public class ATC_FT_014_TPE_FAC_EC_LoginExitosoBanderaOn_OffContrasenaNoEncriptada extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_TPE_FAC_EC_LoginExitosoBanderaOn_OffContrasenaNoEncriptada_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerías
		 *********************************************************************/

		utils.sql.SQLUtil dbFCACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCACQA,
				GlobalVariables.DB_USER_FCACQA, GlobalVariables.DB_PASSWORD_FCACQA);
		TPE_FAC facUtil = new TPE_FAC(data, testCase, dbFCACQA);


		/*
		 * Variables
		 *********************************************************************/

		String wmCodeQRY09 = "101";

		String ValidTiendas = "SELECT CR_TIENDA,CR_PLAZA,FAC_VERSION  FROM TPEUSER.config_tiendas_sadel "
				+ "where CR_PLAZA ='" + data.get("plaza") + "' " + "and CR_TIENDA='" + data.get("tienda") + "'";
		
		String SelectUs =	"SELECT A.ID_USUARIO, B.DESC_ROL, A.ESTADO, A.CONTRASENA, C.CR_PLAZA, C.CR_TIENDA, C.ID_EMPLEADO\r\n"
				+ "FROM XXADCJ.XXADCJ_USUARIOS A, XXADCJ.XXADCJ_ROLES B, XXADCJ.XXADCJ_EMPLEADOS C, XXADCJ.XXADCJ_ROL_FUNCION D\r\n"
				+ "WHERE A.ID_EMPLEADO = C.ID_EMPLEADO\r\n"
				+ "AND C.ID_FUNCION = D.CVE_FUNCION\r\n"
				+ "AND D.CVE_ROL = B.CVE_ROL\r\n"
				+ "AND C.CR_TIENDA= '" + data.get("tienda") + "'\r\n"
				+ "AND C.CR_PLAZA = '" + data.get("plaza") + "'\r\n"
				+ "AND A.ESTADO = 'A'\r\n"
				+ "AND B.DESC_ROL <> 'Asesor'";
		
		String ValidLogin=	"SELECT ID_USER,FECHA_ACCESO,APLICACION,APP "
				+ "FROM TPEUSER.TPE_FAC_LOGIN_APLICACION "
				+ "WHERE  ID_USER = '%s'  "
				+ "AND APLICACION ='FAC' "
				+ "AND FECHA_ACCESO >= trunc(SYSDATE) ORDER BY FECHA_ACCESO DESC";
		
		String ValidTrans=	"select * from (SELECT APPLICATION,ENTITY,OPERATION,SOURCE,FOLIO,CREATION_DATE,WM_CODE,WM_DESC "
				+ " FROM TPEUSER.TPE_FR_TRANSACTION  "
				+ "WHERE CREATION_DATE >= trunc(SYSDATE) "
				+ "AND APPLICATION ='FAC' "
				+ "AND FOLIO = '%s' "
				+ "AND ENTITY='BANK' "
				+ "ORDER BY CREATION_DATE DESC) where rownum = 1";
		
		

		String wmCodeRequestQRY09;

		String folio= "1";
		String uri = data.get("uri");
		testCase.setProject_Name("Encriptacion corresponsalias");
		testCase.setTest_Description(data.get("NAME")+data.get("Caso"));
		testCase.setPrerequisites(data.get("prerequicitos"));

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

		assertFalse(ValidBool, "La tabla no contiene informacion.");

		/* Paso 2 *********************************************************/
		
		addStep("Ejecutar el siguiente query en la BD FCACQA para seleccionar un usuario activo para realizar corresponsalias:");

		System.out.println(GlobalVariables.DB_HOST_FCACQA);
		String SelectUser= String.format(SelectUs, tienda, plaza);
		System.out.println(SelectUser);
		SQLResult SelectUserRes = executeQuery(dbFCACQA, SelectUser);
		String idUsuario="";
		String Passw = "";
		boolean SelectUserResBool = SelectUserRes.isEmpty();

		if (!SelectUserResBool) {
			testCase.addBoldTextEvidenceCurrentStep(
					"Se encontro usuario activo para realizar corresponsalia");
			idUsuario = SelectUserRes.getData(0, "ID_USUARIO");
			Passw = SelectUserRes.getData(0, "CONTRASENA");
			System.out.println("Contrasena: " +Passw);
		}

		testCase.addQueryEvidenceCurrentStep(SelectUserRes);
		
		System.out.println(SelectUserResBool);
		assertFalse(SelectUserResBool, "La tabla no contiene informacion.");



///*Paso 3 *********************************************************/
		/*
		 * SIRVE PARA ENCRIPTAR LAS CONTRASEÑA, SE COMENTA YA QUE EL PASO SIGUE CON DUDA
		 */
		
		
//		addStep("Ejecutar servicio sec.enc.pub:request de la interfaz FEMSA_SEC_ENC para encriptar contraseña");
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
//				//Para obtener la contraseña encriptada que devuelva(VER EN QUE ETIQUETAS REGRESA EL DATO PARA OBTENERLO CORRECTAMENTE)
//				Document Encr_Pass = ReadRequest.convertStringToXMLDocument(JsonResponse);
//				Element eElement = (Element) Encr_Pass.getElementsByTagName("Pass").item(0);
//				Pass_Encr = eElement.getAttribute("Pass");
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
				

				wmCodeRequestQRY09 = RequestUtil.getWmCodeXml(respuestaQRY09);
				//folio = RequestUtil.getFolioXml(respuestaQRY09);
						
				boolean validationRequest = wmCodeQRY09.equals(wmCodeRequestQRY09);
				System.out.println(validationRequest + " - wmCode request: "+wmCodeRequestQRY09 );
				
				testCase.addTextEvidenceCurrentStep("Codigo esperado: " + wmCodeQRY09  + "\r\n" + "Codigo respuesta: " + wmCodeRequestQRY09);
							
			
				assertTrue(validationRequest , "wmCodeQRY09 no es Igual a " + wmCodeQRY09);	
								

	///*Paso 5 *********************************************************/
				
			/*		addStep("Ejecutar la siguiente consulta, para validar el registro del login del usuario:");

					System.out.println(GlobalVariables.DB_HOST_FCACQA);
					
					String ValidLoginFormat = String.format(ValidLogin, idUsuario);
					System.out.println(ValidLoginFormat);
					SQLResult ValidLoginRes = executeQuery(dbFCACQA,ValidLoginFormat );


					boolean ValidLoginResBool = ValidLoginRes.isEmpty();

					if (!ValidLoginResBool) {
						testCase.addBoldTextEvidenceCurrentStep(
								"Se encontro registro del login del usuario");
					}

					testCase.addQueryEvidenceCurrentStep(ValidLoginRes);
					
					System.out.println(ValidLoginResBool);
					assertFalse(ValidLoginResBool, "La tabla no contiene informacion.");*/
	///*Paso 6 *********************************************************/		
					addStep("Realizar la siguiente consulta para validar cada una de nuestras transacciones(QRYS)");
							
					System.out.println(GlobalVariables.DB_HOST_FCACQA);
					String ValidTransFormat= String.format(ValidTrans, folio);
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
		return "ATC_FT_014_TPE_FAC_EC_LoginExitosoBanderaOn_OffContrasenaNoEncriptada_test";
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
