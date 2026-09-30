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

/**Desc: Validar que al pasar el cursor por ensima de los botones de Consultar y Limpiar Filtros
 *MTC_FT_033_Validar menu cerrar sesion
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de CEDIS
 * @author Yair Lizarraga
 * @date Marzo 2025
 */
public class ATC_FT_033_ValidarMenuCerrarSesion extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_033_ValidarMenuCerrarSesion_test(HashMap<String, String> data) throws Exception {
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
        addStep("Ingresar a Portal Cometa");
        boolean resultStep4 = pc.validarUser();
        if(resultStep4){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar el elemento");
        } else{
            testCase.addTextEvidenceCurrentStep("No es visible el elemento de Cometa");
        }
        assertTrue(resultStep4, "No es visible el elemento de Cometa");
        
        

    }
    @Override
    public void beforeTest() {

    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales validas para el perfil de DIRECTOS";
    }
    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }
    @Override
    public String setTestDescription() {
        return "Validar que al pasar el cursor por ensima de los botones de Consultar y Limpiar Filtros";
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