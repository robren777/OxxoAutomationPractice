package EBS.mvp1_FusionDeEstados_H002;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.ie.InternetExplorerOptions;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
/**
* DESCRIPCIÓN: Validar que se pueda dar de baja de manera correcta un Cluster mediante la opción del Checkbox
			   de estatus de la pantalla de configuración del value_set XXFC_CR_CEDIS_CLUSTERS.
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*     -ATC_FT_04_BajaDeClustermMedianteCheckboxEnValue_set_XXFC_CR_CEDIS_CLUSTERS
*  
* PREREQUISITOS:
*				 1.- Tener acceso a aplicacion de QA EBS Mexico. (AVEBQA)
*				 2.- Tener acceso a BD QA EBS Mexico. (AVEBQA)
*				 3.- Tener acceso a Responsabilidad MXOXXO - GLXXXXX - Administrador CEDIS Cluster.
*
* INSUMOS (datos o información que necesita estar preparada o generada para poder ejecutar el script automatizado):
*     -Nombre de Responsabilidad
*     -Nombre de CarpetaCarpeta
*  
*
* @author Gilberto Aguayo Adame
* @date 2/09/2024
*/
public class ATC_FT_04_BajaDeClustermMedianteCheckboxEnValue_set_XXFC_CR_CEDIS_CLUSTERS extends BaseExecution {
	
@Test(dataProvider = "data-provider")
	
	public void ATC_FT_04_BajaDeClustermMedianteCheckboxEnValue_set_XXFC_CR_CEDIS_CLUSTERS_Test(HashMap<String, String> data)
	          throws Exception {
	
	InternetExplorerOptions options = new InternetExplorerOptions();
	options.introduceFlakinessByIgnoringSecurityDomains();
	/*
	 * VARIABLES
	 */
	String ebsUrl = data.get("server");
	String userName = GlobalVariables.getGAGUAYOHexEbsUsr;
	String password = GlobalVariables.getGAGUAYOHexEbsPwd;
	String responsabilidad = data.get("responsabilidad");
	String carpeta = data.get("carpeta");
	String cluster = data.get("ClusterDesHabilitado");
	/*
	 * UTILERIAS
	 */
	SeleniumUtil u = new SeleniumUtil(new IETest(), true);
	LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
	EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
	SikuliUtil sk = new SikuliUtil();
	String pathImg = sk.PatternPath("EBS\\"+"EBS");
	Pattern pathimg = new Pattern();
	ArrayList<String> ListImage = new ArrayList<String>();
	HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
	ArrayList<Pattern> patterns = new ArrayList<Pattern>();
	File Fol = new File(pathImg);
	MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
	/*
	 * PASOS DEL CASO DE PRUEBA
	 */
	
	// PASO 1
	addStep("Ingresar a la liga  http://oxfapfinq1.femcom.net:8011/");
	logger.info("EBS URL: " + ebsUrl);
	System.out.println("EBS URL: " + ebsUrl);
	logger.info("User Name: " + userName);
	System.out.println("User Name: " + userName);
	testCase.addScreenShotCurrentStep("save");
	
	// PASO 2
	addStep("Ingresar a la aplicacion con el usuario y contrasena validos.");
	
	inicio.loginInternacional();
	p.popUpAuthenticacion();
	
	// PASO 3
	addStep("Ingresar a la responsabilidad MXOXXO - GLXXXXX - Administrador CEDIS Cluster.");
	
	p.CheckResponsabilidad();
	testCase.addScreenShotCurrentStep("save");
	//paginaInicial.clickOnResponsabilidad(responsabilidad);
	
	// PASO 4
	addStep("Ingresar al menu Valores de CR CEDIS CLUSTER");
	assertTrue(p.ValidarCarpeta(carpeta), "No se encontro el menu CR CEDIS CLUSTER");
	paginaInicial.clickOnResponsabilidad(carpeta);
	p.accepApp();
	/*archivosTool.selectFileCurrentProject(filePath);
	cargaArchivos.procesoElement.sendKeys(proceso);
	cargaArchivos.btnCargarElement.click();*/
	
	// PASO 5
	addStep("Se ingresa el nombre del value set XXFC_CR_CEDIS_CLUSTERS para consultar los datos que se encuentren configurados.");
	
	p.buscarCluster("04T2");
	testCase.addScreenShotCurrentStep("save");
	
	// PASO 6												
	addStep("Se inactiva un Cluster existente mediante el checkbox de habilitado\r\n "
			+ "en el value set XXFC_CR_CEDIS_CLUSTERS y se da clic al botón guardar.");
	
	
	p.deshabilitarClusterCheckBox();
	testCase.addScreenShotCurrentStep("save");
	p.cerrarVentana();
	assertTrue(p.ValidarCluster(cluster),"El Cluster Seleccionado no se encuentra deshabilitado");
	testCase.addScreenShotCurrentStep("save");
	p.cerrarApplicacion();
	u.getDriver().quit();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_04_BajaDeClustermMedianteCheckboxEnValue_set_XXFC_CR_CEDIS_CLUSTERS_Test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se pueda dar de baja de manera correcta un Cluster mediante la opción del Checkbox\r\n"
				+ " de estatus de la pantalla de configuración del value_set XXFC_CR_CEDIS_CLUSTERS.";
	}

	@Override
	public String setTestDesigner() {
		return "GilbertoAguayo";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.- Tener acceso a aplicacion de QA EBS Mexico. (AVEBQA)\r\n"
				+ "2.- Tener acceso a BD QA EBS Mexico. (AVEBQA)\r\n"
				+ "3.- Tener acceso a Responsabilidad MXOXXO - GLXXXXX - Administrador CEDIS Cluster.";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}
	

}
