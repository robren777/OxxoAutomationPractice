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
 * MTC_FT_021_Validar el registro de auditoría de cada intento Dado que el worker procesa una transacción Cuando realiza un intento de envío a FreedomPOP en BD
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 04/Marzo/2026
 */
public class ATC_FT_218_ValidarRegistroAuditoriaEnvioFreedomPOPenBD extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_218_ValidarRegistroAuditoriaEnvioFreedomPOPenBD_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I25022 OxxoCel");

		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
		
		//Se realiza la consulta
		addStep("Realizar la consulta en BD de Aud Envio");
		boolean resultado = db.consultaTransaccionesAEnvio();
		testCase.addTextEvidenceCurrentStep("Resultado: " + resultado);
		assertTrue(resultado, "No se pudo realizar la consulta");	
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_218_ValidarRegistroAuditoriaEnvioFreedomPOPenBD";
	}

	@Override
	public String setTestDescription() {
		return "Se valide el registro de auditoría de cada intento Dado que el worker procesa una transacción Cuando realiza un intento de envío a FreedomPOP en BD";
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
