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
 * MTC_FT_004-Validar existan registros en la tabla de auditoria.
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author 
 * @date 04/Marzo/2026
 */
public class ATC_FT_226_ValidarRegistrosTablaAuditoria extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_226_ValidarRegistrosTablaAuditoria_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");
		
		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
				
		addStep("Realizar consulta en la tabla de auditoria para validar que existan los registros de las particiones creadas.");
		boolean particiones = db.registroParticionesCreadasTransactions();
		Assert.assertTrue(particiones, "No existen registros de las particiones creadas");
		testCase.addTextEvidenceCurrentStep("Se crearon las particiones correctamente");
		
		addStep("Realizar consulta en la tabla de auditoria para validar que existan los registros de las particiones creadas.");
		boolean particiones2 = db.registroParticionesCreadasSafTransactions();
		Assert.assertTrue(particiones2, "No existen registros de las particiones creadas");
		testCase.addTextEvidenceCurrentStep("Se crearon las particiones correctamente");
		
		addStep("Realizar consulta en la tabla de auditoria para validar que existan los registros de las particiones creadas.");
		boolean particiones3 = db.registroParticionesCreadasEventLog();
		Assert.assertTrue(particiones3, "No existen registros de las particiones creadas");
		testCase.addTextEvidenceCurrentStep("Se crearon las particiones correctamente");
		
		addStep("Realizar consulta en la tabla de auditoria para validar que existan los registros de las particiones creadas.");
		boolean particiones4 = db.registroParticionesCreadasErrorLog();
		Assert.assertTrue(particiones4, "No existen registros de las particiones creadas");
		testCase.addTextEvidenceCurrentStep("Se crearon las particiones correctamente");
		
		addStep("Realizar consulta en la tabla de auditoria para validar que existan los registros del Borrado Histórico.");
		boolean borrado = db.registroBorradoHistoricoTransactions();
		Assert.assertTrue(borrado, "No existen registros del borrado histórico");
		testCase.addTextEvidenceCurrentStep("Se crearon los registros del borrado histórico correctamente ");
		
		addStep("Realizar consulta en la tabla de auditoria para validar que existan los registros del Borrado Histórico.");
		boolean borrado2 = db.registroBorradoHistoricoSafTransactions();
		Assert.assertTrue(borrado2, "No existen registros del borrado histórico");
		testCase.addTextEvidenceCurrentStep("Se crearon los registros del borrado histórico correctamente ");
		
		addStep("Realizar consulta en la tabla de auditoria para validar que existan los registros del Borrado Histórico.");
		boolean borrado3 = db.registroBorradoHistoricoEventLog();
		Assert.assertTrue(borrado3, "No existen registros del borrado histórico");
		testCase.addTextEvidenceCurrentStep("Se crearon los registros del borrado histórico correctamente ");
		
		addStep("Realizar consulta en la tabla de auditoria para validar que existan los registros del Borrado Histórico.");
		boolean borrado4 = db.registroBorradoHistoricoErrorLog();
		Assert.assertTrue(borrado4, "No existen registros del borrado histórico");
		testCase.addTextEvidenceCurrentStep("Se crearon los registros del borrado histórico correctamente ");
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_226_ValidarRegistrosTablaAuditoria";
	}

	@Override
	public String setTestDescription() {
		return "Se valide existan registros en la tabla de auditoria.";
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
