package IMA.I25022OxxoCel.Servicios_Electronicos;

import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelDbValidator;
import modelo.BaseExecution;

/**
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_006-Validar registro de errores en la tabla de auditoria EventLog
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 04/Marzo/2026
 */
public class ATC_FT_228_ValidarRegistroErroresTablaEventLog extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_228_ValidarRegistroErroresTablaEventLog_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I25022 OxxoCel");

		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
		
		//Se realiza la consulta
		addStep("Realizar la consulta en BD para validar: Errores comunes manejados:\r\n"
				+ "  * Partición ya existe (ignorar)\r\n"
				+ "  * Partición no existe al intentar eliminar (ignorar)\r\n"
				+ "  * Permiso denegado (registrar y abortar)");
		boolean resultado = db.consultarEventLog();
		testCase.addTextEvidenceCurrentStep("Resultado: " + resultado);
		assertTrue(resultado, "No se encontró ninguno de los errores esperados en OXC_EVENT_LOG");
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_228_ValidarRegistroErroresTablaEventLog";
	}

	@Override
	public String setTestDescription() {
		return "Se valide registro de errores en la tabla de auditoria EventLog";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mario Garza";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con acceso a BD y proceso de partición.";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
