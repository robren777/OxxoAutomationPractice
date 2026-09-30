package EBS.avante.cho52074189;

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

public class ATC_FT_002_ValidarEjecucionConcurrenteXXFC_ADQImpresionOrdenCompraAbierta
		extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarEjecucionConcurrenteXXFC_ADQImpresionOrdenCompraAbierta_test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombre1 = data.get("nombre1");
		String nombre2 = data.get("nombre2");

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
		 * PASOS DEL CASO DE PRUEBA. Matriz: cho52074189
		 */
		System.out.println("error " + ebsUrl);
		// PASO 1
		addStep("Ingresar al ambiente de EBS");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Español Latinoamericano");
		// PASO 3
		addStep("Ingresar usuario y contraseña");
		// PASO 4
		addStep("Dar clic en el botón de 'Conectese'");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-PO10HER-Compras' ");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar Otros, solicitudes, Requests y RUN");
		paginaInicial.clickOrdenesDeCompra();
		p.runApplet();
		// PASO 7
		addStep("Seleccionar Solicitud Unica y Aceptar");
		p.enter();
		System.out.println("Sigue picarle a ver");
		p.vers();
		p.ejecutarNuevaSolicitud();
		u.hardWait(5);
		// PASO 8
		addStep("Ingresar en el campo el nombre");
		p.ingresarNombreAcentos(nombre1, nombre2);
		u.hardWait(2);
		// PASO 9
		// addStep("Ingresar los parametros " + plaza + "," + fechaInicial + "," +
		// fechaFinal);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "" + "1 Tener asignada la responsabilidad 'MXOXXO-PO10HER-Compras'"
				+ "2 Contar con una OC con varios artículos iguales";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return ""
				+ "Validar que al ejecutar la impresion del PDF de un Orden de Compra con varios articulos iguales  muestre correcta la cantidad y el importe.";

	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_ValidarEjecucionConcurrenteXXFC_ADQImpresionOrdenCompraAbierta_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}