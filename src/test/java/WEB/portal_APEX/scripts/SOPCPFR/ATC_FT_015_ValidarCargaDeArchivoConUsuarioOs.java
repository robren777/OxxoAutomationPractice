package WEB.portal_APEX.scripts.SOPCPFR;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
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
 * Validar que solo el usuario OS no pueda cargar archivos
 * 
 * PREREQUISITOS:
 * - Acceso y credenciales al portal Operaci�n CEDIS
 * - Usuario OS
 * 
 * INSUMOS:
 * - NA
 *
 * @author Edwin Chi
 * @date 29/Octubre/2024
 */

public class ATC_FT_015_ValidarCargaDeArchivoConUsuarioOs extends BaseExecution {
	SeleniumUtil u;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user;
	private String password;


	@Test(dataProvider = "data-provider")
	public void ATC_FT_015_ValidarCargaDeArchivoConUsuarioOs_test(HashMap<String, String> data) throws Exception {
		user = data.get("user");
		password = PasswordUtil.decryptPassword(data.get("password"));
			
		LoginApex loginApex = new LoginApex(u, testCase);
		Inicio inicio = new Inicio(u, testCase);
		ExpansionTiendas expansionTiendas = new ExpansionTiendas(u, testCase);
		
		addStep("Ingresar al portal APEX", "Se visualiza la informaci�n correctamente");
		loginApex.proceedToPortal();
		testCase.addScreenShotCurrentStep("Screenshot");
		
		addStep("Iniciar sesi�n usando un usuario OS", "Se visualiza la informaci�n correctamente");
		loginApex.login_apex(user, password);
		
		addStep("Click en Pron�stico CEDIS", "Se hace click al elemento exitosamente");
		inicio.clickProsticoCEDIS();
		
		addStep("Click en Expansi�n de Tiendas", "Se hace click al elemento exitosamente");
		inicio.clickExpansionDeTiendas();

		addStep("Validar que la tabla 'Expansi�n de Tienda C�lculo' est� disponible para el usuario OS", "La tabla es visible");
		assertTrue(expansionTiendas.isExpansionDeTiendasCalculoDisplayed());
				
		addStep("Validar que la tabla 'Carga Archivo Expansi�n de Tiendas LOG' no est� disponible para el usuario OS", "La tabla est� oculta");
		assertTrue(expansionTiendas.isTableCargaArchivoExpansionDeTiendasLogHidden());
		testCase.addScreenShotCurrentStep("Screenshot");
	}

	@Override
	public void beforeTest() {
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		u.get(url);
	}

	@Override
	public String setTestDescription() {
		return "Validar que solo el usuario OS no pueda cargar archivos";
	}

	@Override
	public String setTestDesigner() {
		return "EdwinJesusC@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_015_ValidarCargaDeArchivoConUsuarioOs";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Acceso y credenciales al portal Operaci�n CEDIS\n"
				+ "2. Usuario OS";
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}
