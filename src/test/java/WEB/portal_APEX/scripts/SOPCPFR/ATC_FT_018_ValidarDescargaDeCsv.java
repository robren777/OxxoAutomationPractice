package WEB.portal_APEX.scripts.SOPCPFR;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.MetodosExtra;
import WEB.portal_APEX.pom.ExpansionTiendas;
import WEB.portal_APEX.pom.Inicio;
import WEB.portal_APEX.pom.LoginApex;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCI�N:
 * Validar la descarga del archivo csv de la tabla Expansi�n de Tienda C�lculo
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

public class ATC_FT_018_ValidarDescargaDeCsv extends BaseExecution {
	SeleniumUtil u;
	MetodosExtra me;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user;
	private String password;


	@Test(dataProvider = "data-provider")
	public void ATC_FT_018_ValidarDescargaDeCsv_test(HashMap<String, String> data) throws Exception {
		me = new MetodosExtra(u, testCase);
		user = data.get("user");
		password = PasswordUtil.decryptPassword(data.get("password"));
			
		String csvFileName = data.get("nombre_del_archivo");
		String rutaDelArchivo = System.getProperty("user.dir") + File.separator + "Config\\Input\\Insumos" + File.separator + csvFileName;

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
		
		addStep("Click en el bot�n Buscar", "Se hace click al elemento exitosamente");
		expansionTiendas.clickBuscarButton();
		expansionTiendas.isLoadingSpinnerInvisible();
		
		addStep("Validar la informaci�n de archivo es cargada en la tabla", "Existen elementos en la tabla");
		expansionTiendas.goToEndOfPage();
		testCase.addScreenShotCurrentStep("Screenshot");	
		boolean isThereFechaInTable = expansionTiendas.getFechaRecordsFromExpansionDeTiendaCalculo()
			.stream()
			.allMatch(fecha -> !fecha.getText().trim().isEmpty());			
		assertTrue(isThereFechaInTable);
		
		addStep("Click en el bot�n Descargar", "Se hace click al elemento exitosamente");
		expansionTiendas.clickDescargarButton();
		
		addStep("Click en la opci�n CSV", "Se hace click al elemento exitosamente");
		expansionTiendas.clickCsvOption();
		
		addStep("Click en el bot�n Descargar", "Se hace click al elemento exitosamente");
		expansionTiendas.clickDownloadButton();
		
		addStep("Mensaje de �xito mostrado", "Se visualiza la informaci�n correctamente");
		assertTrue(expansionTiendas.isSuccessMessageDisplayed());
		
		String downloadedfileName = me.obtenerRutaDelUltimoArchivoModificado("csv").split("Downloads")[1].replace("\\", "");
		
		addStep("Validar las columnas del archivo descargado", "El archivo CSV contiene las columnas: Fecha, Cedis y Total Tiendas");
		List<String> csvLines = Files.readAllLines(Paths.get(me.obtenerRutaDelUltimoArchivoModificado("csv")));
		testCase.addTextEvidenceCurrentStep("Columnas encontradas en el CSV: " + csvLines.get(0));
		boolean doesFileHaveColumns = csvLines.stream()
				.anyMatch(line -> line.contains("Fecha") && line.contains("Cedis") && line.contains("Total Tiendas"));
		assertTrue(doesFileHaveColumns);

		addStep("Validar los registros del archivo descargado", "El archivo CSV contiene registros");
		testCase.addTextEvidenceCurrentStep("El CSV tiene la siguiente cantidad de l�neas: " + csvLines.size());
		boolean doesHaveInformation = csvLines.size() > 1;
		assertTrue(doesHaveInformation);

		addStep("Validar los registros del archivo descargado", "El archivo CSV debe comenzar con el nombre 'expansion'");
		testCase.addTextEvidenceCurrentStep("El archivo CSV contiene el nombre: " + downloadedfileName);
		assertTrue(downloadedfileName.startsWith("expansion"), downloadedfileName + " debe comenzar con: expansion");

		addStep("Validar la extensi�n del archivo descargado", "El archivo CSV debe terminar con la extensi�n .csv");
		testCase.addTextEvidenceCurrentStep("El archivo CSV contiene el nombre y extensi�n: " + downloadedfileName);
		assertTrue(downloadedfileName.endsWith(".csv"), downloadedfileName + " debe terminar con: .csv");	
	}
	
	@Override
	public void beforeTest() {
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		u.get(url);
	}

	@Override
	public String setTestDescription() {
		return "Validar la descarga del archivo csv de la tabla Expansi�n de Tienda C�lculo";
	}

	@Override
	public String setTestDesigner() {
		return "EdwinJesusC@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_018_ValidarDescargaDeCsv";
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
