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
 * MTC_FT_001-Validar existencia de tabla particionada del mes actual y de los siguientes.
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author 
 * @date 04/Marzo/2026
 */
public class ATC_FT_223_ValidarExistenciaTablaParticionada extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_223_ValidarExistenciaTablaParticionada_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I25022 OxxoCel");
	
		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
				
		addStep("Realizar consulta para validar que existan particiones creadas de la tabla Transactions.");
		boolean particion = db.validarParticionesTransactions();
		testCase.addTextEvidenceCurrentStep("Resultado: " + particion);
		Assert.assertTrue(particion, "No existen particiones en la tabla");
		
		addStep("Realizar consulta para validar que existan particiones creadas de la tabla Saf_Transactions.");
		boolean particion2 = db.validarParticionesSafTransactions();
		testCase.addTextEvidenceCurrentStep("Resultado: " + particion2);
		Assert.assertTrue(particion2, "No existen particiones en la tabla");
		
		addStep("Realizar consulta para validar que existan particiones creadas de la tabla Event_log.");
		boolean particion3 = db.validarParticionesEventLog();
		testCase.addTextEvidenceCurrentStep("Resultado: " + particion3);
		Assert.assertTrue(particion3, "No existen particiones en la tabla");
		
		addStep("Realizar consulta para validar que existan particiones creadas de la tabla Error_log.");
		boolean particion4 = db.validarParticionesErrorLog();
		testCase.addTextEvidenceCurrentStep("Resultado: " + particion4);
		Assert.assertTrue(particion4, "No existen particiones en la tabla");
	
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_223_ValidarExistenciaTablaParticionada";
	}

	@Override
	public String setTestDescription() {
		return "Se valida existencia de tabla particionada.";
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
