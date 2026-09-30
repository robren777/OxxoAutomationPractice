package WEB.portal_APEX.scripts.SOPCPFR;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
 * Acceso al men� Adm Tipo Surtido para un usuario CEDIS
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

public class ATC_FT_022_ValidarAccesoAAdmTipoSurtido extends BaseExecution {
	SeleniumUtil u;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user;
	private String password;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_022_ValidarAccesoAAdmTipoSurtido_test(HashMap<String, String> data) throws Exception {
		user = data.get("user");
		password = PasswordUtil.decryptPassword(data.get("password"));
		
		String[] userCedis = data.get("all_cedis").split(",");
		String editCedisValue = data.get("edit_cedis_value");
		String editLocClassValue = data.get("edit_loc_class_value");		
		List<String> expectedTipoSurtido = Arrays.asList(data.get("tipo_surtido").split(","));
		List<String> actualTipoSurtido;
				
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
		
		addStep("Validar el nombre de la tabla", "El nombre de la tabla debe ser: Loc Class - Tipo Surtido");
		assertTrue(admTipoSurtido.isTableTitleDisplayed());
		
		addStep("Validar los botones en pantalla", "Validar el bot�n Editar y Guardar son mostrados en pantalla");
		testCase.addScreenShotCurrentStep("Screenshot");
		assertTrue(admTipoSurtido.isEditButtonDisplayed());
		assertTrue(admTipoSurtido.isSaveButtonDisplayed());
		
		String initalLocClass = admTipoSurtido.getFirstLocClass();
		String initalCedisOperativo = admTipoSurtido.getFirstCedisOperativo();
		String initalTipoSurtido = admTipoSurtido.getFirstTipoSurtido();
		
		addStep("Click en el bot�n Editar", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickEditButton();
		
		addStep("Editar el primer Cedis Operativo", "Se intenta escribir el valor indicado");
		admTipoSurtido.editFirstCedisOperativo(editCedisValue);
		
		addStep("Validar que el campo Cedis Operativo no es editable", "El campo Cedis Operativo no es editable");
		testCase.addScreenShotCurrentStep("Screenshot");
		assertEquals(admTipoSurtido.getFirstCedisOperativo(), initalCedisOperativo);
	    
		addStep("Editar el primer Loc Class agregando la palabra: Test", "Se intenta escribir el valor indicado");
		admTipoSurtido.editFirstLocClass(editLocClassValue);
		
		addStep("Validar que el campo Loc Class no es editable", "Validar que el Loc Class no es editable");
		testCase.addScreenShotCurrentStep("Screenshot");
		assertEquals(admTipoSurtido.getFirstLocClass(), initalLocClass);
			
		addStep("Editar el primer Tipo Surtido", "Se escribe el valor indicado");
		admTipoSurtido.editFirstTipoSurtido();
		
		addStep("Validar el Tipo de Surtido", "El Tipo Surtido es editable");
		testCase.addScreenShotCurrentStep("Screenshot");
		assertNotEquals(admTipoSurtido.getFirstTipoSurtido(), initalTipoSurtido);
		
		addStep("Validar los CEDIS del usuario", "Validar que el usuario CEDIS solo pueda ver sus cedis asignados");
		testCase.addScreenShotCurrentStep("Screenshot");
		boolean doesAllCedisInTableAreValid = admTipoSurtido.getAllCedisText()
			.stream()
			.allMatch(cedisInTable -> Arrays.asList(userCedis).contains(cedisInTable));
		
		assertTrue(doesAllCedisInTableAreValid);
			
		addStep("Validar los datos en Tipo Surtido", "Los datos de la columna Tipo Surtido sean los esperados");
		admTipoSurtido.clickEditButton();
		admTipoSurtido.clickOnFirstTipoSurtido();
		admTipoSurtido.clickOnFirstTipoSurtido();
		actualTipoSurtido = admTipoSurtido.getTipoSurtidoText();
		
		assertTrue(actualTipoSurtido.equals(expectedTipoSurtido), actualTipoSurtido + " debe ser: " + expectedTipoSurtido);	
	}

	@Override
	public void beforeTest() {
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		u.get(url);
	}

	@Override
	public String setTestDescription() {
		return "Acceso al men� Adm Tipo Surtido para un usuario CEDIS";
	}

	@Override
	public String setTestDesigner() {
		return "EdwinJesusC@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_022_ValidarAccesoAAdmTipoSurtido";
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
