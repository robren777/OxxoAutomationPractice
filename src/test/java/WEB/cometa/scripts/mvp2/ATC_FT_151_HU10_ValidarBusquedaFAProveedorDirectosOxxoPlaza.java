package WEB.cometa.scripts.mvp2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que se puede realizar bÃºsquedas solo por el atributo de FA
 * MTC-FT-151-HU10-Validar que como usuario DIRECTOS OXXO PLAZA me permite realizar bÃºsqueda de FA al tener un proveedor seleccionado
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n del perfil DIRECTOS - OXXO PLAZA
 * @author Jorge Hernandez
 * @date Octubre 2024
 */
public class ATC_FT_151_HU10_ValidarBusquedaFAProveedorDirectosOxxoPlaza extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_151_HU10_ValidarBusquedaFAProveedorDirectosOxxoPlaza_test(HashMap<String, String> data){
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
        addStep("Ingresar a Comprobantes de compra y Resumen");
        boolean resultStep4 = pc.clicBotonCometaDirectos();
        if(resultStep4){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Directos");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
        }
        assertTrue(resultStep4, "No se encontro el elemento Directos");

        boolean resultStep4_1 = pc.ClicBotonComprobantesDeCompra();
        if(resultStep4_1){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Comprobantes de Compra");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Comprobantes de Compra");
        }
        assertTrue(resultStep4_1,"No se encontro el elemento Comprobantes de Compra");

        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Seleccionar Proveedor");
        boolean resultStep5 = pc.seleccionProveedor();
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar el proveedor");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo seleccionar el proveedor");
        }
        assertTrue(resultStep5, "No se pudo seleccionar el proveedor");

        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Seleccionar FA y realizar busqueda");
        boolean resultStep6 = pc.seleccionarFA(6);
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar la FA y fecha para busqueda");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo seleccionar la FA y/o fecha para busqueda");
        }
        assertTrue(resultStep6, "No se pudo seleccionar la FA y/o fecha para busqueda");

        /*
         *                                             PASO 7
         ******************************************************************************************/
        boolean resultStep7 = pc.consultarComprobantes();
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("Se realizo la busqueda");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo realizar la busqueda");
        }
        assertTrue(resultStep7, "No se pudo realizar la busqueda");

        u.getDriver().quit();
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se puede realizar bÃºsquedas por FA seleccionando Proveedor";
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
    public String setPrerequisites() {
        return "Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n del perfil DIRECTOS - OXXO PLAZA";
    }

    @Override
    public void beforeTest() {

    }

    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }
}
