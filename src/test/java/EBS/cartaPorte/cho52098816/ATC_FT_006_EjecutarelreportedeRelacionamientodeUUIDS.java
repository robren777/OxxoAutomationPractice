package EBS.cartaPorte.cho52098816;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.CargaDeArchivosTool;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSCargarArchivos;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_006_EjecutarelreportedeRelacionamientodeUUIDS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_EjecutarelreportedeRelacionamientodeUUIDS_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String campo = data.get("campo");
		String archivo = data.get("archivo");
		String navegador = data.get("navegador");
		String dato = data.get("dato");
		String fechainicio = data.get("fechainicio");
		String fechafin = data.get("fechafin");

		/*
		 * SQL Queries
		 */
		String consulta1 = "SELECT NUM_FAC_AP FROM XXFC.xxfc_ap_reluuidplaza_all where rownum <= 10";

		/*
		 * UTILERIAS
		 */
		utils.sql.SQLUtil EBSAVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSCargarArchivos cargarArchivos = new EBSCargarArchivos(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		CargaDeArchivosTool cargarrutas = new CargaDeArchivosTool(u, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52098816
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contrasena.");
		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-AP10MON-USUARIO-FACTURA'");
		testCase.addScreenShotCurrentStep(u, "RESPONSABILIDAD 'MXOXXO-AP10MON-USUARIO-FACTURA'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Carga de Archivos.");
		paginaInicial.clickcargaarchivosr();
		testCase.addScreenShotCurrentStep(u, "Seleccionar: Carga de Archivos");
		// PASO 7
		addStep("Seleccionar el Archivo a Cargar, dar clic en 'Browse…' y seleccionar el archivo (.txt)");
		u.sendKeys(paginaInicial.cajadebusqueda(), campo);
		// PASO 8
		addStep("Seleccionar el Proceso 'Migracion de Proveedores'.");
		cargarrutas.selectFileCurrentProject(archivo);
		paginaInicial.clicklupabusqueda();
		// creamos una instancia de la pantalla primigenia
		String originalWindow = u.getDriver().getWindowHandle();
		// Check we don't have other windows open already
		assert u.getDriver().getWindowHandles().size() == 1;
		// esperamos
		u.hardWait(3);
		// encontramos las ventanas hijas
		for (String windowHandle : u.getDriver().getWindowHandles()) {
			if (!originalWindow.contentEquals(windowHandle)) {
				u.getDriver().switchTo().window(windowHandle);
				break;
			}
		}
		// esperamos a que termine de cargar
		WebElement frame = u.getDriver().findElement(paginaInicial.newframe());
		u.waitForLoadPage();
		u.getDriver().switchTo().frame(frame);
		cargarArchivos.clickIr();
		// PASO 8
		addStep("Seleccionar el Proceso 'Importa Facturas Traslado de Valores'.");
		testCase.addScreenShotCurrentStep(u, "Seleccionar el Proceso 'Importa Facturas Traslado de Valores'.");
		cargarArchivos.clickCreacionResponsabilidad();
		u.getDriver().switchTo().window(originalWindow);
		// PASO 9
		addStep("Dar clic en el botón 'Cargar'.");
		testCase.addScreenShotCurrentStep(u, "Dar clic en el botón 'Cargar'.");
		cargarArchivos.clickCargar();
		paginaInicial.home();
		u.waitForLoadPage();
		paginaInicial.verSolicitudApplets();
		p.runApplet();
		p.encontrar();
		// PASO 10
		addStep("Validar el Concurrente Finalice Normal.");
		testCase.addScreenShotCurrentStep(u, "Validar el Concurrente Finalice Normal.");
		p.visualizarOutput();
		// PASO 11
		addStep("Validar INVOICE_NUM");
		System.out.println(consulta1);
		SQLResult validat = EBSAVANTE.executeQuery(consulta1);
		String atributo = validat.getData(0, "NUM_FAC_AP");
		boolean validar = atributo.equals("ETV_10OBR_003R");
		System.out.print(atributo);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 11
		p.cerrarNavegador();
		p.navegador();
		p.buscarResponsabilidad(navegador);
		p.vers();
		p.runApplet();
		p.ejecutarNuevaSolicitud();
		p.buscarArchivoTab(dato);
		p.parametrosFactura(fechainicio, fechafin);
		p.ejecutarBoton();
		p.ejecutarBotonNo();
		p.encontrar();
		p.verificarEstado("normalVerde", 6000000);
		p.visualizarOutput();
		u.hardWait(2);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		p.cerrarNavegador();
		p.cerrarResp("a");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_EjecutarelreportedeRelacionamientodeUUIDS_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar el reporte de relacionamiento de UUIDS muestra el numero de factura actualizado"
				+ "Pre-requisitos: Haber cargado informacion en la pantalla de Carga y/o ejecutar Concurrente de Importacion de Facturas.  Modificar el numero de una factura previamente cargada";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Pre-requisitos: Haber cargado informacion en la pantalla de Carga y/o ejecutar Concurrente de Importacion de Facturas.  Modificar el numero de una factura previamente cargada";

	}

	@Override
	public void beforeTest() {

	}
}
