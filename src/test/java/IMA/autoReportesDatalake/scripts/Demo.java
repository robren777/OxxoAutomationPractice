package IMA.autoReportesDatalake.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.autoReportesDatalake.om.utilsAutoReportesDatalake;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**

 */
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 4",
		tags = "IMA",
		userStoryId = 55047
		)

public class Demo extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void Demo_test(HashMap<String, String> data) throws Exception {
						
		
		/*****************************Variables Data Provider**************************************/
		String tableName = data.get("tableName");	
		String owner = data.get("owner");
		String type = data.get("type");
		String typeB = data.get("typeB");
		String testName = data.get("testName");
		

		/*****************************Instancias **************************************************/
		utilsAutoReportesDatalake util = new utilsAutoReportesDatalake();
		//Conexion con SnowFlake y otras variables necesarias para que la conexion con snow funcione
		
		HashMap<String,String[]> responseSnow = new HashMap<String, String[]>();
		String queryConsultaEBS;
		
		/*****************************Queries*******************************************************/
		String queryConsultaSF= util.getQuerySFGetMetadata(tableName,util.getSchema(type));
		
		/*************************************** PASO 3 ***************************************/
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

		
	}
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_001_PruebaColumnas";
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