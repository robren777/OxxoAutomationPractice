package EBS.pantallaMigracionCRs10TIJ;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSExtraccion;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ColumnaEstadoLayoutcsvExtraccion extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ColumnaEstadoLayoutcsvExtraccion_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String compania = data.get("compania");
		String estadofinanciero = data.get("estadofinanciero");
		String crsuperior = data.get("crsuperior");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSExtraccion extraccion = new EBSExtraccion(u, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a EBS AVEBQA a la siguiente ruta: http://fcrebsqa.femcom.net:8011");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();

		// PASO 2
		addStep("Ingresar a la Responsabilidad MXOXXO-GLXXXXX-Super Usuario");
		paginaInicial.clickOnResponsabilidad(responsabilidad);

		// PASO 3
		addStep("Ir a CRs > Transferencia de CRs.");
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.transCRsElement.click();

		// PASO 4
		addStep("Se ingresan los datos de la Plaza 10TIJ para exportar el archivo .csv");
		extraccion.companiaElement.sendKeys(compania);
		extraccion.setEstadoFinanciero(estadofinanciero);
		u.hardWait(2);
		extraccion.crSuperiorElement.sendKeys(crsuperior);
		u.hardWait(2);

		// PASO 5
		addStep("Se ingresan los datos de la Plaza 10TIJ para exportar el archivo .csv");
		extraccion.btnExportarElement.click();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Acceso a la responsabilidad: \r\n" + "MXOXXO-GLXXXXX-Super Usuario";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el Layout .csv de Extraccion tenga renombrada la Columna VALIDO_BI por la etiqueta ESTADO."
				+ "Prerequisitos: Acceso a la responsabilidad: MXOXXO-GLXXXXX-Super Usuario";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ColumnaEstadoLayoutcsvExtraccion_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
