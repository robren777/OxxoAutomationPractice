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

public class ATC_FT_013_TPE_FAC_EC_LoginNoExitosoFlagOff extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_TPE_FAC_EC_LoginNoExitosoFlagOff_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utiler�as
		 *********************************************************************/
		SQLUtil dbFCACQA = new SQLUtil(GlobalVariables.DB_HOST_FCACQA, GlobalVariables.DB_USER_FCACQA,GlobalVariables.DB_PASSWORD_FCACQA);
		

		TPE_FAC facUtil = new TPE_FAC(data, testCase, dbFCACQA);
		
		/**
		 * Proyecto: Encriptacion de corresponsalias: 
		 * CP: MTC-FT-004- Login no exitoso de usuario con bandera apagada y contrase�a no encriptada en BD
		 * (QRY09 - SADEL-"flagEncriptado"=FALSE)  Usuario: Cajero
		 * Desc: Se valida proceso de validacion de contrase�a(QRY09) cuando este con bandera apagada y contrase�a 
		 * no encriptada en la BD enviando un usuario no existente para mostrar codigoWM 001 de fallo
		 * @author Marisol Rodriguez
		 * @date  
		 */
		

		/*
		 * Variables
		 *********************************************************************/
		
		
		String tiendasSADEL = "SELECT * FROM TPEUSER.config_tiendas_sadel\r\n"
				+ "where rownum = 1";
		
		String usuariosActivos = " SELECT ID_EMPLEADO,ID_USUARIO, CONTRASENA, ESTADO\r\n"
				+ "FROM XXADCJ.XXADCJ_USUARIOS \r\n"
				+ "WHERE ESTADO = 'A'\r\n"
				+ "AND FECHA_CREACION IS NOT NULL\r\n"
				+ "order by fecha_creacion desc ";
		
		
		String transaction = "SELECT application, entity, operation, folio, wm_code, creation_date\r\n"
				+ "FROM TPEUSER.TPE_FR_TRANSACTION \r\n"
				+ "WHERE  APPLICATION ='FAC' \r\n"
				+ "AND CREATION_DATE BETWEEN TO_DATE('%s','dd/mm/yyyy hh24:mi:ss') \r\n"
				+ "and TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n"
				+ "AND FOLIO ='%s'\r\n"
				+ "AND ENTITY='BANK' \r\n"
				+ "ORDER BY CREATION_DATE DESC ";

		//QRY09
		String wmCodeRequestQRY09;
		String wmCodeToValidateQRY09 = data.get("wmCodeQRY09"); // 001
		String respuestaQRY09;
		
		//Revisar fechas inicio y fin del request
		Date fechaInicio ;
		Date fechaFin;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		formatter.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
	

		testCase.setProject_Name("Encriptacion corresponsalias");
		testCase.setTest_Description(data.get("name")+data.get("description"));
		testCase.setPrerequisites(data.get("prerequicitos"));

		/**************************************************************************************************
		 * Consulta QRY09
		 * 
		 *************************************************************************************************/
				
				
//Paso 1 ****************************************************************************************************
		addStep(" Ejecutar query para validar que las tiendas esten en SADEL ");
				
				System.out.println(tiendasSADEL);
				
				SQLResult tiendasSADEL_R = dbFCACQA.executeQuery(tiendasSADEL);
				
				boolean valida_tiendasSADEL = tiendasSADEL_R.isEmpty();
				
				System.out.println(valida_tiendasSADEL);
				
				testCase.addQueryEvidenceCurrentStep(tiendasSADEL_R);
					
			
		assertTrue(valida_tiendasSADEL, "No se muestran las tiendas activas para SADEL "); 
		
//Paso 2 ****************************************************************************************************
		
		addStep(" Ejecutar el siguiente query en la BD FCACQA para seleccionar un usuario activo para realizar corresponsalias ");
						
				System.out.println(usuariosActivos);
						
				SQLResult usuariosActivos_R = dbFCACQA.executeQuery(usuariosActivos);
				
			//	String ID_USUARIO = null;
						
				boolean valida_usuariosActivos = usuariosActivos_R.isEmpty();
				
				if(!valida_usuariosActivos){
		        		        	
				//	ID_USUARIO = usuariosActivos_R.getData(0, "ID_USUARIO");
					
				//	System.out.println("ID_USUARIO: "+ ID_USUARIO);
		        			        	
		        }
						
				System.out.println(valida_usuariosActivos);
						
				testCase.addQueryEvidenceCurrentStep(usuariosActivos_R);
							
					
		assertTrue(valida_usuariosActivos, "No se muestran los usuarios activos para realizar corresponsalias "); 
		
//Paso 3******************************************************************************************************
		
		/*
		 * Ejecutar servicio sec.enc.pub:request de la interfaz FEMSA_SEC_ENC para encriptar contrase�a ingresando los siguientes valores:
     application:FAC
     entity:OXXO
     credential:<CONTRASE�A A ENCRIPTAR>
     operation:ENCR
     
		 * */
		
//**************************************************************************************************************
		//Se Saca la fecha del request
		fechaInicio = new Date();
		
        String fechaInicial = formatter.format(fechaInicio);
		
		System.out.println(fechaInicial);
				 
// Paso 4 ******************************************************************************************************
		addStep("Ejecutar la siguiente peticion desde el navegador con la url del F5");
		
	   	//	String ID_USUARIO = "12345";
		respuestaQRY09 = facUtil.QRY09(data.get("user"));
		 
		String folioQRY09 = RequestUtil.getFolioXml(respuestaQRY09);
		
		wmCodeRequestQRY09 = RequestUtil.getWmCodeXml(respuestaQRY09);

		boolean validaRespuestaQRY09 = true;
		if (respuestaQRY09 != null) {
			validaRespuestaQRY09 = false;
		}
		assertFalse(validaRespuestaQRY09); 

		
		//Valida el status esperado del QRY09 ******************************************************************
		
		addStep("Verificar que el XML de respuesta tenga como wmCode: " + wmCodeToValidateQRY09);

		boolean validationRequestQRY09 = wmCodeRequestQRY09.equals(wmCodeToValidateQRY09);
		
		System.out.println(validationRequestQRY09 + " - wmCode request QRY09 : " + wmCodeRequestQRY09);
			
		testCase.addTextEvidenceCurrentStep(
				"C�digo esperado: " + wmCodeToValidateQRY09  + "\r\n" + "C�digo XML: " + wmCodeRequestQRY09);

		assertTrue(validationRequestQRY09, "El codigo wmCode no es el esperado: " + data.get("wmCodeQRY09"));
		
		//Obtiene fecha fin de ejecucion
		
		 fechaFin = new Date();
			
	     String fechaFinal = formatter.format(fechaFin);
			
	     System.out.println(fechaFinal);
		
		
		 		
	 
//Paso 5 *******************************************************************************************************
		
		addStep("Realizar la siguiente consulta para validar cada una de nuestras transacciones(QRYS)");
		
     
		String transaction_f = String.format(transaction, folioQRY09, fechaInicial, fechaFinal );
		
		System.out.println("Consulta de transacciones : \r\n "+ transaction_f);
	
		SQLResult transaction_r = executeQuery(dbFCACQA, transaction_f);
		
		boolean validaTransaction = transaction_r.isEmpty();
    
        System.out.println(validaTransaction);
             	
       testCase.addQueryEvidenceCurrentStep(transaction_r);
        
	   assertFalse(validaTransaction , "No se insertaron las transacciones "); 
        
       
       
	
	}

	@Override
	public String setTestFullName() {
		return "";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "MTC-FT-004- Login no exitoso de usuario con bandera apagada y contrase�a no encriptada en BD(QRY09 - SADEL-\"flagEncriptado\"=FALSE) \r\n"
				+ "Usuario: Cajero";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Homero Gamez Guzman";
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
