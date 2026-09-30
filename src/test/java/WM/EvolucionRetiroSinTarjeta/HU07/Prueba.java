package WM.EvolucionRetiroSinTarjeta.HU07;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class Prueba {

	public static void main(String[] args) throws IOException, InterruptedException {
		final String tempToken = "asd";
		 final String tempOrganization = "OXXOSWD";
		 final String tempProjectName = "I22032-Evolución%20Retiros%20sin%20Tarjeta";
		 final String tempPlanId = "5304";

		 HttpClient httpClient = HttpClient.newHttpClient();
		String validationUrl = "https://dev.azure.com/" + tempOrganization + "/_apis/projects/" + tempProjectName + "?api-version=6.0";
	    //System.out.println(validationUrl);
	        HttpRequest validationRequest = HttpRequest.newBuilder()
	                .uri(URI.create(validationUrl))
	                .header("Authorization", "Basic Omh2Y2d3dDJsNGx5a2cyam5zcHQ1c3dhbTU2bW11Mm5pYmRwb3BnaTRycDN5aTN4cnltcnE=")
	                .GET()
	                .build();

	        HttpResponse<String> validationResponse = httpClient.send(validationRequest, HttpResponse.BodyHandlers.ofString());

	        System.out.println(validationResponse.statusCode());
	        System.out.println(validationResponse.body());

	}

}
