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
 * MTC_FT_001_ConsultaLaBaseDeDatosDeTransaccionesPENDIENTES
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions Tener ambiente en QA
 * con FreedomPOP Contar con la API para enviar transacciones
 * 
 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 04/Marzo/2026
 */
public class ATC_FT_160_ConsultaLaBaseDeDatosDeTransaccionesPENDIENTES extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_160_ConsultaLaBaseDeDatosDeTransaccionesPENDIENTES_test(HashMap<String, String> data)
			throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");
		
		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
	
		addStep("Realizar la consulta en BD por transacciones pendientes");
		boolean resultado = db.consultaTransaccionesPendientes();
		testCase.addTextEvidenceCurrentStep("Resultado: " + resultado);
		assertTrue(resultado, "No se pudo realizar la consulta");
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_160_ConsultaLaBaseDeDatosDeTransaccionesPENDIENTES";
	}

	@Override
	public String setTestDescription() {
		return "Consulta La Base De Datos De Transacciones PENDIENTES";
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
		return "Conexion a BD";
	}

	public void afterTest() {
		// TODO Auto-generated method stub
	}

	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}

}
