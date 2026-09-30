package EBS.devolucionesOxxoPay;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.pageObjectModel.EBSProvRelFormaPago;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_005_ValidarLaConfiguracionFormaPagoProveedores extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidarLaConfiguracionFormaPagoProveedores_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String formaPagoQuery = "SELECT * FROM APPS.XXFC_RETEK_FORMAS_PAGOS_V WHERE FORMA_PAGO_ID= 'PYIO49'";
		String rfc = data.get("rfc");
		String formapago = data.get("formapago");
		/*
		 * UTILERIAS
		 */
		utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSProvRelFormaPago formaPago = new EBSProvRelFormaPago(u, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Conectarse a la base de datos EBS 12.2.4");

		// PASO 2
		addStep("Ejecutar el query");
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		System.out.println(formaPagoQuery);
		SQLResult ValidChangeExec = dbAvebqa.executeQuery(formaPagoQuery);
		String forma_pago = "";
		String forma_pago_desc = "";

		boolean ValidChangeRes = ValidChangeExec.isEmpty();
		if (!ValidChangeRes) {
			forma_pago = ValidChangeExec.getData(0, "forma_pago_id");
			forma_pago_desc = ValidChangeExec.getData(0, "forma_pago_desc");
			System.out.println("Forma de pago: " + forma_pago);
			System.out.println("Forma de pago Description: " + forma_pago_desc);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		} else if (ValidChangeRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta /n" + formaPagoQuery);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		}

		// PASO 3
		addStep("Entrar  a la responsabilidad de MXOXXO-CMXXXXX-Super Usuario --> Setup --> Prov - Forma Pago");
		inicio.login();
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.carpetaSetupElement.click();
		paginaInicial.provFormaPagoElement.click();

		// PASO 4
		addStep("Capturar el RFC del proveedor, clic en el botón GO");
		formaPago.rfcElement.sendKeys(rfc);
		formaPago.formaPagoElement.sendKeys(formapago);
		formaPago.btnIrElement.click();
		testCase.addScreenShotCurrentStep(u, "Resultado consulta");
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Tener responsabilidad MXOXXO-CMXXXXX-Super Usuario";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se validara que el proveedor se relacione a la forma de pago para devoluciones."
				+ "Prerequisitos: Tener responsabilidad MXOXXO-CMXXXXX-Super Usuario";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ValidarLaConfiguracionFormaPagoProveedores_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
