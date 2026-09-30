package WEB.mermasDeCafe.scripts;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.mermasDeCafe.om.MermasDeCafe;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_059_Combo_seleccion_de_tienda_usuario_Asesor extends BaseExecution {
	private SeleniumUtil u;
	private MermasDeCafe mdc;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_059_Combo_seleccion_de_tienda_usuario_Asesor_test(HashMap<String, String> data)
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

		addStep("Validar sea visible solamente el filtro: \"Tienda\"");
		Assert.assertTrue(mdc.validarCombosEmpTransAsesor(),
				"No se pudo validar que solamente sea visible el filtro Tienda");
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar con usuario asesor solo sea visible el combo donde se muestra el listado de tiendas pertenecientes al usuario autenticado.";
	}

	@Override
	public String setTestDesigner() {
		return "Sharon S. Estrada Martinez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_059_Combo_seleccion_de_tienda_usuario_Asesor_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con credenciales de acceso al portal web \"Portal aplicaciones oxxo.";
	}

	@AfterMethod
	public void afterMethod() {
		u.quit();
	}
}
