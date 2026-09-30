package WM.interfaces.tpe.tsf;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_013_TPE_TSF_REMESAS_RevisionConcatenacionBDRemesas extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_TPE_TSF_REMESAS_RevisionConcatenacionBDRemesas_test(HashMap<String, String> data) throws Exception {
		
		/**
		 * Informacion de Remesas en IMA
		 * CP: MTC-FT-083-Revision Concatenaci�n BD Remesas
		 * 
		 * Desc: Realizar la revision en base de datos FCTPEQA que se estan guardando 
		  los datos y que estos mismo pasen a la base de datos FCWMREQA
		 * @author Edwin Ramirez
		 */
		
/**
 * Bases de Datos
 */

		SQLUtil dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE_QRO, GlobalVariables.DB_USER_FCTPE_QRO,GlobalVariables.DB_PASSWORD_FCTPE_QRO);		
		SQLUtil dbFCWMREDQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREDQA, GlobalVariables.DB_USER_FCWMREDQA, GlobalVariables.DB_PASSWORD_FCWMREDQA);

/**
* Variables
* ******************************************************************************************
* 
*/
//Paso 3
	
		String consultaRemesas = "SELECT * FROM(\r\n"
				+ "SELECT APPLICATION,OPERATION,ENTITY,FOLIO,CREATION_DATE,PLAZA,TIENDA\r\n"
				+ "FROM TPEUSER.TPE_FR_TRANSACTION\r\n"
				+ "WHERE ENTITY = '"+ data.get("entity")+"'\r\n"
				+ "AND CREATION_DATE > = TO_DATE ('"+ data.get("date")+"' ,'dd/mm/yyyy')\r\n"
				+ "AND FOLIO = '"+ data.get("folio")+"'\r\n"
				+ "ORDER BY CREATION_DATE DESC)WHERE ROWNUM <=1";
//Paso 6
			
		
		
		String consultaRemesas2 = "SELECT * FROM(\r\n"
				+ "SELECT APPLICATION,OPERATION,ENTITY,FOLIO,CREATION_DATE,PLAZA,TIENDA\r\n"
				+ "FROM TPEREP.TPE_FR_TRANSACTION\r\n"
				+ "where folio = '%s'\r\n"
				+ "and operation = '%s'\r\n"
				+ "and application = '%s'\r\n"
				+ "AND FOLIO = '"+ data.get("folio")+"'\r\n"
				+ "ORDER BY CREATION_DATE DESC)WHERE ROWNUM <=1";
		
				
		testCase.setTest_Description(data.get("id") + data.get("descripcion"));
		testCase.setProject_Name("Limite Dispersion Remesas");
		testCase.setPrerequisites("prerequisitos");


/**
* 	
* **********************************Pasos del caso de Prueba *****************************************
*
* 
*/
//*************************************Ejecucion de job***********************************************	
		
/*  addStep("Ejecutar job runTPECopia en Ctrl-M ");
		
   SeleniumUtil u = new SeleniumUtil(new ChromeTest());
	
	JSONObject obj = new JSONObject(data.get("job"));

	testCase.addBoldTextEvidenceCurrentStep("Ejecucion  Control M ");
	Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
	//testCase.addPaso("Paso con addPaso");

	u.get(data.get("server"));
	u.hardWait(50);
	u.waitForLoadPage();
	CM.logOn(); 

	//Buscar del job
	
	ControlM control = new ControlM(u, testCase, obj);
	boolean flag = control.searchJob();
	assertTrue(flag);
	
	//Ejecucion
	String resultado = control.executeJob();
	System.out.println("Resultado de la ejecucion -> " + resultado);

	u.hardWait(40);
	
	//Valor del output 
	

	String Res2 = control.getNewStatus();
	

	System.out.println ("Valor de output getNewStatus:" +Res2);
	
	
	String output = control.getOutput();
	System.out.println ("Valor de output control:" +output);
	
	testCase.addTextEvidenceCurrentStep("Status de ejecucion: "+Res2);
	testCase.addTextEvidenceCurrentStep("Output de ejecucion: "+output);
	//Validacion del caso
	Boolean casoPasado = false;
	
	if(Res2.equals("Wait Condition")) {
	casoPasado = true;
	}		
	
	control.closeViewpoint();
	u.hardWait(40);
	
	u.close();
	assertTrue(casoPasado);
	
	//assertNotEquals("Failure",resultado); */

	       
				
//***************************************** Paso 1,2 y 3 ***************************************************************** 
		//FCTPEQA
		addStep("Realizar la consulta en la base de datos de las remesas con el query");
		System.out.println(consultaRemesas);
		
		SQLResult exe_consultaRemesas = executeQuery(dbFCTPE, consultaRemesas);
		String operacion1 = "";
		String folio1 = "";
		String application = "";
		
		boolean validaConsultaRemesas = exe_consultaRemesas.isEmpty();
		
		if (!validaConsultaRemesas) {
			testCase.addQueryEvidenceCurrentStep(exe_consultaRemesas);
			operacion1 = exe_consultaRemesas.getData(0, "OPERATION");
			folio1 = exe_consultaRemesas.getData(0, "FOLIO");
			application = exe_consultaRemesas.getData(0, "APPLICATION");
			System.out.println("Consulta en: TPEUSER.TPE_FR_TRANSACTION");
			System.out.println("Operacion: "+operacion1+"\r\nFolio: "+folio1+ "\r\nApplication: " +application);
			
		} 
		
		System.out.println(validaConsultaRemesas);
		assertFalse(validaConsultaRemesas, "No existe informacion pendiente de procesar en la tabla."); 
//***************************************** Paso 4, 5 y 6 ***************************************************************** 
		
		//FCWMREDQA
		addStep("Realizar la consulta en la base de datos de las remesas con el query");
		String FormatConsulta2 = String.format(consultaRemesas2, folio1, operacion1, application);
		System.out.println(FormatConsulta2);
		
		SQLResult exe_consultaRemesas2 = executeQuery(dbFCWMREDQA, FormatConsulta2);
		
		boolean validaConsultaRemesas2 = exe_consultaRemesas2.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(exe_consultaRemesas2);
		
		if (!validaConsultaRemesas2) {
			
			testCase.addTextEvidenceCurrentStep("El registro en ambas bases de datos coincide");
						
		} else {
			
			testCase.addTextEvidenceCurrentStep("El registro en ambas bases de datos no coincide");
		}
		
		System.out.println(validaConsultaRemesas2);
		
		assertFalse(validaConsultaRemesas2, "El registro en ambas bases de datos no coincide");
		
//***************************************** Paso 6 ***************************************************************** 
		
		
	
		
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
		return null;
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
