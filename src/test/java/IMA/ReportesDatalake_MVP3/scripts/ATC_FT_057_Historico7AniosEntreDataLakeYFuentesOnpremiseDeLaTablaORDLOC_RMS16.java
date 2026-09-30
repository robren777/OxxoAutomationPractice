package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * Descripcion:
 * Asegurar que se mantiene histórico completo de 7 años y que se depura lo que supere ese plazo
 * 
 * Nombre casos de prueba manual que se abarcan:
 * ATC_FT_057_Historico7AniosEntreDataLakeYFuentesOnpremiseDeLaTablaORDLOC_RMS16
 * 
 * Prerequisitos:
 *1,-Acceso a Snowflake (DB: DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.ORDLOC_RMS16;); 
  2.-Permisos de lectura en los esquemas correspondientes.
  3.-Acceso a la base de datos RMS (FCDASQAS).
  4.-Acceso a Control-M (para ejecución y monitoreo de procesos).

 * Insumos:
 * -N/A
 * @author Angel Gabriel Ramos Martinez
 * @date 06/10/2025
 * 
 /*
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId = 210855
		) */


public class ATC_FT_057_Historico7AniosEntreDataLakeYFuentesOnpremiseDeLaTablaORDLOC_RMS16 extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_057_Historico7AniosEntreDataLakeYFuentesOnpremiseDeLaTablaORDLOC_RMS16_test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Automatización Reportes DataLake MVP3");

		/*****************************************************
		                      Instancias
		 **************************************************/
		UtilsReportesDatalakeMVP3 uti = new UtilsReportesDatalakeMVP3();

		try {
			/*********************************************************************************
			 * Paso 1: Conexión a la base de datos RMS16 (Oracle)
			 **********************************************************************************/
			addStep("Establecer conexión a la base de datos RMS16");
			SQLUtil dbDES = UtilsReportesDatalakeMVP3.createDESConnection();

			/*********************************************************************************
			 * Paso 2: Obtener hasta 10 registros de los últimos 7 años (Oracle)
			 **********************************************************************************/
			addStep("Contar total de registros dentro de los últimos 7 años en ORDHEAD (Oracle)");
			String queryCountLast7YearsOracle =
		    "SELECT COUNT(*) AS TOTAL FROM RMS.ORDLOC WHERE ESTIMATED_INSTOCK_DATE >= ADD_MONTHS(SYSDATE, -84)";
			SQLResult countResultLast7Years = dbDES.executeQuery(queryCountLast7YearsOracle);
			int countLast7YearsOracle = Integer.parseInt(countResultLast7Years.getData(0, "TOTAL").toString());
			testCase.addTextEvidenceCurrentStep("Total registros últimos 7 años en Oracle: " + countLast7YearsOracle);
			

			/*********************************************************************************
			 * Paso 3: Validar que no haya registros más antiguos a 7 años (Oracle)
			 **********************************************************************************/
			addStep("Validar que no existan registros más antiguos a 7 años en ORDHEAD (Oracle)");
			String queryCountOlderThan7Years = "SELECT COUNT(*) AS TOTAL FROM RMS.ORDLOC WHERE ESTIMATED_INSTOCK_DATE< ADD_MONTHS(SYSDATE, -84)";
			SQLResult resultOlderThan7Years = dbDES.executeQuery(queryCountOlderThan7Years);
			int countOlderThan7Years = Integer.parseInt(resultOlderThan7Years.getData(0, 0).toString());
			assertTrue(countOlderThan7Years == 0, "No debe haber datos más antiguos a 7 años en Oracle");
			testCase.addTextEvidenceCurrentStep(
					"Total registros más antiguos a 7 años en Oracle: " + countOlderThan7Years);

			/*********************************************************************************
			 * Paso 4: Conexión a la base de datos Snowflake
			 **********************************************************************************/
			addStep("Establecer conexión a la base de datos Snowflake");
			Connection connectionSnowflake = UtilsReportesDatalakeMVP3.conexionsnow.createSnowflakeConnection();
			assertTrue(connectionSnowflake != null && !connectionSnowflake.isClosed(),
					"La conexión a Snowflake se estableció correctamente");

			/*********************************************************************************
			 * Paso 5: Verificar que haya máximo 10 registros de los últimos 7 años
			 * (Snowflake)
			 **********************************************************************************/
			addStep("Verificar que haya máximo 10 registros de los últimos 7 años en Snowflake");
			String queryCountLast7YearsSnowflake = "SELECT COUNT(*) AS TOTAL FROM DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.ORDLOC_RMS16 WHERE ESTIMATED_INSTOCK_DATE >= DATEADD(year, -7, CURRENT_DATE)";
			Statement stmt1 = connectionSnowflake.createStatement();
			ResultSet rs1 = stmt1.executeQuery(queryCountLast7YearsSnowflake);
			rs1.next();
			int countLast7YearsSnowflake = rs1.getInt("TOTAL");
			testCase.addTextEvidenceCurrentStep(
					"Total registros últimos 7 años en Snowflake: " + countLast7YearsSnowflake);
			rs1.close();
			stmt1.close();

			/*********************************************************************************
			 * Paso 6: Validar que no haya registros más antiguos a 7 años (Snowflake)
			 **********************************************************************************/
			addStep("Validar que no existan registros más antiguos a 7 años en Snowflake");
			String queryCountOlderThan7YearsSnowflake = "SELECT COUNT(*) AS TOTAL FROM DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.ORDLOC_RMS16 WHERE ESTIMATED_INSTOCK_DATE < DATEADD(year, -7, CURRENT_DATE)";
			Statement stmt2 = connectionSnowflake.createStatement();
			ResultSet rs2 = stmt2.executeQuery(queryCountOlderThan7YearsSnowflake);
			rs2.next();
			int countOlderThan7YearsSnowflake = rs2.getInt("TOTAL");
			assertTrue(countOlderThan7YearsSnowflake == 0, "No debe haber datos más antiguos a 7 años en Snowflake");
			testCase.addTextEvidenceCurrentStep(
					"Total registros más antiguos a 7 años en Snowflake: " + countOlderThan7YearsSnowflake);
			rs2.close();
			stmt2.close();

			/*********************************************************************************
			 * Paso 7: Cerrar conexiones
			 **********************************************************************************/
			connectionSnowflake.close();
			dbDES.close();

		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("Error durante la ejecución: " + e.getMessage());
			throw e;
		}

    }
	@Override
	public String setTestFullName() {
		return "ATC_FT_057_Historico7AniosEntreDataLakeYFuentesOnpremiseDeLaTablaORDLOC_RMS16";
	}

	@Override
	public String setTestDescription() {
		return "Asegurar que se mantiene histórico completo de 7 años y que se depura lo que supere ese plazo";
	}

	@Override
	public String setTestDesigner() {
		return "Angel Ramos";
	}

	@Override
	public String setTestInstanceID() {
		return "1";
	}

	@Override
	public String setPrerequisites() {
		return "1.-Acceso a Snowflake (DB:DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.ORDLOC_RMS16)"
				+"2.-Permisos de lectura en los esquemas correspondientes."
				+"3.-Acceso a la base de datos RMS (FCDASQAS)"
				+"4.-Acceso a Control-M (para ejecución y monitoreo de procesos)";
		
	}

	@Override
	public void beforeTest() {
		
	}
}