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

public class ATC_FT_002_ValidarlaejecuciondereporteXXOG extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarlaejecuciondereporteXXOG_test(HashMap<String, String> data) throws Exception {

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
		String fechainicio = data.get("fechainicio");
		String fechafin = data.get("fechafin");
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
		// PASO 9
		addStep("Ingresar los parámetros: Fecha Creacion Inicial: 01-NOV-2020 Fecha Creacion Final: 21-ENE-2021 Aceptar.");
		p.ingresarFechaInicioFechaFin(fechainicio, fechafin);
		p.cerrarconfoco();
		// PASO 10
		addStep("Dar click en ejecutar");
		p.ejecutar();
		p.otraSolicitud();
		// PASO 11
		addStep("Ejecutar otra solicitud");
		// PASO 12
		addStep("Dirigirse al menu: Ver(x) -> Solicitudes -> Encontrar.");
		p.vers();
		// PASO 13
		addStep("Dar clic en Visualizar Output");
		p.encontrar();
		p.visualizarOutput();
		p.cerrarTodo1();
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarlaejecuciondereporteXXOG_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la ejecución del Reporte 'XXOG-Reporte SI Creadas'. Pre-requisitos: 1.- Tener asignada la responsabilidad 'MXOXXO-WM17FAA-COORDINADOR EQP FRM'."
				+ "2.- El usuario debe estar ingresado en la tabla 'apps.xxfc_mapeos_varios'.";
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
		return "1.- Tener asignada la responsabilidad 'MXOXXO-WM17FAA-COORDINADOR EQP FRM'."
				+ "2.- El usuario debe estar ingresado en la tabla 'apps.xxfc_mapeos_varios'.";
	}

	@Override
	public void beforeTest() {

	}
}