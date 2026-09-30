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
 * MTC_FT_003-Validar parametros de limpieza de particiones.
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author 
 * @date 04/Marzo/2026
 */
public class ATC_FT_225_ValidarParametrosLimpiezaParticiones extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_225_ValidarParametrosLimpiezaParticiones_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");
		
		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
				
		addStep("Realizar consulta para validar que existan los parámetros de limpieza de particiones.");
		boolean parametros = db.validarParametrosLimpieza();
		testCase.addTextEvidenceCurrentStep("Resultado: " + parametros);
		Assert.assertTrue(parametros, "No existen todos los parámetros de limpieza en la tabla");
	
	
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_225_ValidarParametrosLimpiezaParticiones";
	}

	@Override
	public String setTestDescription() {
		return "Se valide parametros de limpieza de particiones.";
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
		return "Contar con acceso a BD y proceso de partición.";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
