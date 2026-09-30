package IMA.I25022OxxoCel.Servicios_Electronicos;

import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelDbValidator;
import modelo.BaseExecution;

/**
 * DESCRIPCION: Validar que actualice el estado de la transacción a COMPLETADO en BD
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_005_ValidarQueActualiceElEstadoTransaccionACOMPLETADOenBD
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 04/Marzo/2026
 */
public class ATC_FT_164_ValidarQueActualiceElEstadoTransaccionACOMPLETADOenBD extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_164_ValidarQueActualiceElEstadoTransaccionACOMPLETADOenBD_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");

		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
		
		//Se realiza la consulta
		addStep("Realizar la consulta en BD por transacciones pendientes");
		boolean resultado = db.consultaTransaccionesCompletadas();
		testCase.addTextEvidenceCurrentStep("Resultado: " + resultado);
		assertTrue(resultado, "No se pudo realizar la consulta");
	
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_164_ValidarQueActualiceElEstadoTransaccionACOMPLETADOenBD";
	}

	@Override
	public String setTestDescription() {
		return "Validar Que Actualice El Estado Transacciones a Completado en BD";
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
		return "Acceso a BD";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
