package WEB.gis2Bara.Script;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import WEB.gis2Bara.om.gis2Bara;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: tomar la información de los puntos potenciales y enviar a CNT.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-003-Verificar la
 * ejecución api management.
 *
 * PREREQUISITOS: -Contar con acceso a Postman. -Contar con API correspondiente.
 * -Contar con la Configuración correspondinete en params. -Solicitar la
 * ejecución del cronjob
 * 
 * INSUMOS: Solicitar generación de puntos potenciales (dumy) ID_PUNTO en gis.
 * Modificar locationId.
 * 
 * @author Isabel Rivera
 * @date 10/Enero/2025
 */

@AzureTestInfo(areaPath = "I23016-Gis2 Bara Nuevas Funcionalidades", iterationPath = "I23016-Gis2 Bara Nuevas Funcionalidades", tags = "TestTag1;TestTag2", userStoryId = 280)
public class ATC_FT_003_APIManager extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_APIManager_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("GIS 2 BARA");

		/* Utils **************************************************************/

		gis2Bara gb = new gis2Bara(testCase, data);

		addStep("Realizar la configuración correspondiente en el apartado BODY");
		String token = gb.testGenerateToken2();

		Assert.assertNotNull(token, "El token generado no debería ser nulo.");
		Assert.assertFalse(token.isEmpty(), "El token generado no debería estar vacío.");
		

		addStep("Realizar el llamado del PUT, donde se obtiene la información del API Manager.");
		String postApi = gb.testGenerateToken2();

		try {
			gb.testPutRequest(token);
			
		} catch (Exception e) {
			Assert.fail("La llamada PUT falló: " + e.getMessage());
		}

	}

	@Override
	public void beforeTest() {
		// TODO

	}

	@Override
	public String setPrerequisites() {
		// TODO
		return "-Contar con acceso a Postman.\r\n" + "-Contar con API correspondiente.\r\n"
				+ "-Contar con la URL del POST.\r\n" + "-Contar configuracion correspondinete en params.\r\n"
				+ "-Solicitar la ejecución del cronjob.";
	}

	@Override
	public String setTestDesigner() {
		// TODO
		return "María Isabel Rivera";
	}

	@Override
	public String setTestFullName() {
		// TODO
		return "ATC_FT_003_APIManager";
	}

	@Override
	public String setTestInstanceID() {
		// TODO
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO
		return "Tomar la información de los puntos potenciales y enviar a CNT,Validar la respuesta del APIManager,";
	}
}
