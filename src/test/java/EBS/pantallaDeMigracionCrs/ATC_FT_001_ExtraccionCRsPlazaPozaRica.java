package EBS.pantallaDeMigracionCrs;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSExtraccion;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ExtraccionCRsPlazaPozaRica extends BaseExecution {
	
@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_ExtraccionCRsPlazaPozaRica_Test(HashMap<String, String> data)
	          throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String compania = data.get("compania");
		String estadoFinanciero = data.get("estadoFinanciero");
		String crSuperior = data.get("crSuperior");
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSExtraccion extraccion = new EBSExtraccion(u, testCase);
		
		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52084165
		 */

		// PASO 1
		addStep("Ingresar a EBS AVEBQA a la siguiente ruta: http://fcrebsqa.femcom.net:8011");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		
		// PASO 2
		addStep("Ingresar a la Responsabilidad MXOXXO-GLXXXXX-Super Usuario.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		
		// PASO 3
		addStep("Ir a CRs > Transferencia de CRs.");
		paginaInicial.carpetaCRsElement.click();
		paginaInicial.transCRsElement.click();
		
		// PASO 4
		addStep("Se ingresan los datos de la Plaza de la cual se van a migrar los CRs hacia la nueva Mega Plaza. Se da clic en buscar.");
		extraccion.companiaElement.sendKeys(compania);
		extraccion.setEstadoFinanciero(estadoFinanciero);
		u.hardWait(2);
		extraccion.crSuperiorElement.sendKeys(crSuperior);
		u.hardWait(2);
		
		// PASO 5
		addStep("Se da clic en el botón exportar");
		extraccion.btnExportarElement.click();
		
		// PASO 6 
		addStep("Se valida la cantidad de registros que se generaron de la Plaza a migrar en el csv VS la informacion existente en BD pertenecientes a la plaza a migrar");
		//Falta Queries para validar
}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ExtraccionCRsPlazaPozaRica_Test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que muestre la cantidad de CRs que existe para la Plaza Poza Rica, los cuales seran transferidos hacia la Mega Plaza Veracruz."
				+ "Prerequisitos: Acceso a la responsabilidad: "
				+ "MXOXXO-GLXXXXX-Super Usuario, Acceso a BD de instancia AVEBQA";
	}

	@Override
	public String setTestDesigner() {
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Prerequisitos: Acceso a la responsabilidad: "
				+ "MXOXXO-GLXXXXX-Super Usuario, Acceso a BD de instancia AVEBQA";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

}