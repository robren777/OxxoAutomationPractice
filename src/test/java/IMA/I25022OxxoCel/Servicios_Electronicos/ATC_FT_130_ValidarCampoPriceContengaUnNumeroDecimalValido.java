package IMA.I25022OxxoCel.Servicios_Electronicos;

import static org.awaitility.Awaitility.await;
import static org.testng.Assert.assertTrue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
 * DESCRIPCION: Se valida campo price contenga un numero decimal valido.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_010_ValidarCampoPriceContengaUnNumeroDecimalValido
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_130_ValidarCampoPriceContengaUnNumeroDecimalValido extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_130_ValidarCampoPriceContengaUnNumeroDecimalValido_test(HashMap<String, String> data) throws Exception {
		
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
			
			addStep("Validar campo price como decimal válido");
//			Obtenemos_el_valor_del_campo_price
		    List<String> campos = Arrays.asList("request_payload.price");
		    boolean esDecimalValido = parser.validaDecimal(jsonActualizado, campos);

		    // Guardar evidencia
		    testCase.addTextEvidenceCurrentStep(
		        "Campo request_payload.price enviado: " + 
		        new JsonPath(jsonActualizado).getString("request_payload.price") +
		        " | Decimal válido: " + esDecimalValido
		    );

		    // Assert final
		    Assert.assertTrue(esDecimalValido, "El campo price no contiene un número decimal válido");
			

			//Enviamos_request_y_validamos_respuesta
		    addStep("Validar codigo de respuesta del HttpCode, código esperado " + httpCode);
		    String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado, headers);

		    //Agregamos_Evidencia
		    JSONObject json = new JSONObject(response);
		    testCase.addTextEvidenceCurrentStep(json.toString(4));
		    JsonPath jp = new JsonPath(jsonActualizado);
		    String nuevoId = jp.getString("request_payload.transactionId");
						
		    addStep("Validamos que en BD la transaccion haya llegado");
		    await()
		    .atMost(30, TimeUnit.SECONDS)
		    .pollInterval(5, TimeUnit.SECONDS)
		    .untilAsserted(() -> 
		        assertTrue(
		            db.consultaTransacciones(nuevoId),
		            "No se encontró la transacción en BD"
		        )
		    );
	
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_130_ValidarCampoPriceContengaUnNumeroDecimalValido";
	}

	@Override
	public String setTestDescription() {
		return "Se valida campo price contenga un numero decimal valido.";
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
		return "Haber recibido un request con la información completa.";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
