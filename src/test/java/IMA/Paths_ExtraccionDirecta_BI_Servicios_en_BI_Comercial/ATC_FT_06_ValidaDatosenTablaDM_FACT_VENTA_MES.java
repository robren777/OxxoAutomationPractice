package IMA.Paths_ExtraccionDirecta_BI_Servicios_en_BI_Comercial;

/*
 * 
 * 	Subject\01 Informacion Comercial\BI Servicios en BI Comercial\03 Revisio de Datos - Destino
 * 
 */

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_06_ValidaDatosenTablaDM_FACT_VENTA_MES extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_06_ValidaDatosenTablaDM_FACT_VENTA_MES_Test(HashMap<String, String> data) throws Exception {
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI,
				GlobalVariables.DB_PASSWORD_BI);

		/**
		 * VARIABLES
		 *********************************************************************/

		String Query = "SELECT * FROM BIDMMKT.DM_FACT_VENTA_MES";

		/* PASO 1 *********************************************************************/

		addStep("Por medio de la siguiente consulta se verifican los datos en la tabla de nivel MES");

		System.out.println(Query);
		SQLResult connectionResult = executeQuery(dbPos, Query);

		boolean connection = false;
		if (!connectionResult.isEmpty())
			connection = true;

		if (!connection) {
			testCase.addQueryEvidenceCurrentStep(connectionResult);
		}

		assertTrue(connection, "La tabla no contiene informaci�n.");
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_06_ValidaDatosenTablaDM_FACT_VENTA_MES";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se deben validar que se han insertado los datos en la tabla final para el proceso de mes mediante el siguiente query.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}
}