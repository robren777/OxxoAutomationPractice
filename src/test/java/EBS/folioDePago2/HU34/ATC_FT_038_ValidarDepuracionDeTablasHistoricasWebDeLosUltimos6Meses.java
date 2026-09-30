package EBS.folioDePago2.HU34;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/**
 * PRERREQUISITOS: haber ejecutado la interface RO3_MX tener ordenes con
 * devoluciones y cargos logisticos
 *
 * DESCRIPCION: Validar la depuracion de las tablas:
 * 
 * XXCAFP.XXCAFP_ARCHIVO_HISTORICO XXCAFP.XXCAFP_ARCHIVO_FOLIO_PAGO_2
 * 
 * historicas contengan registros solo de los ultimos 6 meses
 * 
 * 
 * @author jesusm3@hexaware.com
 */
public class ATC_FT_038_ValidarDepuracionDeTablasHistoricasWebDeLosUltimos6Meses extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_038_ValidarDepuracionDeTablasHistoricasWebDeLosUltimos6Meses_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */

		// vars CONTROL-M
		String controlMUser = data.get("user");
		String controlMPass = data.get("ps");
		String controlMServer = data.get("server");
		int maxIntentos = 5;
		int intentos = 0;

		// BASES DE DATOS
		String tabla1 = "select count(*) as total from XXCAFP.xxcafp_archivo_historico WHERE fecha_subida < ADD_MONTHS(SYSDATE, -6)";
		String tabla2 = "select count(*) as total from XXCAFP.xxcafp_archivo_folio_pago_2 WHERE fecha_enviado < ADD_MONTHS(SYSDATE, -6)";

		utils.sql.SQLUtil AVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IAS, GlobalVariables.DB_USER_IAS,
				GlobalVariables.DB_PASSWORD_IAS);

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a Control M: (https://172.26.177.68:8446/ControlM/) Ingresar Usuario Ingresar Contrasena");

		JSONObject obj = new JSONObject(data.get("jobs"));
		Control_mInicio cm = new Control_mInicio(u, controlMUser, controlMPass);
		u.get(controlMServer);
		u.waitForLoadPage();
		cm.logOn();
		testCase.addScreenShotCurrentStep("paso 1");
		u.hardWait(4);

		// testCase.addBoldTextEvidenceCurrentStep("Consulta para verificar registros
		// mayores a 6 meses de antiguedad");

		// Validar la primera tabla
		System.out.println(tabla1);
		SQLResult validat0 = AVANTE.executeQuery(tabla1);
		// testCase.addQueryEvidenceCurrentStep(validat0);

		// PASO 2
		addStep("Ejecutar Job de depuracion WEB");
		ControlM control = new ControlM(u, testCase, obj);
		control.searchJob(); // busca el job
		String resultadoEjecucion = control.executeJob();
		u.hardWait(10);
		
		resultadoEjecucion = control.executeJob();
		u.hardWait(15);
		assertTrue(resultadoEjecucion.equals("Ended OK") || resultadoEjecucion.equals("Wait Condition"),
				"El job XXFC_AP_DEPURA_CARGA_FP no se ejecuto correctamente");

		// Paso 3
		addStep("Revisar las tablas web XXCAFP.XXCAFP_ARCHIVO_HISTORICO y XXCAFP.XXCAFP_ARCHIVO_FOLIO_PAGO_2, "
				+ "validando que el campo FECHA_ENVIADO y FECHA_SUBIDA no contengan "
				+ "registros mayores a 6 meses de antig�edad");

		// Validar la primera tabla
		testCase.addBoldTextEvidenceCurrentStep("Tabla: XXCAFP.XXCAFP_ARCHIVO_HISTORICO");

		System.out.println(tabla1);
		SQLResult validat01 = AVANTE.executeQuery(tabla1);
		testCase.addQueryEvidenceCurrentStep(validat01);

		// Obtener el valor del COUNT(*) directamente
		int countTabla1 = Integer.parseInt(validat01.getData(0, 0));
		if (countTabla1 == 0) {
			testCase.addTextEvidenceCurrentStep(
					"Validaci�n Exitosa: La tabla XXCAFP.XXCAFP_ARCHIVO_HISTORICO no contiene registros mayores a 6 meses.");
		} else {
			testCase.addTextEvidenceCurrentStep("Error: La tabla XXCAFP.XXCAFP_ARCHIVO_HISTORICO contiene "
					+ countTabla1 + " registros mayores a 6 meses.");
			assert countTabla1 == 0 : "La tabla contiene " + countTabla1 + " registros. Validaci�n fallida.";
		}

		u.hardWait(5);

		// Validar la segunda tabla
		testCase.addBoldTextEvidenceCurrentStep("Tabla: XXCAFP.XXCAFP_ARCHIVO_FOLIO_PAGO_2");

		System.out.println(tabla2);
		validat01 = AVANTE.executeQuery(tabla2);
		testCase.addQueryEvidenceCurrentStep(validat01);

		// Obtener el valor del COUNT(*) directamente
		int countTabla2 = Integer.parseInt(validat01.getData(0, 0));
		if (countTabla2 == 0) {
			testCase.addTextEvidenceCurrentStep(
					"Validaci�n Exitosa: La tabla XXCAFP.XXCAFP_ARCHIVO_FOLIO_PAGO_2 no contiene registros mayores a 6 meses.");
		} else {
			testCase.addTextEvidenceCurrentStep("Error: La tabla XXCAFP.XXCAFP_ARCHIVO_FOLIO_PAGO_2 contiene "
					+ countTabla2 + " registros mayores a 6 meses.");
			assert countTabla2 == 0 : "La tabla contiene " + countTabla2 + " registros. Validaci�n fallida.";
		}

		testCase.addTextEvidenceCurrentStep("Validaci�n Exitosa: Se depuraron los registros de las tablas web.");

		// PASO FINAL
		u.getDriver().quit();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_038_ValidarDepuracionDeTablasHistoricasWebDeLosUltimos6Meses_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la depuracion de las tablas:"
				+ "XXCAFP.XXCAFP_ARCHIVO_HISTORICO y XXCAFP.XXCAFP_ARCHIVO_FOLIO_PAGO_2\r\n"
				+ "historicas contengan registros solo de los ultimos 6 meses";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar acceso a JOB de depuracion web \r\n" + "Tener Acceso a la BD en la instancia FCIASQA";
	}

	@Override
	public void beforeTest() {

	}
}
