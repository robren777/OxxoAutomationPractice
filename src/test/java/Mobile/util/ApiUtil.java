package Mobile.util;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

public class ApiUtil {

	public static void main(String[] args) throws Exception {
	
//		HttpResponse respuesta = ApiUtil.sendPostDissableSSL(
//				"https://10.182.92.141:17919/restv2/user/runGet", 
//				{\"NOMBRE_USUARIO\":\"75681455\"});
//		
//		String actualResponseCode = String.valueOf(respuesta.getStatusLine().getStatusCode());
//		String actualResponseMessage = EntityUtils.toString(respuesta.getEntity());
//		
//		
//		System.out.println("codigo de respuesta actual: " + actualResponseCode);
//		System.out.println("respuesta mensaje actual: " + actualResponseMessage);
		
		SSLContextBuilder builder = new SSLContextBuilder();
	    builder.loadTrustMaterial(null, new TrustStrategy() {
	        @Override
	        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            return true;
	        }
	    });

	    SSLConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(builder.build(),
	            SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	    HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslSF).build();
	    HttpPost postRequest = new HttpPost("https://10.182.92.141:17919/restv2/user/runGet");
	    //postRequest.setEntity(new StringEntity("{\"NOMBRE_USUARIO\":\"75681455\"}"));
	    postRequest.setEntity(new StringEntity("{\"NOMBRE_USUARIO\":\"75681455\"}", ContentType.APPLICATION_JSON));
	    

		System.out.println("executing request " + postRequest.getRequestLine());
		HttpResponse response = httpClient.execute(postRequest);
		
		String actualResponseCode = String.valueOf(response.getStatusLine().getStatusCode());
		String actualResponseMessage = EntityUtils.toString(response.getEntity());
		
		
		System.out.println("codigo de respuesta actual: " + actualResponseCode);
		System.out.println("respuesta mensaje actual: " + actualResponseMessage);
		
	}
	
	
	//***********************************************************************
	//***********************************************************************
	//***********************************************************************
	public static HttpResponse sendPostDissableSSL(String url, String body) throws Exception {
		//CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
//		SSLContext context = SSLContexts.custom()
//	            .loadTrustMaterial(TrustSelfSignedStrategy.INSTANCE)
//	            .build();
//
//	    Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
//	            .register("http", PlainConnectionSocketFactory.INSTANCE)
//	            .register("https", new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE))
//	            .build();
//
//	    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
//
//	    CloseableHttpClient httpClient = HttpClients.custom()
//	            .setConnectionManager(connectionManager)
//	            .build();
		
		//--------------------------------------------------------------------------------------------------------
		HttpClient httpClient = HttpClients
	            .custom()
	            //.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustStrategy.INSTANCE).build())
	            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
	            .build();
		
		//--------------------------------------------------------------------------------------------------------
//		TrustManager[] trustAllCerts = new TrustManager[]{
//                new X509TrustManager() {
//                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                        return null;
//                    }
//                    public void checkClientTrusted(
//                        java.security.cert.X509Certificate[] certs, String authType) {
//                    }
//                    public void checkServerTrusted(
//                        java.security.cert.X509Certificate[] certs, String authType) {
//                    }
//                }
//            };
//
//		HttpClient httpClient;
//      
//        SSLContext sc = SSLContext.getInstance("SSL");
//        sc.init(null, trustAllCerts, new java.security.SecureRandom());
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//                sc);
//        httpClient = HttpClients.custom().setSSLSocketFactory(
//                sslsf).build();
//        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		//--------------------------------------------------------------------------------------------------------
      
		
		//String encoding = Base64.getEncoder().encodeToString((user + ":" + pass).getBytes());
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));

		System.out.println("executing request " + httpPost.getRequestLine());
		HttpResponse response = httpClient.execute(httpPost);
		
//		HttpEntity entity = response.getEntity();
//		
//		return EntityUtils.toString(entity);
		return response;
	}

	/**
     * Sends a POST request with JSON payload and returns the HTTP response code.
     *
     * @param urlString   The target URL for the POST request
     * @param jsonPayload JSON payload string to send
     * @param authHeader  Authorization header value (e.g., "Basic QWxhZ...") or null
     * @param cookie      Cookie header value or null
     * @return HTTP status code from the server
     * @throws Exception if any network or I/O error occurs
     * @author Vlad
     * @date 10/October/2025
     */
    public static int postJson(String urlString, String jsonPayload, String authHeader, String cookie) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        if (authHeader != null && !authHeader.isEmpty())
            conn.setRequestProperty("Authorization", authHeader);

        if (cookie != null && !cookie.isEmpty())
            conn.setRequestProperty("Cookie", cookie);

        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonPayload.getBytes());
            os.flush();
        }

        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode;
    }

	/**
     * Holder for API request and response text logs.
     *
     * @author Vlad
     * @date 29/November/2025
     */
	public static class ApiLog {
		public final String requestText;
		public final String responseText;

		public ApiLog(String requestText, String responseText) {
			this.requestText = requestText;
			this.responseText = responseText;
		}
	}    

	/**
	 * Builds a DevTools-style formatted API request & response log.
	 *
	 * @author Vlad
	 * @date 29/November/2025
	 */
	public ApiLog buildApiNotificationLog(
			String url,
			String payload,
			String auth,
			String cookie
		) throws Exception {

		String nl = "\r\n\r\n";
		String empty = "\u2029"; // PDF-safe blank line

		// ---------- REQUEST ----------
		StringBuilder req = new StringBuilder();

		req.append(empty);
		req.append("POST ").append(url).append(empty);

		req.append(empty);
		req.append("Payload:").append(nl);
		req.append(prettyJson(payload)).append(empty);

		// ---------- CALL ----------
		int status = ApiUtil.postJson(url, payload, auth, cookie);

		// ---------- RESPONSE ----------
		StringBuilder res = new StringBuilder();

		req.append(empty);
		res.append("Status: ").append(status).append(empty);

		return new ApiLog(req.toString(), res.toString());
	}

	private String prettyJson(String json) {
		try {
			com.google.gson.Gson gson = new com.google.gson.GsonBuilder()
					.setPrettyPrinting()
					.create();
	
			com.google.gson.JsonElement parsed =
					com.google.gson.JsonParser.parseString(json);
	
			String pretty = gson.toJson(parsed);
	
			// NEW — PDF-compatible newlines
			pretty = pretty.replace("\n", "\u2028");
	
			return pretty;
	
		} catch (Exception e) {
			return json;
		}
	}		
}
