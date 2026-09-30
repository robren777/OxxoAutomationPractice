package EBS.AutRegistrosContables.om;

import java.util.List;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AutRegistrosContablesAPI {

	/**
	static SeleniumUtil u;
	static TestCase testCase;
	SikuliUtil sk;
	Pattern pathimg;
	ArrayList<String> ListImage;
	HashMap<String, Pattern> ImgPattern;
	ArrayList<Pattern> patterns;
	File Folder;

	By aServices = By.xpath("//*[@class='ViewpointsHome tab-item--link my-children-show-key-navigation_outline-width-0']");
	By buttonAvanzado = By.xpath("//*[@id='details-button']");
	By aContinuarASitioNoSeguro = By.xpath("//*[@id='proceed-link']");
*/
	
	/**
	 *  DESCRIPCION:Clase de utileria para el uso del token
	 * @param token - token que se pasa en el header
	 * @return Facturas
	 * @Author bernardo.mejia@grupotimex.onmicrosoft.com
	 * @date 20/04/2025
	 */	 
	public  String tokenAutomation() throws Exception{
		try {
			RestAssured.useRelaxedHTTPSValidation();
			//String Token_Ret = AutRegistrosContables.om.AutRegContables.TokenKeyAutomation();
			String tokenRet = this.tokenKeyAutomation();
			String authToken = tokenRet;
			String apiUrl = "https://pre.iacostforce.mx/api/pagos_cedis/";

			Response response = RestAssured.given()
					.contentType(ContentType.JSON)
					.header("Authorization", "Bearer " + authToken)
					.get(apiUrl);

			if (response.getStatusCode() == 200) {
				String facturas = response.jsonPath().getString("nro_factura");

				if (facturas.contentEquals("[]")) {
					throw new Exception("Error: No se encontró información de workflow, inyectar insumos.");
				} else {
					System.out.println("Facturas obtenidas de workflow: " + facturas);
				}

				return facturas;
			} else {
				System.out.println("Error al obtener el token: " + response.getStatusCode());
				return null;
			}
		} catch (Exception e) {
			System.out.println("Se produjo un error: " + e.getMessage());
			return null;
		}
	}


	/**
	 *  DESCRIPCION:Clase de utileria para el uso del token
	 * @param token - token que se pasa en el header
	 * @return Token
	 * @Author bernardo.mejia@grupotimex.onmicrosoft.com
	 * @date 20/04/2025
	 */ 
	public  String tokenKeyAutomation() throws Exception{
		try {
			RestAssured.useRelaxedHTTPSValidation();
			String authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoicmVmcmVzaCIsImV4cCI6MTc1MzU3NDI0OCwiaWF0IjoxNzQ4MzkwMjQ4LCJqdGkiOiI3MmY2ZmQyMWYxMGQ0Yzc5ODVhZDYzOTRiNzA3MmE0NSIsInVzZXJfaWQiOjF9.-jG7KliI5UyVrO2yxo597rp7umGWPOjHec8LYUfAV9I"; // Reemplázalo con el token real
			String apiUrl = "https://pre.iacostforce.mx/api/refresh/";

			Response response = RestAssured.given()
					.contentType("application/x-www-form-urlencoded")
					.formParam("refresh", authToken)
					.when()
					.post(apiUrl);

			if (response.getStatusCode() == 200) {
				String token = response.jsonPath().getString("access");
				return token;
			} else {
				throw new Exception("Error al obtener el token: " + response.getStatusCode());
			}
		} catch (Exception e) {
			System.out.println("Se produjo un error: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Método para realizar peticion del token
	 * @param token - token que se pasa en el header
	 * @return Numero_oc
	 * @Author bernardo.mejia@grupotimex.onmicrosoft.com
	 * @date 20/04/2025
	 */	 
	public  String tokenAutomationProveedor() throws Exception {
		RestAssured.useRelaxedHTTPSValidation();
		try {
			
			String tokenRet = this.tokenKeyAutomation();
			String authToken = tokenRet; 
			String apiUrl = "https://pre.iacostforce.mx/api/pagos_cedis/";

			Response response = RestAssured.given()
					.contentType(ContentType.JSON)
					.header("Authorization", "Bearer " + authToken)
					.get(apiUrl);

			if (response.getStatusCode() == 200) {
				String numeroOc = response.jsonPath().getString("numero_oc");

				if (response.jsonPath().getString("nro_factura").contentEquals("[]")) {
					System.out.println("nro_factura obtenidas..");

					//Mandar el print al test
					//testCase.addCodeEvidenceCurrentStep("Error: No se encontro informacion de workflow ");
					throw new Exception("Error: No se encontro informacion de workflow, inyectar insumos." );   

				} else {
					String facturas = response.jsonPath().getString("nro_factura");
					System.out.println("Facturas obtenidas de workflow: " + facturas);
				}			


				return   numeroOc;

			} else {
				System.out.println("Error al obtener el token: " + response.getStatusCode());
				return null;
			}
		} catch (Exception e) {
			System.out.println("Se produjo un error: " + e.getMessage());
			return null;
		}

	}
	
	
	/**
	 * Se hace el llamado a la API y regresa el response con la info en Workflow
	 * @return Response de la api que conecta con Workflow
	 * @Author	Bernardo Mejia / Guillermo Garcia
	 * @date 22/05/2025
	 */	 
	public  Response getResponseWorkflow() throws Exception {
		try {
			RestAssured.useRelaxedHTTPSValidation();
			String authToken = tokenKeyAutomation();
			String apiUrl = "https://pre.iacostforce.mx/api/pagos_cedis/";

			Response response = RestAssured.given()
					.contentType(ContentType.JSON)
					.header("Authorization", "Bearer " + authToken)
					.get(apiUrl);
			
		return response;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	
	/**
	 * Metodo que se utiliza para obtener los invoice numbers de la tabla de invoice
	 * @param sqlResult con la tabla de invoice
	 * @return List<String> con los valores de invoice number en la tabla de invoice
	 * @author Guillermo Garcia
	 * @date 23/05/2025
	 */
	public List<String> getAllInvoices(Response jsonResponse) throws Exception{		
		try {
		JsonPath jsonPath = jsonResponse.jsonPath();
		 return jsonPath.getList("nro_factura");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	

	/**
	 * Metodo para mappear la API en un POJO 
	 * @param response
	 * @return List<Factura> con los datos que regresa la api de workflow
	 * @author GUillermo Garcia
	 * @date 23/05/2025
	 */
    public List<Factura> getFacturas(Response response) throws Exception{
    	try {
    	return response.jsonPath().getList("", Factura.class);
    	} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
    }
    
    /**
     * Metodo que se utiliza para contar el numero de lineas que incluye la informacion de workflow previamente mapeada en una Factura
     * @param facturas con la info ya mapeada de workflow
     * @return el conteo total de las lineas que incluye
     * @author Guillermo Garcia
	 * @date 23/05/2025
     */
	public int getTotalLines(List<Factura> facturas) throws Exception{
		try {
		int totalLines = 0;
	    for (Factura factura : facturas) {
	        if (factura.getRegistros_cr() != null) {
	        	totalLines += factura.getRegistros_cr().size();
	        }
	    }
	    return totalLines;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	
}
