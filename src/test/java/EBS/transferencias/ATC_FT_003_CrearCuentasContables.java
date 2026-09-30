package EBS.transferencias;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.CrearCuentasTool;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSCrearCuentas;
import EBS.util.pageObjectModel.EBSMantenimientoCuentasComisiones;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_CrearCuentasContables extends BaseExecution {
	@Test(dataProvider = "data-provider") // Agregar tu Dataprovider manualmente
	public void ATC_FT_003_CrearCuentasContables_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String formaPago = data.get("formaPago");
		String cuenta = data.get("cuenta");
		String auxiliar = data.get("auxiliar");
		String nombre = data.get("nombre");
		String fechaDesde = data.get("fechaDesde");
		String fechaHasta = data.get("fechaHasta");
		String msjCuentaCreada = data.get("msjCuentaCreada");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSMantenimientoCuentasComisiones mantCuentasContables = new EBSMantenimientoCuentasComisiones(u, testCase);
		CrearCuentasTool cuentasTool = new CrearCuentasTool(u, testCase);
		EBSCrearCuentas crearCuentas = new EBSCrearCuentas(u, testCase);
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		// PASO 1
		addStep("Ingresar a la liga: http://oxvebsaq1.femcom.net:8000/");
		inicio.login();
		u.hardWait(3);

		// PASO 2
		addStep("Ingresar a la responsabilidad 'MXOXXOGLXXXXXSuperUsuario' /Mantenimiento a cuentas contables y Comisiones.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.clickMantCuentasConatble();
		mantCuentasContables.btnCrearElement.click();

		// PASO 3
		addStep("Click en el boton APLICAR");
		cuentasTool.camposObligatoriosCrearCuentas(formaPago, cuenta, auxiliar, nombre, fechaDesde, fechaHasta);
		testCase.addScreenShotCurrentStep(u, "Cuenta Creada");
		assertEquals(crearCuentas.msjCuentaCreadaElement.getText(), msjCuentaCreada);
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Acceso al ambiente de pruebas: Ingresar a la liga: http://oxvebsaq1.femcom.net:8000/";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se den de alta las cuentas de manera correcta para la formas de pago."
				+ "Prerequisitos: Acceso al ambiente de pruebas: Ingresar a la liga: http://oxvebsaq1.femcom.net:8000/";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_CrearCuentasContables_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
