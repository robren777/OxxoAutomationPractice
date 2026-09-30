package EBS.cartaPorte;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.CargaDeArchivosTool;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSCargaDeArchivo;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidacionResponsabilidadesConOpcionCargaArchivo extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ValidacionResponsabilidadesConOpcionCargaArchivo_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String proceso = data.get("proceso");
		String filePath = data.get("filepath");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSCargaDeArchivo cargaArchivos = new EBSCargaDeArchivo(u, testCase);
		CargaDeArchivosTool archivosTool = new CargaDeArchivosTool(u, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		// PASO 1
		addStep("Entrar  a la responsabilidad de  MXOXXO-AP10XXX-USUARIO-FACTURA");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		paginaInicial.clickOnResponsabilidad(responsabilidad);

		// PASO 2
		addStep(" Ingresar a la opcion Carga de Archivos");
		paginaInicial.clickcargaarchivosr();

		// PASO 3
		addStep("Elegir el proceso Importa Facturas Traslado de Valores --> Elegir el archivo --> Click en el boton cargar");
		cargaArchivos.procesoElement.sendKeys(proceso);
		archivosTool.selectFile(filePath);
		cargaArchivos.btnCargarElement.click();
		testCase.addScreenShotCurrentStep(u, "Se muestra numero de concurrente");
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con la responsabilidad: " + "MXOXXO-AP10XXX-USUARIO-FACTURA " + "MXOXXO-APXXXXX-Super Usuario "
				+ "MXOXXO-APXXXXX-Super Usuario Consulta ";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validacion que las responsabilidades cuenten con la opcion de carga de archivo y  el proceso Importa Facturas Traslado de Valores."
				+ "Prerequisitos: Contar con la responsabilidad: " + "MXOXXO-AP10XXX-USUARIO-FACTURA "
				+ "MXOXXO-APXXXXX-Super Usuario " + "MXOXXO-APXXXXX-Super Usuario Consulta ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidacionResponsabilidadesConOpcionCargaArchivo_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
