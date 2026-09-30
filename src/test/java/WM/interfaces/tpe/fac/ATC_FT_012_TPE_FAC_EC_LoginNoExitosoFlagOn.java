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

public class ATC_FT_012_TPE_FAC_EC_LoginNoExitosoFlagOn extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_TPE_FAC_EC_LoginNoExitosoFlagOn_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerias
		 *********************************************************************/
		SQLUtil dbFCACQA = new SQLUtil(GlobalVariables.DB_HOST_FCACQA, GlobalVariables.DB_USER_FCACQA,GlobalVariables.DB_PASSWORD_FCACQA);
		

		TPE_FAC facUtil = new TPE_FAC(data, testCase, dbFCACQA);
		
		/**
		 *  Proyecto: Encriptacion de corresponsalias 
		 *  CP: MTC-FT-003- Login no exitoso de usuario con bandera encendida y contrasena encriptada en BD(QRY09 - SADEL"flagEncriptado"=TRUE) 
            Usuario: Cajero
		 * Desc:
		 * Se valida proceso de validacion de contrasena(QRY09) cuando este con bandera encendida y 
		 * contrasena no encriptada en la BD enviando la contrasena erronea para mostrar codigo WM 002 de fallo
		 * @author Marisol Rodriguez
		 * @date 23/Feb/2023 
		 */
		

		/*
		 * Variables
		 *********************************************************************/
		
		
	/*	String tiendasSADEL = "SELECT * FROM TPEUSER.config_tiendas_sadel\r\n"
				+ "where CR_TIENDA= '"+data.get("tienda")+"' \r\n"
				+ "AND CR_PLAZA = '"+data.get("plaza")+"'";*/
		
		//Paso 2
		String usuariosActivos = "SELECT A.ID_USUARIO, B.DESC_ROL, A.ESTADO, A.CONTRASENA, C.CR_PLAZA, C.CR_TIENDA, C.ID_EMPLEADO\r\n"
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
				+ "AND ENTITY='BANK' \r\n"
				+ "AND OPERATION = 'QRY09'\r\n"
				+ "AND APPLICATION = 'FAC'\r\n"
				+ "AND WM_CODE = '"+data.get("wmCodeQRY09")+"' \r\n"
				+ "ORDER BY CREATION_DATE DESC ";

		//QRY09
		String wmCodeRequestQRY09;
		String wmCodeToValidateQRY09 = data.get("wmCodeQRY09"); //002 
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
	/*	addStep(" Ejecutar query para validar que las tiendas esten en SADEL ");
				
				System.out.println(tiendasSADEL);
				
				SQLResult tiendasSADEL_R = dbFCACQA.executeQuery(tiendasSADEL);
				
				boolean valida_tiendasSADEL = tiendasSADEL_R.isEmpty();
				
				System.out.println(valida_tiendasSADEL);
				
				testCase.addQueryEvidenceCurrentStep(tiendasSADEL_R);
					
			
		assertFalse(valida_tiendasSADEL, "No se muestran las tiendas activas para SADEL "); 
		*/
//Paso 2 ****************************************************************************************************
		
		addStep(" Ejecutar el siguiente query en la BD FCACQA para seleccionar un usuario activo para realizar corresponsalias ");
						
				System.out.println(usuariosActivos);
						
				SQLResult usuariosActivos_R = dbFCACQA.executeQuery(usuariosActivos);
				
				String id_usuario = null;
				String plaza = null;
				String tienda = null;
						
				boolean valida_usuariosActivos = usuariosActivos_R.isEmpty();
				
				if(!valida_usuariosActivos){
		        		        	
					id_usuario = usuariosActivos_R .getData(0, "ID_USUARIO");
					plaza = usuariosActivos_R.getData(0, "CR_PLAZA");
					tienda = usuariosActivos_R.getData(0, "CR_TIENDA");
					
					
					System.out.println("ID_Usuario: " + id_usuario);			
					System.out.println("Plaza : " + plaza);
					System.out.println("Tienda " + tienda);
					
					
		        }
						
				System.out.println(valida_usuariosActivos);
						
				testCase.addQueryEvidenceCurrentStep(usuariosActivos_R);
							
					
		assertFalse(valida_usuariosActivos, "No se muestran los usuarios activos para realizar corresponsalias "); 
		
//Obtener fecha Inicial ***************************************************************************************
		
		
		fechaInicio = new Date(); //Se Saca la fecha inicial del request
		
        String fechaInicial = formatter.format(fechaInicio);
		
		System.out.println(fechaInicial);
		
//Paso 3 ******************************************************************************************************
		
				addStep("Validar doblemente en el siguiente query  ya que en la TPE_STORE aveces tiene la misma "
						+ "tienda que SADEL pero diferente plaza");		
				String store_f = String.format(store, plaza, tienda);
		        System.out.println(store_f);
		   		SQLResult store_result = executeQuery(dbFCACQA,store_f);
		   		boolean validastore = store_result.isEmpty();
		   		
		   		testCase.addQueryEvidenceCurrentStep(store_result);
		   		
		   		assertFalse(validastore,"No se mostraron plazas");
						
				 
// Paso 4 ******************************************************************************************************
		addStep("Ejecutar la siguiente peticion desde el navegador con la url del F5");
		
	   	
		respuestaQRY09 = facUtil.QRY09(id_usuario);
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
			
		testCase.addTextEvidenceCurrentStep("Codigo esperado: " + wmCodeToValidateQRY09  + "\r\n" + "Codigo respuesta: " + wmCodeRequestQRY09);

		assertTrue(validationRequestQRY09, "El codigo wmCode no es el esperado: " + data.get("wmCodeQRY09"));
		
//Obtiene fecha fin de ejecucion ********************************************************************************
		
		 fechaFin = new Date();
			
	     String fechaFinal = formatter.format(fechaFin);
			
	     System.out.println(fechaFinal);
		 		
	 
//Paso 4 *******************************************************************************************************
		
		addStep("Realizar la siguiente consulta para validar cada una de nuestras transacciones(QRYS)");
		
     
		String transaction_f = String.format(transaction,  fechaInicial, fechaFinal );
		
		System.out.println("Consulta de transacciones : \r\n "+ transaction_f);
	
		SQLResult transaction_r = executeQuery(dbFCACQA, transaction_f);
		
		boolean validaTransaction = transaction_r.isEmpty();
    
        System.out.println(validaTransaction);
             	
        if(!validaTransaction) {
			
    			
    			testCase.addQueryEvidenceCurrentStep(transaction_r);
    			
    			
    		} else  {
    			
    			testCase.addTextEvidenceCurrentStep("No se insertaron las transacciones realizadas en ");
    		}
        
        
        
	   assertFalse(validaTransaction , "No se insertaron las transacciones "); 
              
	
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_TPE_FAC_EC_LoginNoExitosoFlagOn_test";
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
