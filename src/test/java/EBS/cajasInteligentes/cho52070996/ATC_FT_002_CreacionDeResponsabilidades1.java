package EBS.cajasInteligentes.cho52070996;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.CargaDeArchivosTool;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSCargaDeArchivo;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_CreacionDeResponsabilidades1 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_CreacionDeResponsabilidades1_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String filepath = data.get("filepath");
		String proceso = data.get("proceso");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		CargaDeArchivosTool archivosTool = new CargaDeArchivosTool(u, testCase);
		EBSCargaDeArchivo cargaArchivos = new EBSCargaDeArchivo(u, testCase);

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
		addStep("Ingresar a la responsabilidad 'MXOXXO-FNDXXXXX-Usuario carga masiva'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);

		// PASO 6
		addStep("Seleccionar: Carga de Archivos.");
		u.hardWait(2);

		// PASO 7
		addStep("Dar clic en el boton de 'Browse…' para buscar el archivo a cargar con la informacion de las responsabilidades.");

		// PASO 8
		addStep("Seleccionar el archivo");
		// archivosTool.selectFile(filepath);
		archivosTool.selectFileCurrentProject(filepath);

		// PASO 9
		addStep("Dar clic en el boton de Lupa y seleccionar 'Ir'.");

		// PASO 10
		addStep("Dar clic en el Proceso 'Creacion de responsabilidad'.");
		cargaArchivos.procesoElement.sendKeys(proceso);

		// PASO 11
		addStep("Dar clic en el boton 'Cargar'.");
		cargaArchivos.btnCargarElement.click();

		// PASO 12
		addStep("Validar el proceso dentro del menu: Ver(x) -> Solicitudes -> Encontrar.");
		// Aun no se puede realizar esta validacion
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_CreacionDeResponsabilidades1_Test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la creacion de Responsabilidades en base a una ya existente."
				+ "1.- Contar con acceso a la responsabilidad 'MXOXXO-FNDXXXXX-Usuario carga masiva'."
				+ "2.- Archivo ‘.txt’ a cargar, con la responsabilidad base y la responsabilidad a crear, separados por un pipe (|)."
				+ "3.- Contar con acceso a  la responsabilidad 'MXOXXO-HELP-DESK'";
	}

	@Override
	public String setTestDesigner() {
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.- Tener asignada la responsabilidad 'MXOXXO-APXXXXX-CONSULTAS'."
				+ "2.- Haber creado proveedor desde PANP(Matriz de Casos de Prueba CHO 52068368 PANP P1.xlsx).";
	}

	@Override
	public void beforeTest() {

	}
}
