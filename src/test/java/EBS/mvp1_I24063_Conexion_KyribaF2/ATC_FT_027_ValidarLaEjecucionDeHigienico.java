package EBS.mvp1_I24063_Conexion_KyribaF2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.mvp1_Kyriba_F2.pom.pomKyribaF2;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCI�N: "Se deposita un MT940 en Sandbox de Kyriba hasta llegar a la
 * interface del m�dulo de CM. "
 *
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -ATC_FT_019_HU03_ArchivoDePagoUSD PREREQUISITOS: " -Contar con el repositorio
 * /ebs_data/outcoming/kyriba vacio -Tener estado de cuenta MT940 -Contar con
 * acceso a EBS -Contar con acceso a ControlM
 *
 *
 * 
 * INSUMOS (datos o informaci�n que necesita estar preparada o generada para
 * poder ejecutar el script automatizado): -Nombre de Responsabilidad -Nombre de
 * Carpeta -Nombre de PROVEEDOR
 *
 * @author Gilberto Aguayo Adame
 * @date 4/03/2025
 */

public class ATC_FT_027_ValidarLaEjecucionDeHigienico extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_HigienicoDeProveedores_test(HashMap<String, String> data) throws Exception {

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
		String LineaCaptura1 = data.get("LINEACAPTURA1");
		String LineaCaptura2 = data.get("LINEACAPTURA2");
		String LineaCaptura3 = data.get("LINEACAPTURA3");
		String LineaCaptura4 = data.get("LINEACAPTURA4");
		String monto = data.get("MONTO");
		String taxIVA = data.get("TAXIVA");
		String FacNumber = data.get("FACTURA");
		String FacNumber1 = data.get("FACTURA1");
		String FacNumber2 = data.get("FACTURA2");
		String NombreBanco = data.get("Nombre Banco");
		String NumeroBanco = data.get("Numero Banco");
		String PaisBanco = data.get("Distrito");
		String CalleYNumero = data.get("Calle y numero");
		String Ciudad = data.get("Ciudad");
		String Distrito = data.get("Distrito");
		String Estado = data.get("Estado");
		String CodigoPostal = data.get("Codigo Postal");
		String Estatus = data.get("Estatus");
		String NumeroIdentificacionTributaria = data.get("Numero Identificacion Tributaria");
		String NumeroSucursal = data.get("Numero Sucursal");
		String ABASWIFT = data.get("ABA / SWIFT");
		String Tiposucursal = data.get("Tipo sucursal");
		int NumeroDeFacturas = 2;

		/*
		 * TASA-0%IVA UTILERIAS
		 */

		u = new SeleniumUtil(new ChromeTest(), true);

		pomKyribaF2 kyriba = new pomKyribaF2(u, testCase, data);

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
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a EBS --> Clic en Language y seleccionar Espa�ol --> Ingresar usuario y contrase�a --> Clic en Conectese");

		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		testCase.addScreenShotCurrentStep("save");

		inicio.login();
		// p.popUpAuthenticacion();

		// PASO 2
		addStep("Seleccionar la responsabilidad de MXOXXO-CMXXXXX-Super Usuario --> Seleccionar Banks --> click --> Banks");

		paginaInicial.clickOnResponsabilidad(responsabilidad);
		kyriba.MXOXXO_CMXXXXX_SuperUsuarioSetUpBanks();

		// PASO 3
		addStep("Introducir nombre de Banco y dar clic en Buscar");

		kyriba.MXOXXO_CMXXXXX_BuscarNombreBank(NombreBanco);

		// PASO 3
		addStep("Validar campo \"Pa�s Banco\" y compararlo con el documento layout_bancos_y_sucursales '" + PaisBanco+"'");

		assertTrue(kyriba.ValidarHigienico(PaisBanco), "No se muestra el pais del Banco: '" + PaisBanco+"'");

		// p.accepApp();

		// PASO 4
		addStep("Validar campo \"Nombre Banco\" y compararlo con el documento layout_bancos_y_sucursales '"
				+ NombreBanco+"'");

		assertTrue(kyriba.ValidarHigienico(NombreBanco), "No se muestra el Nombre del Banco: '" + NombreBanco+"'");

		// PASO 5
		addStep("Validar campo \"N�mero Banco\" y compararlo con el documento layout_bancos_y_sucursales '"
				+ NumeroBanco+"'");

		assertTrue(kyriba.ValidarHigienico(NumeroBanco), "No se muestra el N�mero del Banco: '" + NumeroBanco+"'");

		// PASO 6
		addStep("Validar campo \"Numero Identificaci�n Tributaria\" y compararlo con el documento layout_bancos_y_sucursales '"
				+ NumeroIdentificacionTributaria+"'");

		assertTrue(kyriba.ValidarHigIDContibuyente().equals(NumeroIdentificacionTributaria),
				"No se muestra el Numero Identificacion Tributaria del Banco: '" + NumeroIdentificacionTributaria+"'");

		// PASO 7
		addStep("Validar campo \"Calle y numero\" y compararlo con el documento layout_bancos_y_sucursales '"
				+ CalleYNumero+"'");
		u.click(By.xpath("//*[@title='Bank Address']"));
		kyriba.waitUntilElementToBeClickable(By.xpath("//*[@id='N26:EnableUpdateIcon:0']/img"));
		u.click(By.xpath("//*[@id='N26:EnableUpdateIcon:0']/img"));
		assertTrue(kyriba.ValidarHigienico(CalleYNumero), "No se muestra el Calle y N�mero del Banco: '" + CalleYNumero+"'");

		// PASO 8
		addStep("Validar campo \"Ciudad\" y compararlo con el documento layout_bancos_y_sucursales '" + Ciudad+"'");

		assertTrue(kyriba.ValidarHigienico(Ciudad), "No se muestra el Nombre de Ciudad del Banco: '" + Ciudad+"'");

		// PASO 9
		addStep("Validar campo \"Distrito\" y compararlo con el documento layout_bancos_y_sucursales '" + Distrito+"'");

		assertTrue(kyriba.ValidarHigienicoDistro(Distrito), "No se muestra el Disrito del Banco: '" + Distrito+"'");

		// PASO 10
		addStep("Validar campo \"Estado\" y compararlo con el documento layout_bancos_y_sucursales '" + Estado+"'");

		assertTrue(kyriba.ValidarHigiEstado().equals(Estado), "No se muestra el Estado del Pais: '" + Estado+"'");

		// PASO 11
		addStep("Validar campo \"Codigo Postal\" y compararlo con el documento layout_bancos_y_sucursales '"
				+ CodigoPostal+"'");

		assertTrue(kyriba.ValidarHigCodigoPostal().equals(CodigoPostal),
				"No se muestra el N�mero Codigo Postal: '" + CodigoPostal+"'");

		// PASO 12
		addStep("Validar campo \"Estatus\" y compararlo con el documento layout_bancos_y_sucursales '" + Estatus+"'");

		assertTrue(kyriba.ValidarHigiEstatus().equals(Estatus), "No se muestra el Estatus del Banco: '" + Estatus+"'");

		// PASO 13
		addStep("Validar campo \"Numero Sucursal\" y compararlo con el documento layout_bancos_y_sucursales '"
				+ NumeroSucursal+"'");

		u.click(By.xpath("//*[@id='CE_BANK_SEARCH_SUBTAB']"));
		kyriba.MXOXXO_CMXXXXX_BuscarNombreBankSucursal(NombreBanco);
		assertTrue(kyriba.ValidarCampo(NumeroSucursal), "No se muestra el N�mero de Sucursal: '" + NumeroSucursal+"'");

		// PASO 14
		addStep("Validar campo \"BIC / SWIFT\" y compararlo con el documento layout_bancos_y_sucursales: '"
				+ NumeroSucursal+"'");

		assertTrue(kyriba.ValidarHigienico(ABASWIFT), "No se muestra el ABA / SWIFT del Banco: '" + ABASWIFT+"'");

		// PASO 15
		addStep("Validar campo \"Tipo Sucursal\" y compararlo con el documento layout_bancos_y_sucursales: '"
				+ Tiposucursal+"'");

		assertTrue(kyriba.ValidarHigiTipoSucursal().equals(Tiposucursal),
				"No se muestra el Tipo sucursal del Banco: '" + Tiposucursal+"'");

		// PASO 16
		addStep("Validar campo \"Pais direccion sucursal\" y compararlo con el documento layout_bancos_y_sucursales: '"
				+ PaisBanco+"'");

		assertTrue(kyriba.ValidarCampo(PaisBanco), "No se muestra el Nombre Pais Banco de Sucursal: '" + PaisBanco+"'");
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_HigienicoDeProveedores";
	}

	@Override
	public String setTestDescription() {
		return "\"Validar el proceso de generaci�n del archivo de pagos genere correctamente los archivos en el repositorio mediante la ejecuci�n del job XXFC_AP_KYRIBA_OUT_SH\r\n"
				+ "-Ruta: /ebs_data/outcoming/kyriba\r\n"
				+ "-Pagos en la tabla XXFC.XXFC_AP_KYRIBA_PAGOS con estatus en proceso = \"\"F\"\"\"\r\n" + "";
	}

	@Override
	public String setTestDesigner() {
		return "GilbertoA@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return "ATC_FT_HigienicoDeProveedores";
	}

	@Override
	public String setPrerequisites() {
		return "-Contar con el repositorio /ebs_data/outcoming/kyriba vacio\r\n" + "-Tener estado de cuenta MT940\r\n"
				+ "-Contar con acceso a EBS\r\n" + "-Contar con acceso a ControlM";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@AfterMethod
	public void afterMethod() {
		// u.getDriver().quit();
	}
}