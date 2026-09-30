package EBS.cajasInteligentes.cho52071606;

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

public class ATC_FT_002_ValidarelprocesoXXFCconcedisinterior extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarelprocesoXXFCconcedisinterior_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta1 = data.get("carpeta1");
		String campo = data.get("campo");
		String plaza = data.get("plaza");
		String fechauno = data.get("fechauno");
		String fechados = data.get("fechados");
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
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52071606
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
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-CONSULTAS'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar Otro: Ejecutar Solicitud.");
		paginaInicial.clickOnCarpeta(carpeta1);
		paginaInicial.clickEjecutarSolicitudes();
		// PASO 7
		addStep("Seleccionar Solicitud Unica clic en Aceptar.");
		p.inicio();
		// PASO 8
		addStep("Ingresar en el campo Nombre: 'XXFC- Reporte Auditoria Polizas Manuales Cuentas Impuesto', Tab.");
		p.buscarArchivoTab(campo);
		// PASO 9
		addStep("Ingresar los parametros: Plaza o Cedis: 10TMP Fecha Desde: 18-FEB-2021 Fecha Hasta: 18-FEB-2021 Aceptar.");
		p.CamposPlazaFechas(plaza, fechauno, fechados);
		p.cerrarconfoco();
		// PASO 10
		addStep("Ejecutar otra solicitud");
		p.ejecutar();
		p.otraSolicitud();
		// PASO 11
		addStep("Dirigirse al menu: Ver(x) -> Solicitudes -> Encontrar.");
		p.vers();
		p.encontrar();
		// PASO 12
		addStep("Dar clic en Visualizar Output del proceso 'XXFC- Impresion Reporte Auditoria Polizas Manuales Cuentas Impuesto'.");
		p.verificarEstado("ejecutandoVerde", 6000000);
		p.visualizarOutput();
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		p.cerrarconteclas();
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_ValidarelprocesoXXFCconcedisinterior_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar el proceso 'XXFC- Reporte Auditoria Polizas Manuales Cuentas Impuesto' lance el proceso 'XXFC- Impresion Reporte Auditoria Polizas Manuales Cuentas Impuesto' y finalice correctamente con una salida en excel. Pre-requisitos: 1.- Acceso a la responsabilidad 'MXOXXO-APXXXXX-CONSULTAS'."
				+ "2.- Contar con facturas (Standard/Nota de Credito) manuales desde AP contabilizadas y Polizas Manuales en GL";
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
		return "1.- Acceso a la responsabilidad 'MXOXXO-APXXXXX-CONSULTAS'."
				+ "2.- Contar con facturas (Standard/Nota de Credito) manuales desde AP contabilizadas y Polizas Manuales en GL";
	}

	@Override
	public void beforeTest() {

	}
}
