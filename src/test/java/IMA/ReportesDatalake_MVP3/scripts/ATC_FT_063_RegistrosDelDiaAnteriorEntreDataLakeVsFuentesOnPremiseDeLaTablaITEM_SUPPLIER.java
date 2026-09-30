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
 * Descripción:
 * Asegurar que los registros del día anterior se hayan replicado correctamente.
 *
 * Casos de prueba manual:
 * ATC_FT_063_RegistrosDelDiaAnteriorEntreDataLakeVsFuentesOnPremiseDeLaTablaITEM_SUPPLIER
 *
 * Prerrequisitos:
 * 	1.- Acceso a Snowflake
 *   Base de datos: DBZDEV_MEX_OXXO.LAND_DASOP_CAT.ITEM_SUPPLIER
 * 	2.-Permisos de lectura en los esquemas correspondientes.
 * 	3.-Acceso a la base de datos RMS (FCDASQAS).
 * 	4.-Acceso a Control-M (para ejecución y monitoreo de procesos).
 *
 * Insumos:
 * -N/A
 * @author Joaquín Aguilar Hernández
 * @date 19/08/2025
 */
public class ATC_FT_063_RegistrosDelDiaAnteriorEntreDataLakeVsFuentesOnPremiseDeLaTablaITEM_SUPPLIER extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true, testName = "")
    public void ATC_FT_063_RegistrosDelDiaAnteriorEntreDataLakeVsFuentesOnPremiseDeLaTablaITEM_SUPPLIER_test(HashMap<String, String> data) throws Exception {

    	/*****************************************************
         *                      Instancias
         *****************************************************/
        // Paso 1: Conexión a EBS/RMS
        addStep("Paso 1 - Establecer conexión a la base de datos EBS/RMS");
        SQLUtil dbDES = UtilsReportesDatalakeMVP3.createDESConnection();
        addStep("Paso 1-Passed - Conexión exitosa a EBS/RMS");

        // Paso 2: Ejecutar consulta COUNT en EBS/RMS
        addStep("Paso 2 - Obtener cantidad de registros del día anterior en RMS Tabla ITEM_SUPPLIER");
        String qryCountDES = "SELECT COUNT(*) AS REGISTROS_AYER " +
                             "FROM RMS.ITEM_SUPPLIER " +
                             "WHERE CREATE_DATETIME >= TRUNC(SYSDATE) - 1 " +
                             "AND CREATE_DATETIME < TRUNC(SYSDATE)";

        int countEBS = 0;
        try (Connection connDES = dbDES.getConn();
             Statement stmtDES = connDES.createStatement();
             ResultSet rsDES = stmtDES.executeQuery(qryCountDES)) {

            if (rsDES.next()) {
                countEBS = rsDES.getInt("REGISTROS_AYER");
            }
            testCase.addTextEvidenceCurrentStep("Cantidad de registros en RMS: " + countEBS);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al ejecutar consulta en RMS: " + e.getMessage());
        }
        addStep("Paso 2-Passed - Consulta COUNT ejecutada correctamente en RMS Tabla ITEM_SUPPLIER");

        // Paso 3: Conexión a Snowflake
        addStep("Paso 3 - Establecer conexión a la base de datos Snowflake");
        Connection connectionSnowflake = UtilsReportesDatalakeMVP3.conexionsnow.createSnowflakeConnection();
        addStep("Paso 3-Passed - Conexión exitosa a Snowflake");

        // Paso 4: Ejecutar consulta COUNT en Snowflake
        addStep("Paso 4 - Obtener cantidad de registros del día anterior en Snowflake Tabla ITEM_SUPPLIER");
        String qryCountSnowflake = "SELECT COUNT(*) AS REGISTROS_AYER " +
                                   "FROM DBZDEV_MEX_OXXO.LAND_DASOP_CAT.ITEM_SUPPLIER " +
                                   "WHERE CREATE_DATETIME >= DATEADD(DAY, -1, CURRENT_DATE) " +
                                   "AND CREATE_DATETIME < CURRENT_DATE";

        int countSnowflake = 0;
        try (Statement stmt = connectionSnowflake.createStatement();
             ResultSet rs = stmt.executeQuery(qryCountSnowflake)) {

            if (rs.next()) {
                countSnowflake = rs.getInt("REGISTROS_AYER");
            }
            testCase.addTextEvidenceCurrentStep("Cantidad de registros en Snowflake: " + countSnowflake);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al ejecutar consulta en Snowflake: " + e.getMessage());
        } finally {
            if (connectionSnowflake != null) {
                connectionSnowflake.close();
            }
        }

        // Paso 5: Comparación final de conteo
        addStep("Paso 5 - Comparar cantidad de registros entre EBS/RMS y Snowflake Tabla ITEM_SUPPLIER");
        String evidenciaFinal = String.format(
                "Resultado de comparación de registros del día anterior:\n" +
                "COUNT en EBS/RMS: %d\n" +
                "COUNT en Snowflake: %d\n", countEBS, countSnowflake);
        testCase.addTextEvidenceCurrentStep(evidenciaFinal);

        assertEquals(countEBS, countSnowflake,
                "La cantidad de registros entre EBS/RMS y Snowflake no coincide.\n" + evidenciaFinal);
        addStep("Paso 5-Passed - La cantidad de registros coincide entre EBS/RMS y Snowflake Tabla ITEM_SUPPLIER");
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_063_RegistrosDelDiaAnteriorEntreDataLakeVsFuentesOnPremiseDeLaTablaITEM_SUPPLIER";
    }

    @Override
    public String setTestDescription() {
        return "Asegurar que los registros del día anterior se hayan replicado correctamente.";
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
				+ "Base de datos: DBZDEV_MEX_OXXO.LAND_DASOP_CAT.ITEM_SUPPLIER"
				+ "2.-Permisos de lectura en los esquemas correspondientes."
				+ "3.-Acceso a la base de datos RMS (FCDASQAS)"
				+ "4.-Acceso a Control-M (para ejecución y monitoreo de procesos).";
    }

    @Override
    public void beforeTest() {
        
    }
}
