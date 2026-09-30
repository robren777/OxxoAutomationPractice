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

/**Desc: Validar que al seleccionar una opcion de el filtro Plaza sea visible la opcion de Limpiar seleccion en el filtro
 * MTC_FT_068_Validar visibilidad opcion Limpiar Seleccion en filtro Plaza
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de CEDIS
 * @author Jorge Hernandez
 * @date Febrero 2025
 */
public class ATC_FT_068_ValidarVisibilidadOpcionLimpiarSeleccionFiltroPlaza extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_068_ValidarVisibilidadOpcionLimpiarSeleccionFiltroPlaza_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("Portal Web: Cometa");

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
        u.hardWait(3);
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
        addStep("Ingresar a Recepcion");
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
        addStep("Seleccionar el filtro de Plaza");
        boolean resultStep6 = pc.selectPlazaRecepcion();
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("Se visualizan las opciones de el filtro");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el filtro Plaza");
        }
        assertTrue(resultStep6, "No se encontro el filtro Plaza");

        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Seleccionar una opcion de el filtro");
        boolean resultStep7 = pc.selectOpcionPlazaRecepcion("1");
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("Se visualiza la opcion seleccionada en la lista de el filtro");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo seleccionar una opcion de el filtro");
        }
        assertTrue(resultStep7, "No se pudo seleccionar una opcion de el filtro");

        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Verificar que este visible la opcion de Limpiar seleccion en el filtro Plaza");
        boolean resultStep8 = pc.limpiarSeleccionPlaza("visibilidad");
        if(resultStep8){
            testCase.addTextEvidenceCurrentStep("Se visualiza la opcion de Limpiar Seleccion en el filtro");
        } else {
            testCase.addTextEvidenceCurrentStep("No se visualiza la opcion de seleccion en el filtro");
        }
        assertTrue(resultStep8, "No se visualiza la opcion de seleccion en el filtro");
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }

    @Override
    public String setTestDescription() {
        return "Validar que al seleccionar una opcion de el filtro Plaza sea visible la opcion de Limpiar seleccion en el filtro";
    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales vÃ¡lidas para el perfil de CEDIS";
    }

    @Override
    public String setTestDesigner() {
        return "Jorge Hernandez";
    }

    @Override
    public String setTestInstanceID() {
        return "";
    }

    @Override
    public void beforeTest() {

    }
}
