package EBS.cajasInteligentes.cho52068369;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.tools.PlantillaProveedoresBusqueda;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSDetallePlantilla;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.pageObjectModel.EBSPlantillaDeProveedores;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_003_ActualizarRegistroCamposTipoPersonaTipoTasa extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ActualizarRegistroCamposTipoPersonaTipoTasa_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String tipo = data.get("tipo");
		String nivel = data.get("nivel");
		String tipoPersona = data.get("tipoPersona");
		String tipoTasa = data.get("tipoTasa");
		String ValidChange = "SELECT ent_tipo_proveedor, ent_tipo_movimiento, ent_tipo_persona, ent_tipo_beneficio"
				+ " FROM xxfc.xxfc_ap_plantillas_prov" + " WHERE ent_tipo_proveedor = 'ACTIVO FIJO'"
				+ " AND ent_tipo_movimiento = 'PROVEEDOR'";

		/*
		 * UTILERIAS
		 */
		utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		PlantillaProveedoresBusqueda planProvBusqueda = new PlantillaProveedoresBusqueda(u, testCase);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
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
		addStep("Dar clic en el botón de 'Conectese'.");
		inicio.login();

		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-Super Usuario'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);

		// PASO 6
		addStep("Seleccionar Plantilla de Proveedores: Plantilla de Proveedores.");
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.abrePlantillaDeProveedores();

		// PASO 7
		addStep("Realizar la busqueda para el proveedor que se desea actualizar.");
		planProvBusqueda.selectTipo(tipo);
		planProvBusqueda.selectNivel(nivel);
		planProvBusqueda.realizarBusqueda();

		// PASO 8
		addStep("Dar clic en actualizar.");
		plantillaDeProveedores.clickEditar();

		// PASO 9
		addStep("Ingresamos en los campos de Tipo de Persona F y Tipo Tasa I.");
		detallePlan.sendTipoPersona(tipoPersona);
		detallePlan.sendTipoTasa(tipoTasa);
		testCase.addScreenShotCurrentStep(u, "Llenado de campos");

		// PASO 10
		addStep("Dar click en aplicar");
		detallePlan.clickAplicar();

		// PASO 11
		addStep("Validar el cambio en la Plantilla de Proveedores, realizar consulta del proveedor.");
		plantillaDeProveedores.clickIr();
		Assert.assertEquals(plantillaDeProveedores.getTipoPersona(), tipoPersona);
		Assert.assertEquals(plantillaDeProveedores.getTipoTasa(), tipoTasa);
		testCase.addScreenShotCurrentStep(u, "Validacion de cambios");
		u.close();

		// PASO 12
		addStep("Validar en BD el cambio de en la plantilla.");
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		System.out.println(ValidChange);
		SQLResult ValidChangeExec = dbAvebqa.executeQuery(ValidChange);
		String tipo_persona = "";
		String tipo_beneficio = "";

		boolean ValidChangeRes = ValidChangeExec.isEmpty();
		if (!ValidChangeRes) {
			tipo_persona = ValidChangeExec.getData(0, "ent_tipo_persona");
			tipo_beneficio = ValidChangeExec.getData(0, "ent_tipo_beneficio");
			System.out.println("Tipo Persona: " + tipo_persona);
			System.out.println("Tipo Tasa: " + tipo_beneficio);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		} else if (ValidChangeRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta " + ValidChange);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Acceso a la responsabilidad 'MXOXXO-APXXXXX-Super Usuario'.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la correcta actualizacion en la 'Plantilla de Proveedores'."
				+ "Prerequisitos: Acceso a la responsabilidad 'MXOXXO-APXXXXX-Super Usuario'.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ActualizarRegistroCamposTipoPersonaTipoTasa_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
