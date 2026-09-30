package EBS.mvp1_FusionDeEstados_H006;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
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
* DESCRIPCIÓN: Validar que se pueda consultar solamente el value_set XXFC_CR_CEDIS_CLUSTERS dentro del menú XXFC_GL_CEDIS_CLUSTER_ADMON_MENU " +
                "mediante Responsabilidad MXOXXO - GLXXXXX - Administrador CEDIS Clúster.
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*     -ATC_FT_002_Consult_set_XXFC_CR_CEDIS_CLUSTERS_menu_XXFC_GL_CEDIS_CLUSTER_ADMON_MENU_RespAdminCEDISCluster
*  
* PREREQUISITOS:
*				 1.- Tener acceso a aplicacion de QA EBS Mexico. (AVEBQA)
*				 2.- Tener acceso a BD QA EBS Mexico. (AVEBQA)
*				 3.- Tener acceso a Responsabilidad MXOXXO - GLXXXXX - Administrador CEDIS Cluster.
*
* INSUMOS (datos o información que necesita estar preparada o generada para poder ejecutar el script automatizado):
*     -Nombre de Responsabilidad
*     -Nombre de Carpeta
*    
*
* @author Gilberto Aguayo Adame
* @date 2/09/2024
*/
public class ATC_FT_002_Consult_set_XXFC_CR_CEDIS_CLUSTERS_menu_XXFC_GL_CEDIS_CLUSTER_ADMON_MENU_RespAdminCEDISCluster extends BaseExecution {

    private SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_002_Consult_set_XXFC_CR_CEDIS_CLUSTERS_menu_XXFC_GL_CEDIS_CLUSTER_ADMON_MENU_RespAdminCEDISCluster_test(HashMap<String, String>data) throws FindFailed {
        testCase.setProject_Name("Fusion de estado de resultados");

        String url = data.get("server");
        String responsabilidad = data.get("responsabilidad");
        String carpeta = data.get("carpeta");

        u = new SeleniumUtil(new IETest());
        EBSLogin login = new EBSLogin(u, testCase, url, GlobalVariables.getJzarateaHexEbsUsr, GlobalVariables.getJzarateaHexEbsPwd);
        EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);

        SikuliUtil sk = new SikuliUtil();
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
        Pattern pathimg = new Pattern();
        ArrayList<String> ListImage = new ArrayList<String>();
        HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        File Fol = new File(pathImg);
        MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

        // ******************************** PASO 1 **************************************/
        addStep("Ingresar a la liga http://oxfapfinq1.femcom.net:8011/");
        login.navigate(url);
        testCase.addScreenShotCurrentStep("Acceso al portal");

        // ******************************** PASO 2 **************************************/
        addStep("Ingresar a la aplicacion con el usuario y contrasena validos.");
        login.setCredentials();
        testCase.addScreenShotCurrentStep("Ingreso de credenciales");
        login.clickLogIn();
        p.popUpAuthenticacion();
        // ******************************** PASO 3 **************************************/
        addStep("Ingresar a la responsabilidad MXOXXO - GLXXXXX - Administrador CEDIS Cluster.");
        p.CheckResponsabilidad();
		testCase.addScreenShotCurrentStep("save");
		//paginaInicial.clickOnResponsabilidad(responsabilidad);

        // ******************************** PASO 4 **************************************/
        addStep("Validar que se muestre el menu Valores de CR CEDIS CLUSTER");
        testCase.addScreenShotCurrentStep("Menu");

        // ******************************** PASO 5 **************************************/
        addStep("Ingresar al menu Valores de CR CEDIS CLUSTER");
        paginaInicial.clickOnResponsabilidad(carpeta);
        p.accepApp();

        // ******************************** PASO 6 **************************************/
        addStep("Se valida que se pueda ingresar a consultar el nombre del value set XXFC_CR_CEDIS_CLUSTERS para visualizar los datos que se encuentren configurados.");
        p.navigateclusterConfigurados();
        testCase.addScreenShotCurrentStep("Value set");
		p.cerrarApplicacion();
		 u.getDriver().quit();
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_002_Consult_set_XXFC_CR_CEDIS_CLUSTERS_menu_XXFC_GL_CEDIS_CLUSTER_ADMON_MENU_RespAdminCEDISCluster_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se pueda consultar solamente el value_set XXFC_CR_CEDIS_CLUSTERS dentro del menú XXFC_GL_CEDIS_CLUSTER_ADMON_MENU " +
                "mediante Responsabilidad MXOXXO - GLXXXXX - Administrador CEDIS Clúster.";
    }

    @Override
    public String setTestDesigner() {
        return "Antonio Zarate - 1000072516";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "1.- Tener acceso a aplicación de QA EBS México. (AVEBQA)\n" +
                "2.- Tener acceso a Responsabilidad MXOXXO - GLXXXXX - Administrador CEDIS Clúster.";
    }

    @Override
    public void beforeTest() {
    }

    @AfterMethod
    public void afterMethod() {
        u.getDriver().quit();
    }
}
