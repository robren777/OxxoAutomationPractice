package WEB.Pago_Qr_Baz_Mvp3.US01;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WEB.om.PagoBazQrMvp3;
import azure.annotation.AzureTestInfo;
//import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

@AzureTestInfo(areaPath = "I23013 - Pago con QR en OXXO", iterationPath = "I23013 - Pago con QR en OXXO", tags = "ATC_FC_001;QR_BAZ", userStoryId = 73503)
/**
 * DESCRIPCIÓN: Caso de prueba que valida el proceso de reversas SAFv2 fallido
 * en el 3er intento con ITEM de proveedor CNKTA y validar su registro en la
 * bitacora con los datos correctos (contador en 2)
 * 
 * /** DESCRIPCION: Validar que la informacion de las ventas se vea en IMA
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: - MTC-FT-007 Validar que la
 * informacion se vea reflejada en IMA
 *
 * PREREQUISITOS: - Tener los accesos a control M - Contar con el nombre de las
 * carpteas y rutas de los Jobs
 *
 * INSUMOS: - Usuario y Contraseña control M
 *
 * @author Antonio Martínez
 * @date 09/Abril/2025
 */

public class ATC_FT_007_ValidarQueLaInformacionSeVeaReflejadaEnIma extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_ValidarQueLaInformacionSeVeaReflejadaEnIma_test(HashMap<String, String> data)
			throws Exception {

		/* Utils **************************************************************/

		// Comienza Script

		// Variables
		String FolderJob = data.get("folder");
		String NameJob = data.get("job5");

		// Se ejecuta Job 1.- runSAFTagMty

		addStep("Ejecutar el job runSAFTagMty");

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

		String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
		Control_mInicio cm = new Control_mInicio(u, data.get("user"), pssDecrypted);

		u.get(data.get("server"));

		u.waitForLoadPage();
		u.waitForLoadPage();
		cm.logOn();

		JSONObject obj = new JSONObject(data.get("job"));
		ControlM job = new ControlM(u, testCase, obj);
		boolean flag = job.searchJob();
		// /*
		assertTrue(flag);

		String resultado1 = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado1);
		System.out.println("Valor de output :" + job.getOutput());
		assertEquals(resultado1, "Wait for Event");
		job.closeViewpoint();
		addStep("Se ejecuta de manera exitosa el job runSAFTagMty");
		testCase.addScreenShotCurrentStep(u, "EndOk");
		u.quit();

		// Se ejecuta Job 2.- runSAFTagQro
		addStep("Ejecutar el job runSAFTagQro");
		// Recrear SeleniumUtil
		u = new SeleniumUtil(new ChromeTest(), true); //Nueva instancia
		cm = new Control_mInicio(u, data.get("user"), pssDecrypted);

		u.get(data.get("server"));

		u.waitForLoadPage();
		u.waitForLoadPage();
		cm.logOn();

		//Volver a instanciar ControlM con la nueva instancia de SeleniumUtil
		obj = new JSONObject(data.get("job2"));
		job = new ControlM(u, testCase, obj); //Nueva instancia de ControlM

		flag = job.searchJob();

		assertTrue(flag);

		String resultado2 = job.executeJob();
		System.out.println("Resultado de la ejecución -> " + resultado2);
		System.out.println("Valor de output: " + job.getOutput());
		assertEquals(resultado2, "Wait for Event");

		job.closeViewpoint();
		addStep("Se ejecuta de manera exitosa el job runSAFTagQro");
		testCase.addScreenShotCurrentStep(u, "EndOk2");

		u.quit(); // Cerrar el navegador despues de la segunda ejecucion.

		// */
		// Se ejecuta Job 3.- MAIN_Trans2Rep_PagoConQR
		addStep("Ejecutar el job MAIN_Trans2Rep_PagoConQR");
		// Recrear SeleniumUtil
		u = new SeleniumUtil(new ChromeTest(), true); // ✅ Nueva instancia
		cm = new Control_mInicio(u, data.get("user"), pssDecrypted);

		u.get(data.get("server"));

		u.waitForLoadPage();
		u.waitForLoadPage();
		cm.logOn();

		// 🔹 Volver a instanciar ControlM con la nueva instancia de SeleniumUtil
		obj = new JSONObject(data.get("job3"));
		job = new ControlM(u, testCase, obj); // ✅ Nueva instancia de ControlM

		flag = job.searchJob();

		assertTrue(flag);

		String resultado3 = job.executeJob();
		System.out.println("Resultado de la ejecución -> " + resultado3);
		System.out.println("Valor de output: " + job.getOutput());
		u.waitForLoadPage();
		u.waitForLoadPage();
		u.waitForLoadPage();
		u.waitForLoadPage();
		u.waitForLoadPage();
		u.waitForLoadPage();
		assertEquals(resultado3, "Ended OK");

		job.closeViewpoint();
		addStep("Se ejecuta de manera exitosa el job MAIN_Trans2Rep_PagoConQR");
		testCase.addScreenShotCurrentStep(u, "EndOk3");

		u.quit(); // Cerrar el navegador después de la segunda ejecución.

		// Se ejecuta Job 4.- runFT_QR_BAZ_TRX
		addStep("Ejecutar el job runFT_QR_BAZ_TRX");
		// Recrear SeleniumUtil
		u = new SeleniumUtil(new ChromeTest(), true); // ✅ Nueva instancia
		cm = new Control_mInicio(u, data.get("user"), pssDecrypted);

		u.get(data.get("server"));

		u.waitForLoadPage();
		u.waitForLoadPage();
		cm.logOn();

		// Volver a instanciar ControlM con la nueva instancia de SeleniumUtil
		obj = new JSONObject(data.get("job4"));
		job = new ControlM(u, testCase, obj); // ✅ Nueva instancia de ControlM

		flag = job.searchJob();

		assertTrue(flag);

		String resultado4 = job.executeJob();
		System.out.println("Resultado de la ejecución -> " + resultado3);
		System.out.println("Valor de output: " + job.getOutput());
		u.waitForLoadPage();
		u.waitForLoadPage();
		assertEquals(resultado3, "Ended OK");

		job.closeViewpoint();
		addStep("Se ejecuta de manera exitosa el job runFT_QR_BAZ_TRX");
		testCase.addScreenShotCurrentStep(u, "EndOk4");

		// u.quit(); // Cerrar el navegador después de la segunda ejecucion.

		// Se ejecuta Job 5.- RSTJ_OUT_OXXO_BAZ

		/***************************** Paso 1 ************************************/

		addStep("Acceder a control M y Ejecutar el job RSTJ_OUT_OXXO_BAZ");
		// SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PagoBazQrMvp3 bJPrd = new PagoBazQrMvp3(u, testCase);

		String pssDecrypted1 = PasswordUtil.decryptPassword(data.get("ps2"));
		Control_mInicio cm1 = new Control_mInicio(u, data.get("user2"), pssDecrypted1);
		u.get(data.get("server2"));

		u.waitForLoadPage();
		u.waitForLoadPage();
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "ctrl_m1");
		cm1.logOn();
		testCase.addScreenShotCurrentStep(u, "ctrl_m2");

		/***************************** Paso 2 ************************************/

		addStep("Ejecutar el job runSAFV2");
		bJPrd.buscarJobPrd(FolderJob, NameJob);

		/***************************** Paso 3 ************************************/
		addStep("Se ejecuta de manera exitosa el job RSTJ_OUT_OXXO_BAZ");
		bJPrd.validatePatternAndClick("EndOk5");

		// u.quit();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Ejecutar los jobs para FEMSA_FT \r\n" + "2. Contar con el nuevo servicio en ambiente QA.\r\n"
				+ "3. Contar con el ambiente de pruebas del proveedor.\r\n"
				+ "4.- Contar con las credenciales de Control M.\r\n" + "5.- Contar con el Job en Hold.\r\n"
				+ "6.- Contar con el cambio en la url en la BD para el fallo en el reverso";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Antonio Martinez Olivas";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_007_ValidarQueLaInformacionSeVeaReflejadaEnIma";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que la informacion de las ventas se vea en IMA";
	}
}