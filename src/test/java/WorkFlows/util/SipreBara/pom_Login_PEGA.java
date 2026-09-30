package WorkFlows.util.SipreBara;

import java.io.File;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class pom_Login_PEGA {

	SeleniumUtil u;
	TestCase testCase;
	String user;

	private SikuliUtil sikuli = new SikuliUtil();
	private Screen screen = new Screen();

	private Pattern p1;
	private String s1;
	private String folder = "sipreBara";
	private String mainTab = "";
	private String newTab = "";

	public pom_Login_PEGA(SeleniumUtil u, TestCase testCase, String user) {
		this.u = u;
		this.testCase = testCase;
		this.user = user;

	}

	By inputUser = By.id("txtUserID");
	By btnIniciaSesion = By.id("sub");
	By menu = By.cssSelector(".portal-navigation");
	By btnMisAplicaciones = By.cssSelector(".menu-item-active > a:nth-child(1) > span:nth-child(2)");
	By aplicacionesWeb = By.cssSelector(
			"div.switchapps-listitem:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1)");
	By inicioAppsWeb = By.partialLinkText("Inicio Apps Web");
	By inicioAppsWeb2 = By.cssSelector("div#IniAppWeb > img");
	By mostrarAplicaciones = By.cssSelector(".btnBuscarApps");
	By SIPRE_BARA = By.cssSelector(
			"div.content-layout:nth-child(2) > div:nth-child(1) > div:nth-child(1) > span:nth-child(1) > a:nth-child(1)");
	By OptionSipreBara = By.linkText("Sistema de Precios BARA");
	By btnIntentaDeNuevo = By.cssSelector("button.try-again:nth-child(1)");

	String pathImages = sikuli.PatternPath("WorkFlows"+File.separator+"sipreBara");
	Pattern inicioAppsWebSikuli = sikuli.PatternImage(pathImages + "btnInicioAppsWeb.png");

	/**
	 * Inicia Sesion en PEGA
	 * 
	 * @author alicia.ceja
	 */
	public void iniciaSesion() {
		try {
			testCase.addTextEvidenceCurrentStep("Ingresar Usuario");
			u.highLight(inputUser);
			u.sendKeys(inputUser, user);
			testCase.addScreenShotCurrentStep(u, "");

			u.highLight(btnIniciaSesion);
			testCase.addScreenShotCurrentStep(u, "");
			u.unhighLight(inputUser);
			u.click(btnIniciaSesion);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al iniciar sesión---> " + e);
		}

		try {
			testCase.addTextEvidenceCurrentStep("Seleccionar Mostrar Aplicaciones");
			u.waitWebElement(mostrarAplicaciones, 120, 1);
			u.highLight(mostrarAplicaciones);
			testCase.addScreenShotCurrentStep(u, "");
			u.mouseOverAndClick(mostrarAplicaciones);
			u.unhighLight(mostrarAplicaciones);
		} catch (Exception e) {
			throw new AssertionError("Error al Mostrar Aplicaciones");
		}

		try {
			testCase.addTextEvidenceCurrentStep("Seleccionar el aplicativo SIPRE BARA");
			u.waitWebElement(SIPRE_BARA, 120, 2);
			u.highLight(SIPRE_BARA);
			testCase.addScreenShotCurrentStep(u, "");
			u.mouseOverAndClick(SIPRE_BARA);
		} catch (Exception e) {
			throw new AssertionError("No se encontró la opción se Sipre Bara");
		}

		cambiarVentana(1);
	}

	/**
	 * Inicia Sesion con usuarios Categoria y Plaza utilizando SIKULI
	 * 
	 * @author alicia.ceja
	 */
	public void iniciaSesionCatPla() {
		try {
			testCase.addTextEvidenceCurrentStep("Ingresar Usuario");
			u.highLight(inputUser);
			u.sendKeys(inputUser, user);
			testCase.addScreenShotCurrentStep(u, "");

			u.highLight(btnIniciaSesion);
			testCase.addScreenShotCurrentStep(u, "");
			u.unhighLight(inputUser);
			u.click(btnIniciaSesion);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al iniciar sesión");
		}

		try {
			testCase.addTextEvidenceCurrentStep("Seleccionar la opción Mis Aplicaciones e Inicio Apps Web");
			u.waitWebElement(menu, 140, 2);
			selectMisAplicaciones();
			u.waitWebElement(aplicacionesWeb, 120, 1);
			u.highLight(aplicacionesWeb);
			testCase.addScreenShotCurrentStep(u, "");
			u.mouseOverAndClick(aplicacionesWeb);
			u.unhighLight(aplicacionesWeb);
		} catch (Exception e) {
			throw new AssertionError("Fallo al dar clic en Mis Aplicaciones");
		}

		try {
			// testCase.addTextEvidenceCurrentStep("Seleccionar la opción Inicio Apps Web");
			// testCase.addScreenShotCurrentStep(u, "");
			sikuli.screen.wait(inicioAppsWebSikuli, 20).click();
		} catch (Exception e) {
			throw new AssertionError("Fallo al dar clic en Inicio Apps Web");
		}

		try {
			testCase.addTextEvidenceCurrentStep("Seleccionar el botón Mostrar Aplicaciones");
			u.waitWebElement(mostrarAplicaciones, 120, 1);
			u.highLight(mostrarAplicaciones);
			testCase.addScreenShotCurrentStep(u, "");
			u.mouseOverAndClick(mostrarAplicaciones);
			u.unhighLight(mostrarAplicaciones);
		} catch (Exception e) {
			throw new AssertionError("Fallo al dar clic en Mostrar Aplicaciones");
		}

		try {
			testCase.addTextEvidenceCurrentStep("Seleccionar el aplicativo SIPRE BARA");
			u.waitWebElement(OptionSipreBara, 120, 2);
			u.highLight(OptionSipreBara);
			testCase.addScreenShotCurrentStep(u, "");
			u.click(OptionSipreBara);
			u.unhighLight(OptionSipreBara);
		} catch (Exception e) {
			throw new AssertionError("Fallo al dar clic en Sipre Bara");
		}

		try {
			cambiarVentana(1);
		} catch (Exception e) {
			throw new AssertionError("Fallo al cambiar de ventana");
		}

	}

	/**
	 * Selecciona la imagen de Mis Aplicaciones para seleccionar la opcion del
	 * aplicativo de SIPRE_BARA
	 * 
	 * @throws FindFailed
	 * @throws InterruptedException
	 * @author alicia.ceja
	 */

	public void selectMisAplicaciones() throws FindFailed, InterruptedException {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "btnMisAplicaciones.png");
		screen.wait(p1, 30.0);
		screen.click();

	}

	/**
	 * Realiza un Switch a otra ventana
	 * 
	 * @param pantalla
	 * @author alicia.ceja
	 */
	public void cambiarVentana(int pantalla) {
		try {
			ArrayList<String> tabs = new ArrayList<String>(u.getDriver().getWindowHandles());
			u.getDriver().switchTo().window(tabs.get(pantalla));
		} catch (Exception e) {

		}
	}

	/**
	 * Da click en Ok en el mensaje de expiro la sesi�n para continuar con el flujo
	 * 
	 * @throws FindFailed
	 * @throws InterruptedException
	 * @author alicia.ceja
	 */

	public void errorSesion() throws FindFailed, InterruptedException {
		s1 = sikuli.PatternPath(folder = "sipreBara");
		p1 = sikuli.PatternImage(s1 + "btnAceptaSesion.png");

		if (screen.exists(p1) != null) {
			System.out.println("--------> Se encontr� el mensaje de 'La sesi�n expir�''");
			testCase.addTextEvidenceCurrentStep("Se encontr� el mensaje de 'La sesi�n expir�'");
			screen.wait(p1, 30.0);
			testCase.addScreenShotCurrentStep(u, "");
			screen.click();
		}

		System.out.println("--------> Inicio correcto");

	}

}
