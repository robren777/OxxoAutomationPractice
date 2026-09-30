package EBS.tools;
import org.json.JSONArray;
import org.json.JSONObject;

public class ContarFacturas {
    public static int contarFacturas(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray facturas = jsonObject.getJSONArray("Facturas");
        return facturas.length();
    }
}