package IMA.OM_API;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONObject;

public class OxxoCelCargaMasiva {
	
	private static final String UTF_8 = "utf-8";

    public static class ResultadoCarga {
        public int httpCode;
        public String response;
        public JSONObject json;
        public String resultado; // PASADO / FALLADO
    }

    public ResultadoCarga ejecutarCarga(HashMap<String, String> data) throws Exception {

        ResultadoCarga resultadoCarga = new ResultadoCarga();

        String bodyRequest = data.get("bodyReq");
        String urlEndpoint = data.get("urlEnpoint");
        String validaHeaders = data.get("validaH");

        int codeEsperado = Integer.parseInt(data.get("httpCode"));

        // URL
        URL url = new URL(urlEndpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setDoOutput(true);

        // headers
        String[] headers = validaHeaders.split("\n");
        for (String h : headers) {
            String[] kv = h.split(":");
            con.setRequestProperty(kv[0].trim(), kv[1].trim());
        }

        // body
        try (OutputStream os = con.getOutputStream()) {
            os.write(bodyRequest.getBytes(UTF_8));
        }

        int codeReal = con.getResponseCode();

        BufferedReader br;
        if (codeReal >= 200 && codeReal < 300) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), UTF_8));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream(), UTF_8));
        }

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            response.append(line.trim());
        }

        JSONObject json = new JSONObject(response.toString());

        String resultado = (codeReal == codeEsperado) ? "PASADO" : "FALLADO";

        // llenar resultado
        resultadoCarga.httpCode = codeReal;
        resultadoCarga.response = response.toString();
        resultadoCarga.json = json;
        resultadoCarga.resultado = resultado;

        con.disconnect();

        return resultadoCarga;
    }
}
