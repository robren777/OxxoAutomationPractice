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
 * MTC_FT_019_Validar que el worker registra el error temporal en BD
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 04/Marzo/2026
 */
public class ATC_FT_216_ValidarWorkerRegistraErrorTemporalenBD extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_216_ValidarWorkerRegistraErrorTemporalenBD_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");

		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
		
		//Se realiza la consulta
		addStep("Realizar la consulta en BD de Error");
		boolean resultado = db.consultaTransaccionesError();
		testCase.addTextEvidenceCurrentStep("Resultado: " + resultado);
		assertTrue(resultado, "No se pudo realizar la consulta");
	
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_216_ValidarWorkerRegistraErrorTemporalenBD";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el worker registra el error temporal en BD";
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
		return "1- Contar con acceso a la BD transactions\r\n"
		      	 + "2- Tener ambiente en QA con URL de FreedomPOP\r\n"
		         + "3- Contar con la API para enviar transacciones";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
