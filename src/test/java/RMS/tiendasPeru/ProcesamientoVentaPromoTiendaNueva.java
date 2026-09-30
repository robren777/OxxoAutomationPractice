package RMS.tiendasPeru;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ProcesamientoVentaPromoTiendaNueva extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Procesamiento_Venta_Promo_Tienda_Nueva(HashMap<String, String> data) throws Exception {

		// PASO 1
		addStep("Conectarse a la Base de Datos: BDPRMSQ");
		System.out.println("PASO 1");

		SQLUtil dbPrms = new SQLUtil(GlobalVariables.DB_HOST_BDPRMSQ, GlobalVariables.DB_USER_BDPRMSQ,
				GlobalVariables.DB_PASSWORD_BDPRMSQ);

		testCase.addTextEvidenceCurrentStep("Conexion a BDPRMSQ exitosa.");

		// PASO 2
		addStep("Validar que se cuente con un archivo de venta de alguna promocion.");
		System.out.println("PASO 2");

		String inboundDocs = "SELECT * from WMUSER.RTK_INBOUND_DOCS "
				+ "WHERE CREATED_DATE >= SYSDATE AND STATUS = 'L' ORDER BY CREATED_DATE DESC";
		System.out.println(inboundDocs);

		SQLResult executeQuery = executeQuery(dbPrms, inboundDocs);
		String tienda = "";
		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con STATUS = 'L' en WMUSER.RTK_INBOUND_DOCS");

		// PASO 3
		addStep("Consultar la tabla XXPAA_CONFIGURACION_GENERAL");
		System.out.println("PASO 3");

		String configGeneral = "SELECT PARAMETRO, VALOR FROM XXPAA_CONFIGURACION_GENERAL";
		System.out.println(configGeneral);

		executeQuery = executeQuery(dbPrms, configGeneral);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros en XXPAA_CONFIGURACION_GENERAL");

		// PASO 4
		addStep("Consultar la tabla XXFC_TIENDAS_MIGRADAS_GEOCOM");
		System.out.println("PASO 4");

		String tiendasMigradas = "SELECT * FROM TIENDAS_MIGRADAS_GEOCOM";
		System.out.println(tiendasMigradas);

		executeQuery = executeQuery(dbPrms, tiendasMigradas);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros en TIENDAS_MIGRADAS_GEOCOM");

		// PASO 5
		addStep("Eliminar el registro de la tienda " + tienda + " asociada al archivo del paso 2 "
				+ "de la tabla XXFC_TIENDAS_MIGRADAS_GEOCOM para simular que se trata " + "de una tienda nueva");
		System.out.println("PASO 5");

		System.out.println("Solicitar DELETE a la tienda " + tienda);

		// PASO 6
		addStep("Solicitar a los Usu FC Operadores SITE v�a correo electr�nico "
				+ "<UsuFEMCOMOperadoresSITE@oxxo.com> la ejecucion de la cadena de jobs: " + "JobName: POSUPLD_PERU");
		System.out.println("PASO 6");

		// PASO 7
		addStep("Validar que se cuente con un archivo de venta de alguna promocion");
		System.out.println("PASO 7");

		String inboundDocsE = "SELECT * FROM WMUSER.RTK_INBOUND_DOCS "
				+ "WHERE CREATED_DATE >= SYSDATE ORDER BY CREATED_DATE DESC";
		System.out.println(inboundDocsE);

		executeQuery = executeQuery(dbPrms, inboundDocsE);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con STATUS = 'E' en WMUSER.RTK_INBOUND_DOCS");

		// PASO 8
		addStep("Entrar a la BD RMS y Realizar la Siguiente consulta" + "\"SELECT * FROM FEM_FIF_STG "
				+ "WHERE CR_TIENDA = " + tienda + " AND TRAN_CODE IN(15) AND REFERENCE_3 IS NULL "
				+ "AND REFERENCE_9 IS NULL AND TRAN_DATE >= TRUNC(SYSDATE - 1)\"");
		System.out.println("PASO 8");

		String femFif = "SELECT * FROM FEM_FIF_STG " + "WHERE CR_TIENDA = " + tienda
				+ " AND TRAN_CODE IN(15) AND REFERENCE_3 IS NULL "
				+ "AND REFERENCE_9 IS NULL AND TRAN_DATE >= TRUNC(SYSDATE - 1)";
		System.out.println(femFif);

		executeQuery = executeQuery(dbPrms, femFif);

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con CR_TIENDA = '" + tienda + "' en " + "FEM_FIF_STG");

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
		return "Construido. Validar que los procesos xxfcposupldthreads.ksh posupld y xxfcglstg_col  "
				+ "no tomen en cuenta el numero de promocion de la tabla PROMHEAD de una tienda que "
				+ "no  se encuentre en la tabla XXFC_TIENDAS_MIGRADAS_GEOCOM";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_Procesamiento_Venta_Promo_Tienda_Nueva";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
