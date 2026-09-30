package IMA.I25022OxxoCel.Servicios_Electronicos;

import static org.awaitility.Awaitility.await;
import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelApiClient;
import IMA.OM_API.OxxoCelDbValidator;
import IMA.OM_API.OxxoCelParser;

import io.restassured.path.json.JsonPath;
import modelo.BaseExecution;

/**
 * DESCRIPCION: Se valida campo price ingresando caracteres no validos.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_011_ValidarCampoPriceIngresandoCaracteresNoValidos
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_131_ValidarCampoPriceIngresandoCaracteresNoValidos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_131_ValidarCampoPriceIngresandoCaracteresNoValidos_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");

		 //Variables
	    String httpCode = data.get("Codigo");
	    int code = Integer.parseInt(httpCode);
	    String jSon = data.get("jSon");
	    String urlEndpoint = data.get("urlEndpoint");
	    String validaHeaders = data.get("validaH");
		
		
	    OxxoCelApiClient api = new OxxoCelApiClient(testCase);
		OxxoCelParser parser = new OxxoCelParser(testCase);
		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
	    
	    Map<String, String> headers = api.obtenerHeaders(validaHeaders);
		String jsonActualizado = parser.jsonActualizado(jSon);

	    addStep("Validar campo price con decimales no válidos");
	    //Obtenemos_el_valor_del_campo_request_payload.price
	    testCase.addTextEvidenceCurrentStep(
	    	"Campo request_payload.price enviado: " + 
	  		new JsonPath(jsonActualizado).getString("request_payload.price") +
	        " | Valor esperado: decimales inválidos" 
	    );

	    addStep("Validar codigo de respuesta del HttpCode, código esperado " + httpCode);
	    String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado, headers);

	    Assert.assertNotNull(response, "Error al validar la petición httpCode " + httpCode);

	    JSONObject json = new JSONObject(response);
	    testCase.addTextEvidenceCurrentStep(json.toString(4));
	    JsonPath jp = new JsonPath(jsonActualizado);
	    String nuevoId = jp.getString("request_payload.transactionId");

	    addStep("Validamos que en BD la transacción sea rechazada al no cumplir el formato del campo price");
	    await()
	    .atMost(30, TimeUnit.SECONDS)
	    .pollInterval(20, TimeUnit.SECONDS)
	    .untilAsserted(() -> 
	        assertTrue(
	            db.consultaErroresPorTransactionId(nuevoId),
	            "No se encontró la transacción en BD"
	        )
	    );
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_131_ValidarCampoPriceIngresandoCaracteresNoValidos";
	}

	@Override
	public String setTestDescription() {
		return "Se valida campo price ingresando caracteres no validos.";
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
		return "Haber recibido un request con información errónea.";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
