package EBS.cajasInteligentes.cho52068369;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Locale;

import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.tools.PlantillaProveedoresBusqueda;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.pageObjectModel.EBSPlantillaDeProveedores;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidarLaExistenciaDeLosCamposTipoPersonaYTipoTasa extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarLaExistenciaDeLosCamposTipoPersonaYTipoTasa_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String filtro = data.get("labelFiltro").toLowerCase(Locale.ROOT);
		String columnFiltro = data.get("columnFiltro").toLowerCase(Locale.ROOT);

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		PlantillaProveedoresBusqueda planProvBusqueda = new PlantillaProveedoresBusqueda(u, testCase);
		EBSPlantillaDeProveedores plantillaDeProveedores = new EBSPlantillaDeProveedores(u, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Español Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contraseña.");
		// PASO 4
		addStep("Dar clic en el botón de 'Conéctese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-Super Usuario'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar Plantilla de Proveedores: Plantilla de Proveedores.");
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.abrePlantillaDeProveedores();
		// PASO 7
		addStep("Validar la existencia de los filtros 'Tipo de Tasa' y 'Tipo de Persona' en la sección de búsqueda.");
		assertTrue(plantillaDeProveedores.dropdownIsDisplayed(filtro));
		assertTrue(planProvBusqueda.verifyFilterLabel(filtro));
		// PASO 8
		addStep("Validar los campos: 'Tipo Tasa' y 'Tipo Persona' en el resultado de la búsqueda.");
		assertTrue(plantillaDeProveedores.columnIsDisplayed(columnFiltro));
		assertTrue(planProvBusqueda.verifyColumnTittle(columnFiltro));
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarLaExistenciaDeLosCamposTipoPersonaYTipoTasa_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la existencia de los campos 'Tipo Persona' y 'Tipo tasa'  en los filtros de busqueda de la pantalla 'Plantilla de Proveedores'. Acceso a la responsabilidad 'MXOXXO-APXXXXX-Super Usuario'.";
	}

	@Override
	public String setTestDesigner() {
		return "JesusH@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a la responsabilidad 'MXOXXO-APXXXXX-Super Usuario'.";
	}

	@Override
	public void beforeTest() {

	}
}
