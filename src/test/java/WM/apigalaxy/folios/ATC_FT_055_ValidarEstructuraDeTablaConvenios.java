package WM.apigalaxy.folios;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida la estructura de la tabla, tipo de dato y nombre de
 * columnas.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-055-Validar estructura
 * de tabla CONVENIOS
 *
 * PREREQUISITOS: - Acceso a snowflake - Acceso a la db
 *
 * INSUMOS: - Querie:SELECT COLUMN_NAME, DATA_TYPE, ORDINAL_POSITION,
 * IS_NULLABLE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE,
 * DATETIME_PRECISION FROM DBZDEV_MEX_OXXO.INFORMATION_SCHEMA.COLUMNS WHERE
 * TABLE_NAME = 'OUT_260_MKT_CONVENIOS' AND TABLE_SCHEMA =
 * 'CONS_MKT_PROMOCIONES' ORDER BY ORDINAL_POSITION;
 * 
 * @author Guillermo garcia
 * @date 29/05/2026
 */
public class ATC_FT_055_ValidarEstructuraDeTablaConvenios extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_055_ValidarEstructuraDeTablaConvenios_test(HashMap<String, String> data) throws Exception {

	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String querie = "SELECT COLUMN_NAME, DATA_TYPE, ORDINAL_POSITION, IS_NULLABLE, CHARACTER_MAXIMUM_LENGTH, "
		+ "NUMERIC_PRECISION, NUMERIC_SCALE, DATETIME_PRECISION "
		+ "FROM DBZDEV_MEX_OXXO.INFORMATION_SCHEMA.COLUMNS " + "WHERE TABLE_NAME = 'OUT_260_MKT_CONVENIOS' "
		+ "  AND TABLE_SCHEMA = 'CONS_MKT_PROMOCIONES' " + "ORDER BY ORDINAL_POSITION;";
	/***************************************
	 * PASO 1 Hacer conexion a Snowflake
	 ***************************************/
	addStep("Hacer conexion a Snowflake", "Se debe poder hacer conexion");
	testCase.addCodeEvidenceCurrentStep("Se hace conexion a snowflake");

	/***************************************
	 * PASO 2 Ejecutar el querie y almacenar los datos
	 ***************************************/
	addStep("Ejecutar el querie y almacenar los datos", "Debe poder ser ejecutado");
	List<Map<String, Object>> responseQuerie = util.executeQuerySnowflake(querie);
	testCase.addCodeEvidenceCurrentStep("El querie que regreso esta vacio = " + responseQuerie.isEmpty());

	/***************************************
	 * PASO 3 Validar que el response tenga el valor ID_MARKETPLACE
	 ***************************************/
	addStep("Validar que el response tenga el valor ID_MARKETPLACE",
		"Debe existir y su tipo de dato debe ser number");
	assertTrue(util.validateColumnAndDataType(responseQuerie, "ID_MARKETPLACE", "NUMBER"));

	/***************************************
	 * PASO 4 Validar que el response tenga el valor ACCION_CONVENIO
	 ***************************************/
	addStep("Validar que el response tenga el valor ACCION_CONVENIO",
		"Debe existir y su tipo de dato debe ser TEXT(1)");
	assertTrue(util.validateColumnAndDataType(responseQuerie, "ACCION_CONVENIO", "TEXT", 1));

	/***************************************
	 * PASO 5 Validar que el response tenga el valor INFO_CONVENIOS
	 ***************************************/
	addStep("Validar que el response tenga el valor INFO_CONVENIOS",
		"Debe existir y su tipo de dato debe ser VARIANT");
	assertTrue(util.validateColumnAndDataType(responseQuerie, "INFO_CONVENIOS", "VARIANT"));

	/***************************************
	 * PASO 6 Validar que el response tenga el valor ESTADO_CONVENIO
	 ***************************************/
	addStep("Validar que el response tenga el valor ESTADO_CONVENIO",
		"Debe existir y su tipo de dato debe ser TEXT(100)");
	assertTrue(util.validateColumnAndDataType(responseQuerie, "ESTADO_CONVENIO", "TEXT", 100));

	/***************************************
	 * PASO 7 Validar que el response tenga el valor FOLIO_EBS
	 ***************************************/
	addStep("Validar que el response tenga el valor FOLIO_EBS",
		"Debe existir y su tipo de dato debe ser TEXT(100)");
	assertTrue(util.validateColumnAndDataType(responseQuerie, "FOLIO_EBS", "TEXT", 100));

	/***************************************
	 * PASO 8 Validar que el response tenga el valor FECHA_CARGA
	 ***************************************/
	addStep("Validar que el response tenga el valor FECHA_CARGA",
		"Debe existir y su tipo de dato debe ser TIMESTAMP_NTZ");
	assertTrue(util.validateColumnAndDataType(responseQuerie, "FECHA_CARGA", "TIMESTAMP_NTZ"));

	/***************************************
	 * PASO 9 Validar que el response tenga el valor ID_SOLICITUD_BAJA_MKT
	 ***************************************/
	addStep("Validar que el response tenga el valor ID_SOLICITUD_BAJA_MKT",
		"Debe existir y su tipo de dato debe ser NUMBER");
	assertTrue(util.validateColumnAndDataType(responseQuerie, "ID_SOLICITUD_BAJA_MKT", "NUMBER"));

	/***************************************
	 * PASO 10 Validar que el response tenga el valor NOMBRE_CONVENIO
	 ***************************************/
	addStep("Validar que el response tenga el valor NOMBRE_CONVENIO",
		"Debe existir y su tipo de dato debe ser TEXT(150)");
	assertTrue(util.validateColumnAndDataType(responseQuerie, "NOMBRE_CONVENIO", "TEXT", 255));
	testCase.addCodeEvidenceCurrentStep(
		"Esto se detecta como descubrimiento pero se reporta con EBS no afecta al desarrollo actual");

	/***************************************
	 * PASO 11 Validar que el response tenga el valor ERRORES
	 ***************************************/
	addStep("Validar que el response tenga el valor ERRORES", "Debe existir y su tipo de dato debe ser TEXT(255)");
	assertTrue(util.validateColumnAndDataType(responseQuerie, "ERRORES", "TEXT", 255));

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a snowflake\\nAcceso a la db";
    }

    @Override
    public String setTestDescription() {
	return "Se valida la estructura de la tabla, tipo de dato y nombre de columnas.";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_055_ValidarEstructuraDeTablaConvenios";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
