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
 * Filtrar la informaci�n por CEDIS y LOC CLASS
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

public class ATC_FT_027_FiltrarPorCedisYLocClass extends BaseExecution {
	SeleniumUtil u;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user;
	private String password;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_027_FiltrarPorCedisYLocClass_test(HashMap<String, String> data) throws Exception {
		user = data.get("user");
		password = PasswordUtil.decryptPassword(data.get("password"));
		
		String expectedCedis = data.get("cedis");
		String expectedLocClass = data.get("loc_class");
				
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
				
		addStep("Click en el bot�n Acciones", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickOnActionsButton();
		
		addStep("Click en el bot�n Filtro", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickOnFilterButton();
		
		addStep("Selecionar la columna Cedis", "Se selecciona el valor indicado");
		admTipoSurtido.selectColumn("Cedis Operativo");
		
		addStep("Escribir un CEDIS",  "Se escribe el CEDIS al que pertenece al usuario");
		admTipoSurtido.typeValue(expectedCedis);
		
		addStep("Click en el bot�n Guardar", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickOnSaveButtonFromFilter();
		
		addStep("Validar los filtros de la tabla" , "La tabla solo se filtra por el CEDIS esperado");
		admTipoSurtido.isLoadingSpinnerInvisible();
		assertTrue(admTipoSurtido.isFilterConfirmationDisplayed());		
		admTipoSurtido.getFilteredCedisRecords()
			.forEach(actualCedis -> assertEquals(expectedCedis, actualCedis));
		testCase.addScreenShotCurrentStep("Screenshot");

		addStep("Click en el bot�n para remover el filtro", "Se hace click al elemento exitosamente");
		admTipoSurtido.removeFilter();
		admTipoSurtido.isLoadingSpinnerInvisible();
		testCase.addScreenShotCurrentStep("Screenshot");

		addStep("Click en el bot�n Acciones", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickOnActionsButton();
		
		addStep("Click en el bot�n Filtro", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickOnFilterButton();
		
		addStep("Selecionar la columna Loc Class", "Se selecciona el valor indicado");
		admTipoSurtido.selectColumn("Loc Class");
		
		addStep("Escribir un Loc Class", "Se escribe el Loc Class");
		admTipoSurtido.typeValue(expectedLocClass);
		
		addStep("Click en el bot�n Guardar", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickOnSaveButtonFromFilter();
		
		addStep("Validar los filtros de la tabla" , "La tabla solo se filtra por el Loc Class esperado");
		admTipoSurtido.isLoadingSpinnerInvisible();
		assertTrue(admTipoSurtido.isFilterConfirmationDisplayed());
		
		admTipoSurtido.getFilteredLocClassRecords()
			.forEach(actualLocClass -> assertEquals(expectedLocClass, actualLocClass));
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
		return "Filtrar la informaci�n por CEDIS y LOC CLASS";
	}

	@Override
	public String setTestDesigner() {
		return "EdwinJesusC@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_027_FiltrarPorCedisYLocClass";
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
