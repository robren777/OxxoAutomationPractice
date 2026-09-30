package EBS.cajasInteligentes.cho52068369;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.tools.PlantillaProveedoresBusqueda;
import EBS.util.pageObjectModel.EBSDetallePlantilla;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.pageObjectModel.EBSPlantillaDeProveedores;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_ValidarLosFiltrosTipoPersonaYTipoTasa extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarLosFiltrosTipoPersonaYTipoTasa_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String tipoPersona = data.get("tipoPersona");
		String tipoTasa = data.get("tipoTasa");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		PlantillaProveedoresBusqueda planProvBusqueda = new PlantillaProveedoresBusqueda(u, testCase);
		EBSPlantillaDeProveedores plantillaDeProveedores = new EBSPlantillaDeProveedores(u, testCase);
		EBSDetallePlantilla detallePlan = new EBSDetallePlantilla(u, testCase);

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
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contrasena.");
		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-Super Usuario'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar Plantilla de Proveedores: Plantilla de Proveedores.");
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.abrePlantillaDeProveedores();
		// PASO 7
		addStep("Validar los Filtros en la busqueda.");
		planProvBusqueda.selectTasa(tipoTasa);
		planProvBusqueda.selectPersona(tipoPersona);
		planProvBusqueda.realizarBusqueda();
		// PASO 8
		addStep("Validar los campos en la pantalla 'Detalle Plantilla' al dar clic en actualizar.");
		plantillaDeProveedores.clickEditar();
		Assert.assertEquals(detallePlan.getTipoTasa(), tipoTasa);
		Assert.assertEquals(detallePlan.getTipoPersona(), tipoPersona);

		testCase.addScreenShotCurrentStep(u, "Validacion de cambios");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_ValidarLosFiltrosTipoPersonaYTipoTasa_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar los filtros 'Tipo Persona' y 'Tipo tasa' en la seccion de busqueda y que la plantilla detalle muestre los campos. Acceso a la responsabilidad 'MXOXXO-APXXXXX-Super Usuario'.";
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
