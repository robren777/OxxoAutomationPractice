package WEB.cometa.scripts.mvp2;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.SeleniumUtil;

/**
 * Validar que el usuario pueda tener multiples suplayer id
 * MTC-FT-164-E1-Validar que el suplayer id puede ser multiple
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_164_E1_ValidarQueElSuplayerIdPuedeSerMultiple extends BaseExecution {
	
	SeleniumUtil u;
	PortalCometa pom;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_164_E1_ValidarQueElSuplayerIdPuedeSerMultiple_test (HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Portal Web: Cometa");
		
		
		// Variables
        String dbUrl = GlobalVariables.DB_URL_Cometa;
        String dbUser = GlobalVariables.DB_USER_Cometa;
        String dbPassword = GlobalVariables.DB_PASSWORD_Cometa;
        String query = "SELECT supplier FROM public.xxcm_proveedor_has_tienda LIMIT 10;";

        
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
	
	@AfterMethod
	public void afterMethod() {
		 // u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Conexion a BD";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el usuario pueda tener multiples suplayer id";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Zarate";
	}

	@Override
	public String setTestFullName() {
		return "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
