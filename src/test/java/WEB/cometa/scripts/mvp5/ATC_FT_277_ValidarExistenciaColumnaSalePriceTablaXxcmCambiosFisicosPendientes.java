package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.CometaBdValidationHelper;
import modelo.BaseExecution;

/**DESCRIPCION: Validar que la columna sale_price fue agregada correctamente en la tabla
 * xxcm_cambios_fisicos_pendientes como parte del requerimiento de la HU 308163,
 * habilitando el mapeo del precio de articulo desde Movilidad.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_277 [CA03] - Validar existencia de columna sale_price en tabla xxcm_cambios_fisicos_pendientes
 *
 * PREREQUISITOS:
 * 1. Acceso a PostgreSQL cometa con permisos de lectura en information_schema.
 * 2. Credenciales BD configuradas en GlobalVariables.
 *
 * INSUMOS:
 * Credenciales BD cometa configuradas en GlobalVariables (BD_URL, BD_USER, BD_PASS).
 *
 * @author Michelle Ceja
 * @date 2026-08-04
 */
public class ATC_FT_277_ValidarExistenciaColumnaSalePriceTablaXxcmCambiosFisicosPendientes extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_277_ValidarExistenciaColumnaSalePriceTablaXxcmCambiosFisicosPendientes_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		CometaBdValidationHelper bdHelper = new CometaBdValidationHelper(testCase);
		String tabla = "xxcm_cambios_fisicos_pendientes";
		String columna = "sale_price";

		/* PASO 1 *******************************************************************/
		addStep("Conectarse al ambiente PostgreSQL cometa correspondiente a la validacion");
		boolean resultStep1 = bdHelper.validarConexionBD();
		assertTrue(resultStep1,
				"CA03 fallido - Paso 1: No se pudo establecer la conexion a la base de datos PostgreSQL cometa; "
						+ "verificar credenciales en GlobalVariables");
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado validado: La conexion a la base se establece correctamente.");

		/* PASO 2 *******************************************************************/
		addStep("Ejecutar SQL: SELECT column_name, data_type FROM information_schema.columns "
				+ "WHERE table_name = 'xxcm_cambios_fisicos_pendientes' AND column_name = 'sale_price'");
		boolean resultStep2 = bdHelper.validarColumnaEnTabla(tabla, columna);
		assertTrue(resultStep2,
				"CA03 fallido - Paso 2: La consulta no retorna la columna sale_price; "
						+ "la columna no existe en la tabla " + tabla);
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado validado: La consulta retorna la columna sale_price confirmando "
						+ "su existencia en la tabla xxcm_cambios_fisicos_pendientes.");

		/* PASO 3 *******************************************************************/
		addStep("Validar que el tipo de dato de la columna sale_price es compatible con valores monetarios "
				+ "(numeric, decimal o tipo similar)");
		boolean resultStep3 = bdHelper.validarTipoDatoNumerico(tabla, columna);
		assertTrue(resultStep3,
				"CA03 fallido - Paso 3: El tipo de dato de la columna sale_price no es numerico "
						+ "ni compatible con valores monetarios en pesos mexicanos");
		testCase.addTextEvidenceCurrentStep(
				"Resultado esperado validado: El tipo de dato de sale_price es numerico y compatible "
						+ "con valores monetarios en pesos mexicanos.");
	}

	@AfterMethod
	public void afterMethod() {
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "*1. Acceso a PostgreSQL cometa con permisos de lectura en information_schema.      \r\n"
				+ "*2. Credenciales BD configuradas en GlobalVariables (BD_URL, BD_USER, BD_PASS).";
	}

	@Override
	public String setTestDescription() {
		return "Validar que la columna sale_price fue agregada correctamente en la tabla "
				+ "xxcm_cambios_fisicos_pendientes conforme al requerimiento de la HU 308163.";
	}

	@Override
	public String setTestDesigner() {
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_277_ValidarExistenciaColumnaSalePriceTablaXxcmCambiosFisicosPendientes_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
