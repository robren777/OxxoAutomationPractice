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

public class ATC_FT_002_Comisiones extends BaseExecution {
	@Test(dataProvider = "data-provider") // Agregar tu Dataprovider manualmente
	public void ATC_FT_002_Comisiones_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String formaPago = data.get("formaPago");
		String metodoPago = data.get("metodoPago");
		String comision = data.get("comision");
		String tipo = data.get("tipo");
		String tasaIva = data.get("tasaIva");
		String fechaDesde = data.get("fechaDesde");
		String fechaHasta = data.get("fechaHasta");
		String estado = data.get("estado");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSMantenimientoCuentasComisiones mantCuentasContables = new EBSMantenimientoCuentasComisiones(u, testCase);
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
		u.hardWait(2);
		try {
			assertEquals(mantCuentasContables.formaPagoComElement.getText(), formaPago);
			assertEquals(mantCuentasContables.metodoPagoComElement.getText(), metodoPago);
			assertEquals(mantCuentasContables.comisionElement.getText(), comision);
			assertEquals(mantCuentasContables.tipoComElement.getText(), tipo);
			assertEquals(mantCuentasContables.tasaElement.getText(), tasaIva);
			assertEquals(mantCuentasContables.fechaDesdeComElement.getText(), fechaDesde);
			assertEquals(mantCuentasContables.fechaHastaComElement.getText(), fechaHasta);
			assertEquals(mantCuentasContables.estadoComElement.getText(), estado);
			testCase.addScreenShotCurrentStep(u, " Campos Comisiones.");
			testCase.addTextEvidenceCurrentStep("Validacion de campos exitosa");
		} catch (AssertionError e) {
			testCase.addTextEvidenceCurrentStep("Validacion de campos fallida");
			System.out.println(e.toString());
		}

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
		return "ATC_FT_002_Comisiones_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
