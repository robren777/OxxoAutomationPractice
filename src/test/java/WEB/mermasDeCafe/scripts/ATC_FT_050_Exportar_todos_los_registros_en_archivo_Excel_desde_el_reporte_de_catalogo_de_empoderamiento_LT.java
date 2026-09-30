package WEB.mermasDeCafe.scripts;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.mermasDeCafe.om.MermasDeCafe;
import modelo.BaseExecution;
import utils.selenium.SeleniumUtil;

public class ATC_FT_050_Exportar_todos_los_registros_en_archivo_Excel_desde_el_reporte_de_catalogo_de_empoderamiento_LT
		extends BaseExecution {

	private SeleniumUtil u;
	private MermasDeCafe mdc;

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_050_Exportar_todos_los_registros_en_archivo_Excel_desde_el_reporte_de_catalogo_de_empoderamiento_LT_test(
			HashMap<String, String> data) throws Exception {

		String user = data.get("Usuario");
		String ps = data.get("Contrasenia");
		
		String downloadFilePath = "C:\\Users\\GTIM\\Desktop\\Test";
		String path = downloadFilePath + "\\Reporte_Plazas.xlsx";
		String titulo = "Reporte Empoderamiento Líder de Tienda";
		ArrayList<String> tituloColumnas = new ArrayList<>(Arrays.asList("CR Plaza", "Plaza", "Estatus Plaza",
				"CR Tienda", "Tienda", "Estatus Tienda", "Merma Empoderada", "Devolución Empoderada",
				"Ajuste de entrada", "Modified By", "Modified Date"));

		HashMap<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting.popups", 0);
		prefs.put("download.default_directory", downloadFilePath);

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);

		u = new SeleniumUtil(new ChromeDriver(options));
		mdc = new MermasDeCafe(u, testCase);

		addStep("Ingresar a Access Control");
		mdc.Ingresar(user, ps);
		addStep("Ingresar a Merma Autorizada en POS");
		mdc.clickMermaAutorizada();
		addStep("Ingresar a menú Reportes");
		mdc.clickReportesAdmin();
		addStep("Ingresar a Reporte de catalogo de empoderamiento LT");
		mdc.clickReporteCatalogoEmpoderamientoLT();
		addStep("Se da click en el combo de plaza");
		mdc.clickPlaza_ReportePermisos();
		addStep("Se selecciona el primer check para seleccionar todas las plazas del listado desplegado en el combo box.");
		mdc.seleccionarTodasLasPlazas();
		addStep("Presionar el Botón \"Buscar\"");
		mdc.clickBuscar();
		addStep("Presionar el Botón \"Exportar\".");
		mdc.clickExportar();
		
		File folder = new File(downloadFilePath);
		File[] listoffiles = folder.listFiles();
		Assert.assertTrue(listoffiles.length > 0, "no se descargo el fichero");
		HashMap<String, ArrayList<String>> rows = mdc.obtenerFilas(path);
		Assert.assertTrue(rows.containsKey(titulo),
				"el archivo no contiene el titulo \"Reporte Empoderamiento Líder de Tienda\"");
		ArrayList<String> celdas = mdc.obtenerCeldas(path);
		Assert.assertEquals(tituloColumnas, celdas);

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar se pueda exportar a un archivo Excel todos los datos que se encuentran en la tabla  \"Consulta catalogo de empoderamiento\" desde la ruta Reportes>Reporte de catálogo de empoderamiento.";
	}

	@Override
	public String setTestDesigner() {
		return "Sharon S. Estrada Martinez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_050_Exportar_todos_los_registros_en_archivo_Excel_desde_el_reporte_de_catalogo_de_empoderamiento_LT_test";
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