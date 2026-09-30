package RMS.tiendasPeru;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class GenerarFolioPromoYTiendaNueva extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Generar_Folio_Promo_Y_Tienda_Nueva(HashMap<String, String> data) throws Exception {

		// PASO 1
		addStep("Conectarse a la Base de Datos: BDPRMSQ");
		System.out.println("PASO 1");

		SQLUtil dbPrms = new SQLUtil(GlobalVariables.DB_HOST_BDPRMSQ, GlobalVariables.DB_USER_BDPRMSQ,
				GlobalVariables.DB_PASSWORD_BDPRMSQ);

		testCase.addTextEvidenceCurrentStep("Conexion a BDPRMSQ exitosa.");

		// PASO 2
		addStep("Consultar la tabla XXPAA_CONFIGURACION_GENERAL");
		System.out.println("PASO 2");

		String configGeneral = "SELECT PARAMETRO, VALOR FROM XXPAA_CONFIGURACION_GENERAL";
		System.out.println(configGeneral);

		SQLResult executeQuery = executeQuery(dbPrms, configGeneral);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros en XXPAA_CONFIGURACION_GENERAL");

		// PASO 3
		addStep("Consultar la tabla XXFC_TIENDAS_MIGRADAS_GEOCOM");
		System.out.println("PASO 3");

		String tiendasMigradas = "SELECT * FROM TIENDAS_MIGRADAS_GEOCOM";
		System.out.println(tiendasMigradas);

		executeQuery = executeQuery(dbPrms, tiendasMigradas);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros en TIENDAS_MIGRADAS_GEOCOM");

		// PASO 4
		addStep("Eliminar el registro de una tienda de la tabla XXFC_TIENDAS_MIGRADAS_GEOCOM"
				+ " para simular que se trata de una tienda nueva");
		System.out.println("PASO 5");

		System.out.println("Solicitar DELETE a una tienda");

		String tienda = "";

		// PASO 5
		addStep("Ingresar al aplicativo de RMS de PERU ingresando las credenciales");
		System.out.println("PASO 5");

		// PASO 6
		addStep("Crear el Evento, irse a la opci�n Pricing, posteriormente \"Promotion Events\"  > Edit.");
		System.out.println("PASO 6");

		// PASO 7
		addStep("Ingresar los datos del evento a crear y se oprime bot�n OK para crear el evento.");
		System.out.println("PASO 7");

		// PASO 8
		addStep("Para crear la Promoci�n dirigirse a la opci�n Pricing del men� Retek > opci�n \"Promotions\" > New ");
		System.out.println("PASO 8");

		// PASO 9
		addStep("Presionar OK para habilitar el formulario, ingresar los datos de la promoci�n");
		System.out.println("PASO 9");

		// PASO 10
		addStep("Posteriormente Dar clic al bot�n \"Store\" y agregar la tienda " + tienda
				+ " eliminada en el paso 4, presionar el bot�n Apply para aplicar el cambio y" + " presionar OK.");
		System.out.println("PASO 10");

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Construido. Validar  que no se pueda generar un folio de promocion en RMS "
				+ "con una tienda que no se encuentre en la tabla XXFC_TIENDAS_MIGRADAS_GEOCOM.";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Generar_Folio_Promo_Y_Tienda_Nueva";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
