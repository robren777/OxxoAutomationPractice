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
 * Validar que los filtros por fecha funcionan adecuadamente
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

public class ATC_FT_020_FiltrarPorColumnas extends BaseExecution {
	SeleniumUtil u;
	MetodosExtra me;
	private String url = "https://10.180.48.23:8080/ords/logistica_mx/r/f_108/login_desktop";
	private String user;
	private String password;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_020_FiltrarPorColumnas_test(HashMap<String, String> data) throws Exception {
		me = new MetodosExtra(u, testCase);
		user = data.get("user");
		password = PasswordUtil.decryptPassword(data.get("password"));
		
		String fechaInicio = data.get("fecha_inicio");
		String fechaFin = data.get("fecha_fin");
		String csvFileName = data.get("nombre_del_archivo");
		String expectedCedis = data.get("cedis");
		String expectedCsvFecha = data.get("csv_fecha");
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
		testCase.addScreenShotCurrentStep("Screenshot");
		boolean isThereFechaInTable = expansionTiendas.getFechaRecordsFromExpansionDeTiendaCalculo()
			.stream()
			.allMatch(fecha -> !fecha.getText().trim().isEmpty());	
		
		assertTrue(isThereFechaInTable);
		
		addStep("Selecciona una Fecha Inicio", "Se selecciona el valor indicado");
		expansionTiendas.typeFechaInicio(fechaInicio);
		
		addStep("Selecciona una Fecha Fin", "Se selecciona el valor indicado");
		expansionTiendas.typeFechaFin(fechaFin);
		
		addStep("Selecciona un Cedis", "Se selecciona el valor indicado");
		expansionTiendas.selectCedis(expectedCedis);
		
		addStep("Click en el bot�n Buscar", "Se hace click al elemento exitosamente");
		expansionTiendas.clickBuscarButton();
		expansionTiendas.isLoadingSpinnerInvisible();
		
		addStep("Validar los filtros en fechas", "La tabla filtr� por el rango de fechas correspondiente");
		expansionTiendas.goToEndOfPage();
		testCase.addScreenShotCurrentStep("Screenshot");
		boolean isFilteredByFecha= expansionTiendas.getFilteredFechaRecordsFromExpansionDeTiendaCalculo()
			.stream()
			.filter(fecha -> !fecha.getText().trim().isEmpty())
			.allMatch(fecha -> fecha.getText().trim().contains("NOV-2024"));
		assertTrue(isFilteredByFecha);
		
		addStep("Validar los filtros en CEDIS", "Validar que la tabla filtr� por el cedis correspondiente");
		testCase.addScreenShotCurrentStep("Screenshot");
		boolean isFilteredByCedis= expansionTiendas.getFilteredCedisRecordsFromExpansionDeTiendaCalculo()
			.stream()
			.filter(cedis -> !cedis.getText().trim().isEmpty())
			.allMatch(cedis -> cedis.getText().trim().contains(expectedCedis));
		assertTrue(isFilteredByCedis);

		addStep("Click en el bot�n Descargar", "Se hace click al elemento exitosamente");
		expansionTiendas.clickDescargarButton();
		
		addStep("Click en la opci�n CSV", "Se hace click al elemento exitosamente");
		expansionTiendas.clickCsvOption();
		
		addStep("Click en el bot�n Descargar", "Se hace click al elemento exitosamente");
		expansionTiendas.clickDownloadButton();
		
		addStep("Mensaje de �xito mostrado", "Se visualiza la informaci�n correctamente");
		assertTrue(expansionTiendas.isSuccessMessageDisplayed());
		
		addStep("Validar el contenido de las columnas del archivo descargado", "El archivo CSV contiene las columnas: Fecha, Cedis y Total Tiendas");
		List<String> csvLines = Files.readAllLines(Paths.get(me.obtenerRutaDelUltimoArchivoModificado("csv")));
		boolean doesFileHaveColumns = csvLines.stream().anyMatch(line -> line.contains("Fecha") && line.contains("Cedis") && line.contains("Total Tiendas"));
		testCase.addTextEvidenceCurrentStep("Columnas encontradas en el CSV: " + csvLines.get(0));
		assertTrue(doesFileHaveColumns);

		addStep("Validar el contenido filtrado del archivo descargado", "El archivo descagado contiene informaci�n del contenido filtrado");
		csvLines.stream()
			.filter(line -> !line.contains("Fecha"))
			.forEach(line -> {
				testCase.addTextEvidenceCurrentStep("La l�nea contiene " + expectedCedis + " y " + expectedCsvFecha + ": " + line.toString());
				assertTrue(line.contains(expectedCsvFecha), line + " debe contener: " + expectedCsvFecha);
				assertTrue(line.contains(expectedCedis), line + " debe contener: " + expectedCedis);
			});
	}

	@Override
	public void beforeTest() {
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		u.get(url);
	}

	@Override
	public String setTestDescription() {
		return "Validar que los filtros por fecha funcionan adecuadamente";
	}

	@Override
	public String setTestDesigner() {
		return "EdwinJesusC@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_020_FiltrarPorColumnas";
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
