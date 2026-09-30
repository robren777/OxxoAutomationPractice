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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * Descripcion: Verificar que la estructura en DataLake coincida con la
 * estructura de EBS / RMS y Snowflake.
 * 
 * Nombre casos de prueba manual que se abarcan:
 * MTC_FT_054_ValidacionDeEstructuraDeLaTablaORDLOC_RMS16
 * 
 * Prerequisitos: 1,-Acceso a Snowflake (DB:
 * DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.ORDLOC_RMS16;); 2.-Permisos de lectura en los
 * esquemas correspondientes. 3.-Acceso a la base de datos RMS (FCDASQAS).
 * 4.-Acceso a Control-M (para ejecución y monitoreo de procesos).
 * 
 * 
 * Insumos: -N/A
 * 
 * @author Angel Gabriel Ramos Martinez
 * @date 03/10/2025
 * 
 *       /* @AzureTestInfo( areaPath = "I24043-Automatización Reportes
 *       DataLake", iterationPath = "I24043-Automatización Reportes
 *       DataLake\\Sprint 2 MVP3", tags = "IMA", userStoryId = 210842 )
 */

public class ATC_FT_054_ValidacionDeEstructuraDeLaTablaORDLOC_RMS16 extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true, testName = "")
	public void ATC_FT_054_ValidacionDeEstructuraDeLaTablaORDLOC_RMS16_test(HashMap<String, String> data)
			throws Exception {

		testCase.setProject_Name("Automatización Reportes DataLake MVP3");
		/*************************************************
		 * /************************************************* Instancias
		 **************************************************/
		UtilsReportesDatalakeMVP3 uti = new UtilsReportesDatalakeMVP3();

		// Declaración de queries
		String qryDES = "SELECT * FROM RMS.ORDLOC WHERE ROWNUM <= 1";
		String qrySnowflake = "SELECT * FROM DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.ORDLOC_RMS16 LIMIT 1;";

		// Paso 1: Conexión a EBS/RMS
		addStep("Paso 1 - Establecer conexión a la base de datos EBS/RMS");
		SQLUtil dbDES = UtilsReportesDatalakeMVP3.createDESConnection();
		addStep("Paso 1-Passed - Conexión exitosa a EBS/RMS");

		// Paso 2: Ejecutar consulta en EBS/RMS
		addStep("Paso 2 - Ejecutar la consulta en EBS/RMS para obtener columnas de la tabla ORDLOC_RMS16");
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
		addStep("Paso 4 - Ejecutar consulta en Snowflake sobre la tabla ORDLOC_RMS16");
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
				"La estructura de las tablas ORDLOC_RMS16 en EBS/RMS y Snowflake no coinciden.");

		addStep("Paso 5-Passed - La estructura de columnas entre EBS/RMS y Snowflake coincide");

		/***************************************
		 * PASO 6
		 ***************************************/
		addStep("Paso 6 - Validar tipo de dato y longitud de columnas entre RMS y Snowflake");

		// === Obtener tipos de columnas desde RMS ===
		String qryTiposRMS = "SELECT COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION "
				+ "FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = 'ORDLOC' AND OWNER = 'RMS'";

		SQLResult tiposResultRMS = dbDES.executeQuery(qryTiposRMS);
		Map<String, Map<String, String>> mapRMS = new HashMap<>();

		int totalRows = tiposResultRMS.getRowCount();
		for (int i = 0; i < totalRows; i++) {
			String colName = tiposResultRMS.getData(i, "COLUMN_NAME");
			String dataType = tiposResultRMS.getData(i, "DATA_TYPE");
			String dataLength = tiposResultRMS.getData(i, "DATA_LENGTH");
			String dataPrecision = tiposResultRMS.getData(i, "DATA_PRECISION");

			Map<String, String> info = new HashMap<>();
			info.put("DATA_TYPE", dataType);
			info.put("DATA_LENGTH", dataLength);
			info.put("DATA_PRECISION", dataPrecision);
			mapRMS.put(colName, info);
		}

		// === Obtener tipos de columnas desde Snowflake ===
		String qryTiposSnow = "SELECT COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION "
				+ "FROM DBZDEV_MEX_OXXO.INFORMATION_SCHEMA.COLUMNS "
				+ "WHERE TABLE_SCHEMA = 'LAND_DASOP_TRAN' AND TABLE_NAME = 'ORDLOC_RMS16'";

		Statement stmtTipos = connectionSnowflake.createStatement();
		ResultSet rsTipos = stmtTipos.executeQuery(qryTiposSnow);
		Map<String, Map<String, String>> mapSnow = new HashMap<>();

		while (rsTipos.next()) {
			Map<String, String> info = new HashMap<>();
			info.put("DATA_TYPE", rsTipos.getString("DATA_TYPE"));
			info.put("DATA_LENGTH", rsTipos.getString("CHARACTER_MAXIMUM_LENGTH"));
			info.put("NUMERIC_PRECISION", rsTipos.getString("NUMERIC_PRECISION"));
			mapSnow.put(rsTipos.getString("COLUMN_NAME"), info);
		}

		// === Comparación tipo y longitud ===
		StringBuilder diferencias = new StringBuilder();
		for (String col : columnasFiltradasDES) {
			Map<String, String> rms = mapRMS.get(col);
			Map<String, String> snow = mapSnow.get(col);
			if (rms == null || snow == null)
				continue;

			String tipoRMS = rms.get("DATA_TYPE");
			String tipoSnow = snow.get("DATA_TYPE");

			String tipoRMSMapeado = UtilsReportesDatalakeMVP3.mapTipo(tipoRMS);
			String tipoSnowMapeado = UtilsReportesDatalakeMVP3.mapTipo(tipoSnow);

			// Longitudes RMS y Snowflake
			String lenRMS = tipoRMSMapeado.equals("NUMBER") && rms.get("DATA_PRECISION") != null
					? rms.get("DATA_PRECISION")
					: rms.get("DATA_LENGTH");
			String lenSnow = tipoSnowMapeado.equals("NUMBER") && snow.get("NUMERIC_PRECISION") != null
					? snow.get("NUMERIC_PRECISION")
					: snow.get("DATA_LENGTH");

			testCase.addTextEvidenceCurrentStep("Columna: " + col);
			testCase.addTextEvidenceCurrentStep("Tipo RMS: " + tipoRMS + " | Tipo Snowflake: " + tipoSnow);
			testCase.addTextEvidenceCurrentStep("Longitud RMS: " + lenRMS + " | Longitud Snowflake: " + lenSnow);

			// Validación de tipo
			if (!tipoRMSMapeado.equals(tipoSnowMapeado)) {
				diferencias.append("Tipo distinto en ").append(col).append(" -> RMS: ").append(tipoRMS)
						.append(" | Snowflake: ").append(tipoSnow).append("\n");
			}

			// Validación de longitud solo si no es null en Snowflake
			try {
				int lR = lenRMS != null ? Integer.parseInt(lenRMS) : 0;
				int lS = lenSnow != null ? Integer.parseInt(lenSnow) : -1; // -1 = ignorar si es null
				if (lS > 0 && lR != lS) {
					diferencias.append("Longitud distinta en ").append(col).append(" -> RMS: ").append(lR)
							.append(" | Snowflake: ").append(lS).append("\n");
				}
			} catch (Exception e) {
				// Ignorar si no son numéricos
			}
		}

		if (diferencias.length() > 0) {
			testCase.addTextEvidenceCurrentStep("Diferencias encontradas:\n" + diferencias);
			throw new AssertionError("Existen diferencias en tipo o longitud entre RMS y Snowflake.");
		} else {
			addStep("Paso 6-Passed - Tipos y longitudes coinciden correctamente entre RMS y Snowflake");
		}

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_054_ValidacionDeEstructuraDeLaTablaORDLOC_RMS16";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que la estructura en DataLake coincida con la estructura de EBS / RMS y Snowflake";
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
				+ "2.-Permisos de lectura en los esquemas correspondientes."
				+ "3.-Acceso a la base de datos RMS (FCDASQAS)"
				+ "4.-Acceso a Control-M (para ejecución y monitoreo de procesos).";

	}

	@Override
	public void beforeTest() {

	}
}