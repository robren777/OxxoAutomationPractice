package WEB.mermasDeCafe.scripts;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.mermasDeCafe.om.MermasDeCafe;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
//import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_060_Tabla_de_Catalogo_de_Empoderamiento_de_Salida_de_Transferencia_Asesor extends BaseExecution {
	private SeleniumUtil u;
	private MermasDeCafe mdc;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_060_Tabla_de_Catalogo_de_Empoderamiento_de_Salida_de_Transferencia_Asesor_test(
			HashMap<String, String> data) throws Exception {

		String user = data.get("Usuario");
		String ps = data.get("Contrasenia");

		u = new SeleniumUtil(new ChromeTest(), true);
		mdc = new MermasDeCafe(u, testCase);

		addStep("Ingresar a Access Control");
		mdc.Ingresar(user, ps);

		addStep("Ingresar a Merma Autorizada en POS");
		mdc.clickMermaAutorizada();

		addStep("Ingresar a Transferencia");
		mdc.clickTransferencias();

		addStep("Ingresar a Transferencia entre Tiendas");
		mdc.clickTransferenciaEntreTiendas();

		addStep("Se verifica exista la tabla: \"Catálogo de Empoderamiento de Salida de Transferencia\"");
		mdc.validarTablaEmpSalidaTransferencia();

		addStep("Se verifica existan los campos: \"Región, Plaza, CR Tienda, Empoderamiento Salida de Transferencia, Fecha de activación, Modificado por y Opciones\" "
				+ "en la tabla: \"Catálogo de Empoderamiento de Salida de Transferencia\"");
		mdc.validarCamposTablaEmpSalidaTransferencia();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar con usuario asesor solo se muestren sus registros correspondientes a sus tiendas asignadas en el CNT en la tabla \"Catálogo de Empoderamiento de Salida de Transferencia\"";
	}

	@Override
	public String setTestDesigner() {
		return "Sharon S. Estrada Martinez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_060_Tabla_de_Catalogo_de_Empoderamiento_de_Salida_de_Transferencia_Asesor_test";
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
