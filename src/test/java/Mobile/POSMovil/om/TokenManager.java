package Mobile.POSMovil.om;

import java.io.*;
import java.util.Properties;

public class TokenManager {
    private static final String PROPERTIES_FILE = "token.properties";
    private static final String TOKEN_KEY = "auth.token";
    private static final String TOKEN_TIMESTAMP_KEY = "auth.timestamp";
    private static final long TOKEN_EXPIRATION_HOURS = 24; // Token válido por 24 horas

    /**
	 * Método - Guarda el Token en un archivo de propiedades para evitar solicitarlo en cada request
	 * 
	 * @author Marcos Morales
	 * @param token  Token obtenido
	 * @date 17/12/2025
	 */
    public static void saveToken(String token) {
        Properties props = new Properties();
        props.setProperty(TOKEN_KEY, token);
        props.setProperty(TOKEN_TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
        
        try (OutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
            props.store(output, "Authentication Token");
            System.out.println("✓ Token guardado en: " + new File(PROPERTIES_FILE).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error guardando token: " + e.getMessage());
        }
    }

    /**
	 * Método - Obtiene el Token guardado desde el archivo de propiedades
	 * 
	 * @author Marcos Morales
	 * @return Retorna el token como cadena
	 * @date 17/12/2025
	 */
    public static String getToken() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            props.load(input);
            return props.getProperty(TOKEN_KEY);
        } catch (IOException e) {
            System.err.println("Error leyendo token: " + e.getMessage());
            return null;
        }
    }

    /**
	 * Método - Valida si el token es valido de acuerdo a una vigencia de 24 horas
	 * 
	 * @author Marcos Morales
	 * @return Retorna true/false
	 * @date 17/12/2025
	 */
    public static boolean isTokenValid() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            props.load(input);
            String token = props.getProperty(TOKEN_KEY);
            String timestampStr = props.getProperty(TOKEN_TIMESTAMP_KEY);
            
            if (token == null || timestampStr == null) {
                return false;
            }
            
            long tokenTime = Long.parseLong(timestampStr);
            long currentTime = System.currentTimeMillis();
            long hoursElapsed = (currentTime - tokenTime) / (1000 * 60 * 60);
            
            boolean isValid = hoursElapsed < TOKEN_EXPIRATION_HOURS;
            if (!isValid) {
                System.out.println("✗ Token expirado (" + hoursElapsed + " horas transcurridas)");
            }
            return isValid;
        } catch (IOException e) {
            System.out.println("✗ No se encontró archivo de token");
            return false;
        }
    }
}