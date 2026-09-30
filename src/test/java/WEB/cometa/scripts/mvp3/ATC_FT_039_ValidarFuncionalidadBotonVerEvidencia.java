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

/**Desc: Validar dentro de la pantalla de FUE la funcionalidad del boton Ver evidencia
 * MTC_FT_039_Validar la funcionalidad del boton ver evidencia
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de CEDIS
 * @author Yair Lizarraga
 * @date Marzo 2025
 */
public class ATC_FT_039_ValidarFuncionalidadBotonVerEvidencia extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_039_ValidarFuncionalidadBotonVerEvidencia_test(HashMap<String, String> data) throws Exception {
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
        u.hardWait(3);
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
        addStep("Ingresar a CEDIS");
        boolean resultStep5 = pc.cedisRecepciondTransferyDev();
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Recepcion");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Recepcion");
        }
        assertTrue(resultStep5, "No se encontro el elemento Recepcion");
        
        addStep("Filtrar");
        boolean resultStep6 = pc.FiltradoCedisMeses(1);
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Directos");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
        }
        assertTrue(resultStep6, "No se encontro el elemento Recepcion");
        
        u.hardWait(15);
        
        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Dar clic a Ver en la columna FUE");
        boolean resultStep7 = pc.ClicVerFue();
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar FUE");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
        }
        assertTrue(resultStep7, "No se encontro el elemento FUE");
        
        
        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Validar la visualizacion del layout de ver evidencia (cambio de tabla a lista) cambio de Fue a una vista con ids seguido de las imagenes");
        boolean resultStep8 = pc.clicVerEvidencia();
        if(resultStep8){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar FUE");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
        }
        assertTrue(resultStep8, "No se encontro el elemento");

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
        return "Credenciales para el perfil CEDIS";
    }

    @Override
    public String setTestDescription() {
        return "Validar dentro de la pantalla de FUE la funcionalidad del boton Ver evidencia";
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