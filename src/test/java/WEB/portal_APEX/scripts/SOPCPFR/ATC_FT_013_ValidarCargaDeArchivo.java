package WEB.portal_APEX.scripts.SOPCPFR;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
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
 * Validar la carga de archivo con la nomenclatura 'EXPTIEN_aaaammdd.csv'
 * 
 * PREREQUISITOS:
 * - Acceso y credenciales al portal Operaci�n CEDIS
 * - Usuario CEDIS configurado con n�mero de CEDIS espec�ficos
 * 
 * INSUMOS:
 * - Archivo CSV que incluya solo CEDIS asignados al usuario CEDIS
 *
 * @author Edwin Chi
 * @date 29/Octubre/2024
 */

public class ATC_FT_013_ValidarCargaDeArchivo extends BaseExecution {
	SeleniumUtil u;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user;
	private String password;


	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_ValidarCargaDeArchivo_test(HashMap<String, String> data) throws Exception {
		user = data.get("user");
		password = PasswordUtil.decryptPassword(data.get("password"));
		
		String expectedType = "text/csv";
		String expectedStatus = "PROCESADO";	
		String nombreDelArchivo = data.get("nombre_del_archivo");
		String rutaDelArchivo = System.getProperty("user.dir") + File.separator + "Config\\Input\\Insumos" + File.separator + nombreDelArchivo;

		
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
		
		addStep("Click en la tab Cargar Archivo", "Se hace click al elemento exitosamente");
		expansionTiendas.clickTabCargarArchivo();
		testCase.addScreenShotCurrentStep("Screenshot");
		
		addStep("Cargar archivo CVS", "El archivo CVS es cargado en la plataforma");
		expansionTiendas.cargarArchivo(rutaDelArchivo);
		testCase.addScreenShotCurrentStep("Screenshot");
		
		addStep("Click en el bot�n Cargar Archivo del modal", "Se hace click al elemento exitosamente");
		expansionTiendas.clickBotonCargarArchivo();
		testCase.addScreenShotCurrentStep("Screenshot");
		
		assertTrue(expansionTiendas.desaparecioVentanaCargarArchivo());
		expansionTiendas.reloadPage();

		addStep("Validar que el Estatus es CARGADO", "Se visualiza la informaci�n correctamente");
		assertTrue(expansionTiendas.isStatusCargadoDisplayed());
		testCase.addScreenShotCurrentStep("Screenshot");
		
		addStep("Click en la tab Procesa Archivo", "Se hace click al elemento exitosamente");
		expansionTiendas.clickTabProcesaArchivo();
		testCase.addScreenShotCurrentStep("Screenshot");
		
		addStep("Click en OK en el modal", "Se hace click al elemento exitosamente");
		expansionTiendas.clickOkButtonFromModal();
		testCase.addScreenShotCurrentStep("Screenshot");

		addStep("Validar que el Estatus es PROCESADO", "Se visualiza la informaci�n correctamente");
		assertTrue(expansionTiendas.isStatusProcesadoDisplayed());
		testCase.addScreenShotCurrentStep("Screenshot");
		
		addStep("Validar el contenido en los campos en la tabla", "El usuario, el nombre del archivo, el tipo y status es mostrado");
		testCase.addScreenShotCurrentStep("Screenshot");
		assertEquals(expansionTiendas.getUsuarioFromTable(), user);
		assertEquals(expansionTiendas.getNombreArchivoFromTable(), nombreDelArchivo);
		assertEquals(expansionTiendas.getTipoFromTable(), expectedType);
		assertEquals(expansionTiendas.getStatusFromTable(), expectedStatus);
	}

	@Override
	public void beforeTest() {
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		u.get(url);
	}

	@Override
	public String setTestDescription() {
		return "Validar la carga de archivo con la nomenclatura 'EXPTIEN_aaaammdd.csv'";
	}

	@Override
	public String setTestDesigner() {
		return "EdwinJesusC@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_ValidarCargaDeArchivo";
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
