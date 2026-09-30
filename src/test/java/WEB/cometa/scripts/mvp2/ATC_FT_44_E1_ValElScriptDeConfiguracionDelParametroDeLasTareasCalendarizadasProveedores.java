package WEB.cometa.scripts.mvp2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.SeleniumUtil;

/**Validar que el job se ejecuta correctamente
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
 */
public class ATC_FT_44_E1_ValElScriptDeConfiguracionDelParametroDeLasTareasCalendarizadasProveedores extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_44_E1_ValElScriptDeConfiguracionDelParametroDeLasTareasCalendarizadasProveedores_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("Portal Cometa");

        // Variables
        String dbUrl = GlobalVariables.DB_URL_Cometa;
        String dbUser = GlobalVariables.DB_USER_Cometa;
        String dbPassword = GlobalVariables.DB_PASSWORD_Cometa;
        String query = "SELECT * FROM public.xxcm_portal_settings LIMIT 10;";

        
        // Paso 1: Conectar a la base de datos PostgreSQL y realizar la consulta
        addStep("Conectarse a la base de datos");
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
        	

            // Obtener metadatos de la consulta
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Crear un StringBuilder para almacenar los resultados
            StringBuilder resultBuilder = new StringBuilder();

            // Crear la fila de encabezado con formato justificado
            for (int i = 1; i <= columnCount; i++) {
                resultBuilder.append(String.format("%-20s", metaData.getColumnName(i)));
            }
            resultBuilder.append("\n");

            // Rellenar las filas con los datos con formato justificado
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    resultBuilder.append(String.format("%-20s", rs.getString(i)));
                }
                resultBuilder.append("\n");
            }

               // Imprimir los resultados en la evidencia
               testCase.addTextEvidenceCurrentStep(resultBuilder.toString());

       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    @Override
    public void beforeTest() {

    }

    @Override
    public String setPrerequisites() {
        return "Tener acceso a la BD";
    }

    @Override
    public String setTestDescription() {
        return "Validar que el job se ejecuta correctamente";
    }

    @Override
    public String setTestDesigner() {
        return "Jose Zarate";
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}