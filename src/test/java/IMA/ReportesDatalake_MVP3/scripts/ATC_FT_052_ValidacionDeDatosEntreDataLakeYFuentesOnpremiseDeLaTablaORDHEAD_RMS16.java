package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * Descripcion:
 * Datos replicados disponibles; reglas de negocio y catálogos.
 * 
 * Nombre casos de prueba manual que se abarcan:
 * MTC_FT_052_ValidacionDeDatosEntreDataLakeYFuentesOnpremiseDeLaTablaORDHEAD_RMS16
 * 
  * Prerequisitos:
 *1.-Acceso a Snowflake
    Base de datos: DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.ORDHEAD_RMS16
  2.-Permisos de lectura en los esquemas correspondientes.
  3.-Acceso a la base de datos RMS (FCDASQAS).
  4.-Acceso a Control-M (para ejecución y monitoreo de procesos).
 * 
 * Insumos:
 * -N/A
 * @author Angel Gabriel Ramos Martinez
 * @date 03/10/2025
 * 
 /*
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId = 210842
		) */
public class ATC_FT_052_ValidacionDeDatosEntreDataLakeYFuentesOnpremiseDeLaTablaORDHEAD_RMS16 extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_052_ValidacionDeDatosEntreDataLakeYFuentesOnpremiseDeLaTablaORDHEAD_RMS16_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Automatización Reportes DataLake MVP3");

		/**************************************************
		                      Instancias
		 **************************************************/
		UtilsReportesDatalakeMVP3 util = new UtilsReportesDatalakeMVP3();

		// Paso 1: Establecer conexión a la base de datos EBS/RMS
		addStep("Establecer conexión a la base de datos EBS/RMS");
		SQLUtil dbDES = UtilsReportesDatalakeMVP3.createDESConnection();

		// Paso 2: Ejecutar consulta SQL en EBS/RMS
		addStep("Ejecutar consulta SQL en EBS/RMS para obtener datos de ORDHEAD_RMS16");
		String queryEBSRMS ="SELECT COUNT(*) AS TOTAL " +
			    "FROM RMS.ORDHEAD " +
			    "WHERE LAST_UPDATE_DATETIME >= TRUNC(SYSDATE) - 90 " +
			    "AND LAST_UPDATE_DATETIME < TRUNC(SYSDATE)";
		
		SQLResult resultEBSRMS = dbDES.executeQuery(queryEBSRMS);
		List<String[]> dataEBSRMS = resultEBSRMS.getAllData();
		testCase.addQueryEvidenceCurrentStep(resultEBSRMS);
		assertNotNull(dataEBSRMS, "Los resultados de la consulta no se almacenaron correctamente en EBS/RMS.");

		// Paso 3: Guardar el valor de COUNT de EBS para comparación
		addStep("Comparar tipos de datos y valores de EBS/RMS con fuentes on-premise");
		int countEBS = Integer.parseInt(dataEBSRMS.get(0)[0]);

		// Paso 4: Establecer conexión a la base de datos Snowflake
		addStep("Establecer conexión a la base de datos Snowflake");
		Connection connectionSnowflake = UtilsReportesDatalakeMVP3.conexionsnow.createSnowflakeConnection();

		// Paso 5: Ejecutar consulta SQL en Snowflake
		addStep("Ejecutar consulta SQL en Snowflake para obtener datos de DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.ORDHEAD_RMS16");
		String querySnowflake ="SELECT COUNT(*) AS TOTAL " +
			    "FROM DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.ORDHEAD_RMS16 " +
			    "WHERE LAST_UPDATE_DATETIME >= DATEADD(DAY, -90, CURRENT_DATE) " +
			    "AND LAST_UPDATE_DATETIME < CURRENT_DATE";
		int countSnowflake = 0;
		
		try (Statement stmt = connectionSnowflake.createStatement();
				ResultSet rs = stmt.executeQuery(querySnowflake);) {
			if (rs.next()) {
				countSnowflake = rs.getInt(1);
			}
			testCase.addTextEvidenceCurrentStep(
		            String.format("Cantidad de registros en Snowflake: %d", countSnowflake));

		} catch (SQLException e) {
			e.printStackTrace();
			fail("Error al ejecutar consulta en Snowflake: " + e.getMessage());
		} finally {
			if (connectionSnowflake != null) {
				try {
					connectionSnowflake.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// Paso 6: Comparación final con evidencia consolidada
		addStep("Comparar cantidades de registros entre EBS/RMS y Snowflake");
		String evidenciaFinal = String.format(
				"Resultado de comparación de registros:\n" + "COUNT en EBS/RMS: %d\n" + "COUNT en Snowflake: %d\n",
				countEBS, countSnowflake);
		testCase.addTextEvidenceCurrentStep(evidenciaFinal);
		
		// Falla si no coinciden
		assertEquals(countEBS, countSnowflake,
		        "La cantidad de registros entre EBS/RMS y Snowflake no coincide.\n" + evidenciaFinal);

		addStep("Paso 6-Passed - La cantidad de registros coincide entre EBS/RMS y Snowflake");
		
	}
	@Override
	public String setTestFullName() {
		return "ATC_FT_052_ValidacionDeDatosEntreDataLakeYFuentesOnpremiseDeLaTablaORDHEAD_RMS16";
	}

	@Override
	public String setTestDescription() {
		return "Datos replicados disponibles; reglas de negocio y catálogos.";
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
		return "1.-Acceso a Snowflake"
				+ "Base de datos: DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.ORDHEAD_RMS16" 
				+ "2.-Permisos de lectura en los esquemas correspondientes."
				+ "3.-Acceso a la base de datos RMS (FCDASQAS)"
				+ "4.-Acceso a Control-M (para ejecución y monitoreo de procesos).";
		
	}

	@Override
	public void beforeTest() {
		
	}
}