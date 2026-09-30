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
 * DESCRIPCI�N: Asegurar que la informacion se muestre filtrado por Tipo Surtido
 * 
 * PREREQUISITOS: 1. Acceso al portal "Operaci�n Cedis" 2.-Credenciales del
 * portal
 * 
 * INSUMOS: - N/A
 *
 * @author Enrique Castilleros
 * @date 30/Octubre/2024
 */
public class ATC_FT_007_PronosticoFiltradoTipoSurtido extends BaseExecution {
	SeleniumUtil u;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user;
	private String password;
	private String tipo_surtido;
	private String cedis;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_PronosticoFiltradoTipoSurtido_test(HashMap<String, String> data) throws Exception {
		user = data.get("user");
		password = PasswordUtil.decryptPassword(data.get("password"));
		tipo_surtido = data.get("tipo_surtido");
		cedis = data.get("cedis");

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
		assertTrue(descargaPronosticoCEDIS.textoPronosticoFechas());

		// ************************PASO 6************************
		addStep("Dar clic en 'Buscar Pronostico'", "Se visualiza la informacion correctamente");
		testCase.addScreenShotCurrentStep("Screenshot");
		descargaPronosticoCEDIS.clickPronosticoBuscar();
		
		addStep("Validar que existan registros en la tabla", "Se visualiza registros en la tabla");
		testCase.addScreenShotCurrentStep("Screenshot");
		assertTrue(descargaPronosticoCEDIS.sonMostradosLosRegistros());

		// ************************PASO 7************************

		addStep("Dar clic en 'Tipo Surtido Pronostico'", "Se visualiza la informacion correctamente");
		descargaPronosticoCEDIS.selectPronosticoCEDISpronostico(cedis);
		descargaPronosticoCEDIS.selectPronosticoCEDISTipoSurtido(tipo_surtido);

		// ************************PASO 8************************
		addStep("Dar clic en 'Buscar Pronostico'", "Se visualiza la informacion correctamente");
		testCase.addScreenShotCurrentStep("Screenshot");
		descargaPronosticoCEDIS.clickPronosticoBuscar();
		
		addStep("Validar que existan registros en la tabla", "Se visualiza registros en la tabla");
		testCase.addScreenShotCurrentStep("Screenshot");
		assertTrue(descargaPronosticoCEDIS.sonMostradosLosRegistros());

	}

	@Override
	public void beforeTest() {
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();
		u.get(url);
	}

	@Override
	public String setTestDescription() {
		return "Asegurar que la informacion se muestre filtrado por Tipo Surtido";
	}

	@Override
	public String setTestDesigner() {
		return "enriquebaruchc@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_PronosticoFiltradoTipoSurtido";
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
