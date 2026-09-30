package WEB.portal_APEX.scripts.SOPCPFR;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
 * Acceso al men� Adm Tipo Surtido para un usuario OS
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

public class ATC_FT_023_ValidarAccesoAAdmTipoSurtidoUsuarioOs extends BaseExecution {
	SeleniumUtil u;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user;
	private String password;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_023_ValidarAccesoAAdmTipoSurtidoUsuarioOs_test(HashMap<String, String> data) throws Exception {
		user = data.get("user");
		password = PasswordUtil.decryptPassword(data.get("password"));
		
		List<String> expectedTipoSurtido = Arrays.asList(data.get("tipo_surtido").split(","));
		List<String> actualTipoSurtido;
				
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
		
		addStep("Validar el nombre de la tabla: Loc Class - Tipo Surtido", "Se visualiza la informaci�n correctamente");
		assertTrue(admTipoSurtido.isTableTitleDisplayed());
		
		addStep("Validar el bot�n Editar y Guardar son mostrados en pantalla", "Se visualiza la informaci�n correctamente");
		testCase.addScreenShotCurrentStep("Screenshot");
		assertTrue(admTipoSurtido.isEditButtonDisplayed());
		assertTrue(admTipoSurtido.isSaveButtonDisplayed());
		
		String initalLocClass = admTipoSurtido.getFirstLocClass();
		String initalCedisOperativo = admTipoSurtido.getFirstCedisOperativo();
		String initalTipoSurtido = admTipoSurtido.getFirstTipoSurtido();
		
		addStep("Click en el bot�n Editar", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickEditButton();
		
		addStep("Editar el primer Cedis Operativo", "Se selecciona el valor indicado");
		admTipoSurtido.editFirstCedisOperativo("008");
		
		addStep("Validar que el Cedis Operativo no es editable", "El Cedis Operativo no cambi�");
		testCase.addScreenShotCurrentStep("Screenshot");
		assertEquals(admTipoSurtido.getFirstCedisOperativo(), initalCedisOperativo);
	    
		addStep("Editar el primer Loc Class agregando la palabra: Test", "Se intenta editar el campo");
		admTipoSurtido.editFirstLocClass("Test");
		
		addStep("Validar que el Loc Class no es editable: ", "El Loc Class no cambi�");
		testCase.addScreenShotCurrentStep("Screenshot");
		assertEquals(admTipoSurtido.getFirstLocClass(), initalLocClass);
			
		addStep("Editar el primer Tipo Surtido", "Se intenta editar el campo");
		admTipoSurtido.editFirstTipoSurtido();
		
		addStep("Validar que el Tipo Surtido no es editable", "El Tipo Surtido no cambi�");
		testCase.addScreenShotCurrentStep("Screenshot");
		assertEquals(admTipoSurtido.getFirstTipoSurtido(), initalTipoSurtido);
		
		addStep("Validar que el usuario OS ve todos los CEDIS ", "Se visualizan solo los CEDIS del usuario");
		testCase.addScreenShotCurrentStep("Screenshot");		
		assertTrue(admTipoSurtido.getAllCedisText().size() > 0);
			
		addStep("Validar los datos de la columna Tipo Surtido", "Los datos de la columna Tipo Surtido no cambiaron");
		testCase.addScreenShotCurrentStep("Screenshot");
		actualTipoSurtido =  admTipoSurtido.getTipoSurtidoText();
		assertTrue(actualTipoSurtido.equals(expectedTipoSurtido));	
	}

	@Override
	public void beforeTest() {
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		u.get(url);
	}

	@Override
	public String setTestDescription() {
		return "Acceso al men� Adm Tipo Surtido para un usuario OS";
	}

	@Override
	public String setTestDesigner() {
		return "EdwinJesusC@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_023_ValidarAccesoAAdmTipoSurtidoUsuarioOs";
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
