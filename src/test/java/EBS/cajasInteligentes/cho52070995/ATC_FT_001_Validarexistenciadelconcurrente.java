package EBS.cajasInteligentes.cho52070995;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Validarexistenciadelconcurrente extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Validarexistenciadelconcurrente_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String adminalm = data.get("adminalm");
		String otros = data.get("otros");
		String solicitudes = data.get("solicitudes");
		String requests = data.get("requests");
		String campo = data.get("campo");
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52070995
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.\n");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano.\n");
		// PASO 3
		addStep("Ingresar usuario y contrasena.\n");
		// PASO 4
		addStep("Dar clic en el boton de \"Conectese\".\n");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad MXOXXO-WM17FAA-COORDINADOR EQP FRM ");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar Administracion de Almacen: Otros -> Solicitudes -> Requests -> Run");
		paginaInicial.clickOnCarpeta(adminalm);
		paginaInicial.clickOnCarpeta(otros);
		paginaInicial.clickOnCarpeta(solicitudes);
		paginaInicial.clickOnCarpeta(requests);
		paginaInicial.runrequests();
		// PASO 7
		addStep("Seleccionar Solicitud Única, Aceptar");
		p.inicio();
		// PASO 8
		addStep("Ingresar en el campo Nombre: XXOG-Reporte SI Creadas");
		p.ingresarCampo(campo);
		p.cerrarTodo();
		u.close();
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Validarexistenciadelconcurrente_test";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que dentro de la responsabilidad 'MXOXXO-WM17FAA-COORDINADOR EQP FRM', exista el concurrente 'XXOG-Reporte SI Creadas'. Pre-requisitos: 1.- Tener asignada la responsabilidad 'MXOXXO-WM17FAA-COORDINADOR EQP FRM'.";
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
		return "1.- Tener asignada la responsabilidad 'MXOXXO-WM17FAA-COORDINADOR EQP FRM'.";
	}

	@Override
	public void beforeTest() {

	}
}