package RMS.tiendasPeru;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ProcedimientoAlmacenadoPREPRMEXTTiendaExistente extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_Procedimiento_Almacenado_PREPRMEXT_Tienda_Existente(HashMap<String, String> data)
			throws Exception {

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
		String tienda = "";

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros en TIENDAS_MIGRADAS_GEOCOM");

		// PASO 4
		addStep("Solicitar a los Usu FC Operadores SITE v�a correo electr�nico <UsuFEMCOMOperadoresSITE@oxxo.com> "
				+ "la ejecucion de la cadena de jobs");
		System.out.println("PASO 4");

		// PASO 5
		addStep("Revisar que no se inserto informacion correspondiente a la tienda " + tienda + " en "
				+ "la tabla xxfc_promo_prmext_t");
		System.out.println("PASO 5");

		String prmextTienda = String.format("SELECT * FROM XXFC_PROMO_PRMEXT_T WHERE STORE = %s", tienda);
		System.out.println(prmextTienda);

		executeQuery = executeQuery(dbPrms, prmextTienda);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con STORE = " + tienda + " en FROM XXFC_PROMO_PRMEXT_T");

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
		return "Construido. Se validar� que no se inserte en la tabla temporal de promociones "
				+ "xxfc_promo_prmext_t  si la tienda se encuentre en la tabla " + "XXFC_TIENDAS_MIGRADAS_GEOCOM"
				+ "MTC-FT-026";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_Procedimiento_Almacenado_PREPRMEXT_Tienda_Existente";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
