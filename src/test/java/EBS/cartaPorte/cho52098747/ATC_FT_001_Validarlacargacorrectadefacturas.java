package EBS.cartaPorte.cho52098747;

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

public class ATC_FT_001_Validarlacargacorrectadefacturas extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Validarlacargacorrectadefacturas_test(HashMap<String, String> data) throws Exception {

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
		String consulta2 = "SELECT ATRIBUTO5 FROM XXFC.XXFC_CFDFACTURAS WHERE FACC_UUID IN (\r\n"
				+ "'52BE209F-E1D2-4C28-99EF-824D8251DC50',\r\n" + "'2E35AE6C-O357-422C-A5C3-052CF50EA7C3')";
		String consulta3 = "SELECT UUID,NUM_FAC_AP,ESTADO,NOM_ARCH FROM XXFC.xxfc_ap_reluuidplaza_all where UUID IN  (\r\n"
				+ "'52BE209F-E1D2-4C28-99EF-824D8251DC50',\r\n" + "'2E35AE6C-O357-422C-A5C3-052CF50EA7C3')";
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
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52098747
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
		addStep("Validar ATRIBUTTO5 = Y");
		System.out.println(consulta2);
		SQLResult validat = EBSAVANTE.executeQuery(consulta2);
		String atributo = validat.getData(0, "ATRIBUTO5");
		boolean validar = atributo.equals("Y");
		System.out.print(atributo);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		addStep("Validar UUID");
		System.out.println(consulta3);
		SQLResult validat3 = EBSAVANTE.executeQuery(consulta3);
		String atributo3 = validat3.getData(0, "UUID");
		boolean validar3 = atributo3.equals("2E35AE6C-O357-422C-A5C3-052CF50EA7C3");
		System.out.print(atributo);
		assertFalse(!validar3, "La respuesta no tiene el el resultado esperado.");
		// ---------------------------------------------------------------------------
		String atributo4 = validat3.getData(1, "UUID");
		boolean validar4 = atributo4.equals("52BE209F-E1D2-4C28-99EF-824D8251DC50");
		System.out.print(atributo4);
		assertFalse(!validar4, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
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
		return "ATC_FT_001_Validarlacargacorrectadefacturas_test";
	}

	@Override
	public String setTestDescription() {
		return "Comprobar que permita la carga de facturas cuando se cumplan con todas las reglas: "
				+ "- Archivo con extensión CSV " + "- Las columnas en el orden correcto"
				+ "- El proveedor que se encuentren dentro del lookup"
				+ "- La responsabilidad de plaza que se use para la importación debera corresponder con los datos de plaza en el archivo"
				+ "Pre-requisitos: - Configuracion de Lookup con RFC de proveedores permitidos XXFC_PROVEEDORES_ETV, recepcion de archivo CSV."
				+ "- Generar archivos CSV";
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
		return "- Configuracion de Lookup con RFC de proveedores permitidos XXFC_PROVEEDORES_ETV, recepción de archivo CSV."
				+ "- Generar archivos CSV";
	}

	@Override
	public void beforeTest() {

	}
}