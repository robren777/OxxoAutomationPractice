package WM.interfaces.pe6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_029_PE6_ValidaProcesosNoEsAfectadoPorInscripcion extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_029_PE6_ValidaProcesosNoEsAfectadoPorInscripcion_test(HashMap<String, String> data)
			throws Exception {

		SQLUtil dbTPUSER = new SQLUtil(GlobalVariables.DB_HOST_FCTPE_QRO, GlobalVariables.DB_USER_FCTPE_QRO,
				GlobalVariables.DB_PASSWORD_FCTPE_QRO);
		
		/********************************
		 * VARIABLES *
		 ********************************/

		String ValidLogBook="SELECT ID,CREATION_DATE,COUNCIL_TYPE,FILE_NAME,STATUS "
				+ "FROM TPEUSER.TDC_LOG_BOOK "
				+ "WHERE CREATION_DATE >= trunc(sysdate) "
				+ "order by CREATION_DATE desc";
		
		String ValidTrans="SELECT FOLIO,RECONCILIED,CREATION_DATE,PLAZA,TIENDA,BANK "
				+ "FROM TPEUSER.TDC_TRANSACTION "
				+ "WHERE RECONCILIED = '%s' "
				+ "AND creation_date >= TRUNC(sysdate) "
				+ "order by creation_date desc"; 

		testCase.setTest_Description(data.get("Name"));



		/****************************************************************************************************************************************
		 * 
		 * Paso 1
		 * 
		 **************************************************************************************************************************************/

		addStep("Ejecuci�n control-M");

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		
		JSONObject obj = new JSONObject(data.get("job"));

		testCase.addBoldTextEvidenceCurrentStep("Jobs en  Control M ");
		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
		//testCase.addPaso("Paso con addPaso");
		testCase.addBoldTextEvidenceCurrentStep("Login");
		u.get(data.get("server"));
		u.hardWait(40);
		u.waitForLoadPage();
		CM.logOn(); 

		//Buscar del job
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		ControlM control = new ControlM(u, testCase, obj);
		boolean flag = control.searchJob();
		assertTrue(flag);
		
		//Ejecucion
		String resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(30);
		
		//Valor del output 
		

		String Res2 = control.getNewStatus();
		

		System.out.println ("Valor de output getNewStatus:" +Res2);
		
		
		String output = control.getOutput();
		System.out.println ("Valor de output control:" +output);
		
		testCase.addTextEvidenceCurrentStep("Status de ejecucion: "+Res2);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: "+output);
		//Validacion del caso
		Boolean casoPasado = false;
		if(Res2.equals("Ended OK")) {
		casoPasado = true;
		}		
		
		control.closeViewpoint();
		
		assertTrue(casoPasado);
		//assertNotEquals("Failure",resultado);

		/****************************************************************************************************************************************
		 * 
		 * Paso 2
		 * 
		 **************************************************************************************************************************************/

		addStep("Abrir la herramienta de control M para validar que la ejecuci�n del job haya sido exitosa");

		testCase.addTextEvidenceCurrentStep("Resultado de la ejecucion: " + resultado);

		System.out.println("Resultado de la ejecucion -> " + resultado);

		assertEquals(resultado, "Ended OK");

		u.close();
		
		
		/*******************************************************************************************
		 * 
		 * Paso 3
		 * 
		 * PASO COMENTADO YA QUE NO SE SABE LA RUTA DONDE BUSCAR EL ARCHIVO
		 * 
		 ********************************************************************************************/

		
//		addStep("Ingresar al repositorio en el cu�l se almacena el archivo TEF, "
//				+ "validar que se encuentre  el archivo con el nombre oxo4ddmmaa.txt");
		

//	System.out.println(GlobalVariables.DB_HOST_FCTPE);
//	SQLResult ExecuteValidLogs = dbTPUSER.executeQuery(ValidLogBook);
//	String doc ="";
//	
//	boolean ValidSWLogBoolean = ExecuteValidLogs.isEmpty();
//	System.out.println(ValidSWLogBoolean);
//
//	if (!ValidSWLogBoolean) {
//		doc = ExecuteValidLogs.getData(0, "FILE_NAME");		
//	}
	
		
//		String Host=data.get("FTP_HOST");
//		int Port=Integer.parseInt(data.get("FTP_PORT"));
//		String User=data.get("FTP_USER");
//		String Pass=data.get("FTP_PASSWORD");
//		
//		FTPUtil ftp = new FTPUtil(Host, Port, User,Pass);
//	
//
//		String ruta = "/u01/posuser/FEMSA_OXXO/POS/" + data.get("cr_plaza") + "/working/" + doc;
//		System.out.println("Ruta: " + ruta);
//
//		if (ftp.fileExists(ruta)) {
//
//			testCase.addTextEvidenceCurrentStep("Se encontro archivo en la ruta: /u01/posuser/FEMSA_OXXO/POS/"
//					+ data.get("cr_plaza") + "/working/" + doc);
//
//		} else {
//
//			System.out.println("No Existe");
//
//		}
//		testCase.addBoldTextEvidenceCurrentStep("Error. No Existen archivos en la ruta FTP");
//		testCase.addBoldTextEvidenceCurrentStep(ruta);


		//assertFalse(!ftp.fileExists(ruta), "No Existen archivos en la ruta FTP: " + ruta);
	
	
	
	/*******************************************************************************************
	 * 
	 * Paso 4
	 * 
	 ********************************************************************************************/
	addStep("Consultar que en la tabla TDC_LOG_BOOK se muestre el registro del archivo TEF generado:");
	System.out.println(GlobalVariables.DB_HOST_FCTPE_QRO);

	SQLResult ExecuteValidTranss = dbTPUSER.executeQuery(ValidLogBook);

	boolean ValidTranssBoolean = ExecuteValidTranss.isEmpty();
	System.out.println(ValidTranssBoolean);
	String ID="";
	
	if (!ValidTranssBoolean) {
		ID = ExecuteValidTranss.getData(0, "ID");
		testCase.addBoldTextEvidenceCurrentStep("Se muestra el registro del archivo TEF generado ");
		
	}
	testCase.addQueryEvidenceCurrentStep(ExecuteValidTranss);
	assertFalse(ValidTranssBoolean, "No hay registros en la tabla TDC_LOG_BOOK ");
	
	
	/*******************************************************************************************
	 * 
	 * Paso 5
	 * 
	 ********************************************************************************************/
	addStep("En la tabla TDC_TRANSACTION, verificar que en las transacciones exitosas "
			+ " el campo RECONCILIED sea igual al ID del registro del archivo TEF de la tabla TDC_LOG_BOOK.");
	
	System.out.println(GlobalVariables.DB_HOST_FCTPE_QRO);
	String FormatValid=String.format(ValidTrans, ID);
	System.out.println(FormatValid);
	SQLResult ExecuteValid = dbTPUSER.executeQuery(FormatValid);

	boolean ExecuteValidBoolean = ExecuteValid.isEmpty();
	System.out.println(ExecuteValidBoolean);
	

	if (!ExecuteValidBoolean) {
	
		testCase.addBoldTextEvidenceCurrentStep("Se muestra el registro en la tabla TDC_TRANSACTION ");
		
	}
	testCase.addQueryEvidenceCurrentStep(ExecuteValid);
	assertFalse(ExecuteValidBoolean, "No hay registros en la tabla TDC_TRANSACTION ");
	
	}

	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "- Contar con acceso a CONTRL-M.\r\n" + "- Ordenar en hold el job tdcVentas_FEMCO_BBVA3  \r\n"
				+ "- Tener acceso al repositorio donde se almacena el archivo TEF\r\n"
				+ "- Contar con las siguientes transacciones exitosas\r\n" + "Pago de venta con tarjeta";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Automation";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_029_PE6_ValidaProcesosNoEsAfectadoPorInscripcion_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}