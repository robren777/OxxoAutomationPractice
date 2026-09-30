package EBS.oxxoPay;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.Sikuli.PatternUtil;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.pageObjectModel.EBSProvRelFormaPago;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_005_ValidarProveedorRelacionFormaPagoDevoluciones extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_005_ValidarProveedorRelacionFormaPagoDevoluciones_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String ebsPattern = data.get("ebsPattern");
		String responsabilidad = data.get("responsabilidad");
		String setup = data.get("carpeta1");
		String rfc = data.get("rfc");
		String descripcion = data.get("descripcion");
		String formaPago = data.get("formaPago");

		String consulta1 = "SELECT * FROM APPS.XXFC_RETEK_FORMAS_PAGOS_V WHERE FORMA_PAGO_ID= 'PYIO49'";

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSProvRelFormaPago relFormaPago = new EBSProvRelFormaPago(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		utils.sql.SQLUtil EBSAVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: RST Devoluciones OXXO PAY
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Español Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contraseña.");
		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-CMXXXXX-Super Usuario'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Setup -> Prov - Forma Pago");
		paginaInicial.clickOnCarpeta(setup);
		paginaInicial.clickProvFormaPago();
		u.waitForLoadPage();
		// PASO 7
		addStep("Capturar el RFC del proveedor, clic en el botón GO");
		u.sendKeys(relFormaPago.ingresarRFC(), rfc);
		relFormaPago.clickIr();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Descripción");
		assertEquals(descripcion, relFormaPago.getTextDescripcion());
		u.close();
		// PASO 8
		addStep("Conectarse a la base de datos EBS 12.2.4");
		System.out.println(consulta1);
		// PASO 9
		addStep("Ejecutar el query: SELECT * FROM APPS.XXFC_RETEK_FORMAS_PAGOS_V WHERE FORMA_PAGO_ID= 'PYIO49'. ");
		SQLResult validat = EBSAVANTE.executeQuery(consulta1);
		String atributo = validat.getData(0, "FORMA_PAGO_ID");
		boolean validar = atributo.equals(formaPago);
		System.out.print(atributo);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");

		String atributo2 = validat.getData(0, "FORMA_PAGO_DESC");
		boolean validar2 = atributo2.equals(descripcion);
		System.out.println(descripcion);
		assertFalse(!validar2, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");

		EBSAVANTE.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ValidarProveedorRelacionFormaPagoDevoluciones_test";
	}

	@Override
	public String setTestDescription() {
		return "Se validará que el proveedor se relacione a la forma de pago para devoluciones. Tener responsabilidad 'MXOXXO-CMXXXXX-Super Usuario'. ";
	}

	@Override
	public String setTestDesigner() {
		return "JessicaR@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Tener responsabilidad 'MXOXXO-CMXXXXX-Super Usuario'. ";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}