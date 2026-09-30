package EBS.MVP2_RST.scripts.MVP2_RST_E02_HU03;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.MVP2_RST.om.BaseComisionDif;
import EBS.MVP2_RST.om.mvp2ErstEBS;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Se ejecuta el concurrente XXFC - Reporte Consolidado de Cartas
 * OS para socio Fintech
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_002_ValidarMejorasEnElReporteConsolidadoDeCartasOsParaSocioIntegradorFintech
 *
 * PREREQUISITOS: - Tener configurado el ambiente de EBS - Tema de Chrome en
 * modo obscuro
 *
 * INSUMOS: - Proceso de dispersion previo con transacciones exitosas del socio
 * - Conocer rango de fechas de transacciones y dispersion
 *
 * @author Mariana Avitia
 * @date 27/Marzo/2025
 */
public class ATC_FT_002_ValidarMejorasEnElReporteConsolidadoDeCartasOsParaSocioIntegradorFintech extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_002_ValidarMejorasEnElReporteDeCartasParaSocioIntegradorFintech_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("ERST MVP2");

		// Variables globales
		String ebsUrl = GlobalVariables.Url_Ebs_Comision;
		String userName = GlobalVariables.User_EBS_M;
		String password = GlobalVariables.Password_EBS_M;
		// Ruta relativa (dentro de la estructura de tu proyecto):
		File file2 = new File("C:\\CENTRAL\\FrameworkEBS\\Patterns\\EBS");

		// variables
		String concurrente = data.get("Concurrente");
		String compania = "00099";
		String plaza = data.get("Plaza");
		String fechaI = data.get("FechaI");
		String fechaF = data.get("FechaF");

		// utileria

		SeleniumUtil uSelenium = new SeleniumUtil(new ChromeTest(), true);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(uSelenium, testCase);
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		SikuliUtil sk = new SikuliUtil();
		BaseComisionDif BaseComision = new BaseComisionDif(uSelenium, testCase, sk, ImgPatter);
		mvp2ErstEBS rst = new mvp2ErstEBS(uSelenium, testCase);

		// Comienza codigo
		System.out.println("*****************************************PASO 1 y 2 Login************************************************");
		addStep("Se ingresa a la página de EBS");
		assertTrue(rst.loginIngles(ebsUrl, userName, password), "No se realiza el inicio de sesion");

		System.out.println("*****************************************PASO 3************************************************");

		addStep("Dar clic sobre la liga \"MXOXXO-GLXXXXX-CONSULTAS: Request: Financial\"  y  después dar clic dar clic sobre ejecutar");
		assertTrue(paginaInicial.clickOnResponsabilidad("MXOXXO-GLXXXXX-CONSULTAS"), "No existe la responsabilidad");
		addStep("Expandir responsabilidad y abrir carpeta request");
		assertTrue(BaseComision.ClickOnCarpeta("requestC"), "No encuentra la carpeta Request");
		addStep("Expandir responsabilidad y abrir carpetafinancial");
		assertTrue(BaseComision.ClickOnCarpeta("financialC"), "No encuentra la carpeta Request");
		rst.abrirAppletChrome();
		rst.clickCerrar();

		System.out.println("*****************************************PASO 4************************************************");

		addStep("Dar click en \"Requests\" del menú desplegable");
		assertTrue(rst.viewRequest(), "No se encuentra menu Requests");
		addStep("Dar click en \"Submit a New Request\"");
		assertTrue(rst.submitNewRequest(), "No se valida el cambio de responsabilidad");

		// Se le da clic en busqueda concurrente
		addStep("Se le da clic en busqueda");
		assertTrue(BaseComision.BusquedaConcurrente(file2), "No busca el concurrente");
		addStep("Se escribe el nombre del concurrente");
		assertTrue(BaseComision.typeInEBS(file2, concurrente), "No se escribe en ebs el nombre");
		addStep("Se da click en buscar");
		assertTrue(rst.find(), "no se realizó la busqueda");

		System.out.println("*****************************************PASO 5 ************************************************");
		// Se ingresan parametros para generar reporte
		addStep("Se ingresan los parametros para generar la solicitud");
		BaseComision.Escribir(compania);
		BaseComision.Escribir(plaza);
		BaseComision.clickEnPuntos(file2);
		rst.servicioFtech();
		BaseComision.Escribir(fechaI);
		BaseComision.Escribir(fechaF);
		addStep("Dar click en botón \"OK\"");
		rst.okParameters();

		System.out.println("*****************************************PASO 6 ************************************************");
		// Se crea solicitud de reporte
		addStep("Dar click en botón \"Submit\"");
		assertTrue(rst.submitRequest(), "No se pudo completar la solicitud");
		addStep("Dar click en botón \"No\"");
		rst.clicNo();
		addStep("Dar click en botón \"Find\"");
		rst.clicEncontrar();
		addStep("Dar click en botón \"Refresh Data\"");
		rst.clicRefrescar();

		System.out.println("*****************************************PASO 7 ************************************************");
		// Validar que se muestre el concurrente ejecutado
		addStep("Click en botón \"View Output\"");
		rst.viewOutput();
		rst.abrirReprte();
		rst.abrirReprteExcel();
		BaseComision.Enter2(1);
		// Valida formato de reporte consolidado de cartas os
		addStep("Abrir reporte y validar formato de reporte consolidado de cartas os para socio hotpay");
		assertTrue(rst.validarReporte(), "Reporte incorrecto");

		System.out.println("*****************************************PASO 8 ************************************************");
		uSelenium.quit();
		rst.killProcess(new String[] { "jp2launcher.exe", "chrome.exe" });

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Ambiente EBS" + "2. Procesos de dispersión previamente realizado"
				+ "3. Tener registro de fechas transaccionadas"
				+ "4. Registro y mapeo de transacciones generadas desde xpos";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar las mejoras en el reporte de cartas OS, "
				+ "visualizar en el reporte la integración de las cartas FEMSA realizadas "
				+ "en el proceso de dispersión para los socios integradores";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mariana Gabriela Avitia";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_ValidarMejorasEnElReporteConsolidadoDeCartasOsParaSocioIntegradorFintech_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
