package EBS.nexxo.MigracionPortalEquipamento;

import static org.testng.Assert.assertEquals;

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

public class ATC_FT_004_ValidarMuestraNumeroNombreArticulo extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarMuestraNumeroNombreArticulo_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String articulos = data.get("articulos");
        String numero = data.get("numero");
        String variableNumero = data.get("variablenumero");
        String numeroTotal = numero+variableNumero;
        String v = "";

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
		 * PASOS DEL CASO DE PRUEBA, Matriz: MigracionPortalEquipamento
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
		addStep("Ingresar a la Responsabilidad de 'MXOXXO-IN17FAA-Admon_ACT_FIJO_OS'.");
		testCase.addScreenShotCurrentStep(u, "'MXOXXO-IN17FAA-Admon_ACT_FIJO_OS'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Carga de Archivos.");
		paginaInicial.clickArticulos();
		paginaInicial.clickArticulosOrganizacion();
		p.runApplet();
		u.hardWait(4);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		p.clickPuntosNombre();
		p.buscarArchivo(articulos);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(4);
		// PASO 7
		addStep("Validar que se muestre el numero correcto del articulo.");
		v=p.textoCopiadoConvariable(variableNumero);
		u.hardWait(3);
		System.out.println("numerototal" + numeroTotal + "variablev"+ v); 
		assertEquals(numeroTotal,v);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarMuestraNumeroNombreArticulo_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se muestre la información correcta del Articulo"
				+ "Pre-requisitos: Contar con la Responsabilidad de 'MXOXXO-IN17FAA-ADMON' Entrar al Menu de Transacciones --> Articulos de Organizacion Crear Articulo 1 anteriormente."
		        + "NOTA : Caso construido debido al nivel de complicacion, se requiere usuario especifico para continuar con pruebas y la generacion de un articulo distinto en cada uno";

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
		return "Pre-requisitos: Contar con la Responsabilidad de 'MXOXXO-IN17FAA-ADMON' Entrar al Menu de Transacciones --> Articulos de Organizacion Crear Articulo 1 anteriormente."
		        + "NOTA : Caso construido debido al nivel de complicacion, se requiere usuario especifico para continuar con pruebas y la generacion de un articulo distinto en cada uno";



	}

	@Override
	public void beforeTest() {

	}
}