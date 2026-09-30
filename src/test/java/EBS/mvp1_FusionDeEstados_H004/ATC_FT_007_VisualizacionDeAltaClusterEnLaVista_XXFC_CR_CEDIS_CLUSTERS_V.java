package EBS.mvp1_FusionDeEstados_H004;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSLogin;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;


/**
* DESCRIPCIÓN: Validar en la vista XXFC_CR_CEDIS_CLUSTERS_V de la BD de AVEBQA 
				que se muestre de manera correcta la información correspondiente al 
				Cluster dado de alta en el ATC_FT_03
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*     -ATC_FT_007_VisualizacionDeAltaClusterEnLaVista_XXFC_CR_CEDIS_CLUSTERS_V
*  
* PREREQUISITOS:
*				 1.- Tener acceso a aplicacion de QA EBS Mexico. (AVEBQA)
*				 2.- Tener acceso a BD QA EBS Mexico. (AVEBQA)
*				 3.- Tener acceso a Responsabilidad MXOXXO - GLXXXXX - Administrador CEDIS Cluster.
*
* INSUMOS (datos o información que necesita estar preparada o generada para poder ejecutar el script automatizado):
*     -Query de busqueda 1 EBS
*     -Query de busqueda 2 EBS
*     -Query de busqueda comprobador EBS
*     -Responsabilidades a las que se accederá en Data Provider
* @author Jesus Alberto Moreno Delgado
* @date 2/09/2024
*/
public class ATC_FT_007_VisualizacionDeAltaClusterEnLaVista_XXFC_CR_CEDIS_CLUSTERS_V extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_VisualizacionDeAltaClusterEnLaVista_XXFC_CR_CEDIS_CLUSTERS_V_test(
			HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Fusión de estado de resultados");

		String queryEBS1 = data.get("ebs1");
		String queryComprobador = data.get("query_comprobador");
		boolean flag;

		String url = data.get("server");
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");

		SeleniumUtil u = new SeleniumUtil(new IETest());
		EBSLogin login = new EBSLogin(u, testCase, url, GlobalVariables.getJesusHexEbsUsr,
				GlobalVariables.getJesusHexEbsPwd);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);

		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		// ******************************** PASO 1
		// **************************************/
		addStep("Ingresar a la liga http://oxfapfinq1.femcom.net:8011/");
		login.navigate(url);
		testCase.addScreenShotCurrentStep("Acceso al portal");

		// ******************************** PASO 2
		// **************************************/
		addStep("Ingresar a la aplicación con el usuario y contraseña válidos.");
		login.setCredentials();
		testCase.addScreenShotCurrentStep("Ingreso de credenciales");
		login.clickLogIn();

		// ******************************** PASO 3
		// **************************************/
		addStep("Ingresar a la responsabilidad MXOXXO - GLXXXXX - Administrador CEDIS Cluster.");
        p.CheckResponsabilidad();
		testCase.addScreenShotCurrentStep("save");
		//paginaInicial.clickOnResponsabilidad(responsabilidad);

		// ******************************** PASO 4
		// **************************************/
		addStep("Ingresar al menú Valores de CR CEDIS CLUSTER");
		paginaInicial.clickOnResponsabilidad(carpeta);
		p.accepApp();
		u.hardWait(30);
		testCase.addScreenShotCurrentStep("menú");

		// ******************************** PASO 5
		// **************************************/
		addStep("Se ingresa el nombre del value set XXFC_CR_CEDIS_CLUSTERS "
				+ "para consultar los datos que se encuentren configurados.");
		u.hardWait(30);
		testCase.addScreenShotCurrentStep("Paso final");
		p.clickImagen("find");

		// ******************************** PASO 6
		// **************************************/
		addStep("Se consulta la información del Cluster creado en el ATC-FT-03.");
		u.hardWait(10);
		p.clickImagen("test_01");
		u.hardWait(10);
		testCase.addBoldTextEvidenceCurrentStep("Cluster creado en ATC_003: 01T01");
		testCase.addScreenShotCurrentStep("lista_clusters");
		p.clickImagen("view_hierarchies");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("detalles_cluster");

		// ******************************** PASO 7
		// **************************************/
		addStep("Ingresar a la BD de EBS México (AVEBQA).");
		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryComprobador);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "El registro existe en la BD");

		// ******************************** PASO 8
		// **************************************/
		addStep("Validar que se pueda visualizar la información del "
				+ "Cluster creado del ATC-FT-03, en la vista XXFC_CR_CEDIS_CLUSTERS_V.");

		resultadoContenedor = dbEBS.executeQuery(queryEBS1);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			assertTrue(flag, "El registro existe en la BD");
		} else {
			testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
			assertTrue(flag, "El registro no aparece en la BD");

		}
		
		p.cerrarApplicacion();
		u.getDriver().quit();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1.- Tener acceso a aplicación de QA EBS México. (AVEBQA)\r\n"
				+ "2.- Tener acceso a BD QA EBS México. (AVEBQA)\r\n"
				+ "3.- Haber corrido el MTC 003 previamente\r\n"
				+ "4.- Tener acceso a Responsabilidad MXOXXO - GLXXXXX - Administrador CEDIS Cluster.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar en la vista XXFC_CR_CEDIS_CLUSTERS_V de la BD de AVEBQA "
				+ "que se muestre de manera correcta la información correspondiente al "
				+ "Cluster dado de alta en el ATC_FT_03";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_VisualizacionDeAltaClusterEnLaVista_XXFC_CR_CEDIS_CLUSTERS_V_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
