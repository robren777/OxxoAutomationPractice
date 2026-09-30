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

/**Desc: Validar que se muestre el formato corecto de FUE V1, muestre la ruta en vista preliminar CEDIS
 * MTC_FT_019 Validar formato correcto del FUE CEDIS V1 vista preliminar
 * Pre-requisitos: Contar con credenciales validas para el perfil de CEDIS
 * @author Isaac E. Garcia
 * @date Mayo 2025
 */
public class ATC_FT_172_ValidarFormatoCorrectoFUEV1VistaPre extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_172_ValidarFormatoCorrectoFUEV1VistaPre_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("Portal Cometa");

        /*
         * Variables
         ******************************************************************************************/
        String user = data.get("user");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String url = data.get("Url");
        String proveedor = data.get("Proveedor");

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
        addStep("Ingresar a CEDIS");
        boolean resultStep4 = pc.navegarCometaCedis();;
        if(resultStep4){
            testCase.addTextEvidenceCurrentStep("El portal muestra las opciones de CEDIS");
        } else {
            testCase.addTextEvidenceCurrentStep("No es visible el elemento de CEDIS");
        }
        assertTrue(resultStep4, "No es visible el elemento de CEDIS");


        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Ingresar a Recepcion");
        boolean resultStep5 = pc.cedisRecepciondTransferyDev();
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("El portal muestra la pantalla de recepcion");
        } else {
            testCase.addTextEvidenceCurrentStep("No es visible el elemento de boton de Recepcion");
        }
        assertTrue(resultStep5, "No es visible el elemento de boton de Recepcion");


        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Ingresar una plaza, rango de fechas y dar clic en boton consultar");
        boolean resultStep6 = pc.busquedaBusquedaHistorica(2);
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("El portal muestra el resultado de la busqueda");
        } else {
            testCase.addTextEvidenceCurrentStep("No es visible el elemento boton de consulta");
        }
        assertTrue(resultStep6, "No es visible el elemento boton de consulta");

        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Seleccionar un FUE V1");
        boolean resultStep7 = pc.seleccionarFUE_V1();
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("El portal muesta la vista preliminar del FUE");
        } else {
            testCase.addTextEvidenceCurrentStep("No se econtro un registro FUE V1");
        }
        assertTrue(resultStep7, "No se econtro un registro FUE V1");
        

        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Validar que se muestre el formato corecto de FUE V1 (muestre Ruta)");
        boolean resultStep8 = pc.validarFormatoFUE_V1();
        if(resultStep8){
            testCase.addTextEvidenceCurrentStep("La vista preliminar muestra la ruta en el FUE V1");
        } else {
            testCase.addTextEvidenceCurrentStep("El elemento Ruta no es visible");
        }
        assertTrue(resultStep8, "El elemento Ruta no es visible");

        
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
        return "Contar con credenciales validas para el perfil de CEDIS";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se muestre el formato corecto de FUE V1, muestre la ruta en vista preliminar CEDIS";
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