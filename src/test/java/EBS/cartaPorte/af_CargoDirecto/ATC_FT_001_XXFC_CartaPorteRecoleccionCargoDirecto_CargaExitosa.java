package EBS.cartaPorte.af_CargoDirecto;

import static org.testng.Assert.assertEquals;

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

public class ATC_FT_001_XXFC_CartaPorteRecoleccionCargoDirecto_CargaExitosa extends BaseExecution {
	
@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_XXFC_CartaPorteRecoleccionCargoDirecto_CargaExitosa_Test(HashMap<String, String> data)
	          throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String filePath = data.get("filePath");
		String proceso = data.get("proceso");
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		CargaDeArchivosTool archivosTool = new CargaDeArchivosTool(u, testCase);
		EBSCargaDeArchivo cargaArchivos = new EBSCargaDeArchivo(u, testCase);
		
		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52084165
		 */

		// PASO 1
		addStep("Entrar a EBS");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		
		// PASO 2
		addStep("Ir a la responsabilidad: MXOXXO-POXXXXX-PLANTILLA DE REQUISICIONES");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		
		// PASO 3
		addStep("Cargar el archivo generado por el Excel, y seleccionar Carta Porte - Cargo Directo Integrador");
		archivosTool.selectFileCurrentProject(filePath);
		cargaArchivos.procesoElement.sendKeys(proceso);
		cargaArchivos.btnCargarElement.click();
		
		// PASO 4
		addStep("Validar OUTPUT de los concurrentes");
		u.hardWait(5);
		cargaArchivos.solicitudConcurrenteAlertElement.getText();
		assertEquals(cargaArchivos.getNumeroConcurrente(), true);
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_XXFC_CartaPorteRecoleccionCargoDirecto_CargaExitosa_Test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que la carga sea Exitosa, no marque Advertencias"
				+ "Prerequisitos: ";
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
		return "";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

}