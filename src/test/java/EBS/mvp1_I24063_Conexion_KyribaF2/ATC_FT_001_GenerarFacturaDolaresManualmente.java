package EBS.mvp1_I24063_Conexion_KyribaF2;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCI�N: Validar que se pueda generar una factura manual en d�lares.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -ATC_FT_001_GenerarFacturaDolaresManualmente PREREQUISITOS: 1.- Tener acceso
 * a aplicaci�n de QA EBS M�xico. (AVEBQA)\r\n" 2.- Contar con acceso a EBS\r\n"
 * 3-Contar con proveedores configurados para pago en USD\r\n" 4.- Tener acceso
 * a Responsabilidad MXOXXO-APXXXXX-Super Usuario .
 *
 * INSUMOS (datos o informaci�n que necesita estar preparada o generada para
 * poder ejecutar el script automatizado): -Nombre de Responsabilidad -Nombre de
 * Carpeta -Nombre de PROVEEDOR
 *
 * @author Gilberto Aguayo Adame
 * @date 3/03/2025
 */

public class ATC_FT_001_GenerarFacturaDolaresManualmente extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_GenerarFacturaDolaresManualmente_test(HashMap<String, String> data) throws Exception {

		
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
		String Tipo = data.get("tipo");
		String LineaCaptura = data.get("LINEACAPTURA");
		String monto = data.get("MONTO");
		String taxIVA = data.get("TAXIVA");
		String FacNumber = data.get("FACTURA");
		String UNIDADOPERATIVA = data.get("UnidadOperativa");
		String IMPORTE = data.get("IMPORTE");
		String TERMINOS = data.get("Terminos");

		/*
		 * TASA-0%IVA UTILERIAS
		 */
		u = new SeleniumUtil(new IETest(), true);
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
		addStep("Ingresar a EBS --> Clic en Language y seleccionar Espa�ol --> Ingresar usuario y contrase�a --> Clic en Conectese");

		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		testCase.addScreenShotCurrentStep("save");
		inicio.login();
		// p.popUpAuthenticacion();

		// PASO 2
		addStep("Seleccionar la responsabilidad de MXOXXO-APXXXXX-Super Usuario --> Seleccionar Facturas --> Entrar --> Facturas");

		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.clickOnCarpeta(Subcarpeta);
		paginaInicial.clickEntrarFacturas();
		p.accepApp();

		// PASO 3
		addStep("\"Ingresar los datos mandatorios del encabezado de la factura\r\n" + "-Tipo\r\n"
				+ "-Socio Comercial\r\n" + "-Nro Proveedor\r\n" + "-Nombre Sucursal\r\n" + "-Fecha Factura\r\n"
				+ "-Nro Factura\r\n" + "-Div Fac\r\n" + "-Importe Factura\r\n" + "-Fecha Plazos\r\n" + "-T�rminos\r\n"
				+ "-M�todo Pago\r\n" + "-Grupo Pago\"\r\n" + "");

		assertTrue(p.pantallaGenerarFactura(), "La Pantalla Generar Factura No se Ejecuto Correctamente");
		kyriba.GenerarFactura(UNIDADOPERATIVA, proveedor, Tipo, fechainicio, FacNumber, IMPORTE, TERMINOS);
		testCase.addScreenShotCurrentStep("windowGenerarFactura");

		// PASO 4
		addStep("En la parte inferior dar clic en la pesta�a L�neas --> Ingresar los campos mandatorios\r\n"
				+ "-Nro\r\n" + "-Tipo\r\n" + "-Importe\r\n" + "-Fecha Contable\r\n"
				+ "-Cuenta de Distribuci�n por Defecto\r\n" + "-C�digo Clasificaci�n Impuesto\r\n"
				+ "-Grupo Retenciones Impuestos Factura\"\r\n" + "");

		p.facturaTabLineas(LineaCaptura, monto, taxIVA);
		testCase.addScreenShotCurrentStep("lineaContable");

		// PASO 5
		addStep("En la parte \r\n" + "superior de la pantalla dar clic en el Icono Guardar (disco amarillo) ");

		assertTrue(kyriba.btnSaveClick(), "No se pudo hacer clic en el boton save");
		;
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("lineaContable");

		p.cerrarApplicacion();
		u.hardWait(3);
		u.getDriver().quit();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_GenerarFacturaDolaresManualmente";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se pueda generar una factura manual en dolares.";
	}

	@Override
	public String setTestDesigner() {
		return "GilbertoA@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return "ATC_FT_001_GenerarFacturaDolaresManualmente";
	}

	@Override
	public String setPrerequisites() {
		return "1.- Tener acceso a aplicacion de QA EBS Mexico. (AVEBQA)\r\n" + "2.- Contar con acceso a EBS\r\n"
				+ "3-  Contar con proveedores configurados para pago en USD\r\n"
				+ "4.- Tener acceso a Responsabilidad MXOXXO-APXXXXX-Super Usuario .";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}