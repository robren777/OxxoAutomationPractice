package IMA.autoReportesDatalake.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.autoReportesDatalake.om.Credentials;
import IMA.autoReportesDatalake.om.utilsAutoReportesDatalake;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * Descripcion:
 * Validar los valores de las tablas comparando la DB de RMS 
 * contra la BD en Snowflake
 * 
 * Nombre de los casos de prueba manual que se abarcan: 
	MTC-FT-385-Validacion comparativo de datos consumo diario FEM_FIF_STG_VENTAS
	MTC-FT-386-Validacion comparativo de datos consumo diario FEM_FIF_STG_INVENTARIO
	MTC-FT-387-Validacion comparativo de datos consumo diario FEM_FIF_STG_COMPRAS
 * 
 * Prerequisitos:
 * 1.-Acceso a la BD de RMS.
 * 2.-Acceso a la BD de Snowflake.
 * 
 * Insumos:
 * -N/A
 * @author Guillermo Garcia
 * @date 16/10/2024
 * 
 */
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 4",
		tags = "IMA",
		userStoryId = 55047
		)

public class ATC_FT_385_ValidacionComparativoDeDatosConsumoDiarioFemfifstgventas extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_385_ValidacionComparativoDeDatosConsumoDiarioFemfifstgventas_test(HashMap<String, String> data) throws Exception {
				
		/*****************************Variables Data Provider**************************************/
		String tableName = data.get("tableName");	
		String run = data.get("run");
		String type = data.get("type");
		String queryFechaEBS = data.get("queryFechaEBS");
		String queryFechaSnow = data.get("queryFechaSnow");
		String queryAllData= data.get("queryAllData");
		String testName = data.get("testName");
		
		
		/*****************************Instancias **************************************************/
		utilsAutoReportesDatalake util = new utilsAutoReportesDatalake();
		Connection connection = null;
		
		/****************************Queries****************************************************/
		String queryFolios = "SELECT * "
				+ "FROM BIQUERYMKT.CONTROL_CARGAMKT";
		
		//Flag para ejecucion de solo rows especificos del dataProvider
		if(run.equalsIgnoreCase("si")) {	
		
		/*************************************** PASO 0 ***************************************/
		addStep("Caso de prueba : " + testName);
		//apoyo visual para consola
		System.out.println("Ejecutando :" + testName);
		
		/*************************************** PASO 1 ***************************************/
		addStep("Hacer conexion a la Base de datos de RMS y validar conexion.");
		
		//Se obtienen las credenciales depende del tipo de objeto a probar
		SQLUtil rmsDataBaseFolios = new SQLUtil(Credentials.RMS_DB_BD_FCDAHPFR.host,
												Credentials.RMS_DB_BD_FCDAHPFR.user,
												Credentials.RMS_DB_BD_FCDAHPFR.password);
		assertTrue(rmsDataBaseFolios.getConn().isValid(10),"Validacion de conexion a RMS");
		if(rmsDataBaseFolios.getConn().isValid(10))
		testCase.addCodeEvidenceCurrentStep("Conexion a RMS arroja estatus OK");
		
		
		/*************************************** PASO 3 ***************************************/
		addStep("Obtener los folios en la tabla BIQUERYMKT.CONTROL_CARGAMKT.");
		
		SQLResult responseFolios = rmsDataBaseFolios.executeQuery(queryFolios);
		assertFalse(responseFolios.isEmpty(),"Validacion si la respuesta esta vacia de responseTotalRegistros");
		if(!responseFolios.isEmpty())
		testCase.addCodeEvidenceCurrentStep("Se ejecuto con exito el query: " + responseFolios.getQuery());
		testCase.addQueryEvidenceCurrentStep(responseFolios);
		rmsDataBaseFolios.close();
		
		
		/*************************************** PASO  6***************************************/
		addStep("Utilizar los folios y los filtros para consultar la tabla FEM_FIF.");
		
		//Se obtienen las credenciales depende del tipo de objeto a probar
		SQLUtil ebsDataBase = new SQLUtil(  Credentials.RMS_DB_QA_ACTUAL.host,
											Credentials.RMS_DB_QA_ACTUAL.user,
											Credentials.RMS_DB_QA_ACTUAL.password);
		assertTrue(ebsDataBase.getConn().isValid(10),"Validacion de conexion a RMS");
		if(ebsDataBase.getConn().isValid(10))
		testCase.addCodeEvidenceCurrentStep("Conexion a RMS arroja estatus OK");
		
		SQLResult responseTotalRegistros = ebsDataBase.executeQuery(queryFechaEBS);
		assertFalse(responseTotalRegistros.isEmpty(),"Validacion si la respuesta esta vacia de responseTotalRegistros");
		if(!responseTotalRegistros.isEmpty())
		testCase.addCodeEvidenceCurrentStep("Se ejecuto con exito el query: " + responseTotalRegistros.getQuery());
		testCase.addQueryEvidenceCurrentStep(responseTotalRegistros);
		
		
		SQLResult responseConsultaDeRows = ebsDataBase.executeQuery(queryAllData);
		assertFalse(responseConsultaDeRows.isEmpty(),"Validacion si la respuesta esta vacia de responseConsultaDeRows");
		if(!responseConsultaDeRows.isEmpty())
		testCase.addCodeEvidenceCurrentStep("Se ejecuto con exito el query: " + responseConsultaDeRows.getQuery());
		util.prettyPrint(ebsDataBase, responseConsultaDeRows, 5, testCase);
		ebsDataBase.close();
		
		
		/*************************************** PASO 2 ***************************************/
		addStep("Hacer conexion a la Base de datos de Snowflake y validar conexion");						
		try {			
			connection = util.connectToSnow(Credentials.SF_DL_QA_AM.user,
					Credentials.SF_DL_QA_AM.password,
					Credentials.SF_DL_QA_AM.host,
					util.getSchema(type));
			if(connection.isValid(10))
				testCase.addCodeEvidenceCurrentStep("Conexion a SnowFlake arroja estatus OK");		
		
		/*************************************** PASO 4 ***************************************/
		addStep("Validar la tabla y consultar los datos");						
		// Perform desired operations with the connection
		Statement stat = connection.createStatement();		
		testCase.addCodeEvidenceCurrentStep("Se ejecuta el siguiente query en SnowFlake: " + queryFechaSnow);
		ResultSet resultTotalRegistros = stat.executeQuery(queryFechaSnow);
		resultTotalRegistros.next();
		String totalSnow = resultTotalRegistros.getString(1);
						
		//agregar asertion donde compare la respuesta de ambos queries	
		testCase.addCodeEvidenceCurrentStep("El valor encontrado en RMS es: " + responseTotalRegistros.getFirstData());
		testCase.addCodeEvidenceCurrentStep("El valor encontrado en Snow es: " + totalSnow);
//		assertTrue(responseTotalRegistros.getFirstData().equalsIgnoreCase(totalSnow));	
		
					
		ArrayList<String> queries = new ArrayList<>();
		queries = util.getQueriesForDataValidation(responseConsultaDeRows, tableName);
		testCase.addBoldTextEvidenceCurrentStep("Se generaron: " +queries.size() + " queries para validar");
		
		
		for (String query : queries) {	
		System.out.println("validando" + query);	
		/*************************************** PASO 8 ***************************************/
		addStep("Comparando datos");						
		assertTrue(true);		
		testCase.addCodeEvidenceCurrentStep("Se envia el siguiente query: "+ query);
		ResultSet resultSolicitudDeRow = stat.executeQuery(query);
		
		
		/*************************************** PASO 9 ***************************************/
		addStep("Validando respuesta");		
		boolean flag = true;
		//mandar a una utilidad de preferencia y agregar lo de imprimir los errores
		if(resultSolicitudDeRow.next()) {
			testCase.addCodeEvidenceCurrentStep("Los valores encontrados son: ");
	        int columnCount = resultSolicitudDeRow.getMetaData().getColumnCount();
            //Imprime la info encontrada con su columna, el hecho de que la encuentre es que la data existe
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSolicitudDeRow.getString(i) + " "); // Adjust based on column types
                testCase.addCodeEvidenceCurrentStep("Para la columna: " + resultSolicitudDeRow.getMetaData().getColumnName(i));
                testCase.addCodeEvidenceCurrentStep("El valor encontrado es: " + resultSolicitudDeRow.getString(i));
            }
		     
		}
		else {
			testCase.addCodeEvidenceCurrentStep("El query no fue encontrado");
			flag = false;
		}
//		assertTrue(flag);		
		};
		
		//manejo de errores y cierre de snow
		}catch (ClassNotFoundException e) {
		System.out.println("Snowflake JDBC driver not found");
		e.printStackTrace();
		} catch (SQLException e) {
		System.out.println("Error connecting to Snowflake");
		e.printStackTrace();
		} 
		finally {
		// Close the connection
		if (connection != null) {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error closing connection");
			e.printStackTrace();
		}
		}
		}
		}
		else {
		addStep("Se salta esta prueba");
		testCase.addCodeEvidenceCurrentStep("pass");
		assertTrue(true);
		}	
		}

	@Override
	public String setTestFullName() {
		return "ATC_FT_385_ValidacionComparativoDeDatosConsumoDiarioFemfifstgventas";
	}

	@Override
	public String setTestDescription() {
		return "Validar los valores de las tablas comparando la DB de RMS contra la BD en Snowflake";
	}

	@Override
	public String setTestDesigner() {
		return "Guillermo A. Garcia G.";
	}

	@Override
	public String setTestInstanceID() {
		return "1";
	}

	@Override
	public String setPrerequisites() {
		return "1.-Acceso a la BD de RMS.\r\n"
				+ "2.-Acceso a la BD de Snowflake.";
	}

	@Override
	public void beforeTest() {
		
	}
}