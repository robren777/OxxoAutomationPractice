package EBS.Homologacion_CxP_CHG0043865;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import EBS.util.ControlM_HomologacionCxP;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
* DESCRIPCIÓN: Realizar el envío Estatus de Facturas OS al Portal de Xpertal
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*     -ATC_FT_002_EstatusDeFacturasOSaXPERTAL
*  
* PREREQUISITOS:
*	Contar con información en EBS 
	*Contar con acceso a Control-M
	*Contar con acceso a WM_LOG
*
* INSUMOS (datos o información que necesita estar preparada o generada para poder ejecutar el script automatizado):
*     -Haber corrido previamente el ATC_FT_005_GeneracionDeUnaFacturaDeProrrateoAUnaPlazaConCR_Menor_a_30
* 	  -Tener el job runOE15MX_StatusFactOS en status released.
* @author Jesus Alberto Moreno Delgado
* @date 09/01/2025
*/

public class ATC_FT_002_EstatusDeFacturasOSaXPERTAL extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_EstatusDeFacturasOSaXPERTAL_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String job = data.get("job");
		boolean flag;
		boolean result;

		String queryComprobador = data.get("queryComprobador");
		String queryEBS1 = data.get("queryEBS1");
		String queryEBS2 = data.get("queryEBS2");
		String queryEBS3 = data.get("queryEBS3");
		String queryWM6 = data.get("queryWM6");
		String queryWM1 = data.get("queryWM1");
		String queryWM2 = data.get("queryWM2");
		String queryWM3 = data.get("queryWM3");
		String queryEBS5 = data.get("queryEBS5");
		String queryEBS6 = data.get("queryEBS6");
		String queryEBS7 = data.get("queryEBS7");
		String queryEBS8 = data.get("queryEBS8");

		String path = data.get("path");

		String wm_run_id = null;
		String invoice_num = null;

		int maxAttempts = 10;
		int attempts = 0;

		/*
		 * UTILERIAS
		 */

		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		// u = new SeleniumUtil(new IETest(), true);
		u = new SeleniumUtil(new ChromeTest(), true);
		ControlM_HomologacionCxP pom = new ControlM_HomologacionCxP(u, testCase);

		// Extraer invoice_num del registro de referencia
		String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
		JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
		JsonObject entity = jsonObject.getAsJsonObject("Entity");
		invoice_num = entity.get("INVOICE_NUM").getAsString();
		System.out.println("INVOICE_NUM: " + invoice_num);

		// Inicializando bd de WM
		utils.sql.SQLUtil dbWM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQAv2,
				GlobalVariables.DB_USER_FCWMLQAv2, GlobalVariables.DB_PASSWORD_FCWMLQAv2);

		// Paso 1. Ingresar a la BD de EBS
		addStep("Ingresar a la BD de EBS");
		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryComprobador);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 2. Entrar a la tabla XXFC_XP_CARGO_CENTRAL_HEADERS
		addStep("Entrar a la tabla XXFC_XP_CARGO_CENTRAL_HEADERS");
		resultadoContenedor = dbEBS.executeQuery(queryEBS1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 3.Validar que la tabla cuente con los siguientes campos para WM:
		// WM_STATUS, WM_MESSAGE, WM_SHPT_DATE, WM_RUN_ID
		addStep("Validar que la tabla cuente con los siguientes campos para WM: WM_STATUS, WM_MESSAGE, WM_SHPT_DATE, WM_RUN_ID");
		resultadoContenedor = dbEBS.executeQuery(queryEBS2);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 4. Ejecutar el siguiente query para validar que se tenga información de
		// compras XXFC_XP_CARGO_CENTRAL_HEADERS de EBS con WM_STATUS igual a ‘L’
		addStep("Ejecutar el siguiente query para validar que se tenga información de compras XXFC_XP_CARGO_CENTRAL_HEADERS de EBS con WM_STATUS igual a ‘L’");
		resultadoContenedor = dbEBS.executeQuery(queryEBS3);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 5. Validar se muestre Configuración de la interface cargada en la tabla
		// WM_INTERFASE_CONFIG. select * from wm_interfase_config where
		// interfase='OE15MX'
		addStep("Validar se muestre Configuración de la interface cargada en la tabla WM_INTERFASE_CONFIG. select * from wm_interfase_config where interfase='OE15MX'");
		utils.sql.SQLUtil dbWM6 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWM6QA,
				GlobalVariables.DB_USER_FCWM6QA, GlobalVariables.DB_PASSWORD_FCWM6QA);
		resultadoContenedor = dbWM6.executeQuery(queryWM6);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}
		// Paso 6. Ingresar a Control-M
		addStep("Ingresar a Control-M");
		testCase.addBoldTextEvidenceCurrentStep("Iniciar sesion en CTRL-M.");
		result = pom.getControlM(GlobalVariables.CONTROL_M_USER_JesusMoreno, GlobalVariables.CONTROL_M_PWD_JesusMoreno,
				GlobalVariables.CONTROL_M_SERVER);
		assertTrue(result, "Fallo al iniciar sesion en CTRL-M");

		// Paso 7. Ejecutar la interface runOE15MX_StatusFactOS
		addStep("Ejecutar la interface runOE15MX_StatusFactOS");
		u.hardWait(15);
		p.clickImagen("monitoring");
		u.hardWait(15);
		p.buscarJob(job);
		u.hardWait(15);

		flag = false;
		while (attempts < maxAttempts && !flag) {
			p.clickDerecho("os");
			u.hardWait(15);
			p.clickImagen("run_options");
			u.hardWait(5);
			p.clickImagen("run_now");
			u.hardWait(5);
			p.enter();
			u.hardWait(10);
			p.clickImagen("refresh_controlm");
			u.hardWait(160);
			// Verificar si el job corrió correctamente
			resultadoContenedor = dbWM.executeQuery(queryWM3 + " '%" + invoice_num + "'");
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);

			if (!resultadoContenedor.isEmpty()) {
				flag = true;
			}

			attempts++;
		}

		if (!flag) {
			System.out.println("El registro no se encontró en la BD después de " + maxAttempts + " intentos.");
		} else {
			System.out.println("El registro se encontró en la BD.");
		}
		testCase.addScreenShotCurrentStep("Control M Interface");


		// Paso 8. Validar en la tabla WMLOG.WM_LOG_RUN status='S'
		addStep("Validar en la tabla WMLOG.WM_LOG_RUN status='S'");
		dbWM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQAv2, GlobalVariables.DB_USER_FCWMLQAv2,
				GlobalVariables.DB_PASSWORD_FCWMLQAv2);
		resultadoContenedor = dbWM.executeQuery(queryWM1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
			wm_run_id = resultadoContenedor.getData(0, 2);
		} else {
			flag = false;
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 9. Validar en la tabla WMLOG.WM_LOG_ERROR no se genere errores
		addStep("Validar en la tabla WMLOG.WM_LOG_ERROR no se genere errores");
		resultadoContenedor = dbWM.executeQuery(queryWM2 + " '" + wm_run_id + "'");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (resultadoContenedor.isEmpty())
			flag = true;
		assertTrue(flag, "El registro no existe en la BD, lo cual es correcto");

		// Paso 10. Validar en la tabla wmlog.WM_LOG_THREAD estatus ='S'
		addStep("Validar en la tabla wmlog.WM_LOG_THREAD estatus ='S'");

		dbWM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQAv2, GlobalVariables.DB_USER_FCWMLQAv2,
				GlobalVariables.DB_PASSWORD_FCWMLQAv2);
		resultadoContenedor = dbWM.executeQuery(queryWM3 + " '%" + invoice_num + "'");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty())
			flag = true;
		assertTrue(flag, "El registro existe en la BD");

		// Paso 11. Validar que la tabla SELECT * FROM
		// XXFC.XXFC_XP_CARGO_CENTRAL_HEADERS where wm_status='E'
		addStep("Validar que la tabla SELECT * FROM XXFC.XXFC_XP_CARGO_CENTRAL_HEADERS where wm_status='E'");

		resultadoContenedor = dbEBS.executeQuery(queryEBS5 + " '" + invoice_num + "'");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		}

		// Paso 12. Validar campos WM_MESSAGE
		addStep("Validar campos WM_MESSAGE");
		resultadoContenedor = dbEBS.executeQuery(queryEBS6 + " '" + invoice_num + "'");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		}

		// Paso 13. Validar campos WM_SHPT_DATE
		addStep("Validar campos WM_SHPT_DATE");
		resultadoContenedor = dbEBS.executeQuery(queryEBS7 + " '" + invoice_num + "'");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		}

		// Paso 14. Validar campos WM_RUN_ID
		addStep("Validar campos WM_RUN_ID");
		resultadoContenedor = dbEBS.executeQuery(queryEBS8 + " '" + invoice_num + "'");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		}
		
		u.close();
		u.quit();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "\"* Contar con información en EBS  \r\n" + "*Contar con acceso a Control-M\"\r\n"
				+ "*Contar con acceso a WM_LOG";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Realizar el envío Estatus de Facturas OS al Portal de Xpertal";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusM3@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_EstatusDeFacturasOSaXPERTAL";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
