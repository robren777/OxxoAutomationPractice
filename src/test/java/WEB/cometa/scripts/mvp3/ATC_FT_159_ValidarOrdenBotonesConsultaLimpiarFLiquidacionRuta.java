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

/**Desc: Validar que el orden de los botones Consulta y Limpiar Filtros sea Limpiar Filtros y despues Consultar
 * MTC_FT_159_Validar orden de los botones Consulta y Limpiar Filtros Liquidacion de Ruta
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de CEDIS
 * @author Jorge Hernandez
 * @date Marzo 2025
 */
public class ATC_FT_159_ValidarOrdenBotonesConsultaLimpiarFLiquidacionRuta extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_159_ValidarOrdenBotonesConsultaLimpiarFLiquidacionRuta_test(HashMap<String, String> data) throws Exception {
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
            testCase.addTextEvidenceCurrentStep("Se inicia sesion correctamente y se visualiza la pagina de access control");
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
            testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla principal Cometa");
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
            testCase.addTextEvidenceCurrentStep("Se visualizan las opciones de Cedis");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Cedis");
        }
        assertTrue(resultStep4, "No se encontro el elemento Cedis");


        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Ingresar a 'RecepciÃ³n de Transferencias y Devoluciones'");
        boolean resultStep5 = pc.cedisRecepciondTransferyDev();
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla de Recepcion");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Recepcion");
        }
        assertTrue(resultStep5, "No se encontro el elemento Recepcion");

        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Dar clic a boton Liquidacion de Ruta");
        boolean resultStep6 = pc.clickBotonLiquidacionRuta();
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla de Liquidacion de Ruta");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el boton de Liquidacion de Ruta");
        }
        assertTrue(resultStep6, "No se encontro el boton de Liquidacion de Ruta");


        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Validar orden de botones Limpiar Filtros y Consulta");
        boolean resultStep7 = pc.validarOrdenBotonesLiquidacionRuta();
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("La pagina muestra que los botones Limpiar filtros y Consulta se muestran en el orden deseado, Limpiar Filtros despues Consultar");
        } else {
            testCase.addTextEvidenceCurrentStep("Los botones no se encuentran en el orden deseado");
        }
        assertTrue(resultStep7, "Los botones no se encuentran en el orden deseado");

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
        return "Contar con credenciales vÃ¡lidas para el perfil de CEDIS";
    }

    @Override
    public String setTestDescription() {
        return "Validar que el orden de los botones Consulta y Limpiar Filtros sea Limpiar Filtros y despues Consultar";
    }

    @Override
    public String setTestDesigner() {
        return "Jorge Hernandez";
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