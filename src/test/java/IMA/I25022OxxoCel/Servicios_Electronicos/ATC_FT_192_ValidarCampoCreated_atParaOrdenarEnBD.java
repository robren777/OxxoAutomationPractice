package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelDbValidator;
import modelo.BaseExecution;

/**
 * DESCRIPCION: Validar el campo created_at para ordenar en BD
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_033_Validar el campo created_at para ordenar en BD
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 04/Marzo/2026
 */
public class ATC_FT_192_ValidarCampoCreated_atParaOrdenarEnBD extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_192_ValidarCampoCreated_atParaOrdenarEnBD_test(HashMap<String, String> data) throws Exception {
			
		testCase.setProject_Name("I25022 OxxoCel");
		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
		
		String orden = data.get("Orden");

		addStep("Realizamos consulta a DB y validamos que exista el campo created_at, adicional validamos "
				+ "que ordene el campo en orden DESC.");
		boolean resultado = db.validarCampoCreatedAtYOrdenDescendente(orden);
		testCase.addTextEvidenceCurrentStep("Resultado: " + resultado);
		Assert.assertTrue(resultado, "No se encontraron registros en BD");
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_192_ValidarCampoCreated_atParaOrdenarEnBD";
	}

	@Override
	public String setTestDescription() {
		return "Validar exista el campo created_at para ordenar en BD";
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
		return "Contar con acceso a la BD \r\n"
		+ "Tener ambiente en QA con FreedomPOP\r\n"
		+ "Contar con la API para enviar transacciones";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
