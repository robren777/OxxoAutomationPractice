package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelDbValidator;
import modelo.BaseExecution;

/**
 * DESCRIPCION: Consulta la base de datos de transacciones PENDIENTES
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_001-Validar existan registros en estatus REINTENTO con los siguientes campos estado, intentos y next_retry_at.
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 04/Marzo/2026
 */
public class ATC_FT_198_ValidarRegistrosEstatusREINTENTO extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_198_ValidarRegistrosEstatusREINTENTO_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I25022 OxxoCel");
		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
				
		addStep("Realizar consulta para validar que existan registros de Reintento.");
		db.validarTablaReintentos();
		
		addStep("Validar que los registros contengan los siguientes campos status, retry_count y next_retry_at.");
		boolean resultado = db.validarExistenciaCamposReintento();
		testCase.addTextEvidenceCurrentStep("Resultado: " + resultado);
		Assert.assertTrue(resultado, "No existen los campos en la tabla");
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_198_ValidarRegistrosEstatusREINTENTO";
	}

	@Override
	public String setTestDescription() {
		return "Se valide existan registros en estatus REINTENTO con los siguientes campos estado, intentos y next_retry_at.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Leonardo Leonel Ramírez Escobedo";
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
