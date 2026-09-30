package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelDbValidator;
import modelo.BaseExecution;

/**
 * DESCRIPCION: Validar codigo 404 no realiza reintento
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_032_Validar que si existen múltiples transacciones PENDIENTES las transacciones se procesan en orden de recepción (FIFO) en BD
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author 
 * @date 04/Marzo/2026
 */
public class ATC_FT_191_ValidarMultiplesTransaccionesPENDIENTES extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_191_ValidarMultiplesTransaccionesPENDIENTES_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");
		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
		
//		Realizamos_consulta_a_DB_y_validamos_que_existan_multiples_transacciones_pendientes
		
		addStep("Validamos en la tabla de auditoría que existan multiples peticiones pendientes de procesar.");
		boolean resultado = db.consultaTransaccionesPendientes();
		testCase.addTextEvidenceCurrentStep("Resultado: " + resultado);
		Assert.assertTrue(resultado, "No se encontraron registros en BD");
		
			
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_191_ValidarMultiplesTransaccionesPENDIENTES";
	}

	@Override
	public String setTestDescription() {
		return "Validar que si existen múltiples transacciones PENDIENTES las transacciones se procesan en orden de recepción en BD";
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
		return null;
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
