package WM.apis.orders_inbound.order_response;

import modelo.BaseExecution;
import java.util.HashMap;
import org.testng.annotations.Test;
import azure.annotation.AzureTestInfo;
import WM.om.OrdersInbound;
import utils.password.PasswordUtil;
import static org.junit.Assert.assertFalse;

@AzureTestInfo(

		areaPath = "I25086-Proceso End to End\\MVP2 Proceso End to End",
		iterationPath = "I25086-Proceso End to End\\Sprints MVP2\\Sprint 8",
		tags = "",
		userStoryId = 217683

)


public class ATC_FT_005_ComprobarQueSeGenereTokenParaLaAutenticacionDelMensajeORDRSP extends BaseExecution {
	/**
	DESCRIPCIÓN:
	Validar que la API Getway genere token valido cuando sea solicitado por Grupo Modelo con las credenciales correctas
	NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC_FT_005_Comprobar que se genere token para la autenticacion del mensaje ORDRSP
	PREREQUISITOS:
	1.- Metodo: POST
	2.- URL Microservicio para el token
	3.- Usuario y contraseña
	INSUMOS:
	1.- URL del endpoint para recibir Token de ORDRSP.
	2.- Contar con acceso a la BD terra_oxxo_respn (172.26.52.140).
	3.- Contar con acceso a la consola de linux donde se genera el registro de logs del microservicio de Open Shift para mensajes ORDRSP.
	@author	Sergio Robles Ramos
	@date   25/11/2025
	*/
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ComprobarQueSeGenereTokenParaLaAutenticacionDelMensajeORDRSP_test (HashMap<String, String> data) throws Exception {
		/*Utilerias (Conexiones a BD)
		*********************************************************************/
		OrdersInbound oi = new OrdersInbound(testCase);
		String getPathToken = data.get("getPathToken");
		String baseURLTokenTestdata = data.get("baseURLTokenTestdata");
		String clientId = data.get("clientId");
		String clientSecret = data.get("clientSecret");
		PasswordUtil passwordUtil = new PasswordUtil();
		boolean validacionCodigoResp;
	//***************************Paso 1*****************************************************************************
	  addStep("Configurar request en Postman para simular la solicitud de token por parte de Grupo Modelo para la autenticacion en el envio del mensaje ORDRSP, Realizar la siguiente configuracion y dar clic en el boton \"Send\"\r\n"
			+ "\r\n"
			+ "Metodo: Post -> Agregar URL de endpoint para generarcion del token\r\n"
			+ "\r\n"
			+ "Authorization: Basic Auth -> generar variables para Username y Password, y colocar los valores de cada variable\r\n"
			+ "Username: {{clienteidUS}}\r\n"
			+ "Password: {{clientSecretUX}}\r\n"
			+ "\r\n"
			+ "Body: x-www-form-urlencoded -> agregar los siguientes campos\r\n"
			+ "scope = read\r\n"
			+ "grand_type = client_credentials\r\n"
			+ "\r\n"
			+ "Scripts -> Post-response\r\n"
			+ "var res = pm.response.json();\r\n"
			+ "pm.collectionVariables.set(\"accessTokenUX\", res.access_token);\r\n"
			+ " ");
	
	String AccessToken = oi.getAccessToken(getPathToken, baseURLTokenTestdata, passwordUtil.decryptPass(clientId), passwordUtil.decryptPass(clientSecret));
	
	if (AccessToken.equals("error al obtener token")) {
		validacionCodigoResp=true;
		testCase.addTextEvidenceCurrentStep("Error al obtener token.");
	}else {
		validacionCodigoResp=false;
		testCase.addTextEvidenceCurrentStep("Resultado Esperado: Se obtuvo el Token de forma exitosa: " + AccessToken);
	}
	assertFalse("No se pudo obtener el token.", validacionCodigoResp);
	
	}
	
	
	@ Override
	public void beforeTest() {	
	}

	@Override
	public String setPrerequisites() {
		return "1.- Metodo: POST\r\n"
				+ "2.- URL Microservicio para el token\r\n"
				+ "3.- Usuario y contraseña";
	}

	@Override
	public String setTestDescription() {
		return "Validar que la API Getway genere token valido cuando sea solicitado por Grupo Modelo con las credenciales correctas.";
	}

	@Override
	public String setTestDesigner() {
		return "Sergio Robles Ramos";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ComprobarQueSeGenereTokenParaLaAutenticacionDelMensajeORDRSP";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}

