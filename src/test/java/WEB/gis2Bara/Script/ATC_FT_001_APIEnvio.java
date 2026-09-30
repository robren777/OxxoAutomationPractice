package WEB.gis2Bara.Script;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import WEB.gis2Bara.om.gis2Bara;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Ejecutar API "Query" y verificar la llave token.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: - MTC-FT-001-Generar token con
 * endpoint "generateToken".
 *
 * PREREQUISITOS: - Contar con acceso a Postman. - Contar con API
 * correspondiente. - Contar con la URL del POST. - Contar con configuración
 * correspondiente en "Body".
 * 
 * INSUMOS: Solicitar generación de puntos potenciales (dummy) ID_PUNTO en gis.
 * 
 * @author Isabel Rivera
 * @date 10/Enero/2025
 */

@AzureTestInfo(areaPath = "I23016-Gis2 Bara Nuevas Funcionalidades", iterationPath = "I23016-Gis2 Bara Nuevas Funcionalidades", tags = "TestTag1;TestTag2", userStoryId = 4)
public class ATC_FT_001_APIEnvio extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_APIEnvio_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("GIS 2 BARA");

		gis2Bara gb = new gis2Bara(testCase, data);

		addStep("Realizar la configuración correspondiente en el apartado BODY para generar un token.");
		String token = gb.testGenerateToken();
		Assert.assertNotNull(token, "El token no debería ser nulo.");
		
		addStep("Realizar el llamado del POST con el token generado.");
		boolean postApi = gb.testQueryWithToken(token);
		
		addStep("Verificar que la consulta realizada con el token sea exitosa.");
		gb.validatePost(postApi);
		
	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "- Contar con acceso a Postman.\r\n" + "- Contar con API correspondiente.\r\n"
				+ "- Contar con la URL del POST.\r\n" + "- Contar con configuración correspondiente en \"Body\".";
	}

	@Override
	public String setTestDesigner() {
		return "María Isabel Rivera";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_APIEnvio_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Ejecutar API \"Query\" y verificar la llave token.";
	}
}
