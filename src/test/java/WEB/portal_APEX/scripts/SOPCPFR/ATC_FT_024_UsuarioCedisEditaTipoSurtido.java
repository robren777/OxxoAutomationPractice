package WEB.portal_APEX.scripts.SOPCPFR;

import static org.junit.Assert.assertTrue;
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
 * Modificar los campos Tipo Surtido usando un usuario CEDIS
 * 
 * PREREQUISITOS:
 * - Acceso y credenciales al portal Operaci�n CEDIS
 * - Usuario CEDIS
 * 
 * INSUMOS:
 * - N/A
 *
 * @author Edwin Chi
 * @date 29/Octubre/2024
 */

public class ATC_FT_024_UsuarioCedisEditaTipoSurtido extends BaseExecution {
	SeleniumUtil u;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user;
	private String password;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_024_UsuarioCedisEditaTipoSurtido_test(HashMap<String, String> data) throws Exception {
		user = data.get("user");
		password = PasswordUtil.decryptPassword(data.get("password"));
				
		LoginApex loginApex = new LoginApex(u, testCase);
		Inicio inicio = new Inicio(u, testCase);
		AdmTipoSurtido admTipoSurtido = new AdmTipoSurtido(u, testCase);
		
		addStep("Ingresar al portal APEX", "Se visualiza la informaci�n correctamente");
		loginApex.proceedToPortal();
		testCase.addScreenShotCurrentStep("Screenshot");
		
		addStep("Iniciar sesi�n usando un usuario CEDIS", "Se visualiza la informaci�n correctamente");
		loginApex.login_apex(user, password);
		
		addStep("Click en Pron�stico CEDIS", "Se hace click al elemento exitosamente");
		inicio.clickProsticoCEDIS();
		
		addStep("Click en Adm Tipo Surtido", "Se hace click al elemento exitosamente");
		inicio.clickAdmTipoSurtido();

		addStep("Click en el bot�n Editar", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickEditButton();
			
		addStep("Seleccionar el Tipo Surtido como Cajas en el primer registro", "Se selecciona el valor indicado");
		admTipoSurtido.selectTipoSurtidoCajas();

		addStep("Seleccionar el Tipo Surtido como Piezas en el segundo registro", "Se selecciona el valor indicado");
		admTipoSurtido.selectTipoSurtidoPiezas();

		addStep("Seleccionar el Tipo Surtido como Refrigerados en el tercer registro", "Se selecciona el valor indicado");
		admTipoSurtido.selectTipoSurtidoRefrigerados();

		addStep("Click en el bot�n Guardar", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickSaveButton();
		admTipoSurtido.isLoadingSpinnerInvisible();
		
		addStep("Mensaje de �xito mostrado", "Se visualiza la informaci�n correctamente");
		assertTrue(admTipoSurtido.isSuccessMesageDisplayed());
		
		addStep("Validar que se guardaron los registros Cajas, Piezas y Refrigerados", "Se visualiza la informaci�n correctamente");
		assertEquals(admTipoSurtido.getFirstTipoSurtido(), "Cajas");
		assertEquals(admTipoSurtido.getSecondTipoSurtido(), "Piezas");
		assertEquals(admTipoSurtido.getThirdTipoSurtido(), "Refrigerados");
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
		return "Modificar los campos Tipo Surtido usando un usuario CEDIS";
	}

	@Override
	public String setTestDesigner() {
		return "EdwinJesusC@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_024_UsuarioCedisEditaTipoSurtido";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Acceso y credenciales al portal Operaci�n CEDIS\n"
				+ "2. Usuario CEDIS";
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}
