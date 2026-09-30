package EBS.transferencias;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.CrearComisionesTool;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSCrearComisiones;
import EBS.util.pageObjectModel.EBSMantenimientoCuentasComisiones;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_CrearComisiones extends BaseExecution {
	@Test(dataProvider = "data-provider") // Agregar tu Dataprovider manualmente
	public void ATC_FT_004_CrearComisiones_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String formaPago = data.get("formaPago");
		String comision = data.get("comision");
		String fechaDesde = data.get("fechaDesde");
		String fechaHasta = data.get("fechaHasta");
		String msjComisionCreada = data.get("msjComisionCreada");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSMantenimientoCuentasComisiones mantCuentasContables = new EBSMantenimientoCuentasComisiones(u, testCase);
		EBSCrearComisiones crearComisiones = new EBSCrearComisiones(u, testCase);
		CrearComisionesTool comisionesTool = new CrearComisionesTool(u, testCase);
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
		mantCuentasContables.btnComisionesElement.click();
		mantCuentasContables.btnCrearComElement.click();

		// PASO 3
		addStep("Click en el boton APLICAR");
		crearComisiones.estadoElement.click();
		comisionesTool.camposObligatoriosCrearComisiones(formaPago, comision, fechaDesde, fechaHasta);
		testCase.addScreenShotCurrentStep(u, "Crear Comisiones");
		crearComisiones.aplicarElement.click();
		assertEquals(crearComisiones.msjComisionCreadaElement.getText(), msjComisionCreada);
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
		return "Validar que se den de alta de manera correcta las comisiones para la formas de pago."
				+ "Prerequisitos: Acceso al ambiente de pruebas: Ingresar a la liga: http://oxvebsaq1.femcom.net:8000/"
				+ "Verificar que no exista una Comision con la misma forma de pago ya creada, si existe hay que borrarla.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_CrearComisiones_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
