package WEB.gis2Bara.om;

import java.util.Arrays;
//import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import modelo.TestCase;
import utils.password.PasswordUtil;

public class gis2Bara {

	TestCase testCase;
	private Map<String, String> data;

	public gis2Bara(TestCase testCase, Map<String, String> data) {
		this.testCase = testCase;
		this.data = data;
	}

	/**
	 * DESCRIPCIÓN: Ejecutar API "Query" y verificar la llave token.
	 * 
	 * @return Retorna el nuevo token hacia la petición
	 * @author maria.rivera@gtim.mx
	 */

	public String testGenerateToken() throws Exception {
		String token = null;
		String url = data.get("url");
		String referer = data.get("referer");
		String username = data.get("username");
		String password = PasswordUtil.decryptPassword(data.get("password"));

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost postRequest = new HttpPost(url);
			postRequest.setEntity(new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("expiration", "60"),
					new BasicNameValuePair("f", "json"), new BasicNameValuePair("referer", referer),
					new BasicNameValuePair("username", username), new BasicNameValuePair("password", password))));
			postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");

			try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
				int statusCode = response.getStatusLine().getStatusCode();
				System.out.println("Estado de respuesta: " + statusCode);

				String responseString = EntityUtils.toString(response.getEntity());
				// System.out.println("Respuesta del servidor: " + responseString);

				JSONObject jsonResponse = new JSONObject(responseString);

				token = jsonResponse.getString("token");

				testCase.addCodeEvidenceCurrentStep("se obtiene el siguiente token: " + token);
				return token;

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Error al ejecutar la solicitud POST para generar token", e);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al configurar el cliente HTTP para generar token", e);

		}
	}

	/**
	 * Muestra la información encontrada de los puntos potenciales
	 * 
	 * @param token
	 * @author maria.rivera@gtim.mx
	 * @return true
	 */
	public boolean testQueryWithToken(String token) throws Exception {
		String baseUrl = data.get("baseUrl");
		String queryUrl = baseUrl + "?f=json&outFields=*&token=" + token + "&where=1=1";

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet getRequest = new HttpGet(queryUrl);
			try (CloseableHttpResponse response = httpClient.execute(getRequest)) {
				int statusCode = response.getStatusLine().getStatusCode();
				System.out.println("Estado de respuesta 2: " + statusCode);

				String responseString = EntityUtils.toString(response.getEntity());
				//System.out.println("Respuesta del servidor: " + responseString);

				JSONObject jsonResponse = new JSONObject(responseString);

				System.out.println("Consulta realizada con éxito, datos recibidos.");
				testCase.addCodeEvidenceCurrentStep("la petición fué exitosa:" + jsonResponse);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error al ejecutar la solicitud GET: " + e.getMessage());
				e.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al configurar el cliente HTTP para consulta: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * DESCRIPCIÓN: Ejecutar APIManager y verificar la llave token.
	 * 
	 * @return Retorna el nuevo token hacia la petición
	 * @author maria.rivera@gtim.mx
	 */
	public String testGenerateToken2() throws Exception {
		String url = data.get("url");
		String clientId = data.get("client_id");
		String clientSecret = data.get("client_secret");
		String token2;

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost postRequest = new HttpPost(url);
			postRequest.setEntity(
					new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("grant_type", "client_credentials"),
							new BasicNameValuePair("client_id", clientId),
							new BasicNameValuePair("client_secret", clientSecret))));
			postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");

			try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
				int statusCode = response.getStatusLine().getStatusCode();
				System.out.println("Estado de respuesta: " + statusCode);

				String responseString = EntityUtils.toString(response.getEntity());

				JSONObject jsonResponse = new JSONObject(responseString);

				token2 = jsonResponse.getString("access_token");

				testCase.addCodeEvidenceCurrentStep("se obtiene el siguiente token: " + token2);
				return token2;
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Error al ejecutar la solicitud POST para generar token", e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al configurar el cliente HTTP para generar token", e);
		}
	}

	/**
	 * Actualiza la información encontrada de los puntos potenciales
	 * 
	 * @param token
	 * @author maria.rivera@gtim.mx
	 * @return true
	 */
	public boolean testPutRequest(String token2) throws Exception {

		String url2 = data.get("url2");
		String apiKey = "9247176242d9417d9d7fc35ffc3f81fc";	
		System.out.println("url2: " + url2);
		int objectId = Integer.parseInt(data.get("objectId"));
		int locationId = Integer.parseInt(data.get("locationId"));
		String name = data.get("name");
		String propertyNumber = data.get("propertyNumber");
		int incidentNumber = Integer.parseInt(data.get("incidentNumber"));
		String street = data.get("street");
		String number = data.get("number");
		int neighborhoodId = Integer.parseInt(data.get("neighborhoodId"));
		String betweenStreets = data.get("betweenStreets");
		String city = data.get("city");
		String cityId = data.get("cityId");
		String state = data.get("state");
		String stateId = data.get("stateId");
		String postalCode = data.get("postalCode");
		String departmentId = data.get("departmentId");
		String locationCRGIS = data.get("locationCRGIS");
		int latitud = Integer.parseInt(data.get("latitud"));
		int longitud = Integer.parseInt(data.get("longitud"));
		String ageb = data.get("ageb");
		

		JSONObject requestBody = new JSONObject();
		    requestBody.put("objectId", objectId);  // Number
	        requestBody.put("locationId", locationId);  // Number
	        requestBody.put("name", name);
	        requestBody.put("propertyNumber", propertyNumber);
	        requestBody.put("incidentNumber", incidentNumber);  // Number
	        requestBody.put("street", street);
	        requestBody.put("number", number);
	        requestBody.put("neighborhoodId", neighborhoodId);  // Number
	        requestBody.put("betweenStreets", betweenStreets);
			requestBody.put("city", city);
			requestBody.put("cityId", cityId);
			requestBody.put("state", state);
			requestBody.put("stateId", stateId);
			requestBody.put("postalCode", postalCode);
			requestBody.put("departmentId", departmentId);
			requestBody.put("locationCRGIS", locationCRGIS);
	        requestBody.put("latitud", latitud);  // Number
	        requestBody.put("longitud", longitud);  // Number
	        requestBody.put("ageb", "ageb");

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPut putRequest = new HttpPut(url2);
			putRequest.setHeader("Content-Type", "application/json");
			putRequest.setHeader("Authorization", "Bearer " + token2);
			putRequest.setHeader("Tracking-Id", "964e5299-6db0-4334-b34c-e2fe33864bb4");
			putRequest.setHeader("Channel-Id", "WEB");
			putRequest.setHeader("Country-Code", "MX");
			putRequest.setHeader("Language", "SPA");
			putRequest.setHeader("User-Agent", "postman");
			putRequest.setHeader("x-Gateway-APIKey", apiKey);
			putRequest.setHeader("Content-Type", "application/json");
			putRequest.setHeader("Accept", "application/json");

			String authHeader = "Bearer " + token2;
			putRequest.setHeader("Authorization", authHeader);

			StringEntity entity = new StringEntity(requestBody.toString());
			putRequest.setEntity(entity);

			try (CloseableHttpResponse response = httpClient.execute(putRequest)) {
				int statusCode = response.getStatusLine().getStatusCode();
				System.out.println("Estado de respuesta PUT: " + statusCode);

				putRequest.setHeader("Cache-Control", "no-cache");
				putRequest.setHeader("Pragma", "no-cache");

				String responseString = EntityUtils.toString(response.getEntity());
				System.out.println("Respuesta del servidor PUT: " + responseString);
				testCase.addCodeEvidenceCurrentStep("la petición fué exitosa:" + responseString);
				return true;

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error al ejecutar la solicitud PUT: " + e.getMessage());
				e.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al configurar el cliente HTTP para solicitud PUT" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public void validatePost(boolean value) throws Exception {
		
		try {
		
		if(value ==true) {
			testCase.addCodeEvidenceCurrentStep("Respuesta exitosa del API");
		}
		else {
			throw new Exception("Error al ejecutar la solicitud POST para generar token");
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
}
