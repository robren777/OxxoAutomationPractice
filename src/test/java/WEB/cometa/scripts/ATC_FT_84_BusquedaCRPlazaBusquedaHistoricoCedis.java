package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que en el filtro de CR Plaza exista un buscador que permita buscar la plaza que se quiera filtrar.
* MTC-FT-84 - BÃºsqueda CR Plaza en BÃºsqueda HistÃ³rico Oxxo OS.
* @author Jorge Hernandez
* @date Junio 2024
*/
public class ATC_FT_84_BusquedaCRPlazaBusquedaHistoricoCedis extends BaseExecution{

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_84_BusquedaCRPlazaBusquedaHistoricoCedis_test(HashMap<String, String> data) throws Exception{
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
        addStep("Ingresar a la pagina Recepcion de Transferencias y Devoluciones de CEDIS");
        boolean resultStep4 = pc.navegarCometaCedis();
        if(resultStep4){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Cedis");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Cedis");
        }
        assertTrue(resultStep4, "No se encontro el elemento Cedis");

        boolean resultStep4_1 = pc.cedisRecepciondTransferyDev();
        if(resultStep4_1){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Recepcion de Transferencias");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Recepcion de Transferencias");
        }
        assertTrue(resultStep4_1, "No se encontro el elemento Recepcion de Transferencias");

        /*
		 *                                             PASO 5
		 ******************************************************************************************/
        addStep("Seleccionar el filtro de plaza");
        boolean resultStep5 = pc.validarMenuCRPlaza();
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("Se valida el filtro plaza");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento");
        }
        assertTrue(resultStep5, "No se encontro el elemento");

        u.quit();
    }

    @Override
    public void beforeTest() {

    }

    @Override
    public String setPrerequisites() {
        return "1. Contar con credenciales de administrador para el inicio de sesiÃ³n.";
    }

    @Override
    public String setTestDescription() {
        return "Validar que en el filtro de CR Plaza exista un buscador que \n" +
                "permita buscar la plaza que se quiera filtrar.";
    }

    @Override
    public String setTestDesigner() {
        return "Jorge Hernandez Sanchez";
    }

    @Override
    public String setTestFullName() {
        return null;
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
