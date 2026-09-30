package WEB.mermasDeCafe.scripts;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import WEB.mermasDeCafe.om.MermasDeCafe;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_075_Descarga_de_Excel_de_Catalogo_de_Empoderamiento_de_Salida_de_Transferencia_usuario_Asesor
		extends BaseExecution {
	private SeleniumUtil u;
	private MermasDeCafe mdc;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_075_Descarga_de_Excel_de_Catalogo_de_Empoderamiento_de_Salida_de_Transferencia_usuario_Asesor_test(
			HashMap<String, String> data) throws Exception {
		
		String user = data.get("Usuario");
		String ps = data.get("Contrasenia");
		
		String downloadFilePath = "C:\\Users\\GTIM\\Desktop\\Test";
		//String path = downloadFilePath + "\\Reporte_Plazas.xlsx";


		HashMap<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting.popups", 0);
		prefs.put("download.default_directory", downloadFilePath);

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);

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

		addStep("Click en Exportar");
		mdc.clickExportar();
		
		u.hardWait(10);
		u.close();

		File folder = new File(downloadFilePath);
		File[] listoffiles = folder.listFiles();
		Assert.assertTrue(listoffiles.length > 0, "no se descargo el fichero");
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar que se pueda descargar el archivo en formato XLS(Excel) con la información correspondiente de la tabla: Catálogo de Empoderamiento de Salida de Transferencia";
	}

	@Override
	public String setTestDesigner() {
		return "Sharon S. Estrada Martinez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_075_Descarga_de_Excel_de_Catalogo_de_Empoderamiento_de_Salida_de_Transferencia_usuario_Asesor";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con credenciales de acceso al portal web \"Portal aplicaciones oxxo.";
	}

}
