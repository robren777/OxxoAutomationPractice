package Mobile.cedis.mvp3.handheld.sprint1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;



public class ATC_FT_000_Conexion_BD_IMA extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_000_Conexion_BD_IMA_test(HashMap<String, String> data) throws Exception {
	
		//=================================== Conexión BD =========================================//
//		Connection conn = DriverManager.getConnection(url, userc, password);
//		
//		SQLResult res = new SQLResult(conn, query);
//		testCase.addQueryEvidenceCurrentStep(ValidaSatusL_Res);
//		
//		String connectionUrl =
//                "jdbc:sqlserver://zmetaprsrvr01.database.windows.net:1433;"
//                        + "databaseName=zmetaprsdb01;"
//                        + "user=IMAZREADQAUSER;"
//                        + "password=Im4zR3s0924_usRq4_2NDa;"
//                        + "encrypt=true;"
//                        + "trustServerCertificate=true;"
//                        + "loginTimeout=30;";
		
		addStep("Se realiza la consulta en la BD");
		String query = "SELECT TOP (10) * FROM [dbo].[REQUEST] order by fecha desc";
		Connection conn = DriverManager.getConnection(GlobalVariables.url,GlobalVariables.userc,GlobalVariables.password);
		
		SQLResult res = new SQLResult(conn, query);
		testCase.addQueryEvidenceCurrentStep(res);
		
//        ResultSet resultSet = null;
//		
//	    try (Connection connection = DriverManager.getConnection(connectionUrl);
//                Statement statement = connection.createStatement();) {
//
//            // Create and execute a SELECT SQL statement.
//	    	String query = "SELECT TOP (10) * FROM [dbo].[REQUEST] order by fecha desc";
//            resultSet = statement.executeQuery(query);
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//    
//		
//		
//		utils.sql.SQLUtil dbIMA = new utils.sql.SQLUtil(GlobalVariables.url,GlobalVariables.userc,GlobalVariables.password);
//		

			
		//=================================== Inicio de Caso =====================================//
		/*
		 *  Paso 1 - Probar la conexión de la BD
			Nota: Previo hay que realizar la verificación del pedido 
		 */
		addStep("Se prueba la conexión de la BD");
	}
	
	/*
	 * Cedis MVP3 Sprint 1: ATC_FT_000_Conexion_BD_IMA
	 * Descripción:Validar la coneixon a BD de IMA
	 *
	 * @autor Vanessa Torres
	 * @date 04/09/2024
	 */ 
	 	
	@Override
	public String setTestFullName() {
		return "ATC_FT_000_Conexion_BD_IMA";
	}
	
	@Override
	public String setTestDescription() {
		return "Validar la coneixon a BD de IMA";
	}
	
	@Override
	public String setTestDesigner() {
		return "Vanessa Torres";
	}
	
	@Override
	public String setTestInstanceID() {
		return null;
	}
	
	@Override 
	public String setPrerequisites() {
		return "Haber recepcionado un pedido con cajas/canastillas"
				+ "Haber verificado ese mismo pedido";
	}
	
	@BeforeTest
	public void beforeTest() {
	}
	
}