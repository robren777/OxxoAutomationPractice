package EBS.cajasInteligentes.cho52068369;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_008_CrearFacturaNuevoProveedorGenerarContabilidad extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_CrearFacturaNuevoProveedorGenerarContabilidad_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");


		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		

		/*
		 * PASOS DEL CASO DE PRUEBA
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
		addStep("Dar clic en el boton de 'Conectarse'.");
		inicio.login();

		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-Super Usuario'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		
		// PASO 6
		addStep("Seleccionar: Facturas -> Entrar -> Facturas.");
		paginaInicial.carpetaFacturasElement.click();
		paginaInicial.carpetaFacturasEntrarElement.click();
		paginaInicial.clickEntrarFacturas();

		
		//u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_CrearFacturaNuevoProveedorGenerarContabilidad_Test";
	}

	@Override
	public String setTestDescription() {
		return "Crear factura con nuevo Proveedor para verificar asignacion correcta de impuesto- Proveedor sin Retencion de Pago.";
	}

	@Override
	public String setTestDesigner() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public void beforeTest() {

	}
}
