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

public class ATC_FT_008_InactivacionmasivadeCRs extends BaseExecution {
	
@Test(dataProvider = "data-provider")
	
	public void ATC_FT_008_InactivacionmasivadeCRs_Test(HashMap<String, String> data)
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
		String fecha = data.get("fechabi");
		String archivo = data.get("archivo");
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSExtraccion extraccion = new EBSExtraccion(u, testCase);
		
		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: PantallaDeMigracionCrs
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
		testCase.addScreenShotCurrentStep("e ingresan los datos de la Plaza de la cual se van a migrar los CRs hacia la nueva Mega Plaza. Se da clic en buscar.");
		extraccion.clickBotonBuscar();
		// PASO 5
		addStep("Se da clic en el boton siguiente");
		extraccion.clickBotonSiguiente();
		extraccion.SetFecha(fecha);
		extraccion.seleccionarArchivo(extraccion.seleccionarArchivoSinSendkeys(archivo));
		// PASO 6
		addStep("Se da clic en el boton valida archivo");
		testCase.addScreenShotCurrentStep("Se da clic en el boton valida archivo");
		extraccion.clickValidarArchivo();
		// PASO 7
		addStep("Se da clic en el boton siguiente");
		extraccion.clickBotonSiguiente();
		testCase.addScreenShotCurrentStep("Se da clic en el boton siguiente");
		// PASO 8
	    addStep("Se da clic en el boton confirmar");
	    extraccion.clickBotonConfirmar();
		testCase.addScreenShotCurrentStep("Se da clic en el boton confirmar");
		// PASO 9
	    addStep("Se valida en BD que los registros de los CRs que se tomaron para generar el archivo, tengan estatus de Inactivo.");
	    //Falta Queries para validar en el caso
		u.close();
}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_InactivacionmasivadeCRs_Test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se realice la inactivacion masiva de CRs de la Plaza anterior."
				+ "Nota: se modifico el flujo del caso porque el archivo que genera de excel se tiene que modificar manualmente"
				+ "Prerequisitos: Acceso a la responsabilidad: "
				+ "MXOXXO-GLXXXXX-Super Usuario, Acceso a BD de instancia AVEBQA";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
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