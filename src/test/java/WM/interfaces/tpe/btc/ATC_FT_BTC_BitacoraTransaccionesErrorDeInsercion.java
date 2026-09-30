package WM.interfaces.tpe.btc;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_BTC_BitacoraTransaccionesErrorDeInsercion extends BaseExecution{
	
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_BTC_BitacoraTransaccionesErrorDeInsercion(HashMap<String, String> data) throws Exception {
		
/** UTILERIA *********************************************************************/	

		utils.sql.SQLUtil db = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE, GlobalVariables.DB_PASSWORD_FCTPE);
		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLTAEQA, GlobalVariables.DB_USER_FCWMLTAEQA_QAVIEW,GlobalVariables.DB_PASSWORD_FCWMLTAEQA_QAVIEW);


		
/** VARIABLES *********************************************************************/	
		testCase.setTest_Description(data.get("caso"));
		SeleniumUtil u;
		PakageManagment pok;
		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword(data.get("ps"));
		String server = data.get("server");
	
		
		Date fecha = new Date();// obtener fecha del sistema
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss"); // formato
	    SimpleDateFormat formatter2 = new SimpleDateFormat("YYYY-MM-dd"); // formatoQuery
	 
	    String date = formatter.format(fecha);
	    String date2 = formatter2.format(fecha);
		
	    System.out.println("date: "+date);
	    System.out.println("date2: "+date2);
	    
//		String tdcIntegrationServerFormat = "select * from (SELECT ERROR_ID, FOLIO,ERROR_DATE,ERROR_CODE,DESCRIPTION,TPE_TYPE "
//				+ "FROM WMLOG.WM_LOG_ERROR_TPE " + "WHERE TPE_TYPE='SE.CTR' "
//				+ "AND ERROR_DATE >= TO_DATE('"+date2+"', 'YYYY-MM-DD') "
//				+ "AND ERROR_DATE <= TO_DATE('"+date2+"', 'YYYY-MM-DD') "
//				+ "ORDER BY START_DT DESC) where rownum <=1";
		
	
		
		String Bitacora ="SELECT CREATION_DATE,SERVICE_TYPE,OPERATION,STATUS FROM TPEUSER.POS_TRANSACTION "
				+ "WHERE SERVICE_TYPE ='"+data.get("serviceType")+"' "
				+ "AND CREATION_DATE >= TO_DATE('"+date2+"', 'YYYY-MM-DD') "
				+ "AND CREATION_DATE <= TO_DATE('"+date2+"', 'YYYY-MM-DD') "
				+ "ORDER BY CREATION_DATE DESC";
		
		String Bitacora2= "SELECT POS_DATE,MESSAGE,SERVICE_TYPE,OPERATION,CREATION_DATE FROM TPEUSER.POS_SUM_TRANSACTION "
				+ "WHERE SERVICE_TYPE ='"+data.get("serviceType")+"' "
				+ "AND CREATION_DATE >= TO_DATE('"+date2+"', 'YYYY-MM-DD') "
				+ "AND CREATION_DATE <= TO_DATE('"+date2+"', 'YYYY-MM-DD') "
				+ "ORDER BY CREATION_DATE DESC";
		
		String LogError = "SELECT ERROR_ID,TPE_TYPE,ERROR_CODE,ERROR_DATE,DESCRIPTION FROM WMLOG.WM_LOG_ERROR_TPE "
				+ "WHERE ERROR_DATE>= TO_DATE('"+date2+"', 'YYYY-MM-DD') "
				+ "AND ERROR_DATE<= TO_DATE('"+date2+"', 'YYYY-MM-DD') "
				+ "AND TPE_TYPE ='SE.CTR' "
				+ "AND ERROR_CODE='112' "
				+ "ORDER BY ERROR_DATE DESC";
	
				
/** PASOS DEL CASO DE PRUEBA *********************************************************************/	
		
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/****************************************************************************************************************************************
* Paso 0 -- Encender o apagar adapter
* **************************************************************************************************************************************/
//addStep("Comprobar status adapter");
// u = new SeleniumUtil(new ChromeTest(), true);
//IntegrationServerUtil iu = new IntegrationServerUtil(u, data.get("IS_USER"), PasswordUtil.decryptPassword(data.get("IS_PASS")), data.get("IS_IP"));
//iu.changeStatusAdapter(data.get("IS_ADAPTER_NAME"), data.get("IS_STATUS").equalsIgnoreCase("ON"));
//testCase.addScreenShotCurrentStep(u, "Estatus adapter");
	
		/* PASO 1 *********************************************************************/
		System.out.println("****************** QRY01 **********************");
				
		addStep("Ejecutar el servicio runTPECopia.");
		String NumCaso = data.get("numcaso");
		System.out.println("NumCaso: " + NumCaso);
		String jsonIn="";
		if(NumCaso.equals("1")) {
			jsonIn = String.format(data.get("jsonIn"), date, date, date);
			System.out.println("Este mero"+jsonIn);
		}else {
			jsonIn = String.format(data.get("jsonIn"), date, date, date, date, date, date);
			System.out.println(jsonIn);
		}
		
		
		
		
		u = new SeleniumUtil(new ChromeTest(), true);
		pok = new PakageManagment(u, testCase);
		
		System.out.println(GlobalVariables.DB_HOST_FCWMLTAEQA);
		String contra = "http://" + user + ":" + ps + "@" + server + ":5555";
		u.get(contra);
			
		String dateExecution = pok.runIntefaceWmWithTresInputs10(data.get("interfase"), data.get("servicio"), data.get("$resourceID"), "$resourceID",data.get("$path"),"$path",jsonIn,"jsonIn");
		System.out.println("Respuesta dateExecution " + dateExecution);
		
		
		/* PASO 2 *********************************************************************/
	
		addStep("Realizar la siguiente consulta para validar que no se inserto informacion en la bitacora");		

		SQLResult paso3=null;
		if(NumCaso.equals("1")) {
			System.out.println(Bitacora);
			paso3 = executeQuery(db, Bitacora);	
		
		}else {
			System.out.println(Bitacora2);	
			paso3 = executeQuery(db, Bitacora2);	
		
		}
		
			
				
		boolean validationDb3 = paso3.isEmpty();
		
		System.out.println(validationDb3);
		
		if (validationDb3) {				
			testCase.addBoldTextEvidenceCurrentStep("No se inserto informacion en la bitacora - OK");
			testCase.addQueryEvidenceCurrentStep(paso3);
			

		}	

		assertTrue(validationDb3, "se inserto informacion en la bitacora - NOK");
		
		/* PASO 3 *********************************************************************/
		

		addStep("Realizar la siguiente consulta para validar el error");		


		System.out.println(LogError);		
		SQLResult paso4 = executeQuery(dbLog, LogError);		
				
		boolean validationDb4 = paso4.isEmpty();
		
		System.out.println(validationDb3);
		
		if (!validationDb3) {				
			testCase.addBoldTextEvidenceCurrentStep("Se encontro el error 112 de la ejecucion - OK");
			testCase.addQueryEvidenceCurrentStep(paso4);
			

		}	
		System.out.println("FinNumCaso: " + NumCaso);
		assertFalse(validationDb4, "No se encontro el error - NOK");
		
		

	}	
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_008_BTC_BitacoraTransaccionesErrorDeInsercion";
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
		return "-Contar con acceso a la BD FCTPEQA \r\n"
				+ "-Contar con informaci�n en las tablas para de bitacora\r\n"
				+ "-Contar con json que tenga falla en su estructura";
	}

}