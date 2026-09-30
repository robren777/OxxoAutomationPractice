package EBS.pantallaDeMigracionCrs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_ActualizaciondelRETEKCR extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_006_ActualizaciondelRETEKCR_Test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String estadoFinanciero = data.get("estadoFinanciero");
		String crSuperior = data.get("crSuperior");
		String recurso = data.get("recurso");
		String tiendaOrigen = data.get("tiendaorigen");
		String estadoDestino = data.get("estadodestino");
		String plazaDestino = data.get("plazadestino");
		String tipoEjecucion = data.get("tipoejecucion");
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: PantallaDeMigracionCrs
		 */

		// PASO 1
		addStep("Ingresar a EBS AVEBQA a la siguiente ruta: http://fcrebsqa.femcom.net:8011");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		// PASO 2
		addStep("Ingresar a la Responsabilidad MXOXXO-GLXXXXX-Super Usuario.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 3
		addStep("Ir a CRs > Transferencia de CRs.");
		paginaInicial.carpetaCRsElement.click();
		paginaInicial.clickAltaCentrosResponsabilidad();
		p.runApplet();
		u.hardWait(3);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		// PASO 4
		addStep("Ejecucion del concurrente:Ver > Solicitudes > Ejecutar una nueva Solicitud > Solicitud Unica > Aceptar > XXFC - Actualización Cr Retek");
		testCase.addScreenShotCurrentStep(
				"Ejecucion del concurrente:Ver > Solicitudes > Ejecutar una nueva Solicitud > Solicitud Unica > Aceptar > XXFC - Actualización Cr Retek");
		p.vers();
		p.clickNuevaSolicitud();
		p.buscarArchivoTab(recurso);
		u.hardWait(2);
		p.buscarArchivo(estadoFinanciero);
		p.buscarArchivo(crSuperior);
		p.buscarArchivo(tiendaOrigen);
		p.buscarArchivo(estadoDestino);
		p.buscarArchivo(plazaDestino);
		p.buscarArchivo(tiendaOrigen);
		p.buscarArchivo(tipoEjecucion);
		// PASO 5
		addStep("Se ingresan los parametros correspondientes al CR que se le actualizara el RETEK_CR.");
		testCase.addScreenShotCurrentStep(
				"Se ingresan los parametros correspondientes al CR que se le actualizara el RETEK_CR.");
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		p.ejecutar();
		p.ejecutarBotonNo();
		// PASO 6
		addStep("Se valida que se ejecute correctamente el concurrente.");
		testCase.addScreenShotCurrentStep("Se valida que se ejecute correctamente el concurrente.");
		// PASO 7
		addStep("Se valida en la BD que se asigne el RETEK_CR de la Plaza anterior a la nueva Mega Plaza.");
		testCase.addScreenShotCurrentStep("Se valida en la BD que se asigne el RETEK_CR de la Plaza anterior a la nueva Mega Plaza.");
		//Falta Queries para validar
		// PASO 8
		addStep("Dentro de la Responsabilidad de MXOXXO-GLXXXXX-Super Usuario, se ingresa al menu: CRs > Maestro de CRs.");
		testCase.addScreenShotCurrentStep("Dentro de la Responsabilidad de MXOXXO-GLXXXXX-Super Usuario, se ingresa al menu: CRs > Maestro de CRs.");
		// PASO 9
		addStep("Se ingresa el número de la Compania y el Estado Financiero a consultar.");
		testCase.addScreenShotCurrentStep("Se ingresa el numero de la Compañía y el Estado Financiero a consultar.");
		// PASO 10
		addStep("Se consulta el CR de la nueva Mega Plaza para validar que se haya asociado el RETEK_CR de la Plaza anterior.");
		testCase.addScreenShotCurrentStep("Se consulta el CR de la nueva Mega Plaza para validar que se haya asociado el RETEK_CR de la Plaza anterior.");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_ActualizaciondelRETEKCR_Test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se asigne el RETEK_CR de la Plaza anterior a la nueva Mega Plaza."
				+ "Caso no automatizable debido a que los datos de la prueba cambian con cada ejecucion"
				+ "Acceso a BD de instancia AVEBQA";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Prerequisitos: Acceso a la responsabilidad: " + "Acceso a BD de instancia AVEBQA";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}