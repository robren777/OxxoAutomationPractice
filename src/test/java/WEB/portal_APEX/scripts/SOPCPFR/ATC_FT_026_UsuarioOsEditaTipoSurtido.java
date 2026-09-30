package WEB.portal_APEX.scripts.SOPCPFR;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.portal_APEX.pom.AdmTipoSurtido;
import WEB.portal_APEX.pom.Inicio;
import WEB.portal_APEX.pom.LoginApex;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCI�N:
 * Modificar los campos Tipo Surtido usando un usuario OS
 * 
 * PREREQUISITOS:
 * - Acceso y credenciales al portal Operaci�n CEDIS
 * - Usuario OS
 * 
 * INSUMOS:
 * - N/A
 *
 * @author Edwin Chi
 * @date 29/Octubre/2024
 */

public class ATC_FT_026_UsuarioOsEditaTipoSurtido extends BaseExecution {
	SeleniumUtil u;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user;
	private String password;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_026_UsuarioOsEditaTipoSurtido_test(HashMap<String, String> data) throws Exception {
		user = data.get("user");
		password = PasswordUtil.decryptPassword(data.get("password"));
		
		String initalTipoSurtido;
				
		LoginApex loginApex = new LoginApex(u, testCase);
		Inicio inicio = new Inicio(u, testCase);
		AdmTipoSurtido admTipoSurtido = new AdmTipoSurtido(u, testCase);
		
		addStep("Ingresar al portal APEX", "Se visualiza la informaci�n correctamente");
		loginApex.proceedToPortal();
		testCase.addScreenShotCurrentStep("Screenshot");
		
		addStep("Iniciar sesi�n usando un usuario OS", "Se visualiza la informaci�n correctamente");
		loginApex.login_apex(user, password);
		
		addStep("Click en Pron�stico CEDIS", "Se hace click al elemento exitosamente");
		inicio.clickProsticoCEDIS();
		
		addStep("Click en Adm Tipo Surtido", "Se hace click al elemento exitosamente");
		inicio.clickAdmTipoSurtido();

		addStep("Click en el bot�n Editar", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickEditButton();

		initalTipoSurtido = admTipoSurtido.getFirstTipoSurtido();
	
		addStep("Editar el primer Tipo Surtido", "Se intenta editar el primer Tipo Surtido");
		admTipoSurtido.editFirstTipoSurtido();
		
		addStep("Validar los elementos de la tabla desp�es de la edici�n", "El Tipo Surtido no cambi�");
		testCase.addScreenShotCurrentStep("Screenshot");
		assertEquals(admTipoSurtido.getFirstTipoSurtido(), initalTipoSurtido);
	}

	@Override
	public void beforeTest() {
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		u.get(url);
	}

	@Override
	public String setTestDescription() {
		return "Modificar los campos Tipo Surtido usando un usuario OS";
	}

	@Override
	public String setTestDesigner() {
		return "EdwinJesusC@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_026_UsuarioOsEditaTipoSurtido";
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
