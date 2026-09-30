package WEB.portal_APEX.scripts.SOPCPFR;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
 * Validar la carga de archivo es reflejada a otros usuarios que contengan el mismo CEDIS
 * 
 * PREREQUISITOS:
 * - Acceso y credenciales al portal Operaci�n CEDIS
 * - Usuario CEDIS que tenga configurado los CEDIS 9999999056 y 9999999034
 * - Otro usuario CEDIS que tenga configurado los CEDIS 9999999056 y 9999999034
 * 
 * INSUMOS:
 * - Archivo CVS para la carga donde se tengan configurados los CEDIS 9999999056 y 9999999034
 * 
 * @author Edwin Chi
 * @date 07/Noviembre/2024
 */

public class ATC_FT_029_CargaCompartidaEntreCedis extends BaseExecution {
	SeleniumUtil u;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user1;
	private String password1;
	private String user2;
	private String password2;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_029_CargaCompartidaEntreCedis_test(HashMap<String, String> data) throws Exception {
		user1 = data.get("user_1");
		password1 = PasswordUtil.decryptPassword(data.get("password_1"));

		user2 = data.get("user_2");
		password2 = PasswordUtil.decryptPassword(data.get("password_2"));

		String fechaInicio = data.get("fecha_inicio");
		String fechaFin = data.get("fecha_fin");
		
		String nombreDelArchivo = data.get("nombre_del_archivo");
		
		List<String> expectedCedisCompartidos = Arrays.asList(data.get("cedis_compartidos").split(","));
		String rutaDelArchivo = System.getProperty("user.dir") + File.separator + "Config\\Input\\Insumos" + File.separator + nombreDelArchivo;
		
		LoginApex loginApex = new LoginApex(u, testCase);
		Inicio inicio = new Inicio(u, testCase);
		ExpansionTiendas expansionTiendas = new ExpansionTiendas(u, testCase);
		
		addStep("Ingresar al portal APEX", "Se visualiza la informaci�n correctamente");
		loginApex.proceedToPortal();
		testCase.addScreenShotCurrentStep("Screenshot");
		
		addStep("Iniciar sesi�n usando un usuario CEDIS", "Se visualiza la informaci�n correctamente");
		loginApex.login_apex(user1, password1);
		
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
		
		addStep("Selecciona una Fecha Inicio", "Se selecciona el valor indicado");
		expansionTiendas.typeFechaInicio(fechaInicio);
		
		addStep("Selecciona una Fecha Fin", "Se selecciona el valor indicado");
		expansionTiendas.typeFechaFin(fechaFin);
		
		addStep("Click en el bot�n Buscar", "Se hace click al elemento exitosamente");
		expansionTiendas.clickBuscarButton();
		expansionTiendas.isLoadingSpinnerInvisible();
			
		addStep("Validar el contenido de la tabla", "Los CEDIS del usuario existen en la tabla");
		expansionTiendas.goToEndOfPage();
		testCase.addScreenShotCurrentStep("Screenshot");
		boolean isExpectedCedisInScreen = expansionTiendas.getCedisRecordsFromExpansionDeTiendaCalculo()
			.stream()
			.map(cedisInScreen->cedisInScreen.getText().trim())
			.anyMatch(cedisInScreen -> cedisInScreen.contains(expectedCedisCompartidos.get(0)));
		
		assertTrue(isExpectedCedisInScreen);

		addStep("Click en el bot�n Salir", "El usuario CEDIS cierra sesi�n");
		testCase.addScreenShotCurrentStep("Screenshot");
		inicio.cerrarSesion();

		addStep("Iniciar sesi�n usando un usuario CEDIS diferente", "Se visualiza la informaci�n correctamente");
		loginApex.login_apex(user2, password2);
		
		addStep("Click en Pron�stico CEDIS", "Se hace click al elemento exitosamente");
		inicio.clickProsticoCEDIS();
		
		addStep("Click en Expansi�n de Tiendas", "Se hace click al elemento exitosamente");
		inicio.clickExpansionDeTiendas();

		addStep("Selecciona una Fecha Inicio", "Se selecciona el valor indicado");
		expansionTiendas.typeFechaInicio(fechaInicio);
		
		addStep("Selecciona una Fecha Fin", "Se selecciona el valor indicado");
		expansionTiendas.typeFechaFin(fechaFin);

		addStep("Click en el bot�n Buscar", "Se hace click al elemento exitosamente");
		expansionTiendas.clickBuscarButton();
		expansionTiendas.isLoadingSpinnerInvisible();

		addStep("Validar el CEDIS compartido haya sido modificado por el primer usuario", "El usuario anterior aparece que ha modificado el cedis correspondiente");
		expansionTiendas.goToEndOfPage();
		testCase.addScreenShotCurrentStep("Screenshot");
		String rowTextFromFirstSharedCedis = expansionTiendas.getRowsFromCedis(expectedCedisCompartidos.get(0));
		String rowTextFromSecondSharedCedis = expansionTiendas.getRowsFromCedis(expectedCedisCompartidos.get(1));
		assertTrue(rowTextFromFirstSharedCedis.contains(user1), rowTextFromFirstSharedCedis + " debe contener: " + user1);
		assertTrue(rowTextFromSecondSharedCedis.contains(user1), rowTextFromSecondSharedCedis + " debe contener: " + user1);
	}

	@Override
	public void beforeTest() {
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		u.get(url);
	}

	@Override
	public String setTestDescription() {
		return "Validar la carga de archivo es reflejada a otros usuarios que contengan el mismo CEDIS";
	}

	@Override
	public String setTestDesigner() {
		return "EdwinJesusC@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_029_CargaCompartidaEntreCedis";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Acceso y credenciales al portal Operaci�n CEDIS\n"
				+ "2. Usuario CEDIS que tenga configurado los CEDIS 9999999056 y 9999999034\n"
				+ "3. Otro usuario CEDIS que tenga configurado los CEDIS 9999999056 y 9999999034\n"
				+ "4. Archivo CVS para la carga donde se tengan configurados los CEDIS 9999999056 y 9999999034\n";
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}
