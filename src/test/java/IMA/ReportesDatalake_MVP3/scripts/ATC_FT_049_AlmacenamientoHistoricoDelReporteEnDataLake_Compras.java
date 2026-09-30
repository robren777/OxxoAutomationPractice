package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3;
import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3.ExcelReader;
import modelo.BaseExecution;

/**
 * Descripcion:
 *  Verificar que el resultado del query/sp se almacene correctamente en una tabla de DataLake.
 *	Esta tabla debe permitir mantener al menos 2 trimestres de historia.
 *	Además, los registros con antigüedad mayor al período definido deberan depurarse (eliminados)
 * 
 * Nombre casos de prueba manual que se abarcan: ATC_FT_049_AlmacenamientoHistoricoDelReporteEnDataLake_Compras
 * 
 * 
 * Prerequisitos:
 * 1.-Acceso a la BD de Snowflake.
 * 
 * 
 * Insumos:
 * -N/A
 * @author Joaquin Aguilar
 * @date 28/08/2025
 * 
 *//*
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId = 210855
		)*/

public class ATC_FT_049_AlmacenamientoHistoricoDelReporteEnDataLake_Compras extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_049_AlmacenamientoHistoricoDelReporteEnDataLake_Compras_test(HashMap<String, String> data) throws Exception {
						
		
		/***************************** Variables Data Provider **************************************/
        String tableName = data.get("tableName");
        String type = data.get("type");
        String run = data.get("run");
        String typeB = data.get("typeB");
        String testName = data.get("testName");
        String fechaInicio = data.get("fechaInicio");
        String fechaFin = data.get("fechaFin");

        /***************************** Instancias **************************************************/
        UtilsReportesDatalakeMVP3 uti = new UtilsReportesDatalakeMVP3();
        List<Map<String, Object>> resultadoSP1 = new ArrayList<>();

        /***************************** Lectura Excel **************************************************/
        addStep("Lectura del excel");
        File folder = Paths.get("Config", "Input", "DataProviders", "Reportes DataLake_Reportes", "ReportesCompras").toFile();
		File[] files = folder.listFiles((dir, name) -> name.endsWith(".xlsx"));

        List<Map<String, String>> allRows = new ArrayList<>();
        for (File file : files) {
            List<Map<String, String>> rows = ExcelReader.readExcelToMap(file.getAbsolutePath());
            allRows.addAll(rows);
        }

        // Validación de antigüedad de los archivos (no más de 6 meses)
        addStep("Validar que no existan reportes con más de 6 meses de antigüedad.");
        boolean oldReportFound = false;
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);

        for (File file : files) {
            Instant lastModifiedInstant = Instant.ofEpochMilli(file.lastModified());
            LocalDate fileDate = LocalDateTime.ofInstant(lastModifiedInstant, ZoneId.systemDefault()).toLocalDate();

            if (fileDate.isBefore(sixMonthsAgo)) {
                testCase.addCodeEvidenceCurrentStep("Archivo con más de 6 meses: " + file.getName() + " (última modificación: " + fileDate + ")");
                oldReportFound = true;
            } else {
                testCase.addCodeEvidenceCurrentStep("Archivo válido: " + file.getName() + " (última modificación: " + fileDate + ")");
            }
        }

        assertFalse(oldReportFound, "No deben existir reportes con más de 6 meses de antigüedad.");

        /***************************** Validar si debe correr ***************************************/
        if (!run.equalsIgnoreCase("si")) return;

        /***************************** Consola y metadata *******************************************/
        addStep("Caso de prueba : " + testName);
        System.out.println("Ejecutando :" + testName);

        /***************************** PASO 1: Conexión **********************************************/
        addStep("Hacer conexión a Snowflake");

        try {
            resultadoSP1 = UtilsReportesDatalakeMVP3.SPExecutorC.ejecutarSPReporteCompras(fechaInicio, fechaFin);

            assertFalse(resultadoSP1 == null || resultadoSP1.isEmpty(), "El resultado del SP está vacío o es nulo.");

            testCase.addTextEvidenceCurrentStep("Conexión a la base de datos Snowflake establecida correctamente.");
            testCase.addTextEvidenceCurrentStep(
                "Se ejecuta CALL DBZDEV_MEX_OXXO.LAND_DAS_CAT.SP_REPORTE_COMPRAS con fechas: " 
                + fechaInicio + ", " + fechaFin);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al ejecutar el Stored Procedure: " + e.getMessage());
        }

        /***************************** PASO 2: Validación antigüedad en datos de Snowflake usando 'MES' **********/
        addStep("Validar que los datos obtenidos de Snowflake no tengan más de 6 meses (columna MES).");

        YearMonth sixMonthsAgoYM = YearMonth.now().minusMonths(6);

        for (Map<String, Object> row : resultadoSP1) {
            Object mesObj = row.get("MES");

            if (mesObj == null) {
                testCase.addCodeEvidenceCurrentStep("Campo MES ausente en registro: " + row);
                fail("Campo 'MES' ausente en al menos un registro.");
            }

            String mesStr = mesObj.toString().trim();

            if (!mesStr.matches("\\d{6}")) {
                testCase.addCodeEvidenceCurrentStep("Formato inválido en campo MES: " + mesStr + " en registro: " + row);
                fail("Campo 'MES' con formato inválido: " + mesStr);
            }

            int year = Integer.parseInt(mesStr.substring(0, 4));
            int month = Integer.parseInt(mesStr.substring(4, 6));

            // Validación de mes válido (1 a 12)
            if (month < 1 || month > 12) {
                testCase.addCodeEvidenceCurrentStep("Valor de mes inválido en campo MES: " + mesStr + " en registro: " + row);
                fail("Campo 'MES' tiene un mes fuera de rango válido (1-12): " + mesStr);
            }

            YearMonth mesActual = YearMonth.of(year, month);

            if (mesActual.isBefore(sixMonthsAgoYM)) {
                testCase.addCodeEvidenceCurrentStep("Registro con MES antiguo: " + mesStr + " (interpreta como: " + mesActual + ")");
                fail("Se encontró al menos un registro con MES anterior a 6 meses: " + mesStr);
            }

            testCase.addCodeEvidenceCurrentStep("Registro válido: " + mesStr + " (" + mesActual + ")");
        }

        /***************************** PASO 3: Validación estructura columnas ************************/
        addStep("Comparar estructura entre Excel y tabla en Snowflake (solo columnas)");

        // Columnas en Excel
        Set<String> excelColumns = allRows.stream()
            .map(row -> row.get("COLUMN_NAME"))
            .filter(Objects::nonNull)
            .map(String::trim)
            .collect(Collectors.toSet());

        // Columnas en Snowflake (tomadas del primer registro)
        Set<String> snowflakeColumns = resultadoSP1.stream()
            .findFirst()
            .map(Map::keySet)
            .map(keys -> keys.stream().map(String::trim).collect(Collectors.toSet()))
            .orElse(Collections.emptySet());

        testCase.addCodeEvidenceCurrentStep("Columnas en Excel: " + excelColumns);
        testCase.addCodeEvidenceCurrentStep("Columnas en Snowflake: " + snowflakeColumns);

        assertEquals(excelColumns.size(), snowflakeColumns.size(), "Validación del número de columnas");
        assertTrue(excelColumns.equals(snowflakeColumns), "Validación de columnas coincidentes (estructura)");

        testCase.addBoldTextEvidenceCurrentStep("Estructura validada correctamente. Columnas coinciden entre Excel y Snowflake.");
    }
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_049_AlmacenamientoHistoricoDelReporteEnDataLake_Compras";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que el resultado del query/sp se almacene correctamente en una tabla de DataLake.\r\n"
				+ " *	Esta tabla debe permitir mantener al menos 2 trimestres de historia.\r\n"
				+ " *	Además, los registros con antigüedad mayor al período definido deberan depurarse (eliminados)";
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
		return "1.-Acceso a la BD de Snowflake.";
		
	}

	@Override
	public void beforeTest() {
		
	}
}