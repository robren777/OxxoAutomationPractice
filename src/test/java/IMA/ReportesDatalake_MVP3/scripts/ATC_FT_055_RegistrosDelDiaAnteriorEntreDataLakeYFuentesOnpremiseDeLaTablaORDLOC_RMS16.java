package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3;
import modelo.BaseExecution;
import utils.sql.SQLUtil;

/**
 * Descripcion:
 * Verificar que el sistema genere correctamente el reporte mensual.
 * 
 * Nombre casos de prueba manual que se abarcan:
 * ATC_FT_001_ValidacionDeReportesMensual
 * 
 * Prerequisitos:
  1,-Acceso a Snowflake (DB: DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.ORDLOC_RMS16;); 
  2.-Permisos de lectura en los esquemas correspondientes.
  3.-Acceso a la base de datos RMS (FCDASQAS).
  4.-Acceso a Control-M (para ejecución y monitoreo de procesos).
 * 
 * Insumos:
 * -N/A
 * @author Angel Gabriel Ramos Martinez
 * @date 18/08/2025
 * 
 /*
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId = 210842
		)  */


public class ATC_FT_055_RegistrosDelDiaAnteriorEntreDataLakeYFuentesOnpremiseDeLaTablaORDLOC_RMS16 extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_055_RegistrosDelDiaAnteriorEntreDataLakeYFuentesOnpremiseDeLaTablaORDLOC_RMS16_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Automatización Reportes DataLake MVP3");

        /*****************************************************
         *                      Instancias
         *****************************************************/
        // Paso 1: Conexión a EBS/RMS
        addStep("Paso 1 - Establecer conexión a la base de datos EBS/RMS Tabla ORDLOC");
        SQLUtil dbDES = UtilsReportesDatalakeMVP3.createDESConnection();
        addStep("Paso 1-Passed - Conexión exitosa a EBS/RMS");

        // Paso 2: Ejecutar consulta COUNT en EBS/RMS
        addStep("Paso 2 - Obtener cantidad de registros del día anterior en RMS (JOIN ORDHEAD y ORDLOC)");
        int countEBS = UtilsReportesDatalakeMVP3.obtenerRegistrodiaayrRMS(dbDES, testCase);
        addStep("Paso 2-Passed - Consulta COUNT ejecutada correctamente en RMS");

        // Paso 3: Conexión a Snowflake
        addStep("Paso 3 - Establecer conexión a la base de datos Snowflake");
        Connection connectionSnowflake = UtilsReportesDatalakeMVP3.conexionsnow.createSnowflakeConnection();
        addStep("Paso 3-Passed - Conexión exitosa a Snowflake");

     // Paso 4: Ejecutar consulta COUNT
        addStep("Paso 4 - Obtener cantidad de registros del día anterior en Snowflake (JOIN ORDHEAD_RMS16 y ORDLOC_RMS16)");
        String qryCountSnowflake =
                "SELECT COUNT(*) AS REGISTRO_1_DIA " +
                "FROM DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.ORDHEAD_RMS16 h " +
                "JOIN DBZDEV_MEX_OXXO.LAND_DASOP_TRAN.ORDLOC_RMS16 ol " +
                "  ON ol.ORDER_NO = h.ORDER_NO " +
                "WHERE h.CREATE_DATETIME >= CURRENT_DATE() - INTERVAL '1 DAY'";

        int countSnowflake = 0;
        try (Statement stmt = connectionSnowflake.createStatement();
             ResultSet rs = stmt.executeQuery(qryCountSnowflake)) {

            if (rs.next()) {
                countSnowflake = rs.getInt("REGISTRO_1_DIA"); 
            }
            testCase.addTextEvidenceCurrentStep("Cantidad de registros del dia anterior Snowflake: " + countSnowflake);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al ejecutar consulta en Snowflake: " + e.getMessage());
        } finally {
            if (connectionSnowflake != null) {
                connectionSnowflake.close();
            }
        }


        // Paso 5: Comparación final de conteo
        addStep("Paso 5 - Comparar cantidad de registros dia anterior entre RMS y Snowflake (JOIN ORDHEAD/ORDLOC,");
        String evidenciaFinal = String.format(
                "Resultado de comparación de registros di anterior):\n" +
                "COUNT en RMS: %d\n" +
                "COUNT en Snowflake: %d\n",
                countEBS, countSnowflake);
        testCase.addTextEvidenceCurrentStep(evidenciaFinal);

        assertEquals(countEBS, countSnowflake,
                "La cantidad de registros entre EBS/RMS y Snowflake no coincide.\n" + evidenciaFinal);
        addStep("Paso 5-Passed - La cantidad de registros coincide entre RMS y Snowflake");
    }
	@Override
	public String setTestFullName() {
		return "ATC_FT_055_RegistrosDelDiaAnteriorEntreDataLakeYFuentesOnpremiseDeLaTablaORDLOC_RMS16";
	}

	@Override
	public String setTestDescription() {
		return "Registros Del Dia Anterior Entre DataLake Y FuentesOnPremise De La Tabla ORDLOC_RMS16";
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