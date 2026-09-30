package WEB.portal_APEX.scripts.SOPCPFR;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.portal_APEX.pom.ExpansionTiendas;
import WEB.portal_APEX.pom.Inicio;
import WEB.portal_APEX.pom.LoginApex;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCI�N:
 * Verificar que los usuarios OS y CEDIS tienen acceso al men� Expanci�n de Tiendas
 * 
 * PREREQUISITOS:
 * - Acceso y credenciales al portal Operaci�n CEDIS
 * - Usuario CEDIS configurado con n�mero de CEDIS espec�ficos
 * 
 * INSUMOS:
 * - N/A
 *
 * @author Edwin Chi
 * @date 29/Octubre/2024
 */

public class ATC_FT_012_AccesoMenuExpansionTiendas extends BaseExecution {
	SeleniumUtil u;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user;
	private String password;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_AccesoMenuExpansionTiendas_test(HashMap<String, String> data) throws Exception {
		user = data.get("user");
		password = PasswordUtil.decryptPassword(data.get("password"));
		
		LoginApex loginApex = new LoginApex(u, testCase);
		Inicio inicio = new Inicio(u, testCase);
		ExpansionTiendas expansionTiendas = new ExpansionTiendas(u, testCase);
		
		addStep("Ingresar al portal APEX", "Se visualiza la informaci�n correctamente");
		loginApex.proceedToPortal();
		testCase.addScreenShotCurrentStep("Screenshot");
		
		addStep("Iniciar sesi�n usando un usuario CEDIS", "Se visualiza la informaci�n correctamente");
		loginApex.login_apex(user, password);
		
		addStep("Click en Pron�stico CEDIS", "Se hace click al elemento exitosamente");
		inicio.clickProsticoCEDIS();
		
		addStep("Click en Expansi�n de Tiendas", "Se hace click al elemento exitosamente");
		inicio.clickExpansionDeTiendas();
		
		addStep("Click en el bot�n Buscar", "Se hace click al elemento exitosamente");
		expansionTiendas.clickBuscarButton();
		expansionTiendas.isLoadingSpinnerInvisible();
		
		addStep("Validar la secci�n de carga", "La tabla es mostrada: Carga Archivo Expansi�n de Tiendas LOG");
		assertTrue(expansionTiendas.isCargaArchivoExpansionDeTiendasLogDisplayed());
		
		addStep("Validar la secci�n de c�lculo de tiendas", "La tabla es mostrada: Expansi�n de Tienda C�lculo");
		expansionTiendas.goToEndOfPage();
		assertTrue(expansionTiendas.isExpansionDeTiendasCalculoDisplayed());
	}

	@Override
	public void beforeTest() {
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();
		u.get(url);
	}

	@Override
	public String setTestDescription() {
		return "Verificar que los usuarios OS y CEDIS tienen acceso al men� Expanci�n de Tiendas";
	}

	@Override
	public String setTestDesigner() {
		return "EdwinJesusC@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_AccesoMenuExpansionTiendas";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Acceso y credenciales al portal Operaci�n CEDIS\n"
				+ "2. Usuario CEDIS configurado con n�mero de CEDIS espec�ficos";
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}
