package EBS.MVP2_RST.scripts.MVP2_RST_E02_HU04;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.MVP2_RST.om.BaseComisionDif;
import EBS.MVP2_RST.om.mvp2ErstEBS;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Se ejecuta el concurrente XXFC Proceso Dispersion con poliza
 * contable, carta femsa del mismo dia
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: - MTC_FT_001-Ejecucion de
 * XXFC-Proceso de Dispercion con poliza contable, carta femsa del mismo dia
 *
 * PREREQUISITOS: - Tener configurado el ambiente de EBS - Tema de Chrome en
 * modo obscuro
 *
 * INSUMOS: - N/A
 *
 * @author Roberto Renteria
 * @date 24/Marzo/2025
 */
public class ATC_FT_001_EjecucionDeXXFCProcesoDeDispercionConPolizaContableCartaFemsaDelMismoDia extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_EjecucionDeXXFCProcesoDeDispercionConPolizaContableCartaFemsaDelMismoDia_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("ERST MVP2");

		// Variables globales
		String ebsUrl = GlobalVariables.Url_Ebs_Comision;
		String userName = data.get("UserEBS");
		String password = PasswordUtil.decryptPassword(data.get("PasswordEBS"));
		String concurrente = data.get("Concurrente");
		String formaPago = data.get("formaPago");
		File file2 = new File(System.getProperty("user.dir") + "\\Patterns\\EBS");
		String date;

		// utileria
		SeleniumUtil uSelenium = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(uSelenium, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(uSelenium, testCase);
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		SikuliUtil sk = new SikuliUtil();
		BaseComisionDif BaseComision = new BaseComisionDif(uSelenium, testCase, sk, ImgPatter);
		mvp2ErstEBS rst = new mvp2ErstEBS(uSelenium, testCase);

		// Comienza codigo
		rst.killProcess(new String[] { "jp2launcher.exe" });

		addStep("Dentro de EBS entrar en la responsabilidad llamada MXOXXO-GL17ADM-SERVICIOS");
		assertTrue(inicio.login(), "No se realiza el inicio de sesion");

		addStep("Una vez en la responsabilidad entramos en View > Request y seleccionamos Submit a New Request");
		assertTrue(paginaInicial.clickOnResponsabilidad("MXOXXO-GL17ADM-SERVICIOS"), "No existe la responsabilidad");
		assertTrue(BaseComision.ClickOnCarpeta("Informes"), "No encuentra la carpeta Informes");
		assertTrue(BaseComision.ClickOnCarpeta("Solicitar"), "No encuentra la carpeta Solicitar");
		assertTrue(BaseComision.ClickOnCarpeta("Standard"), "No encuentra la carpeta Standard");
		rst.abrirAppletChrome();
		rst.clickCancelar();
		rst.verMenuSol();
		assertTrue(BaseComision.EjecutarNuevaSolicitud(file2), "No se ejecuta nueva solicitud");

		addStep("Se validan los datos a procesar (rango fecha del mismo dia)");
		rst.validateFechas();

		addStep("Ejecutar el programa concurrente " + concurrente);
		rst.typeConcurrente(concurrente);
		rst.clickEjecutar();
		rst.aceptarConcurrente();
		rst.typeFormaPago(formaPago);
		date = rst.getCurrentDate();
		uSelenium.hardWait(5);
		rst.typeFechaIni(date);
		rst.aceptarFormaPago();
		rst.clicEjecutar();
		rst.clicNo();
		rst.clicEncontrar();
		rst.clicRefrescar();

		addStep("Revisar archivo LOG de la solicitud.");
		rst.clicVRegistro();
		rst.getLog();

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
		return "1. Tener configurado el ambiente de EBS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ejecucion De XXFC-ProcesoDeDispercionConPolizaContableCartaFemsa con fecha del mismo dia";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Roberto Renteria";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_EjecucionDeXXFCProcesoDeDispercionConPolizaContableCartaFemsaDelMismoDia_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
