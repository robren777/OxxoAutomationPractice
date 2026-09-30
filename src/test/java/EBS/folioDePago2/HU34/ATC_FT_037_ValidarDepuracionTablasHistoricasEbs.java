package EBS.folioDePago2.HU34;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
 * DESCRIPCION:
 * alidar la depuracion de las tablas: xxfc_ap_cargacsv_all  xxfc_ap_cargacsv_err 
 * historicas no contengan registros mayor o igual a 6 meses de antiguedad
 * 
 * NOMBRE CASO DE PRUEBA QUE ABARCAN:
 * ATC_FT_037_ValidarDepuracionTablasHistoricasEbs
 * 
 * PRERREQUISITOS:
 * Contar acceso a JOB de depuracion EBS Tener Acceso a la BD en la instancia AVEBQA
 *
 * INSUMOS:
 * Contar con datos en tablas xxfc_ap_cargacsv_all  xxfc_ap_cargacsv_err
 *
 * @author ricardor4@hexaware.com
 * @date 09/Junio/2025
 */
public class ATC_FT_037_ValidarDepuracionTablasHistoricasEbs extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_037_ValidarDepuracionTablasHistoricasEbs_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */

		// CONTROL-M
		String controlMUser = data.get("user");
		String controlMPass = data.get("ps");
		String controlMServer = data.get("server");

		// BASES DE DATOS

		String tabla1 = "SELECT count (*) FROM xxfc.xxfc_ap_cargacsv_err WHERE CREATION_DATE <= ADD_MONTHS(SYSDATE, -6)";
		String tabla2 = "SELECT count (*) FROM xxfc.xxfc_ap_cargacsv_all WHERE CREATION_DATE <= ADD_MONTHS(SYSDATE, -6)";
		String tablap = "SELECT TIPO_MAPEO,ENTRADA,SALIDA1,LAST_UPDATE_DATE FROM XXFC.XXFC_MAPEOS_VARIOS WHERE 1=1 AND TIPO_MAPEO = 'XXFC_AP_CFDVALFACTURAS_PKG' AND ENTRADA = 'DEPURACION_TABLAS' AND ESTADO = 'A'";

		utils.sql.SQLUtil AVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a Control M: (https://172.26.177.68:8446/ControlM/?route=UserViews) Ingresar Usuario Ingresar Contrasena");
		
		testCase.addBoldTextEvidenceCurrentStep("Consultas antes de la ejecucion");
		testCase.addBoldTextEvidenceCurrentStep("Parametro de Depuracion EBS");
		// Ejecutar la consulta y obtener el resultado
		System.out.println(tablap);
		SQLResult validatp = AVANTE.executeQuery(tablap);
		testCase.addQueryEvidenceCurrentStep(validatp);
		
		
		testCase.addBoldTextEvidenceCurrentStep("xxfc_ap_cargacsv_all");
		System.out.println(tabla2);
		SQLResult validat13 = AVANTE.executeQuery(tabla2);
		testCase.addQueryEvidenceCurrentStep(validat13);
		
		testCase.addBoldTextEvidenceCurrentStep("Tabla: xxfc_ap_cargacsv_err");
		System.out.println(tabla1);
		SQLResult validat12 = AVANTE.executeQuery(tabla1);
		testCase.addQueryEvidenceCurrentStep(validat12);


		JSONObject obj = new JSONObject(data.get("jobs"));
		Control_mInicio cm = new Control_mInicio(u, controlMUser, controlMPass);
		u.get(controlMServer);
		u.waitForLoadPage();
		u.hardWait(40);
		cm.logOn();
		testCase.addScreenShotCurrentStep("paso 1");
		u.hardWait(4);
		// PASO 2
		addStep("Ejecutar JOb de depuracion EBS: XXFC_AP_DEPURA_CARGA_FP");
		ControlM control = new ControlM(u, testCase, obj);
		control.searchJob(); // busca el job
		String resultadoEjecucion = control.executeJob();
		assertTrue(resultadoEjecucion.equals("Ended OK") || resultadoEjecucion.equals("Wait Condition"),
				"El job XXFC_AP_DEPURA_CARGA_FP no se ejecuto correctamente");
		// PASO 3
		// VALIDAR TABLAS
		// Paso para validar depuración en tablas
		addStep("Ingresar a EBS revisar las tablas ebs: xxfc_ap_cargacsv_all Y xxfc_ap_cargacsv_err validando que el campo CREATION_DATE con fecha mayor o igual a 6 meses de antiguedad");
		testCase.addBoldTextEvidenceCurrentStep("Tabla: xxfc_ap_cargacsv_all");

		
		System.out.println(tabla2);
		assertNotNull(tabla2, "Error: El resultado de la consulta a la base de datos es nulo.");
		
		SQLResult validat2 = AVANTE.executeQuery(tabla2);

		// Agregar evidencia de la consulta al paso actual
		testCase.addQueryEvidenceCurrentStep(validat2);

		// Validar que el COUNT sea igual a 0
		int rowCount2 = Integer.parseInt(validat2.getData(0, 0)); // Método para obtener el número de registros
																	// (asegúrate de que exista en SQLResult)
		if (rowCount2 == 0) {
			// Si el COUNT es 0, se considera una validación exitosa
			testCase.addTextEvidenceCurrentStep(
					"Validacion Exitosa: La tabla xxfc_ap_cargacsv_all no contiene registros (COUNT = 0)");
		} else {
			// Si el COUNT no es 0, lanzar un error con un assert
		    testCase.addTextEvidenceCurrentStep(
		            "Error: La tabla xxfc_ap_cargacsv_all contiene " + rowCount2 + " registros que no fueron depurados.");
		    assert rowCount2 == 0 : "La tabla contiene " + rowCount2 + " registros. Validación fallida.";
		}


		// -------------------------------------------------------------------------------------------

		testCase.addBoldTextEvidenceCurrentStep("Tabla: xxfc_ap_cargacsv_err");
		// Ejecutar la consulta y obtener el resultado
		System.out.println(tabla1);
		SQLResult validat1 = AVANTE.executeQuery(tabla1);

		// Agregar evidencia de la consulta al paso actual
		testCase.addQueryEvidenceCurrentStep(validat1);

		// Validar que el COUNT sea igual a 0
		int rowCount = Integer.parseInt(validat1.getData(0, 0)); // Método para obtener el número de registros
																	// (asegúrate de que exista en SQLResult)

		if (rowCount == 0) {
			// Si el COUNT es 0, se considera una validación exitosa
			testCase.addTextEvidenceCurrentStep(
					"Validacion Exitosa: La tabla xxfc.xxfc_ap_cargacsv_err no contiene registros (COUNT = 0).");
		} else {
			// Si el COUNT no es 0, lanzar un error con un assert
			testCase.addTextEvidenceCurrentStep("Error: La tabla xxfc.xxfc_ap_cargacsv_err contiene " + rowCount
					+ " registros que no fueron depurados.");
			assert rowCount == 0 : "La tabla contiene " + rowCount + " registros. Validación fallida.";
		}
		
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa Se depuraron los registros de las tablas EBS");
		u.getDriver().quit();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_037_ValidarDepuracionTablasHistoricasEbs_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la depuracion de las tablas: " + "xxfc_ap_cargacsv_all " + "xxfc_ap_cargacsv_err "
				+ "historicas no contengan registros mayor o igual a 6 meses de antiguedad";
	}

	@Override
	public String setTestDesigner() {
		return "ricardor4@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar acceso a JOB de depuracion EBS\r\n"
	            + "Tener Acceso a la BD en la instancia AVEBQA";
	}

	@Override
	public void beforeTest() {

	}
}
