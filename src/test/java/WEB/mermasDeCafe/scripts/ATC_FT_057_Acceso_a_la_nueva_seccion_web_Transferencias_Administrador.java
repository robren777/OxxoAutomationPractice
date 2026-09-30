package WEB.mermasDeCafe.scripts;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.mermasDeCafe.om.MermasDeCafe;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_057_Acceso_a_la_nueva_seccion_web_Transferencias_Administrador extends BaseExecution {

	private SeleniumUtil u;
	private MermasDeCafe mdc;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_057_Acceso_a_la_nueva_seccion_web_Transferencias_Administrador_test(HashMap<String, String> data)
			throws Exception {

		String user = data.get("Usuario");
		String ps = data.get("Contrasenia");

		u = new SeleniumUtil(new MEdgeTest(), true);
		mdc = new MermasDeCafe(u, testCase);

		addStep("Ingresar a Access Control");
		mdc.Ingresar(user, ps);

		addStep("Ingresar a Merma Autorizada en POS");
		mdc.clickMermaAutorizada();

		addStep("Ingresar a Transferencia");
		mdc.clickTransferencias();

		addStep("Ingresar a Transferencia entre Tiendas");
		mdc.clickTransferenciaEntreTiendas();

		By empoderamiento = By.linkText("Empoderamiento de Salida de Transferencia");
		if (mdc.validarExistenciaObjeto(empoderamiento, "Empoderamiento")) {
			addStep("Se muestra el apartado de Empoderamiento de Salida de Transferencia");
			testCase.addScreenShotCurrentStep(u, "Empoderamiento");
		}

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar se pueda acceder a la nueva sección  \"Transferencias\" con usuario administrador.";
	}

	@Override
	public String setTestDesigner() {
		return "Sharon S. Estrada Martinez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_057_Acceso_a_la_nueva_seccion_web_Transferencias_Administrador_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con credenciales de acceso al portal web \"Portal aplicaciones oxxo\".";
	}

	@AfterMethod
	public void afterMethod() {
		u.quit();
	}
}