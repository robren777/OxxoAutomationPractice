package WEB.portal_APEX.scripts.SOPCPFR;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.portal_APEX.pom.DescargaPronosticoCEDIS;
import WEB.portal_APEX.pom.Inicio;
import WEB.portal_APEX.pom.LoginApex;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCI�N: Asegurar que la informacion se muestre en el periodo de fecha
 * asignado
 * 
 * PREREQUISITOS: 1. Acceso al portal "Operaci�n Cedis" 2.- Credenciales del
 * portal
 * 
 * INSUMOS: - N/A
 *
 * @author Enrique Castilleros
 * @date 30/Octubre/2024
 */
public class ATC_FT_002_HistorialValidarFiltradoFecha extends BaseExecution {
	SeleniumUtil u;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user;
	private String password;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_HistorialValidarFiltradoFecha_test(HashMap<String, String> data) throws Exception {
		user = data.get("user");
		password = PasswordUtil.decryptPassword(data.get("password"));

		Inicio inicio = new Inicio(u, testCase);
		LoginApex login = new LoginApex(u, testCase);
		DescargaPronosticoCEDIS descargaPronosticoCEDIS = new DescargaPronosticoCEDIS(u, testCase);

		// ************************PASO 1************************

		login.proceedToPortal();
		addStep("Ingresar al portal y se inicia sesion", "Se visualiza la informacion correctamente");
		login.login_apex(user, password);

		// ************************PASO 2************************
		addStep("Dar click en 'Pronostico CEDIS'", "Se visualiza la informacion correctamente");
		assertTrue(inicio.clickProsticoCEDIS());

		// ************************PASO 3************************
		addStep("Dar click en la opcion 'Descarga Pronostico CEDIS'", "Se visualiza la informacion correctamente");
		assertTrue(descargaPronosticoCEDIS.clickDescargaPronosticoCEDIS());

		// ************************PASO 4 y 5************************
		addStep("Se selecciona el rango de fechas", "Se selecciona la fecha correctamente");
		assertTrue(descargaPronosticoCEDIS.textoHistorialFechas());

		// ************************PASO 6************************
		addStep("Dar click en 'Buscar Historial'", "Se visualiza la informacion correctamente");
		assertTrue(descargaPronosticoCEDIS.clickHistorialBuscarHistorial());
	}

	@Override
	public void beforeTest() {
		u = new SeleniumUtil(new MEdgeTest(), true);

		u.getDriver().manage().window().maximize();
		u.get(url);
	}

	@Override
	public String setTestDescription() {
		return "Asegurar que la informacion se muestre en el periodo de fecha asignado";
	}

	@Override
	public String setTestDesigner() {
		return "enriquebaruchc@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_HistorialValidarFiltradoFecha_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Acceso y credenciales al portal Operaci�n CEDIS\n" + "2.- Credenciales del portal";
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}
