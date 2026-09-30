package WM.interfaces.pb1;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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


public class ATC_FT_003_PB1_ProcesarAlmenos10ArchivosTICdeTickets extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_PB1_ProcesarAlmenos10ArchivosTICdeTickets_test(HashMap<String, String> data) throws Exception { 
		
		
		/*Back Office Mexico: MTC-FT-024 PB1 Procesar al menos 10 archivos TIC de tickets de tienda a trav�s de la interface FEMSA_PB1
 * Desc:
 * Prueba de regresi�n  para comprobar la no afectaci�n en la funcionalidad principal de la interface FEMSA_PB1 para procesar al menos 
 * 10 archivos TIC (Tickets de Tienda) de subida (de WM INBOUND a BI/IMA), al ser migrada la interface de WM9.9 a WM10.5.

   Esta interface permite consultar y procesar  la informaci�n de los tickets de las tiendas almacenados en el sistema del POS, y
    enviarla al sistema de BI.
 * @author Gilberto Martinez
 * @date   2023/01/05/
		
		
/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbBi = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BiTicket, GlobalVariables.DB_USER_BiTicket,GlobalVariables.DB_PASSWORD_BiTicket);
				
/** VARIABLES *********************************************************************/	
	
		
	
		
		String selectTempTicket= "SELECT DISTINCT VT_CR,VT_CR_PLAZ,FECHA_CARGA \r\n"
				+ " FROM TEMP_TICKET \r\n"
				+ "WHERE TRUNC(FECHA_CARGA) = TRUNC(SYSDATE) \r\n"
				+ "AND VT_CR_PLAZ = '" + data.get("plaza") + "'";
		
		 testCase.setProject_Name("POC WMX86");
			
		 testCase.setPrerequisites(data.get("prerequicitos"));



/** PASOS DEL CASO DE PRUEBA *********************************************************************/	
		 
//**************************************** Paso 1  *******************************************************************
		
		addStep("Validar la ejecuci�n del Job en Control M, donde debe mostrarse en color verde, que indica que la ejecuci�n se realiz� de forma exitosa para el Job PB1.");
		/*
		- Solicitar a los Usu FC Operadores SITE v�a correo electr�nico <UsuFEMCOMOperadoresSITE@oxxo.com>,la ejecuci�n de la interfaz  PB1, con la siguiente informaci�n:
			JobName: runPB1
			Command Line:runInterfase.sh PB1
			Host 10.5: tqa_clu_int_10.5 (FCWMINTQA3 , FCWMQA8D)
			Folder 10.5: TQA_BO_105
			*/
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		u.get(data.get("server"));
	
		
		String status = "S";
		
		
		JSONObject obj = new JSONObject(data.get("job"));

		testCase.addTextEvidenceCurrentStep("Job en  Control M ");
		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
		
		testCase.addTextEvidenceCurrentStep("Login");
		
		u.hardWait(40);
		u.waitForLoadPage();
		CM.logOn(); 

		//Buscar del job
		testCase.addTextEvidenceCurrentStep("Inicio de job ");
		ControlM control = new ControlM(u, testCase, obj);
		boolean flag = control.searchJob();
//		assertTrue(flag);
		
		//Ejecucion
		String resultado = control.executeJob();
		testCase.addTextEvidenceCurrentStep( resultado);
		System.out.println("Resultado de la ejecucion -> " + resultado);

		//Valor del output 
		testCase.addTextEvidenceCurrentStep("Output: " + control.getOutput());
		System.out.println ("Valor de output :" +control.getOutput());
		
		//Validacion del caso
		Boolean casoPasado = true;
		
		if(resultado.equals("Failure")) {
			
		casoPasado = false;
		
		System.out.println("La ejecucion del Job fue exitosa");
		
		}		
		assertTrue(casoPasado, "La ejecucion del job fallo");

		control.closeViewpoint(); 
		
		u.close();
		
		
		
//************************************************* Paso 2 *****************************************************************
		
		 addStep("Realizar conexi�n a la BD FCTICQA.FEMCOM.NET de BI.");
		 
		 testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_BiTicket);
		 testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_BiTicket);

//************************************************Paso 3 *******************************************************************	 
	 
			addStep("Validar que los datos se hayan insertado en la tabla TEMP_TICKET de la BD FCTICQA.");
	
			System.out.println(selectTempTicket);
			
			SQLResult selectTempTicket_Result = dbBi.executeQuery(selectTempTicket);	
			
			boolean selectTempTicket_valida = selectTempTicket_Result.isEmpty(); // checa que el string contenga datos
	
			if (!selectTempTicket_valida) {
				
				testCase.addQueryEvidenceCurrentStep(selectTempTicket_Result); // Si no esta vacio, lo agrega a la evidencia
		     }
	
			System.out.println(selectTempTicket_valida);
			
			assertFalse("No se encontraron registros en  la tabla TEMP_TICKET", selectTempTicket_valida); // Si esta vacio, imprime mensaje	
			

		

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Procesar al menos 10 archivos TIC de tickets de tienda a trav�s de la interface FEMSA_PB11";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Sergio Robles Ramos";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_PB1_ProcesarAlmenos10ArchivosTICdeTickets_test";
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

