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
 * Consultar informaci�n usando los criterios de b�squeda Nulo en la columna Tipo Surtido
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

public class ATC_FT_028_FiltrarPorTipoSurtidoNulo extends BaseExecution {
	SeleniumUtil u;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user;
	private String password;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_028_FiltrarPorTipoSurtidoNulo_test(HashMap<String, String> data) throws Exception {
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
			
		addStep("Seleccionar el Tipo Surtido como Nulo en el primer registro", "Se selecciona el valor indicado");
		admTipoSurtido.selectTipoSurtidoNulo();

		addStep("Click en el bot�n Guardar", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickSaveButton();
		admTipoSurtido.isLoadingSpinnerInvisible();
		
		addStep("Click en el bot�n Acciones", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickOnActionsButton();
		
		addStep("Click en el bot�n Filtro", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickOnFilterButton();
		
		addStep("Selecionar la columna Tipo Surtido", "Se selecciona el valor indicado");
		admTipoSurtido.selectColumn("Tipo Surtido");
		
		addStep("Escribir el Tipo Surtido: Nulo", "Se escribe el valor indicado");
		admTipoSurtido.typeValue("Nulo");
		
		addStep("Click en el bot�n Guardar", "Se hace click al elemento exitosamente");
		admTipoSurtido.clickOnSaveButtonFromFilter();
		
		addStep("Validar que en la tabla solo se filtra por Nulo", "Se visualizan �nicamente valores Nulos en la tabla");
		admTipoSurtido.isLoadingSpinnerInvisible();
		assertTrue(admTipoSurtido.isFilterConfirmationDisplayed());
		
		admTipoSurtido.getFilteredTipoSurtidoRecords()
			.forEach(actualTipoSurtido -> assertEquals("Nulo", actualTipoSurtido));
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
		return "Consultar informaci�n usando los criterios de b�squeda Nulo en la columna Tipo Surtido";
	}

	@Override
	public String setTestDesigner() {
		return "EdwinJesusC@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_028_FiltrarPorTipoSurtidoNulo";
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
