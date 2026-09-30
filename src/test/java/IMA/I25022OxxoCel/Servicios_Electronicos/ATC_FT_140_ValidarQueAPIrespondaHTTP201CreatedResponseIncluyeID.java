package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelApiClient;
import IMA.OM_API.OxxoCelParser;
import modelo.BaseExecution;


/**
 * DESCRIPCION:Se valida que API responda con HTTP 201 Created y Response incluye el ID generado y transactionId.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_020_ValidarQueAPIrespondaHTTP201CreatedResponseIncluyeID
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_140_ValidarQueAPIrespondaHTTP201CreatedResponseIncluyeID extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_140_ValidarQueAPIrespondaHTTP201CreatedResponseIncluyeID_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");
				
		String httpCode = data.get("Codigo"); //Ingresar en el dataP el codigo http esperado en la peticion
		int code = Integer.parseInt(httpCode);
		String camposM = data.get("camposMandatorios");
		String jSon = data.get("jSon");
		String urlEndpoint = data.get("urlEndpoint");//Ingresar api del endPoint a validar
		String validaHeaders = data.get("validaH");
		
		OxxoCelApiClient api = new OxxoCelApiClient(testCase);
		OxxoCelParser parser = new OxxoCelParser(testCase);
	
	    Map<String, String> headers = api.obtenerHeaders(validaHeaders);
		String jsonActualizado = parser.jsonActualizado(jSon);
	 
		// lista_de_campos
		List<String> listaJsonPaths = Arrays.asList(camposM.split("\\s*,\\s*"));
		
		addStep("Validamos que el request tenga información en los campos mandatorios");
		Assert.assertTrue(parser.validaCamposMandatoriosRequest(jsonActualizado, listaJsonPaths),"Error al validar los campos mandatorios");
//		Convertimos_el_String_en_un_Json_y_lo_agregamos_a_la_evidencia
		JSONObject json = new JSONObject(jsonActualizado);
		testCase.addTextEvidenceCurrentStep(json.toString(4));
		
		addStep("Validar codigo de respuesta HTTP 201 (Created)");
		String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado, headers);

		Assert.assertNotNull(response, "Error al validar la petición HTTP 201");
		JSONObject jsons = new JSONObject(response);
		testCase.addTextEvidenceCurrentStep(jsons.toString(4));

		addStep("Validar que la respuesta incluye id y transactionId");
		boolean creacionExitosa = parser.validaCreacionExitosaConIds(response);
		Assert.assertTrue(creacionExitosa,
		    "La respuesta NO contiene id y/o transactionId válidos");

			}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_140_ValidarQueAPIrespondaHTTP201CreatedResponseIncluyeID";
	}

	@Override
	public String setTestDescription() {
		return "Se valida que API responda con HTTP 201 Created y Response incluye el ID generado y transactionId.";
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
		return "Haber recibido un request con información válida";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
