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

/**Desc: Validar que solo se muestre el nombre de la tienda en la columna Nombre de Tienda en grid recepcion de transferencias
 * MTC_FT_020 Validar nombre tienda solamente tenga el nombre tienda en CEDIS grid
 * Pre-requisitos: Contar con credenciales validas para el perfil de CEDIS
 * @author Isaac E. Garcia
 * @date Mayo 2025
 */
public class ATC_FT_173_ValidarNombreDeTiendaCEDISGrid extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_173_ValidarNombreDeTiendaCEDISGrid_test(HashMap<String, String> data) throws Exception {
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
        addStep("Ingresar a CEDIS");
        boolean resultStep4 = pc.navegarCometaCedis();
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
        addStep("Validar que solo se muestre el nombre de la tienda en la columna nombre de tienda");
        boolean resultStep7 = pc.validarColumnaNombreTienda();
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("El portal muestra en el grid el nombre de tienda correctamente");
        } else {
            testCase.addTextEvidenceCurrentStep("El elemento de Nombre de tienda no es visible");
        }
        assertTrue(resultStep7, "El elemento de Nombre de tienda no es visible");
        

     
        
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
        return "Validar que solo se muestre el nombre de la tienda en la columna Nombre de Tienda en grid recepcion de transferencias";
    }

    @Override
    public String setTestDesigner() {
        return "Isaac E Garcia, HEXAWARE";
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