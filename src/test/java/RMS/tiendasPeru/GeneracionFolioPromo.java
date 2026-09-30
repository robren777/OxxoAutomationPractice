package RMS.tiendasPeru;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class GeneracionFolioPromo extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_Procesamiento_Venta_Promo_Tienda_Sin_Categorizar(HashMap<String, String> data)
			throws Exception {

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

		String tiendasMigradas = String.format("SELECT * FROM TIENDAS_MIGRADAS_GEOCOM" + " WHERE STORE = %s", tienda);
		System.out.println(tiendasMigradas);

		executeQuery = executeQuery(dbPrms, tiendasMigradas);

		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "Se encontraron registros con STORE = " + tienda + " en TIENDAS_MIGRADAS_GEOCOM");

		// PASO 5
		addStep("Solicitar a los Usu FC Operadores SITE v�a correo electr�nico "
				+ "<UsuFEMCOMOperadoresSITE@oxxo.com> la ejecucion de la cadena de jobs");
		System.out.println("PASO 5");

		// PASO 6
		addStep("Validar que no cambio a status E el  archivo de venta de  promocion");
		System.out.println("PASO 6");

		String inboundDocsE = String.format("SELECT * FROM WMUSER.RTK_INBOUND_DOCS "
				+ "WHERE CREATED_DATE >= SYSDATE AND STORE = %s ORDER BY CREATED_DATE DESC", tienda);
		System.out.println(inboundDocsE);

		executeQuery = executeQuery(dbPrms, inboundDocsE);
		String status = "";

		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
			status = executeQuery.getData(0, "STATUS");

		}

		assertFalse(statusQuery, "No se encontraron registros en WMUSER.RTK_INBOUND_DOCS");
		assertNotEquals(status, "E", "Se encontraron registros con STATUS = 'E' en WMUSER.RTK_INBOUND_DOCS");

		// PASO 7
		addStep("Insertar registro en la tabla de la tran_data utilizando promociones y la tienda " + tienda);
		System.out.println("PASO 7");

		System.out.println("SOLICITAR INSERT");

		// PASO 8
		addStep("Solicitar a los Usu FC Operadores SITE v�a correo electr�nico <UsuFEMCOMOperadoresSITE@oxxo.com> la ejecucion de la cadena de jobs\r\n"
				+ "JobName: STOCK_LEDGER_Peru");
		System.out.println("PASO 8");

		// PASO 9
		addStep("Entrar a la BD RMS y Realizar la Siguiente consulta" + "\"SELECT * FROM FEM_FIF_STG "
				+ "WHERE CR_TIENDA = " + tienda + " AND TRAN_CODE IN(15) AND REFERENCE_3 IS NULL "
				+ "AND REFERENCE_9 IS NULL AND TRAN_DATE >= TRUNC(SYSDATE - 1)\"");
		System.out.println("PASO 9");

		String femFif = "SELECT * FROM FEM_FIF_STG " + "WHERE CR_TIENDA = " + tienda
				+ " AND TRAN_CODE IN(15) AND REFERENCE_3 IS NULL "
				+ "AND REFERENCE_9 IS NULL AND TRAN_DATE >= TRUNC(SYSDATE - 1)";
		System.out.println(femFif);

		executeQuery = executeQuery(dbPrms, femFif);

		statusQuery = executeQuery.isEmpty();
		if (statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(statusQuery, "Se encontraron registros con CR_TIENDA = '" + tienda + "' en " + "FEM_FIF_STG");

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
		return "Construido. Validar que los procesos xxfcposupldthreads.ksh posupld y "
				+ "xxfcglstg_Per tomen en cuenta el numero de promocion de la tabla "
				+ "PROMHEAD de una tienda que no se encuentre en la tabla XXFC_TIENDAS_MIGRADAS_GEOCOM";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_Procesamiento_Venta_Promo_Tienda_Sin_Categorizar";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
