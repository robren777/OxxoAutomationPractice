package EBS.MVP2_RST.scripts.MVP2_RST_E01_HU03;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.edge.EdgeDriver;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.MVP2_RST.om.BaseComisionDif;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Validar el atributo "Integrador" en consulta de proveedores, que
 * permitirá identificar el tipo de comisión por socio
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_003_ValidarCheckBoxIntegradorSeleccionadoEnPantallaProveedorRelacionadoAFormaDePago
 *
 * PREREQUISITOS: - Tener configurado el ambiente de EBS - Tema de Chrome en
 * modo obscuro
 *
 * INSUMOS: - N/A
 *
 * @author Mariana Avitia
 * @date 27/Marzo/2025
 */
public class ATC_FT_003_ValidarCheckBoxIntegradorSeleccionadoEnPantallaProveedorRelacionadoAFormaDePago
		extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_003_ValidarCheckBoxIntegradorSeleccionadoEnPantallaProveedorRelacionadoAFormaDePago_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("ERST MVP2");

		// Variables globales
		String ebsUrl = GlobalVariables.Url_Ebs_Comision;
		String userName = data.get("UserEBS");
		String password = PasswordUtil.decryptPassword(data.get("PasswordEBS"));
		// Ruta relativa (dentro de la estructura de tu proyecto
		File file2 = new File(System.getProperty("user.dir") + "\\Patterns\\EBS");

		// variables
		String formaPago = data.get("formaPago");

		// utileria
		SeleniumUtil uSelenium = new SeleniumUtil(new EdgeDriver());
		LoginTool inicio = new LoginTool(uSelenium, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(uSelenium, testCase);
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		SikuliUtil sk = new SikuliUtil();
		PatternUtil pattern = new PatternUtil(sk, uSelenium, null, null, ImgPatter, null, file2);
		BaseComisionDif BaseComision = new BaseComisionDif(uSelenium, testCase, sk, ImgPatter);

		// Comienza codigo
		System.out.println(
				"*****************************************PASO 1 y 2 Login************************************************");
		addStep("Se ingresa a la página de EBS e iniciar sesion");
		assertTrue(inicio.login(), "No se realiza el inicio de sesion");

		System.out.println(
				"*****************************************PASO 3************************************************");

		addStep("Dar clic sobre la liga \"MXOXXO-CMXXXXX-Super Usuario Configurar\"  y  después dar clic dar clic sobre ejecutar");
		assertTrue(paginaInicial.clickOnResponsabilidad("MXOXXO-CMXXXXX-Super Usuario Configurar"),
				"No existe la responsabilidad");

		System.out.println(
				"*****************************************PASO 4************************************************");
		addStep("Click forma de pago");
		assertTrue(BaseComision.ClickOnCarpeta("formaPago"), "No encuentra la carpeta Request");

		addStep("Se ingresa la forma de pago 'PYIO24'corresóndiente a HPAY");
		assertTrue(BaseComision.typeInFormaPago(formaPago), "No se pudo ingresar la forma de pago");

		BaseComision.Enter2(1);
		addStep("Actualizar forma de pago");
		assertTrue(BaseComision.btnActualizarFormaPago(), "No se encontro el boton actualizar forma de pago");

		System.out.println(
				"*****************************************PASO 5************************************************");
		addStep("Validar que exista check box de atributo \"Integrador\"  y se pueda modificar");
		assertTrue(BaseComision.existeCheckBoxIntegrador(),
				"No se encontro el checkbox Integrador en actualizar forma de pago");

		System.out.println(
				"*****************************************PASO 6************************************************");
		addStep("Click en check box \"Integrador\" para activarlo");
		assertTrue(BaseComision.checkIntegrador(), "No fue posible cambiar el estado del checkbox integrador");

		System.out.println(
				"*****************************************PASO 7************************************************");
		addStep("Click en botón \"Apply\"");
		assertTrue(BaseComision.btnAplicarCambiosFormaPago(), "No se encontro el boton actualizar forma de pago");

		System.out.println(
				"*****************************************PASO 8************************************************");
		addStep("Validar mensaje \"Confirmado. El proveedor ha sido actualizado satisfactoriamente\"");
		assertTrue(BaseComision.mensajeCambiosAplicadosProveedorFormaPago(),
				"No se encontro el mensaje de cambioa aplicados");

		System.out.println(
				"*****************************************PASO 9************************************************");
		pattern.closeActiveWindow();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Ambiente EBS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que los cambios se hayan aplicado y el campo integrador este activo en EBS ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mariana Gabriela Avitia";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_ValidarCheckBoxIntegradorSeleccionadoEnPantallaProveedorRelacionadoAFormaDePago_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
