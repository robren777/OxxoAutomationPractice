package EBS.simetrik.CHG0049983;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.EBSDeskApp.pomSimetrik;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/*
 * DESCRIPCION:
 * Crea y elimina un item desde la pantalla de grupos en el aplicativo de EBS
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCA:
 * MTC-FT-007- Validar configuracion de ITEMS de CORRESPONSAL�AS (Altas, Bajas) en pantalla EBS
 * 
 * PREREQUISITOS:
 * -Acceso a la responsabilidad 'MXOXXO-GL-BackOffice Conciliaciones SF y SNF'.
 * -Usar Google Chrome
 * -Acceso al aplicativo EBS
 * 
 * INSUMOS:
 * -Usuario y password para EBS
 * -Nombre del grupo donde se adminsitraran los items
 * -Nombre para nuevo servicio (item) (Puede ser null)
 * -Descripcion para el nuevo servicio (item)
 * 
 * @author 1000068915 - Jorge Hernandez
 * @date 19/Diciembre/2024
 */

public class ATC_FT_007_ValidarConfiguracionAltasBajas_Corresponsalias_EBS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_007_ValidarConfiguracionAltasBajas_Corresponsalias_EBS_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String PatternsImg = "EBS_SIMETRIK";
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String group = data.get("group");
		String servicio = data.get("servicio");
		String descripcion = data.get("descripcion");

		boolean result;

		/*
		 * UTILERIAS
		 */
		u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial ebs = new EBSPaginaInicial(u, testCase, PatternsImg);
		pomSimetrik pom = new pomSimetrik(u, testCase, PatternsImg);

		servicio = ebs.getService(servicio);
		// PASO 1
		addStep("Ingresar a EBS mediante el el enlace: \r\n" + "\r\n" + ebsUrl);
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.loginInternacional();
		
		// PASO 2
		addStep("Dirigirse a la responsabilidad MXOXXO-GL-BackOffice Conciliaciones SF y SNF. Seleccionar la opci�n Captura de grupos para Consultas");
		result = ebs.clickOnResponsabilidad(responsabilidad);
		assertTrue(result, "No ha encontrado la responsabilidad buscada");

		result = ebs.clickCapturaGruposConsultas();
		assertTrue(result, "Fallo al ingresar a la pantalla de grupos");

		// PASO 3
		addStep("Ingresar en el campo Grupo el texto \"SIMETRIK_CORRESP\" y dar clic en el boton Go");
		result = ebs.searchGroup(group);
		assertTrue(result, "Fallo al ingresar el nombre del grupo");

		// PASO 4
		addStep("Dar clic en el icono de la lupa del registro con el Grupo de Ejecucion \"SIMETRIK_CORRESP\"");
		result = ebs.clickViewItems(group);
		assertTrue(result, "No se muestra la lista de items del grupo ");


		// PASO 5
		addStep("En la pantalla de items dar clic en el bot�n \"Crear\"");
		result = ebs.clicBtnCrearItem();
		assertTrue(result, "No se muestra la lista de items del grupo ");

		// PASO 6
		addStep("Ingresar los valores de Servicio y Descripcion en sus respectivos campos. SI APARECE PANTALLA EMERGENTE DE BUSQUEDA SE CIERRA.");
		result = ebs.fillCreateItem(servicio, descripcion);
		assertTrue(result, "No se completo exitosamente el registro del item");
		ebs.closeWindowsExtra();

		// PASO 7
		addStep("Dar clic en el boton \"Aplicar\"");
		result = ebs.clicBtnAplicar();
		assertTrue(result, "No se muestra el mensaje de confirmacion");

		// PASO 8
		addStep("Dar clic en el boton \"Grupos\"");
		result = ebs.clicBtnGrupos();
		assertTrue(result, "No se muestra la pantalla de grupos");

		// PASO 9
		addStep("Dar clic de nuevo en el icono de la lupa para mostrar los items del grupo seleccionado. Buscar el registro del item creado y dar clic en el boton del contenedor de basura (Eliminar Item) ");
		result = ebs.clickViewItems(group);
		assertTrue(result, "No se muestra la lista de items del grupo ");

		result = pom.searchServicio(servicio);
		assertTrue(result, "No se ha encontrado el item creado");

		result = pom.clicBtnDelete(servicio);
		assertTrue(result, "No se muestra el mensaje preguntando si queremos eliminar el item");

		// PASO 10
		addStep("Dar clic en Boton YES ");
		result = pom.clicYesDelete();
		assertTrue(result, "No se muestra el mensaje de confirmacion");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_ValidarConfiguracionAltasBajas_Corresponsalias_EBS_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se realicen acciones crear y borrar items del grupo SIMETRIK_CORRESP en pantalla EBS";
	}

	@Override
	public String setTestDesigner() {
		return "1000068915 - Jorge Hernandez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a la responsabilidad 'MXOXXO-GL-BackOffice Conciliaciones SF y SNF'.";
	}

	@Override
	public void beforeTest() {

	}

	@AfterMethod
	public void afterMethod() {
		try {
			u.getDriver().quit();
		} catch (Exception e) {

		}
	}
}
