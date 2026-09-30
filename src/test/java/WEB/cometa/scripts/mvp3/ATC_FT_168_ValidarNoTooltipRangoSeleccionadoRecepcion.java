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

/**Desc: Se valida que el tooltip Rango de fechas obligatorio no se muestre cuando el filtro rango de fecha ya esta seleccionado en la pantalla recepcion de transferencias
 * MTC_FT_013 Validar NO aparicion de tooltip cuando el rango de fecha este seleccionado pantalla recepcion CEDIS
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de CEDIS
 * @author Yair Lizarraga
 * @date Mayo 2025
 */
public class ATC_FT_168_ValidarNoTooltipRangoSeleccionadoRecepcion extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_168_ValidarNoTooltipRangoSeleccionadoRecepcion_test(HashMap<String, String> data) throws Exception {
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
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Directos");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
        }
        assertTrue(resultStep4, "No se encontro el elemento CEDIS");

        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Ingresar a Recepcion");
        boolean resultStep5 = pc.cedisRecepciondTransferyDev();
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Recepcion");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Recepcion");
        }
        assertTrue(resultStep5, "No se encontro el elemento Recepcion");
        
        
        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Seleccionar rango de fechas");
        boolean resultStep6 = pc.seleccionRangoFechas(3);
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y clickear el elemento");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento");
        }
        assertTrue(resultStep6, "No se encontro el elemento");
        
        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Validar que no se muestre le mensaje de tooltip");
        boolean resultStep7 = pc.validarNoTooltip();
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y clickear el elemento");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento");
        }
        assertTrue(resultStep7, "No se encontro el elemento");
        
        
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
        return "Contar con credenciales validas para el perfil de CEDIS ";
    }

    @Override
    public String setTestDescription() {
        return " Se valida que el tooltip Rango de fechas obligatorio no se muestre cuando el filtro rango de fecha ya esta seleccionado en la pantalla recepcion de transferencias";
    }

    @Override
    public String setTestDesigner() {
        return "Yair Lizarraga, HEXAWARE";
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