package WEB.l24059_Interfaz_mod_BIM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.sql.SQLResult;

/**
*
* DESCRIPCION:  Verificar que el sistema muestre correctamente la relaci�n entre los aprobadores y las regiones.
* @author elberth.ruiz@grupotimex.onmicrosoft.com
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* - MTC-FT-014-Mostrar la relaci�n de aprobadores vs regiones
* 
* PREREQUISITOS: 
* - En el DataProvider ingresar en las distintas columnas el valor a consultar
* 
* @date 01/Abril/2025
* 
* INSUMOS
* - Conexion a la BD FCIAS
* 
*/
@AzureTestInfo(
		areaPath = "I24059-interfaz mod BIM con listado de equip y matriz uni",
		iterationPath = "I24059-interfaz mod BIM con listado de equip y matriz uni\\Sprint 2",
		tags = "I24059_Interfaz_mod_BIM;AutomationMPV1",
		userStoryId = 139290
)
public class ATC_FT_014_Mostrarlarelaciondeaprobadoresvsregiones extends BaseExecution{
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_014_Mostrarlarelaciondeaprobadoresvsregiones_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("FrameworkWeb --> Portal de equipamiento V2");
				
		SQLResult queryResult;
		
		 //Conexiones a BD
		
			//FCIAS***
		 utils.sql.SQLUtil dbFCIAS_XXEX = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQA_QAVIEW,GlobalVariables.DB_USER_FCIASQA_QAVIEW, GlobalVariables.DB_PASSWORD_FCIASQA_QAVIEW);
		 
		 //***********************
		 
		//Se valida la estructura de la Tabla XXEX_APROB_REGION
		 String QUERY_DESC_TABLA_APROB_REGION = "SELECT column_name,data_type, data_length, nullable FROM all_tab_columns WHERE table_name = 'XXEX_APROB_REGION' and owner = 'XXEX'";
		//Se valida la Tabla XXEX_APROB_REGION donde se registra la relacion Aprobador-Region
		 String QUERY_DESC_APROB_REGION = "SELECT * FROM xxex.XXEX_APROB_REGION";

		addStep("En el cliente de BD explorar los objetos hasta llegar a la tabla XXEX_APROB_REGION que se encuentra en el esquema XXEX");
		
			queryResult = executeQuery(dbFCIAS_XXEX, QUERY_DESC_TABLA_APROB_REGION);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacionEstructura = false;
			if (queryResult.getRowCount() > 0) 
				validacionEstructura = true;
			assertTrue(validacionEstructura, "No se obtuvo la estructura de la tabla");

		addStep("Realizar la consulta en la tabla XXEX_APROB_REGION para ver la relacion Aprobador-Region");
			queryResult = executeQuery(dbFCIAS_XXEX, QUERY_DESC_APROB_REGION);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacionQuery = false;
			if (queryResult.getRowCount() > 0) 
				validacionQuery = true;
			assertTrue(validacionQuery, "No se encontraron registros que mostrar");


		
		}
			
	@AfterMethod
	public void afterMethod() {
	//u.getDriver().quit();
		
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "En el DataProvider ingresar en las distintas columnas el valor a consultar";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verificar que el sistema muestre correctamente la relaci�n entre los aprobadores y las regiones.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Elberth Humberto Ruiz Cota";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_014_Mostrarlarelaciondeaprobadoresvsregiones_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
