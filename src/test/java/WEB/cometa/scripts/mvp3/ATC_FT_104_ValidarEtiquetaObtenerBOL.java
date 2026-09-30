package WEB.cometa.scripts.mvp3;

import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import WEB.cometa.om.PortalCometa;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que las etiquetas para obtener el bol ahora se llamen devoluciÃ³n normal y devoluciÃ³n especial programada
 * MTC_FT_104_Validar etiqueta para obtener el BOL
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de CEDIS
 * @author Jorge Hernandez
 * @date Febrero 2025
 */
public class ATC_FT_104_ValidarEtiquetaObtenerBOL extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_104_ValidarEtiquetaObtenerBOL_test(HashMap<String, String> data) throws Exception{
        testCase.setProject_Name("Portal Web: Cometa");

        /*
         * Variables
         ******************************************************************************************/
        String user = data.get("user");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String url = data.get("Url");

        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometa pc = new PortalCometa(u, testCase);
        String dbUrl = GlobalVariables.DB_URL_Cometa;
        String dbUser = GlobalVariables.DB_USER_Cometa;
        String dbPassword = GlobalVariables.DB_PASSWORD_Cometa;
        String query = "SELECT jsonb_array_elements(body->'Movimientos') as movimiento\n" +
                "FROM public.xxcm_historico_correos\n" +
                "WHERE EXISTS (\n" +
                "    SELECT *\n" +
                "    FROM jsonb_array_elements(body->'Movimientos') AS mov\n" +
                "    WHERE (mov->>'movimiento_tienda') NOT IN \n" +
                "          ('Faltante/Sobrante/DaÃ±ado', 'DevoluciÃ³n Especial Programada', 'DevoluciÃ³n Normal')\n" +
                ") limit 4;";

        /*
         *                                             PASO 1
         ******************************************************************************************/
        addStep("Validar mediante una consulta a la Base de Datos para ver el cambio de etiqueta ");
        
        boolean result = false;
        List<String> movimientosPermitidos = Arrays.asList(
                "Recepcion",
                "Faltante/Sobrante/Danado",
                "Devolucion Especial Programada",
                "Devolucion Normal"
        );

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
                   resultBuilder.append(String.format("%-20s", pc.removerTildes(metaData.getColumnName(i))));
               }
               resultBuilder.append("\n");
    
               // Rellenar las filas con los datos con formato justificado
               while (rs.next()) {
                   for (int i = 1; i <= columnCount; i++) {
                       resultBuilder.append(String.format("%-20s", pc.removerTildes(rs.getString(i))));
                   }
                   resultBuilder.append("\n");
                   String movimiento = rs.getString("movimiento");
                   JsonObject jsonObject = JsonParser.parseString(movimiento).getAsJsonObject();
                   result = movimientosPermitidos.contains(pc.removerTildes(jsonObject.get("movimiento_tienda").getAsString()));
               }

            // Imprimir los resultados en la evidencia
            testCase.addTextEvidenceCurrentStep(resultBuilder.toString());
            }
        if(result){
            testCase.addTextEvidenceCurrentStep("Se valida que las etiquetas de bol son las correctas");
        } else {
            testCase.addTextEvidenceCurrentStep("Las etiquetas no son las correctas");
        }
        assertTrue(result, "Las etiquetas no son las correctas");
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }

    @Override
    public String setTestDescription() {
        return "Validar que las etiquetas para obtener el bol ahora se llamen devoluciÃ³n normal y devoluciÃ³n especial programada";
    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales vÃ¡lidas para el perfil de CEDIS";
    }

    @Override
    public String setTestDesigner() {
        return "Jorge Hernandez";
    }

    @Override
    public String setTestInstanceID() {
        return "";
    }

    @Override
    public void beforeTest() {

    }
}
