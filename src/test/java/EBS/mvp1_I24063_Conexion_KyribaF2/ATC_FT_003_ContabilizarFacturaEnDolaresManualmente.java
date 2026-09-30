package EBS.mvp1_I24063_Conexion_KyribaF2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.mvp1_Kyriba_F2.pom.pomKyribaF2;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCI�N: Validar que se pueda contabilizar una factura manual registrada
 * en dolares.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -ATC_FT_003_ContabilizarFacturaEnDolaresManualmente
 * 
 * PREREQUISITOS: 1.- Tener acceso a aplicacion de QA EBS Mexico. (AVEBQA)\r\n"
 * 2.- Contar con acceso a EBS\r\n" 3.- Contar con factura Validada pago en
 * USD\r\n" 4.- Tener acceso a Responsabilidad MXOXXO-APXXXXX-Super Usuario .
 *
 * INSUMOS (datos o informaci�n que necesita estar preparada o generada para
 * poder ejecutar el script automatizado): -Nombre de Responsabilidad -Nombre de
 * Carpeta -Nombre de PROVEEDOR
 *
 * @author Gilberto Aguayo Adame
 * @date 3/04/2025
 */
public class ATC_FT_003_ContabilizarFacturaEnDolaresManualmente extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ContabilizarFacturaEnDolaresManualmente_test(HashMap<String, String> data) throws Exception {

		/*
		 * InternetExplorerOptions options = new InternetExplorerOptions();
		 * options.introduceFlakinessByIgnoringSecurityDomains();
		 */
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getGAGUAYOHexEbsUsr;
		String password = GlobalVariables.getGAGUAYOHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String Subcarpeta = data.get("Subcarpeta");
		String campo = data.get("campo");
		String fechainicio = data.get("fechainicio");
		String fechafin = data.get("fechafin");
		String proveedor = data.get("PROVEEDOR");
		String LineaCaptura = data.get("LINEACAPTURA");
		String monto = data.get("MONTO");
		String taxIVA = data.get("TAXIVA");
		String FacNumber = data.get("FACTURA");

		/*
		 * TASA-0%IVA UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		pomKyribaF2 kyriba = new pomKyribaF2(u, testCase, data);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a EBS --> Clic en Language y seleccionar Espa�ol --> Ingresar usuario y contrase�a --> Clic en Con�ctese\r\n"
				+ "");

		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		testCase.addScreenShotCurrentStep("save");
		inicio.login();
		// p.popUpAuthenticacion();

		// PASO 2
		addStep("Seleccionar la responsabilidad de MXOXXO-APXXXXX-Super Usuario --> Seleccionar Facturas --> Entrar --> Facturas\r\n"
				+ "");

		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.clickOnCarpeta(Subcarpeta);
		paginaInicial.clickEntrarFacturas();
		p.accepApp();

		// PASO 3
		addStep("Dar clic en buscar --> ingresar los par�metros de b�squeda --> Clic en Encontrar\r\n" + "");

		p.pantallaGenerarFactura();
		testCase.addScreenShotCurrentStep("windowGenerarFactura");
		assertTrue(kyriba.BusquedaFactura(FacNumber),"No se Encontro la Factura "+FacNumber);

		// PASO 4
		addStep("Dar clic en Acciones\r\n" + "");

		kyriba.clickAccionesbtn();

		// PASO 5
		addStep("Seleccionar el check box Crear Contabilidad --> Final --> Clic en Aceptar\r\n" + "");

		kyriba.ContabilizarFactura();
		assertTrue(kyriba.ValidarEstadoFactura("EstadoContavilizado"), "La factura no se contabilizo correctamente");
		kyriba.typeKEY("ENTER");
		u.hardWait(1);
		assertTrue(kyriba.ValidarEstadoFactura("EstadoConabilizadoSi"), "La Factura No se muestra Contabilizada");
		p.cerrarApplicacion();
		u.getDriver().quit();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ContabilizarFacturaEnDolaresManualmente";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se pueda contabilizar una factura manual registrada en d�lares\r\n" + "";
	}

	@Override
	public String setTestDesigner() {
		return "GilbertoA@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return "ATC_FT_003_ContabilizarFacturaEnDolaresManualmente";
	}

	@Override
	public String setPrerequisites() {
		return "1.- Tener acceso a aplicacion de QA EBS Mexico. (AVEBQA)\r\n" + "2.- Contar con acceso a EBS\r\n"
				+ "3.- Contar con factura Validada pago en USD\r\n"
				+ "4.- Tener acceso a Responsabilidad MXOXXO-APXXXXX-Super Usuario .";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}