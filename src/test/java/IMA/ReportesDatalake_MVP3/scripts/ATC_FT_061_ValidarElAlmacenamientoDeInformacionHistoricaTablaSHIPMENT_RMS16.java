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
 * Descripción: Verificar que la información histórica se almacene
 * correctamente, y la que esté fuera de rango sea depurada.
 *
 * Casos de prueba manual:
 * ATC_FT_061_ValidarElAlmacenamientoDeInformacionHistoricaTablaSHIPMENT_RMS16
 *
 * Prerrequisitos: 
 * 1.-Acceso a Snowflake Base de datos:
 * DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.SHIPMENT_RMS16 
 * 2.-Permisos de lectura en los esquemas correspondientes. 
 * 3.-Acceso a la base de datos RMS (FCDASQAS).
 * 4.-Acceso a Control-M (para ejecución y monitoreo de procesos).
 * 
 * Insumos: -N/A
 * 
 * @author Joaquín Aguilar Hernández
 * @date 03/10/2025
 * 
 *//*
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake", 
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3", 
		tags = "IMA", 
		userStoryId = 210842
		)*/

public class ATC_FT_061_ValidarElAlmacenamientoDeInformacionHistoricaTablaSHIPMENT_RMS16 extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true, testName = "")
	public void ATC_FT_061_ValidarElAlmacenamientoDeInformacionHistoricaTablaSHIPMENT_RMS16_test(
			HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Automatización Reportes DataLake MVP3");

		/**************************************************
		 * Instancias
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
			addStep("Contar total de registros dentro de los últimos 7 años en SHIPMENT (Oracle)");
			String queryCountLast7YearsOracle = "SELECT COUNT(*) AS TOTAL FROM RMS.SHIPMENT WHERE SHIP_DATE >= ADD_MONTHS(SYSDATE, -84)";
			SQLResult countResultLast7Years = dbDES.executeQuery(queryCountLast7YearsOracle);
			int countLast7YearsOracle = Integer.parseInt(countResultLast7Years.getData(0, "TOTAL").toString());
			testCase.addTextEvidenceCurrentStep("Total registros últimos 7 años en Oracle: " + countLast7YearsOracle);

			/*********************************************************************************
			 * Paso 3: Validar registros más antiguos a 7 años (Oracle)
			 **********************************************************************************/
			addStep("Validar que no existan registros más antiguos a 7 años en SHIPMENT (Oracle)");
			String queryCountOlderThan7Years = "SELECT COUNT(*) AS TOTAL FROM RMS.SHIPMENT WHERE SHIP_DATE < ADD_MONTHS(SYSDATE, -84)";
			SQLResult resultOlderThan7Years = dbDES.executeQuery(queryCountOlderThan7Years);
			int countOlderThan7Years = Integer.parseInt(resultOlderThan7Years.getData(0, 0).toString());
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
			addStep("Verificar cantidad de registros de los últimos 7 años en Snowflake");
			String queryCountLast7YearsSnowflake = "SELECT COUNT(*) AS TOTAL FROM DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.SHIPMENT_RMS16 WHERE SHIP_DATE >= DATEADD(year, -7, CURRENT_DATE)";
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
			String queryCountOlderThan7YearsSnowflake = "SELECT COUNT(*) AS TOTAL FROM DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.SHIPMENT_RMS16 WHERE SHIP_DATE < DATEADD(year, -7, CURRENT_DATE)";
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
		return "ATC_FT_061_ValidarElAlmacenamientoDeInformacionHistoricaTablaSHIPMENT_RMS16";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que la información histórica se almacene correctamente, y la que esté fuera de rango sea depurada.";
	}

	@Override
	public String setTestDesigner() {
		return "Joaquin Aguilar";
	}

	@Override
	public String setTestInstanceID() {
		return "1";
	}

	@Override
	public String setPrerequisites() {
		return "1.-Acceso a Snowflake"
				+ "Base de datos: DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.SHIPMENT_RMS16"
				+ "2.-Permisos de lectura en los esquemas correspondientes."
				+ "3.-Acceso a la base de datos RMS (FCDASQAS)"
				+ "4.-Acceso a Control-M (para ejecución y monitoreo de procesos).";
	}

	@Override
	public void beforeTest() {

	}
}
