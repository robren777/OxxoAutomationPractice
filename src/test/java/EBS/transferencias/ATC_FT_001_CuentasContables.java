package EBS.transferencias;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSMantenimientoCuentasComisiones;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_CuentasContables extends BaseExecution {
	@Test(dataProvider = "data-provider") // Agregar tu Dataprovider manualmente
	public void ATC_FT_001_CuentasContables_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String formaPago = data.get("formaPago");
		String nombre = data.get("nombre");
		String cuenta = data.get("cuenta");
		String auxiliar = data.get("auxiliar");
		String tipo = data.get("tipo");
		String nivel = data.get("nivel");
		String fechaDesde = data.get("fechaDesde");
		String fechaHasta = data.get("fechaHasta");
		String estado = data.get("estado");
		String btnCrear = data.get("btnCrear");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSMantenimientoCuentasComisiones obj = new EBSMantenimientoCuentasComisiones(u, testCase);
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
		try {
			assertEquals(obj.btnCrearElement.getText(), btnCrear);
			assertEquals(obj.formaPagoElement.getText(), formaPago);
			assertEquals(obj.nombreElement.getText(), nombre);
			assertEquals(obj.cuentaElement.getText(), cuenta);
			assertEquals(obj.auxiliarElement.getText(), auxiliar);
			assertEquals(obj.tipoElement.getText(), tipo);
			assertEquals(obj.nivelElement.getText(), nivel);
			assertEquals(obj.fechaDesdeElement.getText(), fechaDesde);
			assertEquals(obj.fechaHastaElement.getText(), fechaHasta);
			assertEquals(obj.estadoElement.getText(), estado);
			testCase.addScreenShotCurrentStep(u, " Mantenimiento a cuentas contables y Comisiones.");
			testCase.addTextEvidenceCurrentStep("Validacion de campos exitosa");
		} catch (AssertionError e) {
			testCase.addTextEvidenceCurrentStep("Validacion de campos fallida");
			System.out.println(e.toString());
		}
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
		return "Validar la correcta actualizacion en la 'Plantilla de Proveedores'."
				+ "Prerequisitos: Acceso al ambiente de pruebas: Ingresar a la liga: http://oxvebsaq1.femcom.net:8000/";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_CuentasContables_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
