package WM.apigalaxy.renta;

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
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-155-Validacion de
 * estructura de tabla rentas
 *
 * PREREQUISITOS: - Acceso a snowflake - Acceso a la db
 *
 * INSUMOS: - Querie:SELECT COLUMN_NAME, DATA_TYPE, ORDINAL_POSITION,
 * IS_NULLABLE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE,
 * DATETIME_PRECISION FROM DBZDEV_MEX_OXXO.INFORMATION_SCHEMA.COLUMNS WHERE
 * TABLE_NAME = 'OUT_260_MKT_CONVENIOS' AND TABLE_SCHEMA =
 * 'CONS_MKT_PROMOCIONES' ORDER BY ORDINAL_POSITION;
 * 
 * @author Guillermo Garcia
 * @date 29/05/2026
 */
public class ATC_FT_155_ValidarEstructuraDeTablaRentas extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_155_ValidarEstructuraDeTablaRentas_test(HashMap<String, String> data) throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String querie = "SELECT COLUMN_NAME, DATA_TYPE, ORDINAL_POSITION, IS_NULLABLE, CHARACTER_MAXIMUM_LENGTH, "
		+ "NUMERIC_PRECISION, NUMERIC_SCALE, DATETIME_PRECISION "
		+ "FROM DBZDEV_MEX_OXXO.INFORMATION_SCHEMA.COLUMNS "
		+ "WHERE TABLE_NAME = 'OUT_260_PROMOTIONAL_RENTAL' " + "  AND TABLE_SCHEMA = 'LAND_MARKETPLACE_TRAN' "
		+ "ORDER BY ORDINAL_POSITION;";
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
	 * PASO 3 Validar que el response tenga el valor RENT_FOLIO
	 ***************************************/
	addStep("Validar que el response tenga el valor RENT_FOLIO",
		"Debe existir y su tipo de dato debe ser number(38,0)");
	util.validateColumnAndDataType(responseQuerie, "RENT_FOLIO", "NUMBER", 38, 0);

	/***************************************
	 * PASO 4 Validar que el response tenga el valor YEAR
	 ***************************************/
	addStep("Validar que el response tenga el valor YEAR", "Debe existir y su tipo de dato debe ser NUMBER(4,0)");
	util.validateColumnAndDataType(responseQuerie, "YEAR", "NUMBER", 4, 0);

	/***************************************
	 * PASO 5 Validar que el response tenga el valor PERIOD_NAME
	 ***************************************/
	addStep("Validar que el response tenga el valor PERIOD_NAME",
		"Debe existir y su tipo de dato debe ser  VARCHAR(10)");
	util.validateColumnAndDataType(responseQuerie, "PERIOD_NAME", "TEXT", 10);

	/***************************************
	 * PASO 6 Validar que el response tenga el valor PROVIDER_NAME
	 ***************************************/
	addStep("Validar que el response tenga el valor PROVIDER_NAME",
		"Debe existir y su tipo de dato debe ser VARCHAR(255)");
	util.validateColumnAndDataType(responseQuerie, "PROVIDER_NAME", "TEXT", 255);

	/***************************************
	 * PASO 7 Validar que el response tenga el valor PROVIDER_ID
	 ***************************************/
	addStep("Validar que el response tenga el valor PROVIDER_ID",
		"Debe existir y su tipo de dato debe ser VARCHAR(50)");
	util.validateColumnAndDataType(responseQuerie, "PROVIDER_ID", "TEXT", 50);

	/***************************************
	 * PASO 8 Validar que el response tenga el valor EMAIL_CATEGORY
	 ***************************************/
	addStep("Validar que el response tenga el valor EMAIL_CATEGORY",
		"Debe existir y su tipo de dato debe ser VARCHAR(512)");
	util.validateColumnAndDataType(responseQuerie, "EMAIL_CATEGORY", "TEXT", 512);

	/***************************************
	 * PASO 9 Validar que el response tenga el valor COVERAGE
	 ***************************************/
	addStep("Validar que el response tenga el valor COVERAGE", "Debe existir y su tipo de dato debe ser VARIANT");
	util.validateColumnAndDataType(responseQuerie, "COVERAGE", "VARIANT");

	/***************************************
	 * PASO 10 Validar que el response tenga el valor ITEMS
	 ***************************************/
	addStep("Validar que el response tenga el valor ITEMS", "Debe existir y su tipo de dato debe ser VARIANT");
	util.validateColumnAndDataType(responseQuerie, "ITEMS", "VARIANT");

	/***************************************
	 * PASO 11 Validar que el response tenga el valor LOAD_TIMESTAMP
	 ***************************************/
	addStep("Validar que el response tenga el valor LOAD_TIMESTAMP",
		"Debe existir y su tipo de dato debe ser  TIMESTAMP_NTZ(9)");
	util.validateColumnAndDataType(responseQuerie, "LOAD_TIMESTAMP", "TIMESTAMP_NTZ", 9);

	/***************************************
	 * PASO 12 Validar que el response tenga el valor RENT_FOLIO_STATUS
	 ***************************************/
	addStep("Validar que el response tenga el valor RENT_FOLIO_STATUS",
		"Debe existir y su tipo de dato debe ser VARCHAR(10)");
	util.validateColumnAndDataType(responseQuerie, "RENT_FOLIO_STATUS", "TEXT", 10);

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
	return "ATC_FT_155_ValidarEstructuraDeTablaRentas";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
