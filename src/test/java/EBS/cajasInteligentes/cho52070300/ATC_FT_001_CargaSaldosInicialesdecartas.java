package EBS.cajasInteligentes.cho52070300;

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

public class ATC_FT_001_CargaSaldosInicialesdecartas extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_CargaSaldosInicialesdecartas_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta1 = data.get("carpeta1");
		String carpeta2 = data.get("carpeta2");
		String campo = data.get("campo");
		String nombrearchivo = data.get("parametroexcel");
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
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52070300
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
		addStep("Ingresar a la responsabilidad 'MXXXXX-GLXXXXX-Consulta'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Solicitudes -> Request -> Standard");
		paginaInicial.clickOnCarpeta(carpeta1);
		paginaInicial.clickOnCarpeta(carpeta2);
		paginaInicial.clickStandard();
		// PASO 7
		p.inicio();
		addStep("Seleccionar Solicitud Unica clic en Aceptar.");
		p.ingresarCampo(campo);
		// PASO 8
		addStep("Ingresar en el campo Nombre XXFC - Carga de Saldos Iniciales de Carta.");
		p.buscarArchivo(nombrearchivo);
		// PASO 9
		addStep("Ingresar el parámetro de Nombre del archivo CARGASALDOS21012021.csv.");
		p.aceptarArchivo();
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
		// PASO 14
		addStep("Visualizar output");
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		p.cerrarTodoN();
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_CargaSaldosInicialesdecartas_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se ejecute correctamente el Proceso 'XXFC - Carga de Saldos Iniciales de Cartas'. Pre-requisitos: 1.- Validar el periodo en que se realizara la carga este configurado en el lookup 'XXFC_GL_SALDOS_CARTAS'."
				+ "2.- Contar con el archivo .csv que se procesara para la carga final del periodo AGO-2020."
				+ "3.- Tener asignada la responsabilidad 'MXXXXX-GLXXXXX-Consulta'";
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
		return "1.- Validar el periodo en que se realizara la carga este configurado en el lookup 'XXFC_GL_SALDOS_CARTAS'."
				+ "2.- Contar con el archivo .csv que se procesara para la carga final del periodo AGO-2020."
				+ "3.- Tener asignada la responsabilidad 'MXXXXX-GLXXXXX-Consulta'";
	}

	@Override
	public void beforeTest() {

	}
}
