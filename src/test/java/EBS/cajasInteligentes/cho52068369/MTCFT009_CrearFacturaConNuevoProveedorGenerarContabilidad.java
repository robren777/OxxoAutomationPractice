package EBS.cajasInteligentes.cho52068369;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class MTCFT009_CrearFacturaConNuevoProveedorGenerarContabilidad extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void MTC_FT_009_CajasInteligentes(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");

		/*
		 * UTILERIAS
		 */
		// SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
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
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();

		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-Super Usuario'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);

		// PASO 6
		addStep("Seleccionar: Facturas -> Entrar -> Facturas");
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.clickFacturasEntrar();
		paginaInicial.clickEntrarFacturas();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Crear factura y Pago con nuevo Proveedor para verificar asignacion correcta "
				+ "de impuesto- Proveedor con Retencion de Pago";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "MTCFT009_ActualizarRegistroCamposTipoPersonaTipoTasa";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
