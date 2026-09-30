package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3;
import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3.ExcelReader;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;


/**
 * Descripcion: Información del reporte debe coincidir con los resultados de los queries en las fuentes
 * 
 * Nombre casos de prueba manual que se abarcan:
 * ATC_FT_011_ComparacionEntreDataLakeVSFuenteOnPremiseColumnaEMPRESATABACALERA
 * 
 * Prerequisitos:
 * 1.-Acceso a la BD de Snowflake.
 * 
 * Insumos:
 * -N/A
 * @author Angel Gabriel Ramos Martinez
 * @date 21/08/2025
 */
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId = 210855
		)

public class ATC_FT_011_ComparacionEntreDataLakeVSFuenteOnPremiseColumnaEMPRESATABACALERA extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_011_ComparacionEntreDataLakeVSFuenteOnPremiseColumnaEMPRESATABACALERA_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Automatización Reportes DataLake MVP3");
		/***************************** Variables Data Provider **************************************/
		String tableName = data.get("tableName");
		String type = data.get("type");
		String run = data.get("run");
		String testName = data.get("testName");
		String fechaInicio = data.get("fechaInicio");
		String fechaFin = data.get("fechaFin");

		/***************************** Validación de parámetros obligatorios *************************/
		if (fechaInicio == null || fechaInicio.isEmpty())
		    fail("Parámetro 'fechaInicio' es obligatorio.");
		if (fechaFin == null || fechaFin.isEmpty())
		    fail("Parámetro 'fechaFin' es obligatorio.");

		/***************************** Instancia de utilidades ****************************************/
		UtilsReportesDatalakeMVP3 util = new UtilsReportesDatalakeMVP3();

		/***************************** PASO 1: Conexión a Snowflake ***********************************/
		// Matriz paso 1: Hacer conexión a la BD de Snowflake
		addStep("Hacer conexión a la base de datos de Snowflake");

		System.out.println("Ejecutando prueba: " + testName);
		if (!run.equalsIgnoreCase("si"))
		    return;

		/***************************** PASO 2: Ejecutar Stored Procedure ******************************/
		// Matriz paso 2: Llamar al SP
		addStep("Llamar al SP dentro de Snowflake: CALL DBZDEV_MEX_OXXO.LAND_DAS_CAT.SP_REPORTE_VENTAS(" + fechaInicio + ", " + fechaFin + ")");

		List<Map<String, Object>> resultadoSP = new ArrayList<>();
		try {
		    resultadoSP = UtilsReportesDatalakeMVP3.SPExecutorV.ejecutarSPReporteVentas(fechaInicio, fechaFin);

		    assertFalse(resultadoSP == null || resultadoSP.isEmpty(), "El resultado del SP está vacío o es nulo.");
		    testCase.addTextEvidenceCurrentStep("Conexión a Snowflake exitosa.");
		    testCase.addTextEvidenceCurrentStep("Stored Procedure ejecutado con fechas: " + fechaInicio + ", " + fechaFin);

		} catch (Exception e) {
		    e.printStackTrace();
		    fail("Error al ejecutar el Stored Procedure: " + e.getMessage());
		}

		/***************************** PASO 3: Lectura de Excel ***************************************/
		addStep("Lectura del archivo Excel para fechas: " + fechaInicio + " - " + fechaFin);

		File folder = Paths.get("Config", "Input", "DataProviders", "Reportes DataLake_Reportes", "ReportesVentas").toFile();
		File[] files = folder.listFiles((dir, name) -> name.endsWith(".xlsx"));

		String mesAnioInicio = fechaInicio.substring(4, 6) + fechaInicio.substring(0, 4); // MMYYYY
		String mesAnioExtra = "052025"; 
		String mesAnioFin = fechaFin.substring(4, 6) + fechaFin.substring(0, 4);         // MMYYYY

		List<Map<String, String>> allRows = new ArrayList<>();
		for (File file : files) {
		    String fileName = file.getName().toUpperCase();
		    if (fileName.contains(mesAnioInicio) || fileName.contains(mesAnioFin) || fileName.contains(mesAnioExtra)) {
		        System.out.println("Archivo incluido: " + file.getName());
		        testCase.addCodeEvidenceCurrentStep("Archivo incluido: " + file.getName());

		        List<Map<String, String>> rows = ExcelReader.readExcelToMap(file.getAbsolutePath());
		        allRows.addAll(rows);
		    }
		}

		assertFalse(allRows.isEmpty(), "No se encontraron archivos Excel para las fechas proporcionadas.");

		/***************************** PASO 4: Validación de estructura *******************************/
		addStep("Validar existencia de columna 'EMPRESA TABACALERA' en Excel");

		boolean foundNOMBRECLIENTEExcel = allRows.stream()
		        .anyMatch(row -> row.containsKey("EMPRESA TABACALERA"));

		assertTrue(foundNOMBRECLIENTEExcel, "La columna 'EMPRESA TABACALERA' no está presente en los archivos Excel.");

		/***************************** PASO 5: Validar parámetros **************************************/
		addStep("Validar formato y tipo de parámetros de entrada");

		assertTrue(fechaInicio.matches("\\d{6}"), "Fecha de inicio con formato incorrecto: " + fechaInicio);
		assertTrue(fechaFin.matches("\\d{6}"), "Fecha de fin con formato incorrecto: " + fechaFin);
		assertTrue("Ventas".equalsIgnoreCase(type) || "Compras".equalsIgnoreCase(type), 
		    "Tipo de reporte no válido: " + type);

		/***************************** PASO 6: Obtener EMPRESA TABACALERA desde Excel ******************/
		// Matriz paso 3: Obtener información EMPRESA TABACALERA
		addStep("Obtener registros con 'EMPRESA TABACALERA' desde Excel");

		List<Map<String, String>> registrosEmpresaTabacalera = allRows.stream()
		    .filter(row -> {
		        String nombreCliente = row.get("EMPRESA TABACALERA");
		        return nombreCliente != null && nombreCliente.trim().equalsIgnoreCase("EMPRESA TABACALERA");
		    })
		    .collect(Collectors.toList());

		long cantidadEmpresaTabacaleraExcel = registrosEmpresaTabacalera.size();
		testCase.addCodeEvidenceCurrentStep("Cantidad de registros leídos del Excel con 'EMPRESA TABACALERA': " + cantidadEmpresaTabacaleraExcel);

		/***************************** PASO 7: Obtener desde SP registros EMPRESA TABACALERA ***********/
		// Matriz paso 4: Validar que registros sean 0 (IEPS eliminado)
		addStep("Validar que registros de SP para 'EMPRESA TABACALERA' tengan ID = 0");

		List<Map<String, Object>> registrosSP_EmpresaTabacalera = resultadoSP.stream()
		    .filter(row -> {
		        Object nombreCliente = row.get("NOMBRE CLIENTE");
		        return nombreCliente != null && nombreCliente.toString().trim().equalsIgnoreCase("EMPRESA TABACALERA");
		    })
		    .collect(Collectors.toList());

		long cantidadSP_EmpresaTabacalera = registrosSP_EmpresaTabacalera.size();
		testCase.addCodeEvidenceCurrentStep("Cantidad de registros en SP con 'EMPRESA TABACALERA': " + cantidadSP_EmpresaTabacalera);

		/***************************** PASO 8: Validación de IDs (opcional) ****************************/

		Set<String> idsExcel = registrosEmpresaTabacalera.stream()
		    .map(row -> row.get("ID")) // Asegúrate que 'ID' es el nombre correcto
		    .filter(Objects::nonNull)
		    .collect(Collectors.toSet());

		Set<String> idsSP = registrosSP_EmpresaTabacalera.stream()
		    .map(row -> row.get("ID"))
		    .filter(Objects::nonNull)
		    .map(Object::toString)
		    .collect(Collectors.toSet());

		Set<String> idsNoEncontrados = idsExcel.stream()
		    .filter(id -> !idsSP.contains(id))
		    .collect(Collectors.toSet());

		assertEquals(0, idsNoEncontrados.size(), "Existen IDs de Excel que no están en el SP para EMPRESA TABACALERA.");

	
	}
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_011_ComparacionEntreDataLakeVSFuenteOnPremiseColumnaEMPRESATABACALERA";
	}

	@Override
	public String setTestDescription() {
		return "Información del reporte debe coincidir con los resultados de los queries en las fuentes";
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
		return "1.-Acceso a la BD de Snowflake.";
		
	}

	@Override
	public void beforeTest() {
		
	}
}