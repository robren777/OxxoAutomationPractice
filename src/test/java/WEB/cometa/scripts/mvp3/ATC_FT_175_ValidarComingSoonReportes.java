package WEB.cometa.scripts.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;


/**Desc: Validar que se muestre el mensaje Coming soon en el menu de reportes directos y CEDIS para admin.         
 * MTC_FT_022 Validar mensaje coming soon en menu reportes directos y CEDIS admin
 * Pre-requisitos: Contar con credenciales validas para el perfil de Directos y CEDIS, Contar con perfil Admin
 * @author Isaac E. Garcia
 * @date Mayo 2025
 */
public class ATC_FT_175_ValidarComingSoonReportes extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_175_ValidarComingSoonReportes_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("Portal Cometa");

        /*
         * Variables
         ******************************************************************************************/
        String user = data.get("user");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String url = data.get("Url");
        
        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometa pc = new PortalCometa(u, testCase);

        /*
         *                                             PASO 1
         ******************************************************************************************/
        addStep("Ingresar a la liga");
        u.get(url);
        u.refresh();
        pc.esperarCarga(3);
        testCase.addScreenShotCurrentStep(u, "Pantalla cargada");
        pc.PrivatePortal();

        /*
         *                                             PASO 2
         ******************************************************************************************/
        addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
        boolean resultStep2 = pc.IniciarSesionNoToken(user, password);
        if(resultStep2){
            testCase.addTextEvidenceCurrentStep("Se logro iniciar sesion correctamente");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo iniciar sesion");
        }
        assertTrue(resultStep2, "No se pudo iniciar sesion");
        
        /*
         *                                             PASO 3
         ******************************************************************************************/
        addStep("Ingresar a Portal Cometa");
        boolean resultStep3 = pc.seleccionarPortalCometaQA();
        if(resultStep3){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar el portal Cometa");
        } else{
            testCase.addTextEvidenceCurrentStep("No es visible el elemento de Cometa");
        }
        assertTrue(resultStep3, "No es visible el elemento de Cometa");
        
        /*
         *                                             PASO 4
         ******************************************************************************************/
        addStep("Ingresar a Directos");
        boolean resultStep4 = pc.clicBotonCometaDirectos();
        if(resultStep4){
            testCase.addTextEvidenceCurrentStep("El portal muestra las opciones de Directos");
        } else {
            testCase.addTextEvidenceCurrentStep("No es visible el elemento Directos");
        }
        assertTrue(resultStep4, "No es visible el elemento Directos");


        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Ingresar a Reportes");
        boolean resultStep5 = pc.validarComingSoonDirectos();
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("El sistema muestra un mensaje de Coming Soon");
        } else {
            testCase.addTextEvidenceCurrentStep("No es alcanzable o visible el elemento de Coming soon!");
        }
        assertTrue(resultStep5, "No es alcanzable o visible el elemento de Coming soon!");


        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Dar clic en el boton inicio externo del menu superior");
        boolean resultStep6 = pc.regresarMenuInicial();
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("El portal muestra la pantalla principal del portal cometa");
        } else {
            testCase.addTextEvidenceCurrentStep("No es visible el boton de Inicio Externo");
        }
        assertTrue(resultStep6, "No es visible el boton de Inicio Externo");

        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Ingresar a CEDIS");
        boolean resultStep7 = pc.navegarCometaCedis();
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("El portal muestra las opciones de CEDIS");
        } else {
            testCase.addTextEvidenceCurrentStep("No es visible el elemento CEDIS");
        }
        assertTrue(resultStep7, "No es visible el elemento CEDIS");
        

        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Ingresar a Reportes");
        boolean resultStep8 = pc.validarComingSoonCEDIS();
        if(resultStep8){
            testCase.addTextEvidenceCurrentStep("El sistema muestra un mensaje de Coming Soon");
        } else {
            testCase.addTextEvidenceCurrentStep("No es alcanzable o visible el elemento de Coming soon!");
        }
        assertTrue(resultStep8, "No es alcanzable o visible el elemento de Coming soon!");

        
        u.getDriver().quit();
  
        
        
        
    }
    @Override
    public void beforeTest() {

    }

    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales validas para el perfil de Directos y CEDIS, Contar con perfil Admin";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se muestre el mensaje Coming soon en el menu de reportes directos y CEDIS para admin";
    }

    @Override
    public String setTestDesigner() {
        return "Isaac E. Garcia";
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}