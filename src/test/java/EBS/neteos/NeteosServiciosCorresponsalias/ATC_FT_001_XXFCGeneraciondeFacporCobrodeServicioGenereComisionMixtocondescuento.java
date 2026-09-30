package EBS.neteos.NeteosServiciosCorresponsalias;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.neteos.LoginNeteos.ClaseLogin;
import EBS.util.neteos.PaginaInicialNeteos.EBSPaginaInicial;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_XXFCGeneraciondeFacporCobrodeServicioGenereComisionMixtocondescuento extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_XXFCGeneraciondeFacporCobrodeServicioGenereComisionMixtocondescuento_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String concurrente1 = data.get("concurrente1");
		String concurrente2 = data.get("concurrente2");
		String servicio = data.get("servicio");
		String fechaini = data.get("fechaini");
		String fechafin = data.get("fechafin");
		By rq = By.xpath(
				"//div[normalize-space()='MXOXXO-GL10CHI-CONSULTA']/parent::a/following-sibling::ul//div[normalize-space()='Request']");
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		ClaseLogin inicio = new ClaseLogin(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"NETEOS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);

		PatternUtil p = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		/*
		 * PASOS DEL CASO DE PRUEBA
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
		addStep("Ingresamos a la responsabilidad MXOXXO-GL10CHI-CONSULTA");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		u.hardWait(4);
		u.scrollToElement(rq);
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.clickStandard();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		p.runApplet();
		u.hardWait(50);
		p.nEnter(1);
		u.hardWait(6);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 6
		addStep("Ingresar el  concurrente XXFC-Generación de Fac. por Cobro de Servicio y seleccionarlo");
		p.enterText(concurrente1);
		u.hardWait(6);
		// PASO 7
         addStep("Ingresar los parámetros:"
				+ "Tipo de reporte: Servicio"
				+ "Servicio:  101870339"
				+ "Fec. Ini. Trans: XX-XX-XXXX"
				+ "Fec. fin. Trans: XX-XX-XXXX");
		
		testCase.addScreenShotCurrentStep(" ");
		// PASO 8
		addStep("Dar clic en Aceptar y Ejecutar ");
		u.hardWait(5);
		p.clickEjecutar();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(7);
		p.clickNo();
		u.hardWait(7);
		
		
		
		// PASO 9
		addStep("Dar clic en Encontrar");
		
		
		//-----------------------------------------SEGUNDO CONCURRENTE-------------------------------------------------------
		
		// PASO 10
		addStep("Ingresar el  concurrente XXFC - Generacion de Fac. Venta por Servicios Locales y seleccionarlo");
		testCase.addScreenShotCurrentStep(" ");
		
		
		
		
		
		
		// PASO 11
		addStep("Ingresar los parámetros:"
				+ "Numero de Proveedor"
				+ "Grupo Ejecucion:"
				+ "Fecha incial:XX-XX-XXXX"
				+ "Fechas final: XX-XX-XXXX");
		
		
		
		testCase.addScreenShotCurrentStep(" ");
		
		
		
		
		
		// PASO 12
		addStep("Dar clic en Aceptar y Ejecutar ");
		u.hardWait(5);
		p.clickEjecutar();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(7);
		p.clickNo();
		u.hardWait(7);
		
		
		
		// PASO 13
		addStep("Dar clic en Encontrar");
		
		
		u.close();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_XXFCGeneraciondeFacporCobrodeServicioGenereComisionMixtocondescuento_test";
	}

	@Override
	public String setTestDescription() {
		return "Verificar las  polizas creadas que no incluyan las comisiones del proveedor y mantenga las del cliente"
		     + "Verificar las Factura AR creadas que incluyan las comisiones del proveedor y el IVA correspondiente a la zona.";
	}

	@Override
	public String setTestDesigner() {
		return "ricardor4@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con registros de cobros de comision a proveedores"
				+ "ITEM Configurado MPE";
	}

	@Override
	public void beforeTest() {

	}
}