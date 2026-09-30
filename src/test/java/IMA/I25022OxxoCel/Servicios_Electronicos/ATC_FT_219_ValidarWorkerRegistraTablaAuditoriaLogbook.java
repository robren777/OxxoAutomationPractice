package IMA.I25022OxxoCel.Servicios_Electronicos;

import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelDbValidator;
import modelo.BaseExecution;

/**
 * DESCRIPCION: Consulta la base de datos de transacciones PENDIENTES
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_022_Validar que worker registra en tabla de auditoría (logbook) en BD:  * ID de transacción,   * Timestamp del intento,   * Payload enviado a FreedomPOP, * Respuesta de FreedomPOP (código HTTP, body), * Tiempo de respuesta (latencia), * Resultado: EXITOSO / FALLIDO / REINTENTO,  * Razón del resultado.
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 04/Marzo/2026
 */
public class ATC_FT_219_ValidarWorkerRegistraTablaAuditoriaLogbook extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_219_ValidarWorkerRegistraTablaAuditoriaLogbook_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I25022 OxxoCel");

		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
		
		//Se realiza la consulta
		addStep("Realizar la consulta en BD de Auditoria");
		boolean resultado = db.consultaTransaccionesAud();
		testCase.addTextEvidenceCurrentStep("Resultado: " + resultado);
		assertTrue(resultado, "No se pudo realizar la consulta");
		}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_219_ValidarWorkerRegistraTablaAuditoriaLogbook";
	}

	@Override
	public String setTestDescription() {
		return "Se valide que worker registra en tabla de auditoría (logbook) en BD:  * ID de transacción,   * Timestamp del intento,   * Payload enviado a FreedomPOP, * Respuesta de FreedomPOP (código HTTP, body), * Tiempo de respuesta (latencia), * Resultado: EXITOSO / FALLIDO / REINTENTO,  * Razón del resultado.";
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
		return "Contar con acceso a la BD, tener ambiente en QA con FreedomPOP y\r\n"
				+ "contar con la API para enviar transacciones.";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
