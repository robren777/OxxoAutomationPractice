package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * Descripción: Verificar que la estructura en DataLake coincida con la
 * estructura de EBS / RMS y Snowflake
 *
 * Casos de prueba manual:
 * TC_FT_058_ValidarLaEstructuraDeColumnasDeLaTablaSHIPMENT_RMS16
 *
 * Prerrequisitos:
 * 	1.-Acceso a Snowflake
 *   Base de datos: DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.SHIPMENT_RMS16
 * 	2.-Permisos de lectura en los esquemas correspondientes.
 * 	3.-Acceso a la base de datos RMS (FCDASQAS).
 * 	4.-Acceso a Control-M (para ejecución y monitoreo de procesos).
 *
 * Autor: Joaquín Aguilar Hernández 
 * Fecha: 04/10/2025
 * 
 * 
 *//*
	 @AzureTestInfo( 
	 areaPath = "I24043-Automatización Reportes DataLake",
	 iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
	 tags = "IMA", 
	 userStoryId = 210855 
	 )*/
public class ATC_FT_058_ValidarLaEstructuraDeColumnasDeLaTablaSHIPMENT_RMS16 extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true, testName = "")
	public void ATC_FT_058_ValidarLaEstructuraDeColumnasDeLaTablaSHIPMENT_RMS16_test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("Automatización Reportes DataLake MVP3");

		/*************************************************
		 * Instancias
		 **************************************************/
		UtilsReportesDatalakeMVP3 uti = new UtilsReportesDatalakeMVP3();
// Declaración de queries
		String qryDES = "SELECT * FROM RMS.SHIPMENT WHERE ROWNUM <= 1";
		String qrySnowflake = "SELECT * FROM DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.SHIPMENT_RMS16 LIMIT 1;";

// Paso 1: Conexión a EBS/RMS
		addStep("Paso 1 - Establecer conexión a la base de datos EBS/RMS");
		SQLUtil dbDES = UtilsReportesDatalakeMVP3.createDESConnection();
		addStep("Paso 1-Passed - Conexión exitosa a EBS/RMS");

// Paso 2: Ejecutar consulta en EBS/RMS
		addStep("Paso 2 - Ejecutar la consulta en EBS/RMS para obtener columnas de la tabla SHIPMENT_RMS16");
		SQLResult resultDES = dbDES.executeQuery(qryDES);

		List<String> columnNamesDES = resultDES.getColumnNames();

// Generar evidencia con numeración
		StringBuilder columnsDES = new StringBuilder();
		int counter = 1;
		for (String col : columnNamesDES) {
			columnsDES.append(counter).append(" ").append(col).append("\n");
			counter++;
		}
		testCase.addTextEvidenceCurrentStep("Columnas RMS:\n" + columnsDES.toString());
		addStep("Paso 2-Passed - Consulta ejecutada correctamente en RMS");

// Paso 3: Conexión a Snowflake
		addStep("Paso 3 - Establecer conexión a la base de datos de Snowflake");
		Connection connectionSnowflake = UtilsReportesDatalakeMVP3.conexionsnow.createSnowflakeConnection();
		addStep("Paso 3-Passed - Conexión exitosa a Snowflake");

// Paso 4: Ejecutar consulta en Snowflake
		addStep("Paso 4 - Ejecutar consulta en Snowflake sobre la tabla SHIPMENT_RMS16");
		Statement stmt = connectionSnowflake.createStatement();
		ResultSet rs = stmt.executeQuery(qrySnowflake);

// Obtener nombres de columnas
		List<String> columnNamesSnowflake = new ArrayList<>();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			columnNamesSnowflake.add(metaData.getColumnName(i));
		}

// Evidencia con numeración
		StringBuilder columnsSnowflake = new StringBuilder();
		counter = 1;
		for (String col : columnNamesSnowflake) {
			columnsSnowflake.append(counter).append(" ").append(col).append("\n");
			counter++;
		}
		testCase.addTextEvidenceCurrentStep("Columnas Snowflake:\n" + columnsSnowflake.toString());
		addStep("Paso 4-Passed - Consulta ejecutada correctamente en Snowflake");

// Paso 5: Comparar estructura de columnas (sin incluir IDMD5 y FECHA_CARGA)
		addStep("Paso 5 - Comparar estructura de columnas entre EBS/RMS y Snowflake");
		Set<String> columnasExcluidas = new HashSet<>(Arrays.asList("IDMD5", "FECHA_CARGA"));

		List<String> columnasFiltradasDES = columnNamesDES.stream().filter(col -> !columnasExcluidas.contains(col))
				.collect(Collectors.toList());

		List<String> columnasFiltradasSnowflake = columnNamesSnowflake.stream()
				.filter(col -> !columnasExcluidas.contains(col)).collect(Collectors.toList());

// Numeración para columnas filtradas RMS
		StringBuilder columnasFiltradasDESStr = new StringBuilder();
		counter = 1;
		for (String col : columnasFiltradasDES) {
			columnasFiltradasDESStr.append(counter).append(" ").append(col).append("\n");
			counter++;
		}
		testCase.addTextEvidenceCurrentStep("Columnas RMS filtradas:\n" + columnasFiltradasDESStr.toString());

// Numeración para columnas filtradas Snowflake
		StringBuilder columnasFiltradasSnowflakeStr = new StringBuilder();
		counter = 1;
		for (String col : columnasFiltradasSnowflake) {
			columnasFiltradasSnowflakeStr.append(counter).append(" ").append(col).append("\n");
			counter++;
		}
		testCase.addTextEvidenceCurrentStep(
				"Columnas Snowflake filtradas:\n" + columnasFiltradasSnowflakeStr.toString());

// Validación de coincidencia de estructura
		assertEquals(columnasFiltradasDES, columnasFiltradasSnowflake,
				"La estructura de las tablas SHIPMENT_RMS16 en EBS/RMS y Snowflake no coinciden.");

		addStep("Paso 5-Passed - La estructura de columnas entre EBS/RMS y Snowflake coincide");

		rs.close();
		stmt.close();
		connectionSnowflake.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_058_ValidarLaEstructuraDeColumnasDeLaTablaSHIPMENT_RMS16";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que la estructura en DataLake coincida con la estructura de EBS / RMS y Snowflake";
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
		return "\"1.-Acceso a Snowflake"
				+ "Base de datos: DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.SHIPMENT_RMS16"
				+ "2.-Permisos de lectura en los esquemas correspondientes"
				+ "3.-Acceso a la base de datos RMS (FCDASQAS)"
				+ "4.-Acceso a Control-M (para ejecución y monitoreo de procesos)";
	}

	@Override
	public void beforeTest() {

	}
}
