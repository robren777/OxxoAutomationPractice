package IMA.autoReportesDatalake.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * Caso de prueba para Validar las columnas y los tipos de datos de la tabla, tanto en Oracle SQL y en Snowflake
 * 
 * Nombre casos de prueba manual que se abarcan:
 * 
 * 
 * Prerequisitos:
 * 1.-Acceso a la BD de EBS.
 * 2.-Acceso a la BD de Snowflake.
 * 3.-Acceso a la BD de RMS.
 * 4.-Acceso a la BD de EBS 12.
 * 5.-Acceso a la BD de RMS 100.
 * 
 * 
 * Insumos:
 * -N/A
 * @author Guillermo Garcia
 * @date 20/12/2024
 * 
 */
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 4",
		tags = "IMA",
		userStoryId = 55047
		)

public class ATC_FT_255_ValidacionDeEstructuraDeLaTablaCebanksv extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_255_ValidacionDeEstructuraDeLaTablaCebanksv_test(HashMap<String, String> data) throws Exception {
						
		
		/*****************************Variables Data Provider**************************************/
		String tableName = data.get("tableName");	
		String owner = data.get("owner");
		String type = data.get("type");
		String run = data.get("run");
		String typeB = data.get("typeB");
		String testName = data.get("testName");
		

		/*****************************Instancias **************************************************/
		utilsAutoReportesDatalake util = new utilsAutoReportesDatalake();
		Connection connection = null;
		HashMap<String,String[]> responseSnow = new HashMap<String, String[]>();

		//Otras variables que ayudan con la logica
		String queryConsultaEBS;
		String [] snowColumns = new String[6];
		
		/*****************************Queries*******************************************************/
		String queryConsultaSF= util.getQuerySFGetMetadata(tableName,util.getSchema(type));
		
		/*************************************** PASO 0 ***************************************/
		addStep("Caso de prueba : " + testName);
		//apoyo visual para consola
		System.out.println("Ejecutando :" + testName);
		
		//Flag para ejecucion de solo rows especificos del dataProvider
				if(run.equalsIgnoreCase("si")) {	
		/*************************************** PASO 1 ***************************************/
		addStep("Hacer conexion a la Base de datos de EBS/RMS y validar conexion.");
		//Se obtienen las credenciales depende del tipo de objeto a probar
		SQLUtil ebsDataBase = new SQLUtil(	util.getHost(type),
											util.getUser(type),
											util.getPassword(type));
		assertTrue(ebsDataBase.getConn().isValid(10),"Validacion de conexion a EBS");
		if(ebsDataBase.getConn().isValid(10))
			testCase.addCodeEvidenceCurrentStep("Conexion a EBS arroja estatus OK");
		
		
		/*************************************** PASO 2 ***************************************/
		addStep("Ejecutar el query de consulta a EBS/RMS y almacenar la respuesta.");	
		//Se agregan validaciones para asignar owner y tableName a RMS esto debido a la diferente logica donde la tabla se divide en 3
		if(type.equalsIgnoreCase("RMS")) {
			tableName = "NB_FEM_FIF_STG";
			owner = "RMS_EXT";
		}
		if(type.equalsIgnoreCase("RMS-OLD")) {
			tableName = "FEM_FIF_STG";
			owner = "RMS_100";
		}
		//Se valida si es una vista en caso de que no se busca el owner real del synonym
		if((!(typeB.equalsIgnoreCase("Vista")))&&(!(type.contains("RMS")))) {
			SQLResult getRealTable = ebsDataBase.executeQuery(util.getQueryRealTable(owner, tableName));
			String ownerReal = getRealTable.getData(0, "TABLE_OWNER");
			String nameReal = getRealTable.getData(0, "TABLE_NAME");
			testCase.addBoldTextEvidenceCurrentStep("El owner encontrado es: " + ownerReal + " y la tabla encontrada es: " + nameReal);	
			queryConsultaEBS = util.getQueryEBSGetMetadata(ownerReal,nameReal);
		}
		else {
			testCase.addBoldTextEvidenceCurrentStep("El objeto a probar es una vista por ende no se busca el owner real");
			queryConsultaEBS = util.getQueryEBSGetMetadata(owner,tableName);
		}
			
		//Se ejecuta el paso normal
		SQLResult response = ebsDataBase.executeQuery(queryConsultaEBS);
		assertFalse(response.isEmpty(),"Validacion si la respuesta esta vacia");
		if(!response.isEmpty())
		testCase.addCodeEvidenceCurrentStep("Se ejecuto con exito el query: " + response.getQuery());
		testCase.addQueryEvidenceCurrentStep(response);
		ebsDataBase.close();
		
		
		/*************************************** PASO 3 ***************************************/
		addStep("Hacer conexion a la Base de datos de Snowflake y validar conexion.");
		//lo incluyo todo a un try para el errorHandler pendiente hacer un manejo de errores mas elegante
		try {			
			connection = util.connectToSnow(Credentials.SF_DL_QA_AM.user,
					Credentials.SF_DL_QA_AM.password,
					Credentials.SF_DL_QA_AM.host,
					util.getSchema(type));
			if(connection.isValid(10))
				testCase.addCodeEvidenceCurrentStep("Conexion a SnowFlake arroja estatus OK");						
		/*************************************** PASO 4 ***************************************/
			addStep("Ejecutar el query de consulta a Snowflake y almacenar la respuesta.");			
			//Se genera un statement para ejecutar las consultas
			Statement stat = connection.createStatement();		
			//Se manda una consulta y se guarda en un resultSet
			ResultSet result = stat.executeQuery(queryConsultaSF);			
			//se almacenan los valores
			responseSnow = util.getSnowValues(result, "Metadata");
			//se valida que la respuesta no sea nula
			String status = !responseSnow.isEmpty() ? "Se obtiene y almacena una respuesta" : "No se pudo obtener una respuesta";
			testCase.addBoldTextEvidenceCurrentStep(status);			
		//Manejo de errores especifico
		}catch (ClassNotFoundException e) {
			System.out.println("Snowflake JDBC driver not found");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error connecting to Snowflake");
			e.printStackTrace();
		} 
		finally {
			//cierre de conexion
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					System.out.println("Error closing connection");
					e.printStackTrace();
				}
			}
		}
		//finaliza manejo de errores
		

		/*************************************** PASO 5 ***************************************/
		addStep("Validar la cantidad de rows en la respuesta de EBS y en la respuesta de snowflake y compararlas.");
		int responseEBSSize = response.getRowCount();
		int responseSnowFlakeSize = responseSnow.size();		
		testCase.addCodeEvidenceCurrentStep("El tamano de la respuesta en SQL es: " + responseEBSSize);
		testCase.addCodeEvidenceCurrentStep("El tamano de la respuesta en SnowFlake es: " + responseSnowFlakeSize);
		//se agrega uno al response de EBS porque en snow se debe tener un campo mas que es respectivo a FECHACARGA en caso de 
		//ser una vista esto se ignora ya que als vistas no tienen FECHACARGA
		responseEBSSize = typeB.equalsIgnoreCase("vista") ? responseEBSSize : responseEBSSize + 1;
		assertTrue(responseEBSSize==responseSnowFlakeSize, "Validacion de tamano de las respuestas");				
		
		//Se genera un set de pruebas para cada campo en la respuesta
		for (int i = 0; i < response.getRowCount(); i++) {			
			testCase.addCodeEvidenceCurrentStep("Validaciones del campo: " + response.getData(i, "COLUMN_NAME"));
			
			if(util.itsIgnorable(tableName, response.getData(i, "COLUMN_NAME"))){
				testCase.addBoldTextEvidenceCurrentStep("Se salta las validaciones el campo: " + response.getData(i, "COLUMN_NAME") 
														+ " debido a que es un campo especial, consultar con desarrollo para mas informacion.");
				continue;
			}
			
			/*************************************** PASO 6 ***************************************/
			addStep("Validar los valores en column name de ambas respuestas.");
			String responseEBSColumnName = response.getData(i, "COLUMN_NAME");
			testCase.addCodeEvidenceCurrentStep("El valor de la columna es " + responseEBSColumnName);			
			assertTrue(responseSnow.containsKey(responseEBSColumnName), "Validacion de nombre de columna para la columna: " + responseEBSColumnName);
			testCase.addCodeEvidenceCurrentStep("Prueba de Nombre de Columna PASS");		
			snowColumns = responseSnow.get(responseEBSColumnName);
			
			
			/*************************************** PASO 7 ***************************************/
			addStep("Validar los valores de Data_Type de ambas respuestas.");
			String responseEBSDataType = response.getData(i, "DATA_TYPE");
			String responseSnowDataType = snowColumns[0];
			String responseEBSPrecision = response.getData(i, "DATA_PRECISION");
			testCase.addCodeEvidenceCurrentStep("El valor de Data_Type en EBS es " + responseEBSDataType);
			testCase.addCodeEvidenceCurrentStep("El valor de Data_Type en Snow es " + responseSnowDataType);
			assertTrue(util.validateDataType(responseEBSDataType,responseSnowDataType,responseEBSPrecision),"Validacion de Data_Type para la columna: " + responseEBSColumnName);
			
			
			/*************************************** PASO 8 ***************************************/
			addStep("Validar los valores de Data_Length de ambas respuestas.");
			String responseEBSDataLength = response.getData(i, "DATA_LENGTH");
			String responseSnowDataLength = snowColumns[1];
			testCase.addCodeEvidenceCurrentStep("El valor de DATA_LENGTH en EBS es " + responseEBSDataLength);
			testCase.addCodeEvidenceCurrentStep("El valor de DATA_LENGTH en Snow es " + responseSnowDataLength);			
			assertTrue(util.validateDataLength(responseEBSDataType,responseEBSDataLength,responseSnowDataLength),"Validacion de DATA_LENGTH para la columna: " + responseEBSColumnName);				
		}
		
		//Se salta esta validacion si es una vista
		if(!(typeB.equalsIgnoreCase("Vista"))) {
			/*************************************** PASO 11 ***************************************/
			addStep("Validar FECHACARGA. paso omitido en la matriz pero se agrega la validacion por temas de Calidad");
			assertTrue(responseSnow.containsKey("FECHACARGA"), "Validacion de nombre de columna");
			snowColumns = responseSnow.get("FECHACARGA");
			String dataType = snowColumns[0];
			String nullable = snowColumns[3];
			testCase.addCodeEvidenceCurrentStep("El tipo de dato de FECHACARGA es: " + dataType);
			testCase.addCodeEvidenceCurrentStep("El valor de nullable de FECHACARGA es: " + nullable);			
		}
				}
		
	}
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_255_ValidacionDeEstructuraDeLaTablaCebanksv";
	}

	@Override
	public String setTestDescription() {
		return "Validar las columnas y los tipos de datos de la tabla, tanto en Oracle SQL y en Snowflake";
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
		return "1.-Acceso a la BD de EBS. "
				+ "2.-Acceso a la BD de Snowflake.";
		
	}

	@Override
	public void beforeTest() {
		
	}
}